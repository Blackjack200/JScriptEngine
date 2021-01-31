package site.misaka.mirai;

import lombok.Getter;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.BotOfflineEvent;
import net.mamoe.mirai.event.events.BotOnlineEvent;
import net.mamoe.mirai.utils.MiraiLogger;
import site.misaka.mirai.script.EngineFacade;
import site.misaka.mirai.script.ScriptLoader;

import java.io.File;
import java.io.IOException;

public class BotServer {
    @Getter
    private final Bot bot;
    @Getter
    private final String path;
    private static BotServer server;
    private final MiraiLogger logger;
    private Listener listener;

    public BotServer(Bot bot, String path) {
        this.bot = bot;
        this.path = path;
        server = this;
        this.logger = MiraiLogger.create("Server");
    }

    public void start() {
        EngineFacade.init();
        File script = new File(this.path, "scripts/");
        script.mkdirs();

        this.bot.getEventChannel().registerListenerHost(new BotEventHandler());

        listener = this.bot.getEventChannel().subscribeAlways(BotOnlineEvent.class, (e) -> {
            this.getLogger().info("机器人登陆成功,开始加载脚本");
            try {
                ScriptLoader.scanLoader(script.toPath());
                listener.complete();
                this.bot.getConfiguration().noNetworkLog();
                this.bot.getConfiguration().noBotLog();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

        this.bot.getEventChannel().subscribeAlways(BotOfflineEvent.class, (e) -> {
            this.getLogger().info("机器人掉线,尝试自动重连");
            this.bot.login();
        });

        this.bot.login();
    }

    public MiraiLogger getLogger() {
        return this.logger;
    }

    public static BotServer getInstance() {
        return server;
    }
}
