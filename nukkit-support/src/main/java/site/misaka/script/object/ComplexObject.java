package site.misaka.script.object;

import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.Skin;
import cn.nukkit.item.Item;
import cn.nukkit.level.Position;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.plugin.Plugin;
import com.denzelcode.form.event.CustomFormSubmitEvent;
import com.denzelcode.form.event.ModalFormSubmitEvent;
import com.denzelcode.form.event.SimpleFormButtonClickEvent;
import com.denzelcode.form.window.CustomWindowForm;
import com.denzelcode.form.window.ModalWindowForm;
import com.denzelcode.form.window.SimpleWindowForm;
import site.misaka.engine.EngineAdapter;
import site.misaka.script.object.slapper.HumanSlapper;
import site.misaka.script.object.slapper.HumanSlapperHook;

import java.nio.charset.StandardCharsets;

@SuppressWarnings("unused")
public class ComplexObject extends AbstractObject {
	public ComplexObject(Plugin plugin, String scriptName, EngineAdapter adapter) {
		super(plugin, scriptName, adapter);
	}

	public HumanSlapperHook.HumanSlapperHookBuilder getSlapperBuilder() {
		return HumanSlapperHook.builder();
	}

	public HumanSlapper createSlapper(Position position, String name, Skin skin, HumanSlapperHook hook) {
		return new HumanSlapper(this, position, name, this.nbt(position, skin), hook);
	}

	public HumanSlapper createSlapper(Position position, String name, Skin skin) {
		return new HumanSlapper(this, position, name, this.nbt(position, skin), this.getSlapperBuilder().build());
	}

	public CustomWindowForm customForm(String callback) {
		CustomWindowForm form = new CustomWindowForm();
		form.addHandler((CustomFormSubmitEvent event) -> getAdapter().invoke(callback, event));
		return form;
	}

	public SimpleWindowForm simpleForm(String callback, String title) {
		SimpleWindowForm form = new SimpleWindowForm(title);
		form.addHandler((SimpleFormButtonClickEvent event) -> getAdapter().invoke(callback, event));
		return form;
	}

	public ModalWindowForm modalForm(String callback, String title, String content, String acceptButtonText, String cancelButtonText) {
		ModalWindowForm form = new ModalWindowForm(title, content, acceptButtonText, cancelButtonText);
		form.addHandler((ModalFormSubmitEvent event) -> getAdapter().invoke(callback, event));
		return form;
	}

	private CompoundTag nbt(Position position, Skin skin) {
		CompoundTag nbt = Entity.getDefaultNBT(position);
		CompoundTag skinTag = new CompoundTag()
				.putByteArray("Data", skin.getSkinData().data)
				.putInt("SkinImageWidth", skin.getSkinData().width)
				.putInt("SkinImageHeight", skin.getSkinData().height)
				.putString("ModelId", skin.getSkinId())
				.putString("CapeId", skin.getCapeId())
				.putByteArray("CapeData", skin.getCapeData().data)
				.putInt("CapeImageWidth", skin.getCapeData().width)
				.putInt("CapeImageHeight", skin.getCapeData().height)
				.putByteArray("SkinResourcePatch", skin.getSkinResourcePatch().getBytes(StandardCharsets.UTF_8))
				.putByteArray("GeometryData", skin.getGeometryData().getBytes(StandardCharsets.UTF_8))
				.putByteArray("AnimationData", skin.getAnimationData().getBytes(StandardCharsets.UTF_8))
				.putBoolean("PremiumSkin", skin.isPremium())
				.putBoolean("PersonaSkin", skin.isPersona())
				.putBoolean("CapeOnClassicSkin", skin.isCapeOnClassic());
		nbt.putCompound("Skin", skinTag);
		nbt.putBoolean("ishuman", true);
		nbt.putString("Item", Item.get(0).getName());
		nbt.putString("Helmet", Item.get(0).getName());
		nbt.putString("Chestplate", Item.get(0).getName());
		nbt.putString("Leggings", Item.get(0).getName());
		nbt.putString("Boots", Item.get(0).getName());
		return nbt;
	}
}
