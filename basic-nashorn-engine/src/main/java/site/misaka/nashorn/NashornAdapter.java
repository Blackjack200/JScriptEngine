package site.misaka.nashorn;


import lombok.SneakyThrows;
import jdk.nashorn.api.scripting.NashornScriptEngine;
import site.misaka.engine.PSREngineAdapter;

import javax.script.ScriptException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class NashornAdapter extends PSREngineAdapter<NashornScriptEngine> {
	private static Class<?> reflect;

	static {
		try {
			reflect = Class.forName("jdk.internal.dynalink.beans.StaticClass");
		} catch (Throwable ignore) {
		}

		try {
			reflect = Class.forName("jdk.dynalink.beans.StaticClass");
		} catch (Throwable ignore) {
		}
	}

	public NashornAdapter(NashornScriptEngine engine) {
		super(engine);
		if (reflect != null) {
			this.engine.put("extern_name", (BiConsumer<String, String>) this::extern_name);
			this.engine.put("extern", (Consumer<String>) this::extern);
		}
	}

	@SneakyThrows
	private Object convert(Class<?> clazz) {
		return reflect.getDeclaredMethod("forClass", Class.class).invoke(null, clazz);
	}

	@Override
	public void invoke(String name, Object... args) {
		try {
			this.engine.invokeFunction(name, args);
		} catch (ScriptException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException ignore) {

		}
	}

	private void extern_impl(Class<?> clazz, String bind) throws ScriptException {
		if (this.engine.get(bind) != null) {
			throw new ScriptException("Global variable " + bind + " is already defined");
		}
		this.engine.put(bind, this.convert(clazz));
	}

	@SneakyThrows
	private void extern(String className) {
		final Class clazz = Class.forName(className);
		this.extern_impl(clazz, clazz.getSimpleName());
	}

	@SneakyThrows
	private void extern_name(String className, String bind) {
		final Class clazz = Class.forName(className);
		this.extern_impl(clazz, bind);
	}
}
