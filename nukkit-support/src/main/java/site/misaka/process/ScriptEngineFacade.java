package site.misaka.process;

import cn.nukkit.Server;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import lombok.Getter;
import site.misaka.NukkitScriptLoader;
import site.misaka.engine.EngineAdapter;
import site.misaka.engine.nashorn.NashornAdapter;
import site.misaka.process.listener.BasicEventListener;
import site.misaka.script.ScriptLogger;
import site.misaka.script.adapter.ConfigUtils;
import site.misaka.script.adapter.DataStructureUtils;
import site.misaka.script.adapter.FileUtils;
import site.misaka.script.adapter.ParseUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class ScriptEngineFacade {
	@Getter
	private static final ArrayList<EngineAdapter> adapters = new ArrayList<>();
	private static boolean init = false;

	public static void init() {
		if (!init) {
			init = true;
			adapters.add(new NashornAdapter(new NashornScriptEngineFactory()));
			NukkitScriptLoader.getInstance().getServer().getPluginManager().registerEvents(new BasicEventListener(), NukkitScriptLoader.getInstance());
		}
	}

	public static Map<String, Object> properties(String scriptName) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put("logger", new ScriptLogger(scriptName));
		map.put("loader", NukkitScriptLoader.getInstance());
		map.put("server", Server.getInstance());
		map.put("ds", new DataStructureUtils(NukkitScriptLoader.getInstance(), scriptName));
		map.put("config", new ConfigUtils(NukkitScriptLoader.getInstance(), scriptName));
		map.put("file", new FileUtils(NukkitScriptLoader.getInstance(), scriptName));
		map.put("parse", new ParseUtils(NukkitScriptLoader.getInstance(), scriptName));
		map.put("__NAME__", scriptName);
		return map;
	}

	public static void invokeEvent(cn.nukkit.event.Event ev, String funcName) {
		for (EngineAdapter adapter : adapters) {
			adapter.invoke(funcName, ev);
		}
	}

	public static void invokeEvent(cn.nukkit.event.Event ev) {
		invokeEvent(ev, ev.getClass().getSimpleName());
	}
}
