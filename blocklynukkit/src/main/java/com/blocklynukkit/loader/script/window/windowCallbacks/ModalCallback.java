package com.blocklynukkit.loader.script.window.windowCallbacks;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.response.FormResponseModal;
import com.blocklynukkit.loader.EngineFacade;

public class ModalCallback extends WindowCallback {
    public String defaultCallback = null;
    public String yesCallback = null;
    public String noCallback = null;

    public ModalCallback(boolean acceptClose) {
        super(acceptClose);
    }

    public void setDefaultCallback(String defaultCallback) {
        this.defaultCallback = defaultCallback;
    }

    public boolean hasDefaultCallback() {
        return defaultCallback != null;
    }

    public void setNoCallback(String noCallback) {
        this.noCallback = noCallback;
    }

    public void setYesCallback(String yesCallback) {
        this.yesCallback = yesCallback;
    }

    @Override
    public void call(PlayerFormRespondedEvent event) {
        if (!(event.getResponse() instanceof FormResponseModal)) return;
        if (event.getResponse() == null) {
            if (hasDefaultCallback() && event.wasClosed()) {
                EngineFacade.invokeALL(defaultCallback, event);
            }
            return;
        }
        FormResponseModal modal = (FormResponseModal) event.getResponse();
        if (modal.getClickedButtonId() == 0 && yesCallback != null) {
            EngineFacade.invokeALL(yesCallback, event);
        }
        if (modal.getClickedButtonId() == 1 && noCallback != null) {
            EngineFacade.invokeALL(noCallback, event);
        }
        if (hasDefaultCallback()) {
            EngineFacade.invokeALL(defaultCallback, event);
        }
    }
}
