package site.misaka.blocklynukkit.script.object.form;

import com.denzelcode.form.window.SimpleWindowForm;
import lombok.var;

public class SimpleFormBuilder {
    public String content;
    public String title;

    public SimpleFormBuilder content(String content) {
        this.content = content;
        return this;
    }

    public SimpleFormBuilder title(String title) {
        this.title = title;
        return this;
    }

    public SimpleWindowForm build() {
        var form = new SimpleWindowForm(this.title);
        form.setContent(this.content);
        return form;
    }
}
