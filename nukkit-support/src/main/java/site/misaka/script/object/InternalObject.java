package site.misaka.script.object;

import cn.nukkit.math.NukkitMath;
import cn.nukkit.plugin.Plugin;
import com.sun.management.OperatingSystemMXBean;
import site.misaka.engine.EngineAdapter;

import java.lang.management.ManagementFactory;

public class InternalObject extends AbstractObject {
    public InternalObject(Plugin plugin, String scriptName, EngineAdapter adapter) {
        super(plugin, scriptName, adapter);
    }

    public double getSystemCPULoad() {
        return ((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getSystemCpuLoad();
    }

    public int getAvailableProcessorCount() {
        return Runtime.getRuntime().availableProcessors();
    }

    public double getMemoryTotalSizeMB() {
        return NukkitMath.round((Runtime.getRuntime().totalMemory()) / 1024D / 1024, 2);
    }

    public double getMemoryUsedSizeMB() {
        return NukkitMath.round((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024D / 1024, 2);
    }
}
