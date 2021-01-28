package site.misaka.blocklynukkit.script.object;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.player.PlayerKickEvent;
import cn.nukkit.math.NukkitMath;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.ScriptCustomEventPacket;
import cn.nukkit.permission.Permission;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.TaskHandler;
import cn.nukkit.utils.Config;
import com.alibaba.fastjson.JSON;
import com.sun.management.OperatingSystemMXBean;
import lombok.SneakyThrows;
import lombok.var;
import me.onebone.economyapi.EconomyAPI;
import org.yaml.snakeyaml.Yaml;
import site.misaka.blocklynukkit.engine.EngineFacade;
import site.misaka.blocklynukkit.script.object.command.CallBackCommand;
import site.misaka.blocklynukkit.script.object.command.CommandInfo;
import site.misaka.blocklynukkit.utils.MetricsLite;
import site.misaka.engine.EngineAdapter;
import site.misaka.utils.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings({"SameParameterValue", "unused"})
public class Function extends AbstractObject {
    private final Yaml yaml = new Yaml();

    public Function(Plugin plugin, String scriptName, EngineAdapter adapter) {
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

    /* Plugin Related Function */
    @Override
    @Deprecated
    public Plugin getPlugin() {
        return super.getPlugin();
    }

    public void callFunction(String callName, Object... args) {
        EngineFacade.invokeALL(callName, args);
    }

    /* File Related Function */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Deprecated
    public File getFile(String dir, String fileName) {
        var file = new File(plugin.getDataFolder().getAbsolutePath().concat(File.separator).concat(dir).concat(File.separator));
        file.mkdir();
        return new File(file.getAbsolutePath().concat(File.separator).concat(fileName));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SneakyThrows
    public boolean createFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            return file.createNewFile();
        }
        return true;
    }

    @Deprecated
    public String readFile(String path) {
        String content = this._ReadFile(path);
        return content == null ? "FILE NOT FOUND" : content;
    }

    public String _ReadFile(String path) {
        try {
            return FileUtils.file_get_content(new File(path));
        } catch (IOException ignored) {
        }
        return null;
    }

    public boolean writeFile(String path, String content) {
        try {
            var out = new BufferedWriter(new FileWriter(path));
            out.write(content);
            out.close();
            return true;
        } catch (IOException ignored) {

        }
        return false;
    }

    public boolean isFileSame(String path1, String path2) {
        return (new File(path1)).equals(new File(path2));
    }

    public Config createConfig(File file, int type) {
        return new Config(file, type);
    }

    //Use config.getAll().keySet().toArray() instead of getAllKeyInConfig
    @Deprecated
    public Object[] getAllKeyInConfig(Config config) {
        return config.getAll().keySet().toArray();
    }

    public Object parseYAML(String content) {
        return yaml.load(content);
    }

    public String emitYAML(Object data) {
        return yaml.dump(data);
    }

    public Object parseJSON(String content) {
        return JSON.parse(content);
    }

    public String emitJSON(Object data) {
        return JSON.toJSONString(data);
    }

    @Deprecated
    public String JSONtoYAML(String json) {
        return this.emitYAML(this.parseJSON(json));
    }

    @Deprecated
    public String YAMLtoJSON(String yaml) {
        return this.emitJSON(this.parseYAML(yaml));
    }

    public void setPrivateCall(String origin, String destination) {
        EngineFacade.getDirection().get(this.getAdapter()).put(origin, destination);
    }

    /* Temporary Data Related Function
     * These function is deprecated please use a map or redis instead fo them */
    protected ConcurrentHashMap<String, Object> easyCache = new ConcurrentHashMap<>();

    @Deprecated
    public void putEasy(String key, Object value) {
        this.easyCache.put(key, value);
    }

    @Deprecated
    public Object getEasy(String key) {
        return this.easyCache.get(key);
    }

    /* Command Related Function */
    @SuppressWarnings("SpellCheckingInspection")
    public void createPermission(String permission, String description, String _default) {
        switch (_default.toUpperCase(Locale.ROOT)) {
            case "OP":
            case "ADMIN":
                _default = Permission.DEFAULT_OP;
                break;
            case "ALL":
            case "EVERY":
            case "TRUE":
                _default = Permission.DEFAULT_TRUE;
                break;
            case "FALSE":
            case "NONE":
            case "NO":
                _default = Permission.DEFAULT_FALSE;
                break;
            case "NOTOP":
                _default = Permission.DEFAULT_NOT_OP;

        }
        Server.getInstance().getPluginManager().addPermission(new Permission(permission, description, _default));
    }

    public void removePermission(String permission) {
        Server.getInstance().getPluginManager().removePermission(permission);
    }

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

    //TODO setTimeout
    //TODO clearInterval

    @Deprecated
    public int getTaskId(TaskHandler handler) {
        return handler.getTaskId();
    }

    public void cancelTask(int taskId) {
        Server.getInstance().getScheduler().cancelTask(taskId);
    }

    /* Entity Related Function */
    public Vector3 newVector3(double x, double y, double z) {
        return new Vector3(x, y, z);
    }

    @Deprecated
    @SuppressWarnings("SpellCheckingInspection")
    public Vector3 buildvec3(double x, double y, double z) {
        return newVector3(x, y, z);
    }

    //Please manipulate Player Object
    @Deprecated
    public boolean checkPlayerPermission(String permission, Player player) {
        return player.hasPermission(permission);
    }

    @Deprecated
    public boolean PlayerIsOP(Player player) {
        return player.isOp();
    }

