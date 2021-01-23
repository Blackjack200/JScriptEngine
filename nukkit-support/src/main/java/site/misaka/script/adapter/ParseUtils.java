package site.misaka.script.adapter;

import cn.nukkit.plugin.Plugin;
import com.alibaba.fastjson.JSON;
import org.yaml.snakeyaml.Yaml;
import site.misaka.engine.EngineAdapter;

public class ParseUtils extends AbstractUtils {
	public ParseUtils(Plugin plugin, String scriptName, EngineAdapter adapter) {
		super(plugin, scriptName, adapter);
	}

	public Object parseYAML(String content) {
		Yaml yaml = new Yaml();
		return yaml.load(content);
	}

	public String emitYAML(Object data) {
		Yaml yaml = new Yaml();
		return yaml.dump(data);
	}

	public Object parseJSON(String content) {
		return JSON.parse(content);
	}

	public String emitJSON(Object data) {
		return JSON.toJSONString(data);
	}
}
