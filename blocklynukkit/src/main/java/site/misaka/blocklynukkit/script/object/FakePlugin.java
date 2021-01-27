package site.misaka.blocklynukkit.script.object;

import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginDescription;
import cn.nukkit.plugin.PluginLoader;
import cn.nukkit.plugin.PluginLogger;
import cn.nukkit.utils.Config;
import site.misaka.blocklynukkit.BlocklyNukkit;

import java.io.File;
import java.io.InputStream;

public class FakePlugin implements Plugin {
    //Highly recommend https://semver.org
    private final String name, author, version;

    public FakePlugin(String name, String author, String version) {
        this.name = name;
        this.author = author;
        this.version = version;
    }

    public void onLoad() {

    }

    public void onEnable() {

    }

    public boolean isEnabled() {
        return false;
    }

    public void onDisable() {

    }

    public boolean isDisabled() {
        return false;
    }

    public File getDataFolder() {
        return null;
    }

    public PluginDescription getDescription() {
        return new PluginDescription("name: " + this.name + "\nmain: null\nversion: \"" + this.version + "\"\nauthor: " + this.author + "\napi: [\"1.0.8\"]\ndescription: JSE_BlocklyNukkit\nload: POSTWORLD\n");
    }

    public InputStream getResource(String s) {
        return null;
    }

    public boolean saveResource(String s) {
        return false;
    }

    public boolean saveResource(String s, boolean b) {
        return false;
    }

    public boolean saveResource(String s, String s1, boolean b) {
        return false;
    }

    public Config getConfig() {
        return null;
    }

    public void saveConfig() {

    }

    public void saveDefaultConfig() {

    }

    public void reloadConfig() {

    }

    public Server getServer() {
        return Server.getInstance();
    }

    public String getName() {
        return this.name;
    }

    public PluginLogger getLogger() {
        return BlocklyNukkit.getInstance().getLogger();
    }

    public PluginLoader getPluginLoader() {
        return null;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }
}
