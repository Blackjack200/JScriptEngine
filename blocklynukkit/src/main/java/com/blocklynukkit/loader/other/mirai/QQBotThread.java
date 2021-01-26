package com.blocklynukkit.loader.other.mirai;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.utils.BotConfiguration;

public class QQBotThread implements Runnable {
    public long QQNumber = 0;
    public String QQPassword = "";
    public Bot bot;

    public QQBotThread(long account, String password) {
        QQNumber = account;
        QQPassword = password;
        bot = BotFactoryJvm.newBot(QQNumber, QQPassword, new BotConfiguration() {{
            fileBasedDeviceInfo("deviceInfo.json");
        }});
    }

    @Override
    public void run() {
        bot.login();
        bot.getFriends().forEach(friend -> System.out.println(friend.getId() + ":" + friend.getNick()));
        bot.join();
    }
}
