package com.blocklynukkit;

import cn.nukkit.Server;
import com.blocklynukkit.loader.script.*;

public class JavaAPI {
    public AlgorithmManager algorithm = new AlgorithmManager(null);
    public BlockItemManager blockitem = new BlockItemManager(null);
    public DatabaseManager database = new DatabaseManager(null);
    public EntityManager entity = new EntityManager(null);
    public FunctionManager function = new FunctionManager(null);
    public FunctionManager manager = function;
    public GameManager gameapi = Server.getInstance().getPluginManager().getPlugins().containsKey("GameAPI") ? new GameManager(null) : null;
    public InventoryManager inventory = new InventoryManager(null);
    public LevelManager world = new LevelManager(null);
    public NotemusicManager notemusic = new NotemusicManager(null);
    public ParticleManager particle = new ParticleManager(null);
    public WindowManager window = new WindowManager(null);

    public JavaAPI() {
    }
}