    //Please manipulate Player Object
    @Deprecated
    public int getPlayerGameMode(Player player) {
        return player.getGamemode();
    }

    //Please manipulate Player Object
    @Deprecated
    public void kickPlayer(Player player, String reason) {
        player.kick(PlayerKickEvent.Reason.UNKNOWN, reason);
    }

    //TODO getPlayerArea
    //TODO checkIsBear
    public void buildSkinFor(Player player, String skinName, Player to) {
        //TODO
    }

    @Deprecated
    @SuppressWarnings("SpellCheckingInspection")
    public void buildskinfor(Player player, String skinName, Player to) {
        this.buildSkinFor(player, skinName, to);
    }

    public void buildSkin(Player player, String skinName) {
        //TODO
    }

    @Deprecated
    @SuppressWarnings("SpellCheckingInspection")
    public void buildskin(Player player, String skinName) {
        this.buildSkin(player, skinName);
    }

    //EconomyAPI Support

    public double getMoney(String name) {
        return EconomyAPI.getInstance().myMoney(name);
    }

    @Deprecated
    public double getMoney(Player player) {
        return this.getMoney(player.getName());
    }

    public void reduceMoney(String name, double amount) {
        EconomyAPI.getInstance().reduceMoney(name, amount);
    }

    @Deprecated
    public void reduceMoney(Player player, double amount) {
        this.reduceMoney(player.getName(), amount);
    }

    public void addMoney(String name, double amount) {
        EconomyAPI.getInstance().addMoney(name, amount);
    }

    @Deprecated
    public void addMoney(Player player, double amount) {
        this.addMoney(player.getName(), amount);
    }

    public void setMoney(String name, double amount) {
        EconomyAPI.getInstance().setMoney(name, amount);
    }

    @Deprecated
    public void setMoney(Player player, double amount) {
        this.setMoney(player.getName(), amount);
    }

    /* HTML Server */
    //TODO setHTMLPlaceholder
    //TODO httpRequest

    /* Internal Function */
    //TODO isWindows
    public double getCPULoad() {
        return ((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getSystemCpuLoad();
    }

    public int getAvailableProcessorCount() {
        return Runtime.getRuntime().availableProcessors();
    }

    @Deprecated
    public int getCPUCores() {
        return this.getAvailableProcessorCount();
    }

    public double getMemoryTotalSizeMB() {
        return NukkitMath.round((Runtime.getRuntime().totalMemory()) / 1024D / 1024, 2);
    }

    public double getMemoryUsedSizeMB() {
        return NukkitMath.round((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024D / 1024, 2);
    }

    public void forceDisconnect(Player player) {
        var packet = new ScriptCustomEventPacket();
        packet.eventName = "CRASH";
        packet.eventData = new byte[0];
        player.dataPacket(packet);
    }

    /* Reserved Function */
    private String str_repeat(String ch, int count) {
        var len = ch.length();
        var buffer = new char[count *= len];
        var data = ch.toCharArray();
        for (int i = 0; i < count; i += len) {
            System.arraycopy(data, 0, buffer, i, len);
        }
        return new String(buffer, 0, count);
    }

    private String str_repeatB(String ch, int len) {
        var builder = new StringBuilder();
        int l = 0;
        while (++l == len) {
            builder.append(ch);
        }
        return builder.toString();
    }

    private String align(int num, int len) {
        var numStr = Integer.toString(num);
        var strLen = numStr.length();
        if (strLen < len) {
            return str_repeat("0", len - strLen).concat(numStr);
        } else if (strLen > len) {
            return numStr.substring(strLen - len);
        }
        return numStr;
    }

    /* Encryption Related Function */
    @SneakyThrows
    public String MD5Encryption(String string) {
        return Arrays.toString(MessageDigest.getInstance("MD5").digest(string.getBytes()));
    }

    @SneakyThrows
    public String SHA1Encryption(String string) {
        return Arrays.toString(MessageDigest.getInstance("SHA-1").digest(string.getBytes()));
    }

    public void bStats(String name, String version, String author, int id) {
        MetricsLite metrics = new MetricsLite(new FakePlugin(name, author, version), id);
    }

    final private byte[] motdData = new byte[]{1, 0, 0, 0, 0, 0, 3, 106, 7, 0, -1, -1, 0, -2, -2, -2, -2, -3, -3, -3, -3, 18, 52, 86, 120, -100, 116, 22, -68};

    public void getServerMotd(String host, int port, String callback) {
        Server.getInstance().getScheduler().scheduleTask(this.getPlugin(), () -> {
            try {
                var socket = new DatagramSocket();
                socket.setSoTimeout(5000);
                DatagramPacket packet = new DatagramPacket(Arrays.copyOf(motdData, 1024), 1024, InetAddress.getByName(host), port);
                socket.send(packet);
                socket.receive(packet);
                getAdapter().invoke(callback, (Object) new String(packet.getData(), 35, packet.getLength()).split(";"));
                socket.close();
            } catch (Throwable e) {
                getAdapter().invoke(callback, false, e);
            }
        }, true);
    }

    @Deprecated
    @SneakyThrows
    public Object getVariableFrom(String scriptName, String varName) {
        return EngineFacade.getScripts().get(scriptName).getValue(varName);
    }

    @Deprecated
    @SneakyThrows
    public void putVariableTo(String scriptName, String varName, Object object) {
        EngineFacade.getScripts().get(scriptName).put(varName, object);
    }
}