package com.blocklynukkit.loader.other;

import cn.nukkit.Server;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.service.ServicePriority;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class BstatsBN {
    public static final int B_STATS_VERSION = 1;
    public Plugin plugin;
    public int pluginId;
    public BstatsBN(Plugin plugin,int pluginId){
        this.plugin=plugin;
        this.pluginId=pluginId;
        Server.getInstance().getServiceManager().register(BstatsBN.class,this,plugin, ServicePriority.NORMAL);
    }
    public JsonObject getPluginData() {
        JsonObject data = new JsonObject();

        String pluginName = plugin.getName();
        String pluginVersion = plugin.getDescription().getVersion();

        data.addProperty("pluginName", pluginName); // Append the name of the plugin
        data.addProperty("id", pluginId); // Append the id of the plugin
        data.addProperty("pluginVersion", pluginVersion); // Append the version of the plugin

        JsonArray customCharts = new JsonArray();
        data.add("customCharts", customCharts);

        return data;
    }
}
