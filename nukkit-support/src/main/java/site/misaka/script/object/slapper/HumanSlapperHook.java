package site.misaka.script.object.slapper;

import lombok.Builder;
import lombok.Getter;

@Builder
public class HumanSlapperHook {
	@Getter
	private String onUpdate;
	@Getter
	private String attack;
	@Getter
	private String entityBaseTick;
}
