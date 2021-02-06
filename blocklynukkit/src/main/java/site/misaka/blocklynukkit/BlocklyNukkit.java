package site.misaka.blocklynukkit;

import cn.nukkit.plugin.PluginBase;
import lombok.Getter;
import lombok.SneakyThrows;
import site.misaka.blocklynukkit.engine.EngineFacade;
import site.misaka.blocklynukkit.script.ScriptLoader;

public class BlocklyNukkit extends PluginBase {
    @Getter
    private static BlocklyNukkit instance;

    @Override
    public void onLoad() {
        instance = this;
        System.setProperty("nashorn.args", "--language=es6");
    }

    @SneakyThrows
    @Override
    public void onEnable() {
        EngineFacade.init();
        ScriptLoader.scanLoader(this.getDataFolder().toPath(), this.getLogger());
    }

    @Override
    public void onDisable() {
        EngineFacade.invokeALL("finalize");
    }
}
