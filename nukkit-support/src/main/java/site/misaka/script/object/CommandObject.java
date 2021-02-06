package site.misaka.script.object;

import cn.nukkit.Server;
import cn.nukkit.permission.Permission;
import cn.nukkit.plugin.Plugin;
import site.misaka.engine.EngineAdapter;
import site.misaka.script.UnionData;
import site.misaka.script.object.command.CallBackCommand;
import site.misaka.script.object.command.CommandInfo;

import java.util.Locale;

//TODO
public class CommandObject extends AbstractObject {

    public CommandObject(Plugin plugin, String scriptName, EngineAdapter adapter) {
        super(plugin, scriptName, adapter);
    }

    public CommandInfo.CommandInfoBuilder createBuilder() {
        return CommandInfo.builder();
    }

    public CommandInfo createInfo(String name, String description, String callback, String permission) {
        return this.createBuilder().name(name).description(description).callback(callback).permission(permission).build();
    }

    public CommandInfo createInfo(String name, String description, String callback) {
        return this.createBuilder().name(name).description(description).callback(callback).permission(null).build();
    }

    public void register(CommandInfo command) {
        CallBackCommand cal = new CallBackCommand(command, this.getAdapter());
        UnionData.getCommandMap().put(command, cal);
        Server.getInstance().getCommandMap().register(command.getName(), cal);
    }

    public void createPermission(String permission, String description, String _default) {
        switch (_default.toUpperCase(Locale.ROOT)) {
            case "OP":
            case "ADMIN":
                _default = Permission.DEFAULT_OP;
                break;
            case "ALL":
            case "EVERY":
            case "TRUE":
                _default = Permission.DEFAULT_TRUE;
                break;
            case "FALSE":
            case "NONE":
            case "NO":
                _default = Permission.DEFAULT_FALSE;
                break;
            case "NOT_OP":
                _default = Permission.DEFAULT_NOT_OP;

        }
        Server.getInstance().getPluginManager().addPermission(new Permission(permission, description, _default));
    }

    public void removePermission(String permission) {
        Server.getInstance().getPluginManager().removePermission(permission);
    }
}
