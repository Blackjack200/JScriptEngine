package site.misaka.script.adapter;

import cn.nukkit.plugin.Plugin;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class DataStructureUtils extends AbstractUtils {
	public DataStructureUtils(Plugin plugin, String scriptName) {
		super(plugin, scriptName);
	}

	public LinkedHashMap<Object, Object> map() {
		return new LinkedHashMap<>();
	}

	public <KT, VT> LinkedHashMap<KT, VT> map(KT key, VT value) {
		return new LinkedHashMap<>();
	}

	public LinkedList<Object> list() {
		return new LinkedList<>();
	}

	public <T> LinkedList<T> list(T element) {
		return new LinkedList<>();
	}
}
