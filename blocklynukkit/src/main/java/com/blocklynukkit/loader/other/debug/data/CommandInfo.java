package com.blocklynukkit.loader.other.debug.data;

import cn.nukkit.Server;

import java.util.*;

public class CommandInfo {
    public String commandName;
    public String commandDescription;
    public Stack<CallInfo> callInfos = new Stack<>();
    public StringBuilder completer = new StringBuilder();
    public String fromPlugin;

    public CommandInfo(String commandName, String commandDescription) {
        this.commandName = commandName;
        this.commandDescription = commandDescription;
    }

    public CommandInfo(String commandName, String commandDescription, String plugin) {
        this(commandName, commandDescription);
        this.fromPlugin = plugin;
    }

    public void newCall(long lastTime, String startTime, String callFromPlugin, String callFromFunction, String senderName, String[] commnadArgs) {
        callInfos.push(new CallInfo(this, lastTime, startTime, callFromPlugin, callFromFunction, senderName, commnadArgs));
    }

    public void setLastCallError(String errorInfo, StackTraceElement[] e) {
        callInfos.peek().setError(errorInfo, e);
    }

    public String lastCallInfo() {
        if (callInfos.size() == 0) {
            return "此命令尚未被调用过";
        }
        return callInfos.peek().toString();
    }

    public boolean canIdentityPlugin() {
        return fromPlugin == null;
    }

    public String getPlugin() {
        return fromPlugin;
    }

    public Map<String, String> getCallsTime(int times) {
        Map<String, String> out = new LinkedHashMap<>();
        if (callInfos.empty()) {
            completer = new StringBuilder();
            Server.getInstance().getCommandMap().getCommand(commandName).getCommandParameters().forEach((key, value) -> {
                completer.append("\n    ").append(key).append(": ").append(Arrays.toString(value));
            });
            out.put("初始化", "注册命令：" + commandName + "\n命令描述：" + commandDescription + "\n完成器：" + completer.toString() + "\n");
        }
        Iterator it = callInfos.iterator();
        for (int i = 0; i < times; i++) {
            if (!it.hasNext()) {
                break;
            }
            CallInfo call = (CallInfo) it.next();
            out.put((call.senderName.equals("CONSOLE") ? "控制台" : call.senderName) + " " + call.startTime.split("T")[1], call.toString());
        }
        return out;
    }
}

class CallInfo {
    public long lastTime;
    public String startTime;
    public String callFromPlugin;
    public String callFromFunction;
    public String senderName;
    public String[] commnadArgs;
    public CommandInfo parent;
    public boolean isError = false;
    public StackTraceElement[] stackTraceElements;
    public String errorInfo;

    public CallInfo(CommandInfo parent, long lastTime, String startTime, String callFromPlugin, String callFromFunction, String senderName, String[] commnadArgs) {
        this.lastTime = lastTime;
        this.startTime = startTime;
        this.callFromPlugin = callFromPlugin;
        this.callFromFunction = callFromFunction;
        this.senderName = senderName;
        this.commnadArgs = commnadArgs;
        this.parent = parent;
    }

    public CallInfo setError(String errorInfo, StackTraceElement[] e) {
        this.isError = true;
        this.stackTraceElements = e;
        StringBuilder err = new StringBuilder(errorInfo);
        Arrays.asList(stackTraceElements).forEach(t -> {
            err.append("在").append(t.getFileName()).append("第").append(t.getLineNumber()).append("行 ").append(t.getClassName()).append("类").append(t.getMethodName()).append("方法").append("\n");
        });
        this.errorInfo = err.toString();
        return this;
    }

    @Override
    public String toString() {
        StringBuilder cmd = new StringBuilder("/" + parent.commandName);
        Arrays.asList(commnadArgs).forEach(e -> cmd.append(" " + e));
        return "调用时间：" + startTime + "\n"
                + "调用者：" + (senderName.equals("CONSOLE") ? "控制台" : "玩家" + senderName) + "\n"
                + "命令内容：" + cmd.toString() + "\n"
                + "回调函数：" + callFromPlugin + "::" + callFromFunction + "\n"
                + "处理耗时：" + lastTime + "ms\n"
                + (isError ? "错误信息：" + errorInfo + "\n" : "");
    }
}
