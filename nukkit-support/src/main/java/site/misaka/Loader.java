package site.misaka;

import cn.nukkit.entity.Entity;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import lombok.Getter;
import org.codehaus.groovy.jsr223.GroovyScriptEngineFactory;
import org.jruby.embed.jsr223.JRubyEngineFactory;
import org.luaj.vm2.script.LuaScriptEngineFactory;
import site.misaka.groovy.GroovyProcessor;
import site.misaka.jpython.JythonProcessor;
import site.misaka.jruby.JRubyProcessor;
import site.misaka.luaj.LuaJProcessor;
import site.misaka.script.EngineFacade;
import site.misaka.script.ScriptLoader;
import site.misaka.script.object.slapper.HumanSlapper;
import site.misaka.utils.MetricsLite;

import java.io.IOException;
import java.util.Map;

public class Loader extends PluginBase {
	@Getter
	private static Loader instance = null;

	@Override
	public void onEnable() {
		instance = this;
		EngineFacade.init();
		Map<String, Plugin> plugin = this.getServer().getPluginManager().getPlugins();

		//Hardcoded
		if (plugin.containsKey("NS_JythonSupport")) {
			EngineFacade.getAdapters().add(new JythonProcessor());
			this.getLogger().info("Enable Python support");
		}

		if (plugin.containsKey("NS_GroovySupport")) {
			EngineFacade.getAdapters().add(new GroovyProcessor(new GroovyScriptEngineFactory()));
			this.getLogger().info("Enable Groovy support");
		}

		if (plugin.containsKey("NS_JRubySupport")) {
			EngineFacade.getAdapters().add(new JRubyProcessor(new JRubyEngineFactory()));
			this.getLogger().info("Enable Ruby support");
		}

		if (plugin.containsKey("NS_LuaJSupport")) {
			EngineFacade.getAdapters().add(new LuaJProcessor(new LuaScriptEngineFactory()));
			this.getLogger().info("Enable Lua support");
		}

		this.getDataFolder().mkdirs();

		MetricsLite lite = new MetricsLite(this, 10110);

		Entity.registerEntity("JSE_NPC", HumanSlapper.class);
		System.setProperty("nashorn.args", "--language=es6");

		try {
			ScriptLoader.scanLoader(this.getDataFolder().toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDisable() {
		EngineFacade.invokeALL("finalize");
	}
}