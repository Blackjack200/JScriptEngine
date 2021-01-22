package site.misaka;

import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import lombok.Getter;
import org.codehaus.groovy.jsr223.GroovyScriptEngineFactory;
import org.jruby.embed.jsr223.JRubyEngineFactory;
import org.luaj.vm2.script.LuaScriptEngineFactory;
import site.misaka.groovy.GroovyAdapter;
import site.misaka.jpython.JPythonAdapter;
import site.misaka.jruby.JRubyAdapter;
import site.misaka.luaj.LuaJAdapter;
import site.misaka.process.ScriptEngineFacade;
import site.misaka.script.ScriptLoader;

import java.io.IOException;
import java.util.Map;

public class NukkitScriptLoader extends PluginBase {
	@Getter
	private static NukkitScriptLoader instance = null;

	@Override
	public void onEnable() {
		instance = this;
		ScriptEngineFacade.init();
		Map<String, Plugin> plugin = this.getServer().getPluginManager().getPlugins();
		//硬编码快乐
		if (plugin.containsKey("NS_JPythonSupport")) {
			ScriptEngineFacade.getAdapters().add(new JPythonAdapter());
			this.getLogger().info("开启Python支持");
		}

		if (plugin.containsKey("NS_GroovySupport")) {
			ScriptEngineFacade.getAdapters().add(new GroovyAdapter(new GroovyScriptEngineFactory()));
			this.getLogger().info("开启Groovy支持");
		}

		if (plugin.containsKey("NS_JRubySupport")) {
			ScriptEngineFacade.getAdapters().add(new JRubyAdapter(new JRubyEngineFactory()));
			this.getLogger().info("开启Ruby支持");
		}

		if (plugin.containsKey("NS_LuaJSupport")) {
			ScriptEngineFacade.getAdapters().add(new LuaJAdapter(new LuaScriptEngineFactory()));
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