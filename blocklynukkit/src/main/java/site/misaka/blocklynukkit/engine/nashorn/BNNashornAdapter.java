package site.misaka.blocklynukkit.engine.nashorn;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import site.misaka.blocklynukkit.engine.EngineFacade;
import site.misaka.nashorn.NashornAdapter;

import java.util.function.Consumer;

public class BNNashornAdapter extends NashornAdapter {
    public BNNashornAdapter(NashornScriptEngine engine) {
        super(engine);
        //we use lambda
        this.engine.put("F", (Consumer<String>) this::back);
    }

    //Support BlocklyNukkit
    @SuppressWarnings("UnusedReturnValue")
    private <T> T back(T object) {
        return object;
    }

    @Override
    public void invoke(String name, Object... args) {
        if (EngineFacade.getDirection().get(this).containsKey(name)) {
            name = EngineFacade.getDirection().get(this).get(name);
        }
        super.invoke(name, args);
    }
}