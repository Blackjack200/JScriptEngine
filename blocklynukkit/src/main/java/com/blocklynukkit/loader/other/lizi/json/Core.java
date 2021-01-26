package com.blocklynukkit.loader.other.lizi.json;

import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * 核心操作类
 *
 * @author zhaoqk
 * <p>
 * 2020年8月10日 下午5:07:20
 */
public class Core {


    /**
     * 发送好友消息
     *
     * @param selfQQ 框架QQ
     * @param fromQQ 好友QQ
     * @param msg    发送的内容
     * @param random 撤回消息用
     * @param req    撤回消息用
     */
    public static void sendPrivateMessages(long selfQQ, long fromQQ, String msg, long random, long req) {
        Map<String, Object> json = new HashMap<>();
        json.put("type", 101);
        json.put("selfQQ", selfQQ);
        json.put("fromQQ", fromQQ);
        json.put("msg", msg);
        json.put("random", random);
        json.put("req", req);
        Main.clientTest.sendMsg(new GsonBuilder().create().toJson(json));
    }

    /**
     * 发送好友Json消息
     *
     * @param selfQQ 框架QQ
     * @param fromQQ 好友QQ
     * @param msg    发送的Json内容
     * @param random 撤回消息用
     * @param req    撤回消息用
     */
    public static void sendPrivateMessagesJson(long selfQQ, long fromQQ, String msg, long random, int req) {
        Map<String, Object> json = new HashMap<>();
        json.put("type", 102);
        json.put("selfQQ", selfQQ);
        json.put("fromQQ", fromQQ);
        json.put("msg", msg);
        json.put("random", random);
        json.put("req", req);
        Main.clientTest.sendMsg(new GsonBuilder().create().toJson(json));
    }

    /**
     * 发送图文消息
     *
     * @param selfQQ
     * @param fromQQ
     * @param msg
     * @param random
     * @param req
     */
    public static void sendPrivateMessagesPicText(long selfQQ, long fromQQ, String msg, long random, long req) {
        Map<String, Object> json = new HashMap<>();
        json.put("type", 103);
        json.put("selfQQ", selfQQ);
        json.put("fromQQ", fromQQ);
        json.put("msg", msg);
        json.put("random", random);
        json.put("req", req);
        Main.clientTest.sendMsg(new GsonBuilder().create().toJson(json));
    }

    /**
     * 处理好友验证事件
     *
     * @param selfQQ 框架QQ
     * @param fromQQ 好友QQ
     * @param seq    请求附带的seq
     * @param status 是否同意 1同意 2拒绝
     */
    public static void handlePrivateEvent(long selfQQ, long fromQQ, long seq, int status) {
        Map<String, Object> json = new HashMap<>();
        json.put("type", 104);
        json.put("selfQQ", selfQQ);
        json.put("fromQQ", fromQQ);
        json.put("seq", seq);
        json.put("status", status);
        Main.clientTest.sendMsg(new GsonBuilder().create().toJson(json));
    }

    /**
     * QQ点赞
     *
     * @param selfQQ 框架QQ
     * @param fromQQ 好友QQ
     */
    public static void callPraise(long selfQQ, long fromQQ, long number) {
        Map<String, Object> json = new HashMap<>();
        json.put("type", 105);
        json.put("selfQQ", selfQQ);
        json.put("fromQQ", fromQQ);
        json.put("number", number);
        Main.clientTest.sendMsg(new GsonBuilder().create().toJson(json));
    }

    /**
     * 发送好友红包
     *
     * @param selfQQ  框架QQ
     * @param fromQQ  对方QQ
     * @param number  红包数量
     * @param balance 红包金额
     * @param msg     祝福语
     * @param payPwd  支付密码
     */
    public static void pushRedPacket(long selfQQ, long fromQQ, long number, long balance, String msg, String payPwd) {
        Map<String, Object> json = new HashMap<>();
        json.put("type", 106);
        json.put("selfQQ", selfQQ);
        json.put("fromQQ", fromQQ);
        json.put("number", number);
        json.put("balance", balance);
        json.put("payPwd", payPwd);
        json.put("msg", msg);
        Main.clientTest.sendMsg(new GsonBuilder().create().toJson(json));
    }

    /**
     * 发送群聊消息
     *
     * @param selfQQ    框架QQ
     * @param fromGroup 群号
     * @param msg       发送的内容
     * @param anonymous 是否匿名 0否 1是
     */
    public static void sendGroupMessages(long selfQQ, long fromGroup, String msg, int anonymous) {
        Map<String, Object> json = new HashMap<>();
        json.put("type", 201);
        json.put("selfQQ", selfQQ);
        json.put("fromGroup", fromGroup);
        json.put("msg", msg);
        json.put("anonymous", anonymous);
        Main.clientTest.sendMsg(new GsonBuilder().create().toJson(json));
    }

    /**
     * 发送群聊消息
     *
     * @param selfQQ    框架QQ
     * @param fromGroup 群号
     * @param msg       发送的Json内容
     * @param anonymous 是否匿名 0否 1是
     */
    public static void sendGroupMessagesJson(long selfQQ, long fromGroup, String msg, int anonymous) {
        Map<String, Object> json = new HashMap<>();
        json.put("type", 202);
        json.put("selfQQ", selfQQ);
        json.put("fromGroup", fromGroup);
        json.put("anonymous", anonymous);
        Main.clientTest.sendMsg(new GsonBuilder().create().toJson(json));
    }

