package com.blocklynukkit.loader.script.window.windowCallbacks;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.response.FormResponseCustom;
import com.blocklynukkit.loader.EngineFacade;
import com.blocklynukkit.loader.script.event.CustomWindowEvent;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.Map;

public class CustomCallback extends WindowCallback {
    public String defaultCallback = null;
    public Int2ObjectMap<String> actionCallbacks = new Int2ObjectOpenHashMap<>();

    public CustomCallback(boolean acceptClose) {
        super(acceptClose);
    }

    public void setDefaultCallback(String defaultCallback) {
        this.defaultCallback = defaultCallback;
    }

    public boolean hasDefaultCallback() {
        return defaultCallback != null;
    }

    public void addActionCallback(int index, String actionCallback) {
        actionCallbacks.put(index, actionCallback);
    }

    public void addActionCallback(String actionCallback) {
        actionCallbacks.put(actionCallbacks.size(), actionCallback);
    }

    @Override
    public void call(PlayerFormRespondedEvent event) {
        if (!(event.getResponse() instanceof FormResponseCustom)) return;
        if (event.getResponse() == null) {
            if (hasDefaultCallback() && event.wasClosed()) {
                EngineFacade.invokeALL(defaultCallback, new CustomWindowEvent(event));
            }
            return;
        }
        for (Map.Entry<Integer, String> each : actionCallbacks.int2ObjectEntrySet()) {
            EngineFacade.invokeALL(each.getValue(), new CustomWindowEvent(each.getKey(), event));
        }
        if (hasDefaultCallback()) {
            EngineFacade.invokeALL(defaultCallback, event);
        }
    }
}
