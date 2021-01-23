package site.misaka.process;

import cn.nukkit.Server;
import lombok.Getter;
import site.misaka.Loader;
import site.misaka.engine.EngineAdapter;
import site.misaka.script.ScriptLogger;
import site.misaka.script.adapter.CallBackCommand;
import site.misaka.script.adapter.builder.Command;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class UnionData {

	//TODO
	@Getter
	private static final AtomicInteger count = new AtomicInteger(0);
	@Getter
	private static final ConcurrentHashMap<String, EngineAdapter> scripts = new ConcurrentHashMap<>();
	@Getter
	private static final ConcurrentHashMap<Command, CallBackCommand> commandMap = new ConcurrentHashMap<>();

	public static Map<String, Object> getProperties(String scriptName) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put("logger", new ScriptLogger(scriptName));
		map.put("loader", Loader.getInstance());
		map.put("server", Server.getInstance());

		//Magic Variable
		map.put("__NAME__", scriptName);
		//Need Discussion
		//map.put("__ADAPTER__", adapter);
		return map;
	}
}
