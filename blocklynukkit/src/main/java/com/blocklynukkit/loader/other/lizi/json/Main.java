package com.blocklynukkit.loader.other.lizi.json;

import com.blocklynukkit.loader.EngineFacade;
import com.blocklynukkit.loader.Loader;
import com.blocklynukkit.loader.script.event.QQFriendMessageEvent;
import com.blocklynukkit.loader.script.event.QQGroupMessageEvent;
import com.blocklynukkit.loader.script.event.QQOtherEvent;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

public class Main {

    public static ChatClient clientTest = null;
    public static String ip = "127.0.0.1";
    public static int port = 8404;

    public Main() {

    }

    public static void start() {
        if (clientTest != null) {
            try {
                clientTest.socket.close();
            } catch (IOException e) {
                Loader.getPluginLogger().warning("与小栗子机器人框架通讯错误！");
            }
            clientTest.interrupt();
        }
        clientTest = new ChatClient(ip, port);
        clientTest.start();
    }

    /**
     * 收到好友消息
     *
     * @param data data
     */
    public static void receivePrivateMessages(String data) {
        JsonObject json = new JsonParser().parse(data).getAsJsonObject();
        long selfQQ = json.getAsJsonPrimitive("selfQQ").getAsLong();//框架QQ
        if (selfQQ < 0) selfQQ = 4294967296L + selfQQ;
        long fromQQ = json.getAsJsonPrimitive("fromQQ").getAsLong();//对方QQ
        if (fromQQ < 0) fromQQ = 4294967296L + fromQQ;
        long random = json.getAsJsonPrimitive("random").getAsLong();//撤回消息用
        long req = json.getAsJsonPrimitive("req").getAsLong();//撤回消息用
        String msg = json.getAsJsonPrimitive("msg").getAsString();//消息内容
        EngineFacade.invokeALL("QQFriendMessageEvent", new QQFriendMessageEvent(selfQQ, fromQQ, random, req, msg));
    }

    /**
     * 收到群聊消息
     *
     * @param data data
     */
    public static void receiveGroupMessages(String data) {
        JsonObject json = new JsonParser().parse(data).getAsJsonObject();
        long selfQQ = json.getAsJsonPrimitive("selfQQ").getAsLong();//框架QQ
        if (selfQQ < 0) selfQQ = 4294967296L + selfQQ;
        long fromGroup = json.getAsJsonPrimitive("fromGroup").getAsLong();//群号
        if (fromGroup < 0) fromGroup = 4294967296L + fromGroup;
        long fromQQ = json.getAsJsonPrimitive("fromQQ").getAsLong();//对方QQ
        if (fromQQ < 0) fromQQ = 4294967296L + fromQQ;
        String msg = json.getAsJsonPrimitive("msg").getAsString();//消息内容
        EngineFacade.invokeALL("QQGroupMessageEvent", new QQGroupMessageEvent(selfQQ, fromGroup, fromQQ, msg));
    }

    public static void receiveEventMessages(String data) {
        JsonObject json = new JsonParser().parse(data).getAsJsonObject();
        long selfQQ = json.getAsJsonPrimitive("selfQQ").getAsLong();//框架QQ
        if (selfQQ < 0) selfQQ = 4294967296L + selfQQ;
        long fromGroup = json.getAsJsonPrimitive("fromGroup").getAsLong();//群号
        if (fromGroup < 0) fromGroup = 4294967296L + fromGroup;
        int msgType = json.getAsJsonPrimitive("msgType").getAsInt();//类型
        long triggerQQ = json.getAsJsonPrimitive("triggerQQ").getAsLong();//对方QQ
        if (triggerQQ < 0) triggerQQ = triggerQQ + 4294967296L;
        //String triggerQQName = json.getString("triggerQQName");//对方昵称
        long seq = json.getAsJsonPrimitive("seq").getAsLong();//操作用
        EngineFacade.invokeALL("QQOtherEvent", new QQOtherEvent(selfQQ, fromGroup, msgType, triggerQQ, seq));
    }
}