    /**
     * 发送群图文消息
     *
     * @param selfQQ
     * @param fromGroup
     * @param msg
     * @param anonymous
     */
    public static void sendGroupMessagesPicText(long selfQQ, long fromGroup, String msg, int anonymous) {
        Map<String, Object> json = new HashMap<>();
        json.put("type", 203);
        json.put("selfQQ", selfQQ);
        json.put("fromGroup", fromGroup);
        json.put("msg", msg);
        json.put("anonymous", anonymous);
        Main.clientTest.sendMsg(new GsonBuilder().create().toJson(json));
    }

    /**
     * 处理群验证事件
     *
     * @param selfQQ    框架QQ
     * @param fromGroup 群号
     * @param fromQQ    申请人QQ
     * @param seq       请求附带的seq
     * @param status    11同意 12拒绝  14忽略
     * @param fromType  3某人申请加群 1我被邀请加入群
     */
    public static void handleGroupEvent(long selfQQ, long fromGroup, long fromQQ, long seq, int status, int fromType) {
        Map<String, Object> json = new HashMap<>();
        json.put("type", 204);
        json.put("selfQQ", selfQQ);
        json.put("fromGroup", fromGroup);
        json.put("fromQQ", fromQQ);
        json.put("seq", seq);
        json.put("status", status);
        json.put("fromType", fromType);
        Main.clientTest.sendMsg(new GsonBuilder().create().toJson(json));
    }

    /**
     * 设置群名片
     *
     * @param selfQQ    框架QQ
     * @param fromGroup 群号
     * @param fromQQ    对方QQ
     * @param cardName  群名片
     */
    public static void setGroupCardName(long selfQQ, long fromGroup, long fromQQ, String cardName) {
        Map<String, Object> json = new HashMap<>();
        json.put("type", 205);
        json.put("selfQQ", selfQQ);
        json.put("fromGroup", fromGroup);
        json.put("fromQQ", fromQQ);
        json.put("cardName", cardName);
        Main.clientTest.sendMsg(new GsonBuilder().create().toJson(json));
    }

    /**
     * 删除群成员
     *
     * @param selfQQ    框架QQ
     * @param fromGroup 群号
     * @param fromQQ    对方QQ
     * @param refuse    拒绝加群申请 0否 1是
     */
    public static void delGroupMember(long selfQQ, long fromGroup, long fromQQ, int refuse) {
        Map<String, Object> json = new HashMap<>();
        json.put("type", 206);
        json.put("selfQQ", selfQQ);
        json.put("fromGroup", fromGroup);
        json.put("fromQQ", fromQQ);
        json.put("refuse", refuse);
        Main.clientTest.sendMsg(new GsonBuilder().create().toJson(json));
    }

    /**
     * 群禁言
     *
     * @param selfQQ    框架QQ
     * @param fromGroup 群号
     * @param fromQQ    对方QQ
     * @param second    时间 秒
     */
    public static void prohibitSpeak(long selfQQ, long fromGroup, long fromQQ, int second) {
        Map<String, Object> json = new HashMap<>();
        json.put("type", 207);
        json.put("selfQQ", selfQQ);
        json.put("fromGroup", fromGroup);
        json.put("fromQQ", fromQQ);
        json.put("second", second);
        Main.clientTest.sendMsg(new GsonBuilder().create().toJson(json));
    }

    /**
     * 撤回群消息
     *
     * @param selfQQ    框架QQ
     * @param fromGroup 群号
     * @param random    消息附带的random
     * @param req       消息附带的req
     */
    public static void withdrawGroupMessages(long selfQQ, long fromGroup, long random, long req) {
        Map<String, Object> json = new HashMap<>();
        json.put("type", 208);
        json.put("selfQQ", selfQQ);
        json.put("fromGroup", fromGroup);
        json.put("random", random);
        json.put("req", req);
        Main.clientTest.sendMsg(new GsonBuilder().create().toJson(json));
    }

    /**
     * 发送临时消息
     *
     * @param selfQQ    框架QQ
     * @param fromGroup 群号
     * @param fromQQ    对方QQ
     * @param msg       发送的内容
     * @param random    撤回消息用
     * @param seq       撤回消息用
     */
    public static void sendGroupTempMessages(long selfQQ, long fromGroup, long fromQQ, String msg, long random, long seq) {
        Map<String, Object> json = new HashMap<>();
        json.put("type", 209);
        json.put("selfQQ", selfQQ);
        json.put("fromGroup", fromGroup);
        json.put("fromQQ", fromQQ);
        json.put("msg", msg);
        json.put("random", random);
        json.put("seq", seq);
        Main.clientTest.sendMsg(new GsonBuilder().create().toJson(json));
    }

    /**
     * 发送群聊红包
     *
     * @param selfQQ    框架QQ
     * @param fromGroup 群号
     * @param number    红包数量
     * @param balance   红包金额
     * @param msg       祝福语
     * @param payPwd    支付密码
     */
    public static void pushRedPacketGroup(long selfQQ, long fromGroup, long number, long balance, String msg, String payPwd) {
        Map<String, Object> json = new HashMap<>();
        json.put("type", 210);
        json.put("selfQQ", selfQQ);
        json.put("fromGroup", fromGroup);
        json.put("number", number);
        json.put("balance", balance);
        json.put("payPwd", payPwd);
        json.put("msg", msg);
        Main.clientTest.sendMsg(new GsonBuilder().create().toJson(json));
    }
}
