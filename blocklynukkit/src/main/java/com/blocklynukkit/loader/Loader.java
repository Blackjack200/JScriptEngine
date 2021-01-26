package com.blocklynukkit.loader;

import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.Listener;
import cn.nukkit.permission.Permission;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginLogger;
import cn.nukkit.resourcepacks.ResourcePack;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import com.blocklynukkit.loader.other.BNCrafting;
import com.blocklynukkit.loader.other.Babel;
import com.blocklynukkit.loader.other.Entities.BNNPC;
import com.blocklynukkit.loader.other.Entities.FloatingItemManager;
import com.blocklynukkit.loader.other.Entities.FloatingText;
import com.blocklynukkit.loader.other.chemistry.EnableChemistryBlocks;
import com.blocklynukkit.loader.other.cmd.BNPluginsListCommand;
import com.blocklynukkit.loader.other.debug.data.CommandInfo;
import com.blocklynukkit.loader.other.generator.render.BaseRender;
import com.blocklynukkit.loader.other.lizi.BNQQBot;
import com.blocklynukkit.loader.script.EntityManager;
import com.blocklynukkit.loader.script.FunctionManager;
import com.blocklynukkit.loader.script.LevelManager;
import com.blocklynukkit.loader.script.window.windowCallbacks.WindowCallback;
import com.blocklynukkit.loader.utils.MetricsLite;
import com.blocklynukkit.loader.utils.Utils;
import com.sun.net.httpserver.HttpServer;
import com.xxmicloxx.NoteBlockAPI.NoteBlockPlayerMain;
import de.theamychan.scoreboard.network.Scoreboard;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import lombok.SneakyThrows;
import site.misaka.engine.EngineAdapter;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import java.io.File;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Loader extends PluginBase implements Listener {
    public static Loader plugin;
    public static File pluginFile;

    public static String positionsTemp = "";
    public static int checkUpDateTime = 0;


    public static BNCrafting bnCrafting = new BNCrafting();
    public static HttpServer httpServer = null;
    public static EventLoader eventLoader;
    public static FloatingItemManager floatingItemManager = new FloatingItemManager();
    public static NoteBlockPlayerMain noteBlockPlayerMain = new NoteBlockPlayerMain();

    public static Map<String, CommandInfo> scriptPluginCommandMap = new HashMap<>();

    public static Map<String, List<Integer>> pluginTasksMap = new HashMap<>();
    public static Random mainRandom = new Random(System.currentTimeMillis());
    //es2020->es5翻译器
    public static Babel babel = null;
    //windowManager变量
    public static final ConcurrentHashMap<Integer, String> functioncallback = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<Integer, WindowCallback> windowCallbackMap = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Integer, ScriptObjectMirror> scriptObjectMirrorCallback = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, String> serverSettingCallback = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, Boolean> acceptCloseCallback = new ConcurrentHashMap<>();
    public static Map<String, Scoreboard> boards = new HashMap<>();
    public static Map<String, String> tipsVar = new HashMap<>();
    //blockitemManager变量
    public static short registerBlocks = 0;
    public static short registerItems = 0;
    public static List<Integer> registerItemIds = new ArrayList<>();
    //levelManager变量
    public static Map<String, Object> skyLandOptions = new HashMap<>();
    public static int OceanSeaLevel = 64;
    public static List<BaseRender> levelRenderList = new ArrayList<>();
    //functionManager变量
    public static BNQQBot qq = new BNQQBot();
    public static String fakeNukkitCodeVersion = "";

    @SneakyThrows
    @Override
    public void onEnable() {
        plugin = this;
        pluginFile = this.getFile();
        this.checkDependency(this.getServer().getPluginManager().getPlugins());
        //创建各种基对象
        //这里没有database因为后面要检查依赖库是否存在再创建
        //10/25add 现在创建多个基对象，动态创建，无需在插件启动时创建
        //如果没有显式使用bn拓展材质，则启用化学资源包，防止客户端暴毙
        boolean haveBNResourceExtend = false;
        for (ResourcePack pack : this.getServer().getResourcePackManager().getResourceStack()) {
            if (pack.getPackName().contains("BNExtend")) {
                haveBNResourceExtend = true;
                break;
            }
        }
        if (!haveBNResourceExtend) {
            EnableChemistryBlocks.enable();
        }
        //创建红石音乐插件主线程
        noteBlockPlayerMain.onEnable();//if(plugins.containsKey("GameAPI"))gameManager=new GameManager();
        //修改路径类加载器，使得脚本可以调用其他插件
        ClassLoader cl = plugin.getClass().getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);
        //为nashorn js引擎开启es6支持
        System.setProperty("nashorn.args", "--language=es6");

        //加载统计器类
        MetricsLite metricsLite = new MetricsLite(this, 6769);
        //世界生成器初始化
        LevelManager.doreloadSkyLandGeneratorSettings();
        LevelManager.doreloadOceanGeneratorSettings();
        //bn高级合成台模块监听
        this.getServer().getPluginManager().registerEvents(bnCrafting, this);
        //bn浮空物品模块监听
        this.getServer().getPluginManager().registerEvents(floatingItemManager, this);
        //获取云端同步列表并下载
        Config config = new Config(this.getDataFolder() + "/update.yml", Config.YAML);
        if (!config.exists("mods")) {
            config.set("mods", Collections.singletonList("first.js"));
            config.save();
        }
        List<String> list = (List<String>) config.get("mods");
        if (list != null)
            for (String a : list) {
                Utils.download("https://blocklynukkitxml-1259395953.cos.ap-beijing.myqcloud.com/" + a, new File(this.getDataFolder() + "/" + a));
            }
        //创建二级文件夹
        getDataFolder().mkdirs();
        new File(getDataFolder() + "/skin").mkdirs();
        new File(getDataFolder() + "/notemusic").mkdirs();
        new File(getDataFolder() + "/lib").mkdirs();

        //注册事件监听器，驱动事件回调
        this.getServer().getPluginManager().registerEvents(this, this);

        //注册bn的生物实体
        Entity.registerEntity("BNFloatingText", FloatingText.class);
        Entity.registerEntity("BNNPC", BNNPC.class);
        //注册bn命令
        plugin.getServer().getPluginManager().addPermission(new Permission("blocklynukkit.opall", "blocklynukkit插件op权限", "op"));
        //hotreloadjs命令被bnreload替代
        //plugin.getServer().getCommandMap().register("hotreloadjs",new ReloadJSCommand());
        plugin.getServer().getCommandMap().register("bnplugins", new BNPluginsListCommand());

        //开启速建官网服务器
        Config portconfig = new Config(this.getDataFolder() + "/port.yml", Config.YAML);
        int portto;
        if (portconfig.exists("port")) {
            portto = (int) portconfig.get("port");
        } else {
            portconfig.set("port", 8182);
            portto = 8182;
        }
        portconfig.save();
        Utils.makeHttpServer(portto);
        EngineFacade.init();
        try {
            ScriptLoader.scanLoader(this.getDataFolder().toPath());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    //监听bn被卸载事件
    public void onDisable() {
        LevelManager.dosaveSkyLandGeneratorSettings();
        LevelManager.dosaveOceanGeneratorSettings();
        EntityManager.recycleAllFloatingText();
        EntityManager.recycleAllBNNPC();
        if (httpServer != null) {
            httpServer.stop(0);
        }

        Server.getInstance().getScheduler().cancelTask(this);
        System.gc();
    }

    public static PluginLogger getPluginLogger() {
        return plugin.getLogger();
    }

    public static FunctionManager getFunctionManager() {
        return new FunctionManager(null);
    }

    public void checkDependency(Map<String, Plugin> plugins) {
        if (!plugins.containsKey("EconomyAPI")) {
            try {
                Utils.downloadPlugin("https://repo.nukkitx.com/main/me/onebone/economyapi/2.0.0-SNAPSHOT/economyapi-2.0.0-20190517.112309-17.jar");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!plugins.containsKey("KotlinLib")) {
            try {
                Utils.downloadPlugin("https://blocklynukkitxml-1259395953.cos.ap-beijing.myqcloud.com/jar/KotlinLib.jar"); //KotlinLib url
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!plugins.containsKey("PlaceholderAPI")) {
            try {
                if (Server.getInstance().getLanguage().getName().contains("中文"))
                    Loader.getPluginLogger().warning(TextFormat.RED + "您没有安装PlaceholderAPI,虽然不是必须安装，但PlaceHolderAPI前置有些作用，建议您安装，下载地址：https://repo.nukkitx.com/main/com/creeperface/nukkit/placeholderapi/PlaceholderAPI/1.4-SNAPSHOT/PlaceholderAPI-1.4-20200314.133954-18.jar");
                if (!Server.getInstance().getLanguage().getName().contains("中文"))
                    Loader.getPluginLogger().warning(TextFormat.RED + "You haven't installed PlaceholderAPI,although it's not necessary,but PlaceHolderAPI is needed by the moudle inner_http_page_server and moudle scoreboard,we suggest you to install,download link: https://repo.nukkitx.com/main/com/creeperface/nukkit/placeholderapi/PlaceholderAPI/1.4-SNAPSHOT/PlaceholderAPI-1.4-20200314.133954-18.jar");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!plugins.containsKey("FakeInventories")) {
            try {
                Utils.downloadPlugin("https://blocklynukkitxml-1259395953.cos.ap-beijing.myqcloud.com/jar/fakeinventories-1.0.3-SNAPSHOT.jar");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public synchronized void callCommand(String commandName, CommandSender sender, String[] args, String functionName) {
        long start = System.currentTimeMillis();
        for (Map.Entry<String, EngineAdapter> entry : UnionData.scriptPlugins.entrySet()) {
            if (((ScriptEngine) entry.getValue().get()).get(functionName) == null) {
                continue;
            }
            try {
                scriptPluginCommandMap.get(commandName).newCall(System.currentTimeMillis() - start,
                        LocalDateTime.now().toString(), entry.getKey(), functionName, sender.getName(), args);
                entry.getValue().invoke(functionName, sender, args);
            } catch (final Exception se) {
                se.printStackTrace();
            }
        }
    }
}
