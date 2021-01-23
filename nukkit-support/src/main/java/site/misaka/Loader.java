package site.misaka;

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
import site.misaka.process.ScriptEngineFacade;
import site.misaka.script.ScriptLoader;

import java.io.IOException;
import java.util.Map;

public class Loader extends PluginBase {
	@Getter
	private static Loader instance = null;

	@Override
	public void onEnable() {
		instance = this;
		ScriptEngineFacade.init();
		Map<String, Plugin> plugin = this.getServer().getPluginManager().getPlugins();
		//硬编码快乐
		if (plugin.containsKey("NS_JythonSupport")) {
			ScriptEngineFacade.getAdapters().add(new JythonProcessor());
			this.getLogger().info("开启Python支持");
		}

		if (plugin.containsKey("NS_GroovySupport")) {
			ScriptEngineFacade.getAdapters().add(new GroovyProcessor(new GroovyScriptEngineFactory()));
			this.getLogger().info("开启Groovy支持");
		}

		if (plugin.containsKey("NS_JRubySupport")) {
			ScriptEngineFacade.getAdapters().add(new JRubyProcessor(new JRubyEngineFactory()));
			this.getLogger().info("开启Ruby支持");
		}

		if (plugin.containsKey("NS_LuaJSupport")) {
			ScriptEngineFacade.getAdapters().add(new LuaJProcessor(new LuaScriptEngineFactory()));
			this.getLogger().info("开启Lua支持");
		}

		this.getDataFolder().mkdirs();

		try {
			ScriptLoader.scanLoader(this.getDataFolder().toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}