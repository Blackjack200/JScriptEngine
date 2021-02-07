package site.misaka.mirai.script.object;

import com.sun.management.OperatingSystemMXBean;
import site.misaka.engine.EngineAdapter;

import java.lang.management.ManagementFactory;

public class InternalObject extends AbstractObject {
    public InternalObject(String path, String scriptName, EngineAdapter adapter) {
        super(path, scriptName, adapter);
    }

    public double getSystemCPULoad() {
        return ((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getSystemCpuLoad();
    }

    public int getAvailableProcessorCount() {
        return Runtime.getRuntime().availableProcessors();
    }

    public long getMemoryTotalSizeMB() {
        return Math.round((Runtime.getRuntime().totalMemory()) / 1024D / 1024);
    }

    public long getMemoryUsedSizeMB() {
        return Math.round((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024D / 1024);
    }
}
