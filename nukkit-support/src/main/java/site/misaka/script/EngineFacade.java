package site.misaka.script;

import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import lombok.Getter;
import site.misaka.Loader;
import site.misaka.engine.IEngineProcessor;
import site.misaka.nashorn.NashornProcessor;

import java.util.ArrayList;
import java.util.Vector;

public class EngineFacade {
	@Getter
	private static final Vector<IEngineProcessor> adapters = new Vector<>();
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
		EngineFacade.invokeALL(funcName, ev);
	}

	public static void invokeEvent(cn.nukkit.event.Event ev) {
		invokeEvent(ev, ev.getClass().getSimpleName());
	}
}
