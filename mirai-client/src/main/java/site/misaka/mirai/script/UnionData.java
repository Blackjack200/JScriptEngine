package site.misaka.mirai.script;

import lombok.Getter;
import site.misaka.engine.EngineAdapter;
import site.misaka.mirai.BotServer;

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

    public static Map<String, Object> getProperties(String scriptName) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("server", BotServer.getInstance());
        map.put("logger", BotServer.getInstance().getLogger());
        //Magic Variable
        map.put("__NAME__", scriptName);
        //Need Discussion
        //map.put("__ADAPTER__", adapter);
        return map;
    }
}
