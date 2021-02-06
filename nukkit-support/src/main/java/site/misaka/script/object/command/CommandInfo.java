package site.misaka.script.object.command;

import lombok.Builder;
import lombok.Getter;

//TODO
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
