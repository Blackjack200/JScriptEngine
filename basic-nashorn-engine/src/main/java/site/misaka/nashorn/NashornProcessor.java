package site.misaka.nashorn;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import site.misaka.engine.JSR223Processor;

import javax.script.ScriptEngine;

public class NashornProcessor extends JSR223Processor<NashornScriptEngineFactory, NashornAdapter> {
    public NashornProcessor(NashornScriptEngineFactory factory) {
        super(factory);
    }

    @Override
    protected NashornAdapter createAdapter(ScriptEngine engine) {
        return new NashornAdapter((NashornScriptEngine) engine);
    }
}