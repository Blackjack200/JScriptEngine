package site.misaka.process;

import lombok.Getter;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import site.misaka.Loader;
import site.misaka.engine.IEngineProcessor;
import site.misaka.nashorn.NashornProcessor;
import site.misaka.process.listener.BasicEventListener;

import java.util.ArrayList;

public class ScriptEngineFacade {
	@Getter
	private static final ArrayList<IEngineProcessor> adapters = new ArrayList<>();
	private static boolean init = false;

	public static void init() {
		if (!init) {
			init = true;
			adapters.add(new NashornProcessor(new NashornScriptEngineFactory()));
			Loader.getInstance().getServer().getPluginManager().registerEvents(new BasicEventListener(), Loader.getInstance());
		}
	}

	public static void invokeALL(String funcName, Object... args) {
		for (IEngineProcessor adapter : adapters) {
			adapter.invokeALL(funcName, args);
		}
	}

	public static void invokeEvent(cn.nukkit.event.Event ev, String funcName) {
		ScriptEngineFacade.invokeALL(funcName, ev);
	}

	public static void invokeEvent(cn.nukkit.event.Event ev) {
		invokeEvent(ev, ev.getClass().getSimpleName());
	}
}
