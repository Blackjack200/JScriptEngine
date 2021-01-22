package site.misaka.engine;

import java.util.List;
import java.util.Map;

public abstract class EngineAdapter {
	abstract public void load(String code, Map<String, Object> variables);

	abstract public void invoke(String name, Object... args);

	abstract public List<String> extensions();
}