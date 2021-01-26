package site.misaka.blocklynukkit.script.object.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import lombok.Getter;
import site.misaka.engine.EngineAdapter;

public class CallBackCommand extends Command {
    @Getter
    private final EngineAdapter adapter;
    @Getter
    private final CommandInfo builder;

    public CallBackCommand(CommandInfo builder, EngineAdapter adapter) {
        super(builder.getName(), builder.getDescription());
        this.builder = builder;
        this.adapter = adapter;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (this.builder.getPermission() != null && !sender.hasPermission(this.builder.getPermission())) {
            return true;
        }
        this.getAdapter().invoke(this.builder.getCallback(), sender, args);
        return true;
    }
}

