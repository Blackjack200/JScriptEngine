package site.misaka.script.adapter;

import cn.nukkit.plugin.Plugin;
import com.google.common.util.concurrent.AtomicDouble;
import site.misaka.engine.EngineAdapter;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ThreadUtils extends AbstractUtils {
	public ThreadUtils(Plugin plugin, String scriptName, EngineAdapter adapter) {
		super(plugin, scriptName, adapter);
	}

	public Thread create(Runnable runnable) {
		return new Thread(runnable);
	}

	public synchronized void synchronize(Runnable runnable) {
		synchronized (this) {
			runnable.run();
		}
	}

	public AtomicInteger aInteger(int i) {
		return new AtomicInteger(i);
	}

	public AtomicLong aLong(long l) {
		return new AtomicLong(l);
	}

	public AtomicDouble aDouble(double d) {
		return new AtomicDouble(d);
	}

	public AtomicBoolean aBoolean(boolean b) {
		return new AtomicBoolean(b);
	}
}
