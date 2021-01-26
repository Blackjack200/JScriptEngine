package com.blocklynukkit.loader.script.window;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindowSimple;
import com.blocklynukkit.loader.Loader;
import com.blocklynukkit.loader.script.window.windowCallbacks.SimpleCallback;

import java.util.LinkedHashMap;
import java.util.Map;

public class Simple {
    public LinkedHashMap<ElementButton, String> buttonsMap = new LinkedHashMap<>();
    public int id = (int) Math.floor(Math.random() * 10000000);
    public String title = "";
    public String context = "";
    private ElementButton previousButton = null;

    public Simple buildButton(String text, String img) {
        ElementButton buttontmp = new ElementButton(text);
        if (img.startsWith("http")) {
            buttontmp.addImage(new ElementButtonImageData("url", img));
        } else if (img.length() > 4) {
            buttontmp.addImage(new ElementButtonImageData("path", img));
        }
        buttonsMap.put(buttontmp, null);
        previousButton = buttontmp;
        return this;
    }

    public Simple button(String text, String img) {
        return buildButton(text, img);
    }

    public Simple button(String text) {
        return buildButton(text, "");
    }

    public Simple setAction(String action) {
        if (previousButton != null) {
            buttonsMap.replace(previousButton, action);
        }
        return this;
    }

    public Simple action(String action) {
        return setAction(action);
    }

    public Simple setTitle(String title) {
        this.title = title;
        return this;
    }

    public Simple title(String title) {
        return this.setTitle(title);
    }

    public Simple setContext(String context) {
        this.context = context;
        return this;
    }

    public Simple context(String context) {
        return this.setContext(context);
    }

    public Simple showToPlayer(Player p) {
        return this.showToPlayer(p, null, false);
    }

    public Simple showToPlayer(Player p, boolean acceptClose) {
        return this.showToPlayer(p, null, acceptClose);
    }

    public Simple showToPlayer(Player p, String callback) {
        return this.showToPlayer(p, callback, false);
    }

    public Simple showToPlayer(Player p, String callback, boolean acceptClose) {
        synchronized (Loader.windowCallbackMap) {
            SimpleCallback windowCallback = new SimpleCallback(acceptClose);
            FormWindowSimple window = new FormWindowSimple(title, context);
            int index = 0;
            for (Map.Entry<ElementButton, String> each : buttonsMap.entrySet()) {
                if (each.getValue() != null) {
                    windowCallback.addActionCallback(index, each.getValue());
                }
                index++;
                window.addButton(each.getKey());
            }
            if (callback != null) {
                windowCallback.setDefaultCallback(callback);
            }
            Loader.windowCallbackMap.put(id, windowCallback);
            p.showFormWindow(window, id);
        }
        return this;
    }

    public Simple show(Player p) {
        return this.showToPlayer(p);
    }

    public Simple show(Player p, boolean accpetClose) {
        return this.showToPlayer(p, accpetClose);
    }

    public Simple show(Player p, String callback) {
        return this.showToPlayer(p, callback);
    }

    public Simple show(Player p, String callback, boolean acceptClose) {
        return this.showToPlayer(p, callback, acceptClose);
    }

    @Override
    public String toString() {
        FormWindowSimple window = new FormWindowSimple(title, context);
        for (ElementButton button : buttonsMap.keySet()) {
            window.addButton(button);
        }
        return "SimpleWindowForm: " + window.getJSONData() + "";
    }
}
