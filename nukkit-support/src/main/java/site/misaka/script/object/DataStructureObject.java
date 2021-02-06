package site.misaka.script.object;

import cn.nukkit.plugin.Plugin;
import site.misaka.engine.EngineAdapter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class DataStructureObject extends AbstractObject {
    public DataStructureObject(Plugin plugin, String scriptName, EngineAdapter adapter) {
        super(plugin, scriptName, adapter);
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

    public ConcurrentHashMap<Object, Object> concurrentMap() {
        return new ConcurrentHashMap<>();
    }

    public <KT, VT> ConcurrentHashMap<KT, VT> concurrentMap(KT key, VT value) {
        return new ConcurrentHashMap<>();
    }

    public Vector<Object> vector() {
        return new Vector<>();
    }

    public <T> Vector<T> vector(T element) {
        return new Vector<>();
    }
}
