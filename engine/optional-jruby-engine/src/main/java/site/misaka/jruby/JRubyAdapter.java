package site.misaka.jruby;

import org.jruby.embed.jsr223.JRubyEngine;
import site.misaka.engine.JSR223Adapter;

import javax.script.ScriptException;

public class JRubyAdapter extends JSR223Adapter<JRubyEngine> {
    public JRubyAdapter(JRubyEngine engine) {
        super(engine);
    }

    @Override
    public void invoke(String name, Object... args) {
        try {
            this.engine.invokeFunction(name, args);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException ignore) {

        }
    }
}
