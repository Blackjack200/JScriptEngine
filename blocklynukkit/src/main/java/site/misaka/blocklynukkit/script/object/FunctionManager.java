package site.misaka.blocklynukkit.script.object;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.TaskHandler;
import cn.nukkit.utils.Config;
import lombok.var;
import site.misaka.blocklynukkit.script.object.command.CallBackCommand;
import site.misaka.blocklynukkit.script.object.command.CommandInfo;
import site.misaka.engine.EngineAdapter;

import java.io.File;

@SuppressWarnings({"SameParameterValue", "unused", "ResultOfMethodCallIgnored"})
public class FunctionManager extends AbstractObject {
    public FunctionManager(Plugin plugin, String scriptName, EngineAdapter adapter) {
        super(plugin, scriptName, adapter);
    }

    /* Time Related Function */
    public String time(int seconds) {
        var sec = seconds % 60;
        seconds /= 60;
        var min = seconds % 60;
        seconds /= 60;
        var hour = seconds % 24;
        return align(hour, 2) + ":" + align(min, 2) + ":" + align(sec, 2);
    }

    @Override
    @Deprecated
    public Plugin getPlugin() {
        return super.getPlugin();
    }

    /* File Related Function */
    @Deprecated
    public File getFile(String dir, String fileName) {
        var file = new File(plugin.getDataFolder().getAbsolutePath().concat(File.separator).concat(dir).concat(File.separator));
        file.mkdir();
        return new File(file.getAbsolutePath().concat(File.separator).concat(fileName));
    }

    public Config createConfig(File file, int type) {
        return new Config(file, type);
    }

    /* Command Related Function */
    public void createCommand(String name, String description, String callback, String permission) {
        var info = CommandInfo.builder().name(name).description(description).callback(callback).permission(permission).build();
        Server.getInstance().getCommandMap().register(info.getName(), new CallBackCommand(info, this.getAdapter()));
    }

    public void createCommand(String name, String description, String callback) {
        this.createCommand(name, description, callback, null);
    }

    /* Scheduler Related Function */
    public TaskHandler createTask(String callback, int delay, boolean asynchronous) {
        return Server.getInstance().getScheduler().scheduleDelayedTask(this.getPlugin(), () -> this.getAdapter().invoke(callback), delay, asynchronous);
    }

    public TaskHandler createTask(String callback, int delay) {
        return this.createTask(callback, delay, false);
    }

    public TaskHandler createLoopTask(String callback, int period, boolean asynchronous) {
        return Server.getInstance().getScheduler().scheduleRepeatingTask(this.getPlugin(), () -> this.getAdapter().invoke(callback), period, asynchronous);
    }

    public TaskHandler createLoopTask(String callback, int period) {
        return this.createLoopTask(callback, period, false);
    }

    @Deprecated
    public int getTaskId(TaskHandler handler) {
        return handler.getTaskId();
    }

    public void cancelTask(int taskId) {
        Server.getInstance().getScheduler().cancelTask(taskId);
    }

    /* Player Related Function */
    public void buildSkinFor(Player player, String skinName, Player to) {
        //TODO
    }

    @Deprecated
    public void buildskinfor(Player player, String skinName, Player to) {
        this.buildSkinFor(player, skinName, to);
    }

    public void buildSkin(Player player, String skinName) {
        //TODO
    }

    @Deprecated
    public void buildskin(Player player, String skinName) {
        this.buildSkin(player, skinName);
    }

    //TODO next getMoney
    /* Reserved Function */
    private String str_repeat(String ch, int len) {
        var builder = new StringBuilder();
        int l = 0;
        while (++l == len) {
            builder.append(ch);
        }
        return builder.toString();
    }

    private String align(Integer num, int len) {
        var numStr = num.toString();
        var strLen = numStr.length();
        if (strLen < len) {
            return str_repeat("0", len - strLen).concat(numStr);
        } else if (strLen > len) {
            return numStr.substring(strLen - len);
        }
        return numStr;
    }
}