package com.blocklynukkit.loader;

import cn.nukkit.entity.data.Skin;
import cn.nukkit.item.Item;
import site.misaka.engine.EngineAdapter;

import javax.script.ScriptEngine;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class UnionData {
    public static Map<String, ScriptEngine> engineMap = new HashMap<>();
    public static Map<String, HashSet<String>> privateEventHooks = new HashMap<>();
    public static Map<String, EngineAdapter> scriptPlugins = new HashMap<>();

    public static Map<Item, Item> furnaceMap = new HashMap<>();
    public static Map<String, Skin> playerClothesMap = new HashMap<>();
    public static Map<String, BufferedImage> skinImageMap = new HashMap<>();
    public static Map<String, String> playerGeoNameMap = new HashMap<>();
    public static Map<String, String> playerGeoJsonMap = new HashMap<>();
    public static Map<String, String[]> mcFunctionMap = new HashMap<>();
    public static Map<String, Object> easyTempMap = new HashMap<>();
    public static Map<String, String> htmlHolderMap = new HashMap<>();

}
