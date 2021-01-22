package site.misaka.jpython;

import org.python.core.Py;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import site.misaka.engine.EngineAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class JPythonAdapter extends EngineAdapter {
	protected ArrayList<PythonInterpreter> engineList = new ArrayList<>();
	protected List<String> extensions;

	public JPythonAdapter() {
		ArrayList<String> extensions = new ArrayList<>();
		extensions.add("py");
		this.extensions = Collections.unmodifiableList(extensions);
	}

	@Override
	public List<String> extensions() {
		return this.extensions;
	}

	@Override
	public void load(String code, Map<String, Object> variables) {
		PythonInterpreter interpreter = new PythonInterpreter();
		for (Map.Entry<String, Object> entry : variables.entrySet()) {
			interpreter.set(entry.getKey(), entry.getValue());
		}

		interpreter.exec(code);
		this.engineList.add(interpreter);
	}

	@Override
	public void invoke(String name, Object... args) {
		for (PythonInterpreter engine : this.engineList) {
			try {
				PyFunction func = engine.get(name, PyFunction.class);
				if (func != null) {
					PyObject[] pyArgs = new PyObject[args.length];

					for (int i = 0; i < pyArgs.length; i++) {
						pyArgs[i] = Py.java2py(args[i]);
					}
					func.__call__(pyArgs);
				}
			} catch (Throwable ignore) {

			}
		}
	}
}