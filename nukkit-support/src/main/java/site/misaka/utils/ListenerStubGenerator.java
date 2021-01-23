package site.misaka.utils;

import java.lang.reflect.Modifier;

public class ListenerStubGenerator {
	public static void main(String args[]) throws ClassNotFoundException {
//find ./* -type f -name "*.java"

		String str =
				"./block/BlockPlaceEvent.java\n" +
						"./block/BlockBreakEvent.java\n" +
						"./block/BlockIgniteEvent.java\n" +
						"./block/DoorToggleEvent.java\n" +
						"./block/ItemFrameDropItemEvent.java\n" +
						"./block/BlockPistonChangeEvent.java\n" +
						"./block/LiquidFlowEvent.java\n" +
						"./block/BlockFallEvent.java\n" +
						"./block/BlockEvent.java\n" +
						"./block/BlockSpreadEvent.java\n" +
						"./block/BlockGrowEvent.java\n" +
						"./block/SignChangeEvent.java\n" +
						"./block/LeavesDecayEvent.java\n" +
						"./block/BlockUpdateEvent.java\n" +
						"./block/BlockBurnEvent.java\n" +
						"./block/BlockFadeEvent.java\n" +
						"./block/BlockFormEvent.java\n" +
						"./block/BlockFromToEvent.java\n" +
						"./block/BlockRedstoneEvent.java\n" +
						"./entity/ProjectileHitEvent.java\n" +
						"./entity/EntityDeathEvent.java\n" +
						"./entity/EntityArmorChangeEvent.java\n" +
						"./entity/EntityCombustEvent.java\n" +
						"./entity/EntityVehicleEnterEvent.java\n" +
						"./entity/EntityRegainHealthEvent.java\n" +
						"./entity/EntityInventoryChangeEvent.java\n" +
						"./entity/EntityInteractEvent.java\n" +
						"./entity/EntityDamageByEntityEvent.java\n" +
						"./entity/EntityCombustByEntityEvent.java\n" +
						"./entity/EntityMotionEvent.java\n" +
						"./entity/ProjectileLaunchEvent.java\n" +
						"./entity/EntityDamageByBlockEvent.java\n" +
						"./entity/EntitySpawnEvent.java\n" +
						"./entity/CreeperPowerEvent.java\n" +
						"./entity/EntityDamageByChildEntityEvent.java\n" +
						"./entity/EntityDespawnEvent.java\n" +
						"./entity/EntityEvent.java\n" +
						"./entity/ItemSpawnEvent.java\n" +
						"./entity/CreatureSpawnEvent.java\n" +
						"./entity/ExplosionPrimeEvent.java\n" +
						"./entity/EntityShootBowEvent.java\n" +
						"./entity/ItemDespawnEvent.java\n" +
						"./entity/EntityDamageEvent.java\n" +
						"./entity/EntityBlockChangeEvent.java\n" +
						"./entity/EntityLevelChangeEvent.java\n" +
						"./entity/EntityVehicleExitEvent.java\n" +
						"./entity/EntityCombustByBlockEvent.java\n" +
						"./entity/EntityTeleportEvent.java\n" +
						"./entity/EntityPortalEnterEvent.java\n" +
						"./entity/EntityExplosionPrimeEvent.java\n" +
						"./entity/EntityExplodeEvent.java\n" +
						"./inventory/InventoryPickupItemEvent.java\n" +
						"./inventory/StartBrewEvent.java\n" +
						"./inventory/BrewEvent.java\n" +
						"./inventory/InventoryOpenEvent.java\n" +
						"./inventory/InventoryPickupTridentEvent.java\n" +
						"./inventory/FurnaceBurnEvent.java\n" +
						"./inventory/CraftItemEvent.java\n" +
						"./inventory/EnchantItemEvent.java\n" +
						"./inventory/InventoryCloseEvent.java\n" +
						"./inventory/InventoryPickupArrowEvent.java\n" +
						"./inventory/InventoryEvent.java\n" +
						"./inventory/InventoryTransactionEvent.java\n" +
						"./inventory/FurnaceSmeltEvent.java\n" +
						"./inventory/InventoryClickEvent.java\n" +
						"./inventory/InventoryMoveItemEvent.java\n" +
						"./level/LevelInitEvent.java\n" +
						"./level/ChunkEvent.java\n" +
						"./level/ChunkLoadEvent.java\n" +
						"./level/ChunkPopulateEvent.java\n" +
						"./level/WeatherChangeEvent.java\n" +
						"./level/LevelEvent.java\n" +
						"./level/SpawnChangeEvent.java\n" +
						"./level/LevelSaveEvent.java\n" +
						"./level/ChunkUnloadEvent.java\n" +
						"./level/WeatherEvent.java\n" +
						"./level/ThunderChangeEvent.java\n" +
						"./level/LevelUnloadEvent.java\n" +
						"./level/LevelLoadEvent.java\n" +
						"./level/StructureGrowEvent.java\n" +
						"./player/PlayerQuitEvent.java\n" +
						"./player/PlayerRespawnEvent.java\n" +
						"./player/PlayerBedEnterEvent.java\n" +
						"./player/PlayerItemConsumeEvent.java\n" +
						"./player/PlayerPreLoginEvent.java\n" +
						"./player/PlayerCommandPreprocessEvent.java\n" +
						"./player/PlayerSettingsRespondedEvent.java\n" +
						"./player/PlayerEditBookEvent.java\n" +
						"./player/PlayerGameModeChangeEvent.java\n" +
						"./player/PlayerServerSettingsRequestEvent.java\n" +
						"./player/PlayerAchievementAwardedEvent.java\n" +
						"./player/PlayerFoodLevelChangeEvent.java\n" +
						"./player/PlayerInteractEntityEvent.java\n" +
						"./player/PlayerEvent.java\n" +
						"./player/PlayerToggleSwimEvent.java\n" +
						"./player/PlayerDropItemEvent.java\n" +
						"./player/PlayerMapInfoRequestEvent.java\n" +
						"./player/PlayerEatFoodEvent.java\n" +
						"./player/PlayerFormRespondedEvent.java\n" +
						"./player/PlayerToggleGlideEvent.java\n" +
						"./player/PlayerInvalidMoveEvent.java\n" +
						"./player/PlayerCreationEvent.java\n" +
						"./player/PlayerChunkRequestEvent.java\n" +
						"./player/PlayerChangeSkinEvent.java\n" +
						"./player/PlayerJoinEvent.java\n" +
						"./player/PlayerInteractEvent.java\n" +
						"./player/PlayerChatEvent.java\n" +
						"./player/PlayerTeleportEvent.java\n" +
						"./player/PlayerBucketEmptyEvent.java\n" +
						"./player/PlayerToggleSneakEvent.java\n" +
						"./player/PlayerAsyncPreLoginEvent.java\n" +
						"./player/PlayerKickEvent.java\n" +
						"./player/PlayerItemHeldEvent.java\n" +
						"./player/PlayerBucketEvent.java\n" +
						"./player/PlayerDeathEvent.java\n" +
						"./player/PlayerLocallyInitializedEvent.java\n" +
						"./player/PlayerToggleSprintEvent.java\n" +
						"./player/PlayerAnimationEvent.java\n" +
						"./player/PlayerToggleFlightEvent.java\n" +
						"./player/PlayerGlassBottleFillEvent.java\n" +
						"./player/PlayerBlockPickEvent.java\n" +
						"./player/PlayerMessageEvent.java\n" +
						"./player/PlayerMoveEvent.java\n" +
						"./player/PlayerMouseOverEntityEvent.java\n" +
						"./player/PlayerJumpEvent.java\n" +
						"./player/PlayerBedLeaveEvent.java\n" +
						"./player/PlayerBucketFillEvent.java\n" +
						"./player/PlayerLoginEvent.java\n" +
						"./plugin/PluginEnableEvent.java\n" +
						"./plugin/PluginDisableEvent.java\n" +
						"./plugin/PluginEvent.java\n" +
						"./potion/PotionApplyEvent.java\n" +
						"./potion/PotionCollideEvent.java\n" +
						"./potion/PotionEvent.java\n" +
						"./redstone/RedstoneUpdateEvent.java\n" +
						"./server/QueryRegenerateEvent.java\n" +
						"./server/ServerCommandEvent.java\n" +
						"./server/ServerEvent.java\n" +
						"./server/DataPacketSendEvent.java\n" +
						"./server/DataPacketReceiveEvent.java\n" +
						"./server/BatchPacketsEvent.java\n" +
						"./server/PlayerDataSerializeEvent.java\n" +
						"./server/RemoteServerCommandEvent.java\n" +
						"./vehicle/VehicleDestroyEvent.java\n" +
						"./vehicle/VehicleDamageEvent.java\n" +
						"./vehicle/VehicleEvent.java\n" +
						"./vehicle/VehicleUpdateEvent.java\n" +
						"./vehicle/EntityEnterVehicleEvent.java\n" +
						"./vehicle/VehicleCreateEvent.java\n" +
						"./vehicle/EntityExitVehicleEvent.java\n" +
						"./vehicle/VehicleMoveEvent.java\n" +
						"weather.LightningStrikeEvent";
		str = str.replace("./", "").replace(".java", "").replace("/", ".");
		final String package_ = "cn.nukkit.event.";
		final String fmt = "@EventHandler(priority = EventPriority.MONITOR) public void %s(%s event) {ScriptEngineFacade.invokeEvent(event);}";
		String split[] = str.split("\n");

		for (String name : split) {
			String pkg = package_ + name;
			Class clazz = Class.forName(pkg);
			int modifier = clazz.getModifiers();

			if (Modifier.isPublic(modifier) && !(Modifier.isAbstract(modifier))) {
				try {
					clazz.getDeclaredMethod("getHandlers");
					System.out.println(String.format(fmt, pkg.substring(pkg.lastIndexOf(".") + 1), pkg));
				} catch (Throwable ignore) {

				}
			}
		}
	}
}
