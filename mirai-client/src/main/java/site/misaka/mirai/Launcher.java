package site.misaka.mirai;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
            FileUtils.file_put_content(file.getAbsolutePath(), JSON.toJSON(map).toString());
        }

        try {
            Map<String, Object> data = ((JSONObject) JSON.parse(FileUtils.file_get_content(file))).getInnerMap();
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
            Bot bot = BotFactory.INSTANCE.newBot(Long.parseLong(data.get("account").toString()), data.get("password").toString(), configuration);
            BotServer server = new BotServer(bot, path);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}