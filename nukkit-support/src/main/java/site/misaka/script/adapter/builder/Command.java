package site.misaka.script.adapter.builder;

import lombok.Builder;
import lombok.Getter;

//TODO
@Builder
public class Command {
	@Getter
	private final String name;
	@Getter
	private final String description;

	@Getter
	private final String callback;
}
