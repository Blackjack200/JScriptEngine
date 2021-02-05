package site.misaka.mirai;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;
import site.misaka.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;


public class Launcher {

    public static void main(String args[]) {
        System.setProperty("mirai.no-desktop", "mirai.no-desktop");
        String path = System.getProperty("user.dir");
        File file = new File(path, "config.json");
        if (!file.exists()) {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put("account", 110L);
            map.put("password", "123456");
            map.put("protocol", "WATCH");
            FileUtils.file_put_content(file.getAbsolutePath(), (new Gson()).toJson(map));
        }

        try {
            Map<String, Object> data = (new Gson()).fromJson(FileUtils.file_get_content(file), new TypeToken<LinkedHashMap<?, ?>>() {
            }.getType());
            BotConfiguration configuration = new BotConfiguration();
            BotConfiguration.MiraiProtocol protocol = BotConfiguration.MiraiProtocol.ANDROID_WATCH;

            switch (data.get("protocol").toString().toUpperCase(Locale.ROOT)) {
                case "PAD":
                    protocol = BotConfiguration.MiraiProtocol.ANDROID_PAD;
                    break;
                case "PHONE":
                    protocol = BotConfiguration.MiraiProtocol.ANDROID_PHONE;
                    break;
            }
            configuration.setProtocol(protocol);
            configuration.fileBasedDeviceInfo("device.json");
            configuration.noBotLog();
            Bot bot = BotFactory.INSTANCE.newBot(((Double) data.get("account")).longValue(), data.get("password").toString(), configuration);
            BotServer server = new BotServer(bot, path);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}