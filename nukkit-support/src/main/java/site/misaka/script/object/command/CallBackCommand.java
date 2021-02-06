package site.misaka.script.object.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import lombok.Getter;
import site.misaka.engine.EngineAdapter;

//TODO
public class CallBackCommand extends Command {
    @Getter
    private final EngineAdapter adapter;
    @Getter
    private final CommandInfo builder;

    public CallBackCommand(CommandInfo builder, EngineAdapter adapter) {
        super(builder.getName(), builder.getDescription());
        this.builder = builder;
        this.adapter = adapter;
        if (this.builder.getPermission() != null) {
            this.setPermission(this.builder.getPermission());
        }
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        this.adapter.invoke(this.builder.getCallback(), sender, commandLabel, args);
        return true;
    }
}
