package site.misaka.blocklynukkit.script.object.command;

import lombok.Builder;
import lombok.Getter;

@Builder
public class CommandInfo {
    @Getter
    private final String name;
    @Getter
    private final String description;
    @Getter
    private final String callback;
    @Getter
    private final String permission;
}
