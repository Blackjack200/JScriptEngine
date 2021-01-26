package com.blocklynukkit.loader.other.lizi;

import com.blocklynukkit.loader.other.lizi.json.Core;
import com.blocklynukkit.loader.other.lizi.json.Main;
import com.blocklynukkit.loader.other.lizi.json.StringUtils;

import java.util.Base64;
import java.util.Random;


public class BNQQBot {
    public int qqid;
    public Random random = new Random(System.currentTimeMillis());

    public BNQQBot() {

    }

    public void startBot() {
        Main.start();
    }

    public void reDirectBot(String ip) {
        Main.ip = ip.split(":")[0];
        if (ip.split(":").length > 1) {
            Main.port = Integer.parseInt(ip.split(":")[1]);
        }
        if (Main.clientTest != null) {
            Main.start();
        }
    }

    public void sendFriendMessage(String fromQQ, String toQQ, String message) {
        Core.sendPrivateMessages(Long.parseLong(fromQQ), Long.parseLong(toQQ), message, random.nextLong(), random.nextLong());
    }

    public void sendGroupMessage(String fromQQ, String toGroup, String message) {
        Core.sendGroupMessages(Long.parseLong(fromQQ), Long.parseLong(toGroup), message, 0);
    }

    public void sendGroupPicMessage(String fromQQ, String toGroup, String picturePaths, String message) {
        String[] pics = picturePaths.split(";");
        String[] base64s = new String[picturePaths.split(";").length];
        for (int i = 0; i < pics.length; i++) {
            byte[] bts = StringUtils.readFile(pics[i]);//读取文件
            String base64Str = Base64.getEncoder().encodeToString(bts);//字节数组转Base64
            base64Str = "[pic:" + base64Str + "]";//组装图片的格式
            base64s[i] = base64Str;
        }
        for (int i = 0; i < pics.length; i++) {
            message = message.replaceAll("%picture" + i + "%", base64s[i]);
        }
        Core.sendGroupMessagesPicText(Long.parseLong(fromQQ), Long.parseLong(toGroup), message, 0);
    }

    public void kickGroupMember(String fromQQ, String toGroup, String toQQ) {
        Core.delGroupMember(Long.parseLong(fromQQ), Long.parseLong(toGroup), Long.parseLong(toQQ), 0);
    }

    public void banSpeakGroupMember(String fromQQ, String toGroup, String toQQ, int second) {
        Core.prohibitSpeak(Long.parseLong(fromQQ), Long.parseLong(toGroup), Long.parseLong(toQQ), second);
    }

}
