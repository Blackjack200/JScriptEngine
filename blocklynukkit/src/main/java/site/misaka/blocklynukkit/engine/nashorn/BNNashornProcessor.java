package site.misaka.blocklynukkit.engine.nashorn;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import site.misaka.engine.PSREngineProcessor;

import javax.script.ScriptEngine;

public class BNNashornProcessor extends PSREngineProcessor<NashornScriptEngineFactory, BNNashornAdapter> {

    public BNNashornProcessor(NashornScriptEngineFactory factory) {
        super(factory);
    }

    @Override
    protected BNNashornAdapter createAdapter(ScriptEngine engine) {
        return new BNNashornAdapter((NashornScriptEngine) engine);
    }
}