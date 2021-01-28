package site.misaka.jpython;

import org.python.core.Py;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import site.misaka.engine.EngineAdapter;

public class JythonAdapter extends EngineAdapter<PythonInterpreter> {
	public JythonAdapter(PythonInterpreter engine) {
		super(engine);
	}

	@Override
	public void invoke(String name, Object... args) {
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

	@Override
	public void put(String name, Object val) {
		this.engine.set(name, val);
	}

	@Override
	public Object getValue(String key) {
		return this.engine.get(key);
	}

	@Override
	public boolean load(String code) {
		this.engine.exec(code);
		return true;
	}

	@Override
	public PythonInterpreter get() {
		return this.engine;
	}
}
