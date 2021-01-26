package com.blocklynukkit.loader.script;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandEnum;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.entity.data.Skin;
import cn.nukkit.event.Event;
import cn.nukkit.event.player.PlayerKickEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.PlayerSkinPacket;
import cn.nukkit.network.protocol.VideoStreamConnectPacket;
import cn.nukkit.permission.Permission;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginDescription;
import cn.nukkit.plugin.PluginLoader;
import cn.nukkit.plugin.PluginLogger;
import cn.nukkit.scheduler.Task;
import cn.nukkit.scheduler.TaskHandler;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import com.blocklynukkit.loader.EngineFacade;
import com.blocklynukkit.loader.Loader;
import com.blocklynukkit.loader.UnionData;
import com.blocklynukkit.loader.other.BstatsBN;
import com.blocklynukkit.loader.other.Clothes;
import com.blocklynukkit.loader.other.debug.data.CommandInfo;
import com.blocklynukkit.loader.other.lizi.BNQQBot;
import com.blocklynukkit.loader.script.bases.BaseManager;
import com.blocklynukkit.loader.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.management.OperatingSystemMXBean;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.ir.Block;
import me.onebone.economyapi.EconomyAPI;
import site.misaka.engine.EngineAdapter;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.net.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.blocklynukkit.loader.Loader.fakeNukkitCodeVersion;

public class FunctionManager extends BaseManager {
    @Override
    public String toString() {
        return "BlocklyNukkit Based Object";
    }

    private final Loader plugin;

    public BNQQBot qq = Loader.qq;


    public FunctionManager(EngineAdapter engine) {
        super(engine);
        this.plugin = Loader.plugin;
    }

    public Player[] getOnlinePlayers() {
        return (Player[]) Server.getInstance().getOnlinePlayers().values().toArray();
    }

    //here 11/6
    public boolean isPathExists(String path) {
        return new File(path).exists();
    }

    public String[] getFolderFiles(String path) {
        File file = new File(path);
        return (file.exists() ? (file.isDirectory() ? (file.list()) : null) : null);
    }

    public long getFileSize(String path) {
        File file = new File(path);
        return file.exists() ? (file.isFile() ? (file.length()) : -1) : -1;
    }

