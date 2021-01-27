package site.misaka.script.object;

import cn.nukkit.Server;
import cn.nukkit.plugin.Plugin;
import site.misaka.engine.EngineAdapter;
import site.misaka.script.UnionData;
import site.misaka.script.object.command.CallBackCommand;
import site.misaka.script.object.command.CommandInfo;

//TODO
public class CommandObject extends AbstractObject {

	public CommandObject(Plugin plugin, String scriptName, EngineAdapter adapter) {
		super(plugin, scriptName, adapter);
	}

	public CommandInfo.CommandInfoBuilder createBuilder() {
		return CommandInfo.builder();
	}

	public void register(CommandInfo command) {
		CallBackCommand cal = new CallBackCommand(command, this.getAdapter());
		UnionData.getCommandMap().put(command, cal);
		Server.getInstance().getCommandMap().register(command.getName(), cal);
	}
}
