package site.misaka.script.adapter;

import cn.nukkit.plugin.Plugin;
import site.misaka.engine.EngineAdapter;

import java.util.Locale;

public class StringUtils extends AbstractUtils {
	public StringUtils(Plugin plugin, String scriptName, EngineAdapter adapter) {
		super(plugin, scriptName, adapter);
	}

	public String format(String fmt, Object... args) {
		return String.format(fmt, args);
	}

	public String replace(String needle, String replacement, String ctx) {
		return ctx.replace(needle, replacement);
	}

	public String substring(String ctx, int start, int end) {
		return ctx.substring(start, end);
	}

	public String toLower(String ctx) {
		return ctx.toLowerCase(Locale.ROOT);
	}

	public String toUpper(String ctx) {
		return ctx.toUpperCase(Locale.ROOT);
	}

	public int length(String ctx) {
		return ctx.length();
	}
}
