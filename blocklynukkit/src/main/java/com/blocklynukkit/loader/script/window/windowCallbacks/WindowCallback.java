package com.blocklynukkit.loader.script.window.windowCallbacks;

import cn.nukkit.event.player.PlayerFormRespondedEvent;

abstract public class WindowCallback {
    private boolean acceptClose;

    public WindowCallback(boolean acceptClose) {
        this.acceptClose = acceptClose;
    }

    public boolean isAcceptClose() {
        return this.acceptClose;
    }

    public void setAcceptClose(boolean acceptClose) {
        this.acceptClose = acceptClose;
    }

    public abstract void call(PlayerFormRespondedEvent event);
}
