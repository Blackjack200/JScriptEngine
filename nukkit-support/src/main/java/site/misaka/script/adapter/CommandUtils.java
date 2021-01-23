package site.misaka.script.adapter;

import cn.nukkit.Server;
import cn.nukkit.plugin.Plugin;
import site.misaka.engine.EngineAdapter;
import site.misaka.process.UnionData;
import site.misaka.script.adapter.builder.Command;

//TODO
public class CommandUtils extends AbstractUtils {

	public CommandUtils(Plugin plugin, String scriptName, EngineAdapter adapter) {
		super(plugin, scriptName, adapter);
	}

	public Command.CommandBuilder createBuilder() {
		return Command.builder();
	}

	public void register(Command command) {
		CallBackCommand cal = new CallBackCommand(command, this.getAdapter());
		UnionData.getCommandMap().put(command, cal);
		Server.getInstance().getCommandMap().register(command.getName(), cal);
	}
}
