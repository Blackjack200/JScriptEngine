package site.misaka.script.object.slapper;

import cn.nukkit.entity.EntityHuman;
import cn.nukkit.level.Position;
import cn.nukkit.nbt.tag.CompoundTag;
import site.misaka.script.object.ComplexObject;

public class HumanSlapper extends EntityHuman {
	private final HumanSlapperHook hook;
	private final ComplexObject object;

	public HumanSlapper(ComplexObject object, Position position, String name, CompoundTag tag, HumanSlapperHook hook) {
		super(position.getLevel().getChunk((
				(int) position.getX()) >> 4, ((int) position.getZ()) >> 4), tag);
		this.setScale(1F);
		this.setNameTag(name);
		this.setHealth(this.getMaxHealth());
		this.setNameTagVisible(true);
		this.setNameTagAlwaysVisible(true);
		this.hook = hook;
		this.object = object;
	}

	@Override
	public boolean entityBaseTick() {
		if (this.hook.getEntityBaseTick() != null && !this.hook.getEntityBaseTick().isEmpty()) {
			this.object.getAdapter().invoke(this.hook.getEntityBaseTick(), this);
		}
		return super.entityBaseTick();
	}

	@Override
	public boolean attack(float damage) {
		if (this.hook.getAttack() != null && !this.hook.getAttack().isEmpty()) {
			this.object.getAdapter().invoke(this.hook.getAttack(), this, damage);
		}
		return super.attack(damage);
	}

	@Override
	public boolean onUpdate(int currentTick) {
		if (this.hook.getOnUpdate() != null && !this.hook.getOnUpdate().isEmpty()) {
			this.object.getAdapter().invoke(this.hook.getOnUpdate(), this, currentTick);
		}
		return super.onUpdate(currentTick);
	}
}