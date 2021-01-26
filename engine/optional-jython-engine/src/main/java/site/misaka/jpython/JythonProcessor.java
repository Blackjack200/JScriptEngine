package site.misaka.jpython;

import org.python.util.PythonInterpreter;
import site.misaka.engine.IEngineProcessor;
import site.misaka.engine.Processor;

import java.util.*;

public class JythonProcessor extends IEngineProcessor<JythonAdapter> {
	protected Vector<JythonAdapter> engineList = new Vector<>();
	protected List<String> extensions;

	public JythonProcessor() {
		ArrayList<String> extensions = new ArrayList<>();
		extensions.add("py");
		this.extensions = Collections.unmodifiableList(extensions);
	}

	@Override
	public List<String> extensions() {
		return this.extensions;
	}

	@Override
	public JythonAdapter process(String code, Map<String, Object> variables) {
		return this.process(code, variables, null);
	}

	@Override
	public JythonAdapter process(String code, Map<String, Object> variables, Processor processor) {
		JythonAdapter adapter = new JythonAdapter(new PythonInterpreter());
		for (Map.Entry<String, Object> entry : variables.entrySet()) {
			adapter.put(entry.getKey(), entry.getValue());
		}

		if (processor != null) {
			processor.preprocess(adapter);
		}

		if (adapter.load(code)) {
			this.engineList.add(adapter);
			return adapter;
		}
		return null;
	}

	@Override
	public void invokeALL(String name, Object... args) {
		for (JythonAdapter engine : this.engineList) {
			engine.invoke(name, args);
		}
	}
}