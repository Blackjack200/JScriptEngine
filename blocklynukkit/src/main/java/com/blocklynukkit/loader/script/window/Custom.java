package com.blocklynukkit.loader.script.window;

import cn.nukkit.Player;
import cn.nukkit.form.element.*;
import cn.nukkit.form.window.FormWindowCustom;
import com.blocklynukkit.loader.Loader;
import com.blocklynukkit.loader.script.window.windowCallbacks.CustomCallback;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Custom {
    public LinkedHashMap<Element, String> elementMap = new LinkedHashMap<>();
    private Element previousElement = null;
    public int id = (int) Math.floor(Math.random() * 10000000);
    public String title = "";

    public Custom setTitle(String title) {
        this.title = title;
        return this;
    }

    public Custom showToPlayer(Player p) {
        return this.showToPlayer(p, null, false);
    }

    public Custom showToPlayer(Player p, boolean acceptClose) {
        return this.showToPlayer(p, null, acceptClose);
    }

    public Custom showToPlayer(Player p, String callback) {
        return this.showToPlayer(p, callback, false);
    }

    public Custom show(Player p) {
        return this.showToPlayer(p);
    }

    public Custom show(Player p, boolean accpetClose) {
        return this.showToPlayer(p, accpetClose);
    }

    public Custom show(Player p, String callback) {
        return this.showToPlayer(p, callback);
    }

    public Custom show(Player p, String callback, boolean acceptClose) {
        return this.showToPlayer(p, callback, acceptClose);
    }

    public Custom showToPlayer(Player p, String callback, boolean acceptClose) {
        synchronized (Loader.windowCallbackMap) {
            CustomCallback windowCallback = new CustomCallback(acceptClose);
            FormWindowCustom window = new FormWindowCustom(title);
            int index = 0;
            for (Map.Entry<Element, String> each : elementMap.entrySet()) {
                if (each.getValue() != null) {
                    windowCallback.addActionCallback(index, each.getValue());
                }
                index++;
                window.addElement(each.getKey());
            }
            if (callback != null) {
                windowCallback.setDefaultCallback(callback);
            }
            Loader.windowCallbackMap.put(id, windowCallback);
            p.showFormWindow(window, id);
        }
        return this;
    }

    public Custom showAsSetting(Player p, String callback) {
        synchronized (Loader.serverSettingCallback) {
            Loader.serverSettingCallback.put(p.getName(), callback);
            p.addServerSettings(new FormWindowCustom(title, new ArrayList<>(elementMap.keySet())));
        }
        return this;
    }

    public Custom showAsSetting(Player p, String imageURL, String callback) {
        synchronized (Loader.serverSettingCallback) {
            Loader.serverSettingCallback.put(p.getName(), callback);
            FormWindowCustom custom = new FormWindowCustom(title, new ArrayList<>(elementMap.keySet()));
            if (imageURL.length() >= 5) {
                if (imageURL.startsWith("http")) {
                    custom.setIcon(new ElementButtonImageData("url", imageURL));
                } else {
                    custom.setIcon(new ElementButtonImageData("path", imageURL));
                }
            }
            p.addServerSettings(custom);
        }
        return this;
    }

    public void addNewElement(Element element) {
        previousElement = element;
        elementMap.put(element, null);
    }

    public Custom buildLabel(String text) {
        addNewElement(new ElementLabel(text));
        return this;
    }

    public Custom label(String text) {
        return buildLabel(text);
    }

    public Custom buildInput(String title, String placeholder) {
        addNewElement(new ElementInput(title, placeholder));
        return this;
    }

    public Custom input(String title, String placeholder) {
        return buildInput(title, placeholder);
    }

    public Custom buildInput(String title, String placeholder, String defaulttext) {
        addNewElement(new ElementInput(title, placeholder, defaulttext));
        return this;
    }

    public Custom input(String title, String placeholder, String defaulttext) {
        return buildInput(title, placeholder, defaulttext);
    }

    public Custom buildToggle(String title) {
        addNewElement(new ElementToggle(title));
        return this;
    }

    public Custom toggle(String title) {
        return buildToggle(title);
    }

    public Custom buildToggle(String title, boolean open) {
        addNewElement(new ElementToggle(title, open));
        return this;
    }

    public Custom toggle(String title, boolean open) {
        return buildToggle(title, open);
    }

    public Custom buildDropdown(String title, String inner) {
        ElementDropdown dropdown = new ElementDropdown(title);
        for (String a : inner.split(";")) {
            dropdown.addOption(a);
        }
        addNewElement(dropdown);
        return this;
    }

    public Custom dropdown(String title, String inner) {
        return buildDropdown(title, inner);
    }

    public Custom buildDropdown(String title, String inner, int index) {
        ElementDropdown dropdown = new ElementDropdown(title);
        for (String a : inner.split(";")) {
            dropdown.addOption(a);
        }
        dropdown.setDefaultOptionIndex(index);
        addNewElement(dropdown);
        return this;
    }

    public Custom dropdown(String title, String inner, int index) {
        return buildDropdown(title, inner, index);
    }

    public Custom buildSlider(String title, double min, double max, int step, double defaultvalue) {
        ElementSlider slider = new ElementSlider(title, (float) min, (float) max, step, (float) defaultvalue);
        addNewElement(slider);
        return this;
    }

    public Custom slider(String title, double min, double max, int step, double defaultvalue) {
        return buildSlider(title, min, max, step, defaultvalue);
    }

    public Custom buildSlider(String title, double min, double max, int step) {
        ElementSlider slider = new ElementSlider(title, (float) min, (float) max, step);
        addNewElement(slider);
        return this;
    }

    public Custom slider(String title, double min, double max, int step) {
        return buildSlider(title, min, max, step);
    }

    public Custom buildSlider(String title, double min, double max) {
        ElementSlider slider = new ElementSlider(title, (float) min, (float) max);
        addNewElement(slider);
        return this;
    }

    public Custom slider(String title, double min, double max) {
        return buildSlider(title, min, max);
    }

    public Custom buildStepSlider(String title, String options) {
        ElementStepSlider stepSlider = new ElementStepSlider(title);
        for (String each : options.split(";")) {
            stepSlider.addStep(each);
        }
        addNewElement(stepSlider);
        return this;
    }

    public Custom stepSlider(String title, String options) {
        return buildStepSlider(title, options);
    }

    public Custom stepslider(String title, String options) {
        return buildStepSlider(title, options);
    }

    public Custom buildStepSlider(String title, String options, int index) {
        ElementStepSlider stepSlider = new ElementStepSlider(title);
        for (String each : options.split(";")) {
            stepSlider.addStep(each);
        }
        stepSlider.setDefaultOptionIndex(index);
        addNewElement(stepSlider);
        return this;
    }

    public Custom stepSlider(String title, String options, int index) {
        return buildStepSlider(title, options, index);
    }

    public Custom stepslider(String title, String options, int index) {
        return buildStepSlider(title, options, index);
    }

    public Custom setAction(String callback) {
        if (previousElement != null) {
            elementMap.put(previousElement, callback);
        }
        return this;
    }

    public Custom action(String callback) {
        return setAction(callback);
    }

    @Override
    public String toString() {
        FormWindowCustom window = new FormWindowCustom(title, new ArrayList<>(elementMap.keySet()));
        return "CustomWindowForm: " + window.getJSONData();
    }
}
