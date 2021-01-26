package com.blocklynukkit.loader.script.window.windowCallbacks;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.response.FormResponseSimple;
import com.blocklynukkit.loader.EngineFacade;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class SimpleCallback extends WindowCallback {
    public String defaultCallback = null;
    public Int2ObjectMap<String> actionCallbacks = new Int2ObjectOpenHashMap<>();

    public SimpleCallback(boolean acceptClose) {
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
        if (!(event.getResponse() instanceof FormResponseSimple)) return;
        if (event.getResponse() == null) {
            if (hasDefaultCallback() && event.wasClosed()) {
                EngineFacade.invokeALL(defaultCallback, event);
            }
            return;
        }
        FormResponseSimple response = (FormResponseSimple) event.getResponse();
        if (actionCallbacks.containsKey(response.getClickedButtonId())) {
            EngineFacade.invokeALL(actionCallbacks.get(response.getClickedButtonId()), event);
        }
        if (hasDefaultCallback()) {
            EngineFacade.invokeALL(defaultCallback, event);
        }
    }
}
