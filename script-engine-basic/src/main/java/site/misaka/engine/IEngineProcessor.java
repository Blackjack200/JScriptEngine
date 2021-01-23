package site.misaka.engine;

import java.util.List;
import java.util.Map;

public abstract class IEngineProcessor<T> {
	public abstract T process(String code, Map<String, Object> variables);

	public abstract T process(String code, Map<String, Object> variables, Processor processor);

	public abstract void invokeALL(String name, Object... args);

	public abstract List<String> extensions();
}
