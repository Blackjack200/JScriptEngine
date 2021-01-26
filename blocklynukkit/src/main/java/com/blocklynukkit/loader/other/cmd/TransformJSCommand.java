package com.blocklynukkit.loader.other.cmd;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

public class TransformJSCommand extends Command {
    public TransformJSCommand() {
        super("transformjs", "将es2020-es2015的高版本es转译到bn支持的es5上", "transformjs");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        //TODO: 完成此命令
        return false;
    }
}