    public void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) file.delete();
    }

    public void doPathCreate(String path) {
        File file = new File(path);
        if (file.exists()) file.delete();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isPathReadable(String path) {
        return new File(path).canRead();
    }

    public boolean isPathWritable(String path) {
        return new File(path).canWrite();
    }

    public void copyFile(String fromPath, String toPath) {
        File from = new File(fromPath);
        File to = new File(toPath);
        if (to.exists()) to.delete();
        if (from.exists() && from.canRead() && to.canWrite()) {
            try {
                Files.copy(from.toPath(), to.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //here 10/15
    public void runCMD(String cmd) {
        String cmdStr = cmd;
        Runtime run = Runtime.getRuntime();
        try {
            Process process = run.exec(cmdStr);
            process.waitFor(6, TimeUnit.SECONDS);
            InputStream in = process.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String message;
            while ((message = br.readLine()) != null) {
                sb.append(message);
            }
            System.out.println(sb);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //here 10/13
    public String getResource(String name) {
        if (name.startsWith("http")) {
            return Utils.sendGet(name, null);
        }
        name = name.replaceFirst("\\./", "");
        File tmp = new File("./plugins/BlocklyNukkit/" + name);
        if (tmp.exists()) {
            return Utils.readToString("./plugins/BlocklyNukkit/" + name);
        }
        return null;
    }

    //here 8/21
    public void setNukkitCodeVersion(String string) {
        fakeNukkitCodeVersion = string;
    }

    //here 8/18
    public String getPlayerDeviceModal(Player player) {
        return player.getLoginChainData().getDeviceModel();
    }

    public String getPlayerDeviceID(Player player) {
        return player.getLoginChainData().getDeviceId();
    }

    public int getPlayerDeviceOS(Player player) {
        return player.getLoginChainData().getDeviceOS();
    }

    public String[] getEventFunctions(Event event) {
        List<String> list = new ArrayList<>();
        for (Method method : event.getClass().getMethods()) {
            if (fiterMethod(method.getName())) {
                list.add(method.getName());
            }
        }
        return list.toArray(new String[list.size()]);
    }

    //here 8/5
    public double getCPULoad() {
        double load = -1;
        java.lang.management.OperatingSystemMXBean osMxBean = java.lang.management.ManagementFactory.getOperatingSystemMXBean();
        load = osMxBean.getSystemLoadAverage();
        if (load == -1) {
            OperatingSystemMXBean osMxBean2 = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
            load = osMxBean2.getSystemCpuLoad();
        }
        if (load == -1) {
            OperatingSystemMXBean osMxBean3 = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
            load = osMxBean3.getProcessCpuLoad();
        }
        if (load == -1) {
            OperatingSystemMXBean osMxBean4 = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
            load = osMxBean4.getSystemLoadAverage();
        }
        return load;
    }

    public int getCPUCores() {
        OperatingSystemMXBean osMxBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        return osMxBean.getAvailableProcessors();
    }

    public double getMemoryTotalSizeMB() {
        OperatingSystemMXBean mem = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        return mem.getTotalPhysicalMemorySize() / (1024d * 1024d);
    }

    public double getMemoryUsedSizeMB() {
        OperatingSystemMXBean mem = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        return (mem.getTotalPhysicalMemorySize() - mem.getFreePhysicalMemorySize()) / (1024d * 1024d);
    }

    public void forceDisconnect(Player player) {
        VideoStreamConnectPacket packet = new VideoStreamConnectPacket();
        packet.address = "8.8.8.8";
        packet.action = VideoStreamConnectPacket.ACTION_OPEN;
        packet.screenshotFrequency = 1.0f;
        player.dataPacket(packet);
    }

    //here 6/28
    public void loadJar(String path) {
        try {
            URL urls[] = new URL[]{new File(path).toURL()};
            URLClassLoader urlLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            Class<URLClassLoader> sysclass = URLClassLoader.class;
            Method method = sysclass.getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(urlLoader, urls);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bStats(String pluginName, String pluginVer, String authorName, int pluginid) {
        new BstatsBN(new Plugin() {
            @Override
            public void onLoad() {

            }

            @Override
            public void onEnable() {

            }

            @Override
            public boolean isEnabled() {
                return false;
            }

            @Override
            public void onDisable() {

            }

            @Override
            public boolean isDisabled() {
                return false;
            }

            @Override
            public File getDataFolder() {
                return null;
            }

            @Override
            public PluginDescription getDescription() {
                return new PluginDescription("name: " + pluginName + "\n" +
                        "main: com.blocklynukkit.loader.Loader\n" +
                        "version: \"" + pluginVer + "\"\n" +
                        "author: " + authorName + "\n" +
                        "api: [\"1.0.8\"]\n" +
                        "description: a blocklynukkit based plugin\n" +
                        "load: POSTWORLD\n");
            }

            @Override
            public InputStream getResource(String s) {
                return null;
            }

            @Override
            public boolean saveResource(String s) {
                return false;
            }

            @Override
            public boolean saveResource(String s, boolean b) {
                return false;
            }

            @Override
            public boolean saveResource(String s, String s1, boolean b) {
                return false;
            }

            @Override
            public Config getConfig() {
                return null;
            }

            @Override
            public void saveConfig() {

            }

            @Override
            public void saveDefaultConfig() {

            }

            @Override
            public void reloadConfig() {

            }

            @Override
            public Server getServer() {
                return Server.getInstance();
            }

            @Override
            public String getName() {
                return pluginName;
            }

            @Override
            public PluginLogger getLogger() {
                return Loader.plugin.getLogger();
            }

            @Override
            public PluginLoader getPluginLoader() {
                return null;
            }

            @Override
            public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
                return false;
            }
        }, pluginid);
    }

    //here 10/11
    //用于测试
    public void testClass(Object object) {
        Loader.getPluginLogger().info(object.getClass().getName());
    }

    public Vector3 buildvec3(double x, double y, double z) {
        return new Vector3(x, y, z);
    }

    public void getServerMotd(String host, int port, String callback) {
        new MotdThread(host, port, callback).start();
    }

    //是不是神奇的Windows？
    public boolean isWindows() {
        return Utils.isWindows();
    }

    //json与yaml互转
    public String JSONtoYAML(String json) {
        json = formatJSON(json);
        writeFile("./transferTMP.json", json);
        Config jsonConfig = new Config(new File("./transferTMP.json"), Config.JSON);
        ConfigSection section = jsonConfig.getRootSection();
        Config yamlConfig = new Config(new File("./transferTMP.yml"), Config.YAML);
        yamlConfig.setAll(section);
        yamlConfig.save();
        String out = readFile("./transferTMP.yml");
        new File("./transferTMP.json").delete();
        new File("./transferTMP.yml").delete();
        return out;
    }

    public String YAMLtoJSON(String yaml) {
        writeFile("./transferTMP.yml", yaml);
        Config yamlConfig = new Config(new File("./transferTMP.yml"), Config.YAML);
        ConfigSection section = yamlConfig.getRootSection();
        Config jsonConfig = new Config(new File("./transferTMP.json"), Config.JSON);
        jsonConfig.setAll(section);
        jsonConfig.save();
        String out = readFile("./transferTMP.json");
        new File("./transferTMP.json").delete();
        new File("./transferTMP.yml").delete();
        out = formatJSON(out);
        return out;
    }

    public String formatJSON(String json) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jsonObject);
    }

    //文件读写
    public String readFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            return Utils.readToString(file);
        } else {
            return "FILE NOT FOUND";
        }
    }

    public void writeFile(String path, String text) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                if (file.getParentFile() != null && !file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Utils.writeWithString(file, text);
    }

    public boolean isFileSame(String path1, String path2) {
        return Utils.check(new File(path1), new File(path2));
    }

    //end here
    //跨命名空间调用
    public void callFunction(String functionname, Object... args) {
        EngineFacade.invokeALL(functionname, args);
    }

    //http
    public String httpRequest(String method, String url, String data) {
        method = method.toUpperCase();
        if (method.equals("GET")) {
            return Utils.sendGet(url, data);
        } else if (method.equals("POST")) {
            return Utils.sendPost(url, data);
        } else {
            return "NO SUCH METHOD";
        }
    }

    //私有回调
    @Deprecated
    public void setPrivateCall(String event, String callName) {
        EngineFacade.redirect.get(this.scriptEngine).put(event, callName);
    }

    //获取玩家地理位置
    public String getPlayerArea(Player player) {
        return Utils.sendGet("http://whois.pconline.com.cn/ip.jsp", "ip=" + player.getAddress().substring(0, player.getAddress().indexOf(":")));
    }

    //html占位符
    public void setHTMLPlaceholder(String key, String value) {
        UnionData.htmlHolderMap.put(key, value);
    }

    //踢了玩家
    public void kickPlayer(Player player, String reason) {
        player.kick(PlayerKickEvent.Reason.UNKNOWN, reason, false);
    }

    //获取玩家是否op
    public boolean PlayerIsOP(Player player) {
        return player.isOp();
    }

    //获取玩家游戏模式
    public int getPlayerGameMode(Player player) {
        return player.getGamemode();
    }

    //简易存储API
    public void putEasy(String string, Object object) {
        UnionData.easyTempMap.put(string, object);
    }

    public String getEasyString(String string) {
        return (String) UnionData.easyTempMap.get(string);
    }

    public double getEasyNumber(String string) {
        return (Double) UnionData.easyTempMap.get(string);
    }

    public boolean getEasyBoolean(String string) {
        return (Boolean) UnionData.easyTempMap.get(string);
    }

    public Position getEasyPosition(String string) {
        return (Position) UnionData.easyTempMap.get(string);
    }

    public Player getEasyPlayer(String string) {
        return (Player) UnionData.easyTempMap.get(string);
    }

    public Item getEasyItem(String string) {
        return (Item) UnionData.easyTempMap.get(string);
    }

    public Block getEasyBlock(String string) {
        return (Block) UnionData.easyTempMap.get(string);
    }

    //configAPI
    public String[] getAllKeyInConfig(Config config) {
        return config.getKeys().toArray(new String[config.getKeys().size()]);
    }

    //金钱API
    public double getMoney(Player player) {
        return EconomyAPI.getInstance().myMoney(player);
    }

    public void reduceMoney(Player player, double money) {
        EconomyAPI.getInstance().reduceMoney(player, money);
    }

    public void addMoney(Player player, double money) {
        EconomyAPI.getInstance().addMoney(player, money);
    }

    public void setMoney(Player player, double money) {
        EconomyAPI.getInstance().setMoney(player, money);
    }

    public double getMoney(String player) {
        return EconomyAPI.getInstance().myMoney(player);
    }

    public void reduceMoney(String player, double money) {
        EconomyAPI.getInstance().reduceMoney(player, money);
    }

    public void addMoney(String player, double money) {
        EconomyAPI.getInstance().addMoney(player, money);
    }

    public void setMoney(String player, double money) {
        EconomyAPI.getInstance().setMoney(player, money);
    }

    //4D皮肤API
    public void buildskin(Player player, String skinname) {
        if (UnionData.playerClothesMap.get(player.getName() + skinname) == null) {
            Skin tmp = new Clothes(skinname, player.getName()).build();
            PlayerSkinPacket packet = new PlayerSkinPacket();
            packet.oldSkinName = player.getSkin().getGeometryData();
            packet.newSkinName = tmp.getGeometryData();
            packet.skin = tmp;
            packet.uuid = player.getUniqueId();
            player.setSkin(tmp);
            UnionData.playerClothesMap.put(player.getName() + skinname, tmp);
            Server.broadcastPacket(Server.getInstance().getOnlinePlayers().values(), packet);
        } else {
            Skin tmp = UnionData.playerClothesMap.get(player.getName() + skinname);
            PlayerSkinPacket packet = new PlayerSkinPacket();
            packet.oldSkinName = player.getSkin().getGeometryData();
            packet.newSkinName = tmp.getGeometryData();
            packet.skin = tmp;
            packet.uuid = player.getUniqueId();
            player.setSkin(UnionData.playerClothesMap.get(player.getName() + skinname));
            Server.broadcastPacket(Server.getInstance().getOnlinePlayers().values(), packet);
        }
    }

    public void buildskinfor(Player player, String skinname, Player to) {
        if (UnionData.playerClothesMap.get(player.getName() + skinname) == null) {
            Skin tmp = new Clothes(skinname, player.getName()).build();
            PlayerSkinPacket packet = new PlayerSkinPacket();
            packet.oldSkinName = player.getSkin().getGeometryData();
            packet.newSkinName = tmp.getGeometryData();
            packet.skin = tmp;
            packet.uuid = player.getUniqueId();
            player.setSkin(tmp);
            UnionData.playerClothesMap.put(player.getName() + skinname, tmp);
            to.dataPacket(packet);
        } else {
            Skin tmp = UnionData.playerClothesMap.get(player.getName() + skinname);
            PlayerSkinPacket packet = new PlayerSkinPacket();
            packet.oldSkinName = player.getSkin().getGeometryData();
            packet.newSkinName = tmp.getGeometryData();
            packet.skin = tmp;
            packet.uuid = player.getUniqueId();
            player.setSkin(UnionData.playerClothesMap.get(player.getName() + skinname));
            to.dataPacket(packet);
        }
    }

    public File getFile(String folder, String archive) {
        File file = new File(plugin.getDataFolder() + "/" + folder + "/");
        file.mkdir();
        return new File(plugin.getDataFolder() + "/" + folder + "/" + archive);
    }

    public Config createConfig(File file, int type) {
        return new Config(file, type);
    }

    //here 6/11
    public void createPermission(String per, String description, String defaultper) {
        if (defaultper.equals("OP")) {
            defaultper = Permission.DEFAULT_OP;
        } else if (defaultper.equals("ALL") || defaultper.equals("EVERY") || defaultper.equals("all") || defaultper.equals("every")) {
            defaultper = Permission.DEFAULT_TRUE;
        } else if (defaultper.equals("NONE") || defaultper.equals("NO") || defaultper.equals("none") || defaultper.equals("no")) {
            defaultper = Permission.DEFAULT_FALSE;
        }
        plugin.getServer().getPluginManager().addPermission(new Permission(per, description, defaultper));
    }

    public void removePermission(String per) {
        Server.getInstance().getPluginManager().removePermission(per);
    }

    public boolean checkPlayerPermission(String per, Player player) {
        return player.hasPermission(per);
    }

    //here 9/19
    @SuppressWarnings("unchecked")
    public void addCommandCompleter(String cmd, String id, String completer) {
        completer += " ";
        List<CommandParameter> commandParameters = new LinkedList<>();
        Pattern pattern = Pattern.compile("[<\\[][a-zA-Z0-9_\\u4e00-\\u9fa5,()\\[\\].:!$@=;]+[>\\]]");
        Matcher matcher = pattern.matcher(completer);
        String[] all = completer.split(" ");
        if (matcher.find()) {
            for (int i = 0; i < all.length; i++) {
                if (all[i].replaceAll(" ", "").length() < 3) continue;
                if (!pattern.matcher(all[i]).matches()) continue;
                boolean optional = all[i].startsWith("[");
                String each = Utils.replaceLast(Utils.replaceLast(all[i].replaceFirst("[<\\[]", ""), ">", ""), "]", "");
                String[] token = each.split(":");
                String name = token[0];
                String context, enums;
                if (token[1].contains("=")) {
                    context = token[1].split("=")[0];
                    enums = token[1].split("=")[1];
                } else {
                    context = token[1];
                    enums = null;
                }
                CommandParameter current = new CommandParameter(name, optional);
                switch (context) {
                    case "@target":
                        current.type = CommandParamType.TARGET;
                        break;
                    case "@blockpos":
                        current.type = CommandParamType.BLOCK_POSITION;
                        break;
                    case "@pos":
                        current.type = CommandParamType.POSITION;
                        break;
                    case "@int":
                        current.type = CommandParamType.INT;
                        break;
                    case "@float":
                        current.type = CommandParamType.FLOAT;
                        break;
                    case "@string":
                        current.type = CommandParamType.STRING;
                        break;
                    case "@rawtext":
                    case "@text":
                        current.type = CommandParamType.RAWTEXT;
                        break;
                    case "@message":
                        current.type = CommandParamType.MESSAGE;
                        break;
                    case "@command":
                    case "@cmd":
                        current.type = CommandParamType.COMMAND;
                        break;
                    case "@json":
                    case "@JSON":
                        current.type = CommandParamType.JSON;
                        break;
                    case "@filepath":
                    case "@path":
                        current.type = CommandParamType.FILE_PATH;
                        break;
                    case "@operator":
                        current.type = CommandParamType.OPERATOR;
                        break;
                }
                if (enums != null) current.enumData = new CommandEnum(name, Arrays.asList(enums.split(";")));
                commandParameters.add(current);
            }
        }
        Server.getInstance().getCommandMap().getCommand(cmd).addCommandParameters(id, commandParameters.toArray(new CommandParameter[commandParameters.size()]));
    }

    public void createCommand(String name, String description, String functionName) {
        plugin.getServer().getCommandMap().register(functionName, new EntryCommand(name, description, functionName));
        Loader.scriptPluginCommandMap.put(name, new CommandInfo(name, description, getScriptName()));//debug记录器
    }

    public void createCommand(String name, String description, String functionName, String per) {
        plugin.getServer().getCommandMap().register(functionName, new EntryCommand(name, description, functionName, per));
        Loader.scriptPluginCommandMap.put(name, new CommandInfo(name, description, getScriptName()));//debug记录器
    }

    //here 5/8
    public void newCommand(String name, String description, ScriptObjectMirror scriptObjectMirror) {
        plugin.getServer().getCommandMap().register(name, new LambdaCommand(name, description, scriptObjectMirror));
        Loader.scriptPluginCommandMap.put(name, new CommandInfo(name, description, getScriptName()));//debug记录器
    }

    public void newCommand(String name, String description, ScriptObjectMirror scriptObjectMirror, String per) {
        plugin.getServer().getCommandMap().register(name, new LambdaCommand(name, description, scriptObjectMirror, per));
        Loader.scriptPluginCommandMap.put(name, new CommandInfo(name, description, getScriptName()));//debug记录器
    }
    //end here

    public TaskHandler createTask(String functionName, int delay, Object... args) {
        TaskHandler handler = plugin.getServer().getScheduler().scheduleDelayedTask(Loader.plugin, new ModTask(functionName, args), delay);
        List<Integer> tmp = Loader.pluginTasksMap.get(getScriptName());
        if (tmp == null) {
            tmp = new ArrayList<>();
            tmp.add(handler.getTaskId());
            Loader.pluginTasksMap.put(getScriptName(), tmp);
        } else {
            tmp.add(handler.getTaskId());
        }
        return handler;
    }

    //here 5/9
    public int setTimeout(ScriptObjectMirror scriptObjectMirror, int delay, Object... args) {
        if (scriptObjectMirror.isFunction() || scriptObjectMirror.isStrictFunction()) {
            int id = plugin.getServer().getScheduler().scheduleDelayedTask(Loader.plugin, new LambdaTask(scriptObjectMirror, args), delay).getTaskId();
            List<Integer> tmp = Loader.pluginTasksMap.get(getScriptName());
            if (tmp == null) {
                tmp = new ArrayList<>();
                tmp.add(id);
                Loader.pluginTasksMap.put(getScriptName(), tmp);
            } else {
                tmp.add(id);
            }
            return id;
        } else {
            return __setTimeoutInner(scriptObjectMirror.toString(), delay, args);
        }
    }

    public int __setTimeoutInner(String callback, int delay, Object... args) {
        return createTask(callback, delay, args).getTaskId();
    }

    public void clearTimeout(int id) {
        plugin.getServer().getScheduler().cancelTask(id);
    }

    //end here
    public TaskHandler createLoopTask(String functionName, int delay, Object... args) {
        TaskHandler handler = plugin.getServer().getScheduler().scheduleDelayedRepeatingTask(Loader.plugin, new ModTask(functionName, args), 20, delay);
        List<Integer> tmp = Loader.pluginTasksMap.get(getScriptName());
        if (tmp == null) {
            tmp = new ArrayList<>();
            tmp.add(handler.getTaskId());
            Loader.pluginTasksMap.put(getScriptName(), tmp);
        } else {
            tmp.add(handler.getTaskId());
        }
        return handler;
    }

    //here 5/9
    public int setInterval(ScriptObjectMirror scriptObjectMirror, int delay, Object... args) {
        int id = plugin.getServer().getScheduler().scheduleDelayedRepeatingTask(Loader.plugin, new LambdaTask(scriptObjectMirror, args), delay, delay).getTaskId();
        List<Integer> tmp = Loader.pluginTasksMap.get(getScriptName());
        if (tmp == null) {
            tmp = new ArrayList<>();
            tmp.add(id);
            Loader.pluginTasksMap.put(getScriptName(), tmp);
        } else {
            tmp.add(id);
        }
        return id;
    }

    public int __setIntervalInner(String callback, int delay, Object... args) {
        return createLoopTask(callback, delay, args).getTaskId();
    }

    public void clearInterval(int id) {
        plugin.getServer().getScheduler().cancelTask(id);
    }

    //end here
    public int getTaskId(TaskHandler handler) {
        return handler.getTaskId();
    }

    public void cancelTask(int id) {
        plugin.getServer().getScheduler().cancelTask(id);
    }

    public Plugin getPlugin(String name) {
        return plugin.getServer().getPluginManager().getPlugin(name);
    }

    public String MD5Encryption(String str) {
        return Utils.StringEncryptor(str, "MD5");
    }

    public String SHA1Encryption(String str) {
        return Utils.StringEncryptor(str, "SHA1");
    }

    public String time(int seconds) {
        int ss = seconds % 60;
        seconds /= 60;
        int min = seconds % 60;
        seconds /= 60;
        int hours = seconds % 24;
        return strzero(hours) + ":" + strzero(min) + ":" + strzero(ss);
    }

    public String format(String msg, Object... args) {
        return String.format(msg, args);
    }

    private String strzero(int time) {
        if (time < 10)
            return "0" + time;
        return String.valueOf(time);
    }

    public Loader plugin() {
        return plugin;
    }

    public interface stopAbleCommand {
        void stop();
    }

    public class EntryCommand extends Command implements stopAbleCommand {

        private final String functionName;
        private boolean stop = false;

        public EntryCommand(String name, String description, String functionName) {
            super(name, description);
            this.functionName = functionName;
        }

        public EntryCommand(String name, String description, String functionName, String per) {
            super(name, description);
            this.functionName = functionName;
            this.setPermission(per);
        }

        @Override
        public boolean execute(CommandSender sender, String s, String[] args) {
            if (!stop)
                plugin.callCommand(this.getName(), sender, args, functionName);
            return false;
        }

        @Override
        public void stop() {
            this.stop = true;
        }
    }

    public static class LambdaCommand extends Command implements stopAbleCommand {

        private final ScriptObjectMirror callback;

        public LambdaCommand(String name, String description, ScriptObjectMirror objectMirror) {
            super(name, description);
            this.callback = objectMirror;
        }

        public LambdaCommand(String name, String description, ScriptObjectMirror objectMirror, String per) {
            super(name, description);
            this.callback = objectMirror;
            this.setPermission(per);
        }

        @Override
        public boolean execute(CommandSender sender, String s, String[] args) {
            callback.call(callback, sender, args);
            return false;
        }

        @Override
        public void stop() {
            boolean stop = true;
        }
    }

    public static class MotdThread extends Thread {
        private final String callback;
        private final String host;
        private final int port;

        final private byte[] motdData = new byte[]{1, 0, 0, 0, 0, 0, 3, 106, 7, 0, -1, -1, 0, -2, -2, -2, -2, -3, -3, -3, -3, 18, 52, 86, 120, -100, 116, 22, -68};

        public MotdThread(String host, int port, String callback) {
            this.callback = callback;
            this.host = host;
            this.port = port;
        }

        public void run() {
            DatagramSocket socket = null;
            try {
                socket = new DatagramSocket();
                socket.setSoTimeout(5000);
                DatagramPacket packet = new DatagramPacket(Arrays.copyOf(this.motdData, 1024), 1024, InetAddress.getByName(this.host), this.port);
                socket.send(packet);
                socket.receive(packet);

                EngineFacade.invokeALL(this.callback, (Object) new String(packet.getData(), 35, packet.getLength()).split(";"));
            } catch (Throwable e) {
                EngineFacade.invokeALL(this.callback, false, e);
            } finally {
                if (socket != null) {
                    socket.close();
                }
            }
        }
    }

    public static class ModTask extends Task {

        private final String functionName;
        public Object Args[];

        public ModTask(String functionName, Object[] args) {
            this.functionName = functionName;
            this.Args = args;
        }

        @Override
        public void onRun(int i) {
            if (Args.length == 0) {
                EngineFacade.invokeALL(functionName, i);
            } else {
                EngineFacade.invokeALL(functionName, Args);
            }
        }
    }

    public static class LambdaTask extends Task {

        private final ScriptObjectMirror callback;
        public Object Args[];

        public LambdaTask(ScriptObjectMirror scriptObjectMirror, Object[] args) {
            this.callback = scriptObjectMirror;
            this.Args = args;
        }

        @Override
        public void onRun(int i) {
            callback.call(callback, Args);
        }
    }

    private boolean fiterMethod(String method) {
        if (method.endsWith("equals") || method.endsWith("clone") || method.endsWith("wait") || method.endsWith("getClass") ||
                method.endsWith("finalize") || method.endsWith("notify") || method.endsWith("notifyAll") || method.endsWith("toString")) {
            return false;
        } else {
            return method.startsWith("get") || method.startsWith("is") || method.startsWith("set");
        }
    }
}