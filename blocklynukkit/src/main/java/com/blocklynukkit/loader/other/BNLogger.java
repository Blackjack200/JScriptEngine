package com.blocklynukkit.loader.other;

import cn.nukkit.Server;
import cn.nukkit.utils.LogLevel;
import cn.nukkit.utils.Logger;

public class BNLogger implements Logger {
    private final String prefix;

    public BNLogger(String scriptName) {
        this.prefix = "[BN][" + scriptName + "] ";
    }

    @Override
    public void emergency(String message) {
        this.log(LogLevel.EMERGENCY, message);
    }

    @Override
    public void alert(String message) {
        this.log(LogLevel.ALERT, message);
    }

    @Override
    public void critical(String message) {
        this.log(LogLevel.CRITICAL, message);
    }

    @Override
    public void error(String message) {
        this.log(LogLevel.ERROR, message);
    }

    @Override
    public void warning(String message) {
        this.log(LogLevel.WARNING, message);
    }

    @Override
    public void notice(String message) {
        this.log(LogLevel.NOTICE, message);
    }

    @Override
    public void info(String message) {
        this.log(LogLevel.INFO, message);
    }

    @Override
    public void debug(String message) {
        this.log(LogLevel.DEBUG, message);
    }

    @Override
    public void log(LogLevel level, String message) {
        Server.getInstance().getLogger().log(level, this.prefix + message);
    }

    @Override
    public void emergency(String message, Throwable t) {
        this.log(LogLevel.EMERGENCY, message, t);
    }

    @Override
    public void alert(String message, Throwable t) {
        this.log(LogLevel.ALERT, message, t);
    }

    @Override
    public void critical(String message, Throwable t) {
        this.log(LogLevel.CRITICAL, message, t);
    }

    @Override
    public void error(String message, Throwable t) {
        this.log(LogLevel.ERROR, message, t);
    }

    @Override
    public void warning(String message, Throwable t) {
        this.log(LogLevel.WARNING, message, t);
    }

    @Override
    public void notice(String message, Throwable t) {
        this.log(LogLevel.NOTICE, message, t);
    }

    @Override
    public void info(String message, Throwable t) {
        this.log(LogLevel.INFO, message, t);
    }

    @Override
    public void debug(String message, Throwable t) {
        this.log(LogLevel.DEBUG, message, t);
    }

    @Override
    public void log(LogLevel level, String message, Throwable t) {
        Server.getInstance().getLogger().log(level, this.prefix + message, t);
    }
}
