package com.blocklynukkit.loader;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.LiquidFlowEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.inventory.FurnaceSmeltEvent;
import cn.nukkit.event.level.ChunkPopulateEvent;
import cn.nukkit.event.level.ChunkUnloadEvent;
import cn.nukkit.event.player.PlayerCommandPreprocessEvent;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.event.server.DataPacketSendEvent;
import cn.nukkit.event.server.ServerCommandEvent;
import cn.nukkit.item.Item;
import cn.nukkit.lang.TranslationContainer;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.ProtocolInfo;
import cn.nukkit.network.protocol.StartGamePacket;
import cn.nukkit.scheduler.Task;
import cn.nukkit.utils.BinaryStream;
import com.blocklynukkit.loader.other.Items.ItemComponentEntry;
import com.blocklynukkit.loader.other.Items.ItemData;
import com.blocklynukkit.loader.other.generator.render.BaseRender;
import com.blocklynukkit.loader.other.packets.ItemComponentPacket;
import com.blocklynukkit.loader.script.event.FakeSlotChangeEvent;
import com.blocklynukkit.loader.script.event.StoneSpawnEvent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xxmicloxx.NoteBlockAPI.SongDestroyingEvent;
import com.xxmicloxx.NoteBlockAPI.SongEndEvent;
import com.xxmicloxx.NoteBlockAPI.SongStoppedEvent;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;

@SuppressWarnings("unused")
public class EventLoader implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void BlockPlaceEvent(cn.nukkit.event.block.BlockPlaceEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void BlockBreakEvent(cn.nukkit.event.block.BlockBreakEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void BlockIgniteEvent(cn.nukkit.event.block.BlockIgniteEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void DoorToggleEvent(cn.nukkit.event.block.DoorToggleEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void ItemFrameDropItemEvent(cn.nukkit.event.block.ItemFrameDropItemEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void BlockPistonChangeEvent(cn.nukkit.event.block.BlockPistonChangeEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void LiquidFlowEvent(cn.nukkit.event.block.LiquidFlowEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void BlockFallEvent(cn.nukkit.event.block.BlockFallEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void BlockSpreadEvent(cn.nukkit.event.block.BlockSpreadEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void BlockGrowEvent(cn.nukkit.event.block.BlockGrowEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void SignChangeEvent(cn.nukkit.event.block.SignChangeEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void LeavesDecayEvent(cn.nukkit.event.block.LeavesDecayEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void BlockUpdateEvent(cn.nukkit.event.block.BlockUpdateEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void BlockBurnEvent(cn.nukkit.event.block.BlockBurnEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void BlockFadeEvent(cn.nukkit.event.block.BlockFadeEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void BlockFormEvent(cn.nukkit.event.block.BlockFormEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void BlockFromToEvent(cn.nukkit.event.block.BlockFromToEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void BlockRedstoneEvent(cn.nukkit.event.block.BlockRedstoneEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void ProjectileHitEvent(cn.nukkit.event.entity.ProjectileHitEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void EntityDeathEvent(cn.nukkit.event.entity.EntityDeathEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void EntityArmorChangeEvent(cn.nukkit.event.entity.EntityArmorChangeEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void EntityCombustEvent(cn.nukkit.event.entity.EntityCombustEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void EntityVehicleEnterEvent(cn.nukkit.event.entity.EntityVehicleEnterEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void EntityRegainHealthEvent(cn.nukkit.event.entity.EntityRegainHealthEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void EntityInventoryChangeEvent(cn.nukkit.event.entity.EntityInventoryChangeEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void EntityInteractEvent(cn.nukkit.event.entity.EntityInteractEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void EntityMotionEvent(cn.nukkit.event.entity.EntityMotionEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void ProjectileLaunchEvent(cn.nukkit.event.entity.ProjectileLaunchEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void EntitySpawnEvent(cn.nukkit.event.entity.EntitySpawnEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void CreeperPowerEvent(cn.nukkit.event.entity.CreeperPowerEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void EntityDespawnEvent(cn.nukkit.event.entity.EntityDespawnEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void ItemSpawnEvent(cn.nukkit.event.entity.ItemSpawnEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void CreatureSpawnEvent(cn.nukkit.event.entity.CreatureSpawnEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void ExplosionPrimeEvent(cn.nukkit.event.entity.ExplosionPrimeEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void EntityShootBowEvent(cn.nukkit.event.entity.EntityShootBowEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void ItemDespawnEvent(cn.nukkit.event.entity.ItemDespawnEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void EntityDamageEvent(cn.nukkit.event.entity.EntityDamageEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void EntityBlockChangeEvent(cn.nukkit.event.entity.EntityBlockChangeEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void EntityLevelChangeEvent(cn.nukkit.event.entity.EntityLevelChangeEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void EntityVehicleExitEvent(cn.nukkit.event.entity.EntityVehicleExitEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void EntityTeleportEvent(cn.nukkit.event.entity.EntityTeleportEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void EntityPortalEnterEvent(cn.nukkit.event.entity.EntityPortalEnterEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void EntityExplosionPrimeEvent(cn.nukkit.event.entity.EntityExplosionPrimeEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void EntityExplodeEvent(cn.nukkit.event.entity.EntityExplodeEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void InventoryPickupItemEvent(cn.nukkit.event.inventory.InventoryPickupItemEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void StartBrewEvent(cn.nukkit.event.inventory.StartBrewEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void BrewEvent(cn.nukkit.event.inventory.BrewEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void InventoryOpenEvent(cn.nukkit.event.inventory.InventoryOpenEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void InventoryPickupTridentEvent(cn.nukkit.event.inventory.InventoryPickupTridentEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void FurnaceBurnEvent(cn.nukkit.event.inventory.FurnaceBurnEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void CraftItemEvent(cn.nukkit.event.inventory.CraftItemEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void EnchantItemEvent(cn.nukkit.event.inventory.EnchantItemEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void InventoryCloseEvent(cn.nukkit.event.inventory.InventoryCloseEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void InventoryPickupArrowEvent(cn.nukkit.event.inventory.InventoryPickupArrowEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void InventoryTransactionEvent(cn.nukkit.event.inventory.InventoryTransactionEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void FurnaceSmeltEvent(cn.nukkit.event.inventory.FurnaceSmeltEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void InventoryClickEvent(cn.nukkit.event.inventory.InventoryClickEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void InventoryMoveItemEvent(cn.nukkit.event.inventory.InventoryMoveItemEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void LevelInitEvent(cn.nukkit.event.level.LevelInitEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void ChunkLoadEvent(cn.nukkit.event.level.ChunkLoadEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void ChunkPopulateEvent(cn.nukkit.event.level.ChunkPopulateEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void WeatherChangeEvent(cn.nukkit.event.level.WeatherChangeEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void SpawnChangeEvent(cn.nukkit.event.level.SpawnChangeEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void LevelSaveEvent(cn.nukkit.event.level.LevelSaveEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void ChunkUnloadEvent(cn.nukkit.event.level.ChunkUnloadEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void ThunderChangeEvent(cn.nukkit.event.level.ThunderChangeEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void LevelUnloadEvent(cn.nukkit.event.level.LevelUnloadEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void LevelLoadEvent(cn.nukkit.event.level.LevelLoadEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void StructureGrowEvent(cn.nukkit.event.level.StructureGrowEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerQuitEvent(cn.nukkit.event.player.PlayerQuitEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerRespawnEvent(cn.nukkit.event.player.PlayerRespawnEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerBedEnterEvent(cn.nukkit.event.player.PlayerBedEnterEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerItemConsumeEvent(cn.nukkit.event.player.PlayerItemConsumeEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerPreLoginEvent(cn.nukkit.event.player.PlayerPreLoginEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerCommandPreprocessEvent(cn.nukkit.event.player.PlayerCommandPreprocessEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerSettingsRespondedEvent(cn.nukkit.event.player.PlayerSettingsRespondedEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerEditBookEvent(cn.nukkit.event.player.PlayerEditBookEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerGameModeChangeEvent(cn.nukkit.event.player.PlayerGameModeChangeEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerServerSettingsRequestEvent(cn.nukkit.event.player.PlayerServerSettingsRequestEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerAchievementAwardedEvent(cn.nukkit.event.player.PlayerAchievementAwardedEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerFoodLevelChangeEvent(cn.nukkit.event.player.PlayerFoodLevelChangeEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerInteractEntityEvent(cn.nukkit.event.player.PlayerInteractEntityEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerToggleSwimEvent(cn.nukkit.event.player.PlayerToggleSwimEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerDropItemEvent(cn.nukkit.event.player.PlayerDropItemEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerMapInfoRequestEvent(cn.nukkit.event.player.PlayerMapInfoRequestEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerEatFoodEvent(cn.nukkit.event.player.PlayerEatFoodEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerFormRespondedEvent(cn.nukkit.event.player.PlayerFormRespondedEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerToggleGlideEvent(cn.nukkit.event.player.PlayerToggleGlideEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerInvalidMoveEvent(cn.nukkit.event.player.PlayerInvalidMoveEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerCreationEvent(cn.nukkit.event.player.PlayerCreationEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerChunkRequestEvent(cn.nukkit.event.player.PlayerChunkRequestEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerChangeSkinEvent(cn.nukkit.event.player.PlayerChangeSkinEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerJoinEvent(cn.nukkit.event.player.PlayerJoinEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerInteractEvent(cn.nukkit.event.player.PlayerInteractEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerChatEvent(cn.nukkit.event.player.PlayerChatEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerTeleportEvent(cn.nukkit.event.player.PlayerTeleportEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerBucketEmptyEvent(cn.nukkit.event.player.PlayerBucketEmptyEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerToggleSneakEvent(cn.nukkit.event.player.PlayerToggleSneakEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerAsyncPreLoginEvent(cn.nukkit.event.player.PlayerAsyncPreLoginEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerKickEvent(cn.nukkit.event.player.PlayerKickEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerItemHeldEvent(cn.nukkit.event.player.PlayerItemHeldEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerDeathEvent(cn.nukkit.event.player.PlayerDeathEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerLocallyInitializedEvent(cn.nukkit.event.player.PlayerLocallyInitializedEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerToggleSprintEvent(cn.nukkit.event.player.PlayerToggleSprintEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerAnimationEvent(cn.nukkit.event.player.PlayerAnimationEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerToggleFlightEvent(cn.nukkit.event.player.PlayerToggleFlightEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerBlockPickEvent(cn.nukkit.event.player.PlayerBlockPickEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerMoveEvent(cn.nukkit.event.player.PlayerMoveEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerMouseOverEntityEvent(cn.nukkit.event.player.PlayerMouseOverEntityEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerJumpEvent(cn.nukkit.event.player.PlayerJumpEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerBedLeaveEvent(cn.nukkit.event.player.PlayerBedLeaveEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerBucketFillEvent(cn.nukkit.event.player.PlayerBucketFillEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerLoginEvent(cn.nukkit.event.player.PlayerLoginEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PluginEvent(cn.nukkit.event.plugin.PluginEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PotionApplyEvent(cn.nukkit.event.potion.PotionApplyEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PotionCollideEvent(cn.nukkit.event.potion.PotionCollideEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void RedstoneUpdateEvent(cn.nukkit.event.redstone.RedstoneUpdateEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void QueryRegenerateEvent(cn.nukkit.event.server.QueryRegenerateEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void ServerCommandEvent(cn.nukkit.event.server.ServerCommandEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void DataPacketSendEvent(cn.nukkit.event.server.DataPacketSendEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void DataPacketReceiveEvent(cn.nukkit.event.server.DataPacketReceiveEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void BatchPacketsEvent(cn.nukkit.event.server.BatchPacketsEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerDataSerializeEvent(cn.nukkit.event.server.PlayerDataSerializeEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void RemoteServerCommandEvent(cn.nukkit.event.server.RemoteServerCommandEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void VehicleDestroyEvent(cn.nukkit.event.vehicle.VehicleDestroyEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void VehicleDamageEvent(cn.nukkit.event.vehicle.VehicleDamageEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void VehicleUpdateEvent(cn.nukkit.event.vehicle.VehicleUpdateEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void EntityEnterVehicleEvent(cn.nukkit.event.vehicle.EntityEnterVehicleEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void VehicleCreateEvent(cn.nukkit.event.vehicle.VehicleCreateEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void EntityExitVehicleEvent(cn.nukkit.event.vehicle.EntityExitVehicleEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void VehicleMoveEvent(cn.nukkit.event.vehicle.VehicleMoveEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void LightningStrikeEvent(cn.nukkit.event.weather.LightningStrikeEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFormResponse(PlayerFormRespondedEvent event) {
        synchronized (Loader.functioncallback) {
            if (!event.wasClosed() && event.getResponse() != null) {
                if (Loader.functioncallback.containsKey(event.getFormID())) {
                    int a = event.getFormID();
                    EngineFacade.invokeEvent(event, Loader.functioncallback.get(a));
                }
            }
        }
        synchronized (Loader.windowCallbackMap) {
            if (Loader.windowCallbackMap.containsKey(event.getFormID())) {
                Loader.windowCallbackMap.get(event.getFormID()).call(event);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if (event.getMessage().equals("/version") || event.getMessage().equals("version") ||
                event.getMessage().equals("/version ") || event.getMessage().equals("version ")) {
            Player sender = event.getPlayer();
            sender.sendMessage(new TranslationContainer("nukkit.server.info.extended", sender.getServer().getName(),
                    sender.getServer().getNukkitVersion(),
                    Loader.fakeNukkitCodeVersion,
                    sender.getServer().getApiVersion(),
                    sender.getServer().getVersion(),
                    String.valueOf(ProtocolInfo.CURRENT_PROTOCOL)));
            event.setCancelled();
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent event) {
        switch (event.getAction()) {
            case RIGHT_CLICK_BLOCK:
                EngineFacade.invokeEvent(event, "RightClickBlockEvent");
                break;
            case LEFT_CLICK_BLOCK:
                EngineFacade.invokeEvent(event, "LeftClickBlockEvent");
                break;
            case RIGHT_CLICK_AIR:
                EngineFacade.invokeEvent(event, "ClickOnAirEvent");
                break;
            case PHYSICAL:
                EngineFacade.invokeEvent(event, "PhysicalTouchEvent");
                break;
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onServerCommand(ServerCommandEvent event) {
        if (event.getCommand().equals("/version") || event.getCommand().equals("version") ||
                event.getCommand().equals("/version ") || event.getCommand().equals("version ")) {
            CommandSender sender = event.getSender();
            sender.sendMessage(new TranslationContainer("nukkit.server.info.extended", sender.getServer().getName(),
                    sender.getServer().getNukkitVersion(),
                    Loader.fakeNukkitCodeVersion,
                    sender.getServer().getApiVersion(),
                    sender.getServer().getVersion(),
                    String.valueOf(ProtocolInfo.CURRENT_PROTOCOL)));
            event.setCancelled();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (event.getEntity().getName().equals("BNNPC")) event.setAttackCooldown(0);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFurnaceSmelt(FurnaceSmeltEvent event) {
        Item out = null;
        for (Map.Entry<Item, Item> entry : UnionData.furnaceMap.entrySet()) {
            if (entry.getKey().equals(event.getSource(), true, true)) {
                Item res = event.getFurnace().getInventory().getResult();
                if (res != null) {
                    if (res.getId() != 0 && !res.equals(entry.getValue(), true, true)) continue;
                    if (res.getId() == 0 || res.getCount() == 0) {
                        out = entry.getValue();
                    } else {
                        out = entry.getValue();
                        out.setCount(res.getCount() + 1);
                    }
                }
            }
        }
        if (out != null) {
            event.setResult(out);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDataPacketReceive(DataPacketReceiveEvent event) {
        if (event.getPacket().pid() == ProtocolInfo.SET_LOCAL_PLAYER_AS_INITIALIZED_PACKET) {
            ItemComponentPacket packet = new ItemComponentPacket();
            for (int data : Loader.registerItemIds) {
                packet.entries.add(new ItemComponentEntry(
                        "blocklynukkit:" + Item.get(data).getName(),
                        new CompoundTag("components")
                                .putString("minecraft:icon", Item.get(data).getName())
                                .putString("minecraft:render_offsets", "miscellaneous")
                ));
            }
            event.getPlayer().dataPacket(packet);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDataPacketSend(DataPacketSendEvent event) {
        DataPacket tmp = event.getPacket();
        if (tmp.pid() == ProtocolInfo.START_GAME_PACKET) {
            StartGamePacket packet = (StartGamePacket) tmp;
            packet.eduEditionOffer = 1;
            packet.hasEduFeaturesEnabled = true;
            //获取原版物品元数据
            InputStream stream = Server.class.getClassLoader().getResourceAsStream("runtime_item_ids.json");
            if (stream == null) {
                throw new AssertionError("Unable to locate RuntimeID table");
            } else {
                Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
                Gson gson = new Gson();
                Type collectionType = (new TypeToken<Collection<ItemData>>() {
                }).getType();
                Collection<ItemData> entries = gson.fromJson(reader, collectionType);
                BinaryStream paletteBuffer = new BinaryStream();
                paletteBuffer.putUnsignedVarInt(entries.size() + Loader.registerItemIds.size());
                for (int data : Loader.registerItemIds) {
                    paletteBuffer.putString("blocklynukkit:" + Item.get(data).getName());
                    paletteBuffer.putLShort(data);
                    if (ProtocolInfo.CURRENT_PROTOCOL > 408)
                        paletteBuffer.putBoolean(true);
                }
                for (ItemData data : entries) {
                    paletteBuffer.putString(data.name);
                    paletteBuffer.putLShort(data.id);
                    if (ProtocolInfo.CURRENT_PROTOCOL > 408)
                        paletteBuffer.putBoolean(false);
                }
                byte[] itemData = paletteBuffer.getBuffer();
                try {
                    //修改运行时物品元数据
                    Field objXField = tmp.getClass().getDeclaredField("ITEM_DATA_PALETTE");
                    objXField.setAccessible(true);
                    Field modifiers = objXField.getClass().getDeclaredField("modifiers");
                    modifiers.setAccessible(true);
                    modifiers.setInt(objXField, objXField.getModifiers() & ~Modifier.FINAL);
                    objXField.set(tmp, itemData);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    {
                        packet.reset();
                        packet.putEntityUniqueId(packet.entityUniqueId);
                        packet.putEntityRuntimeId(packet.entityRuntimeId);
                        packet.putVarInt(packet.playerGamemode);
                        packet.putVector3f(packet.x, packet.y, packet.z);
                        packet.putLFloat(packet.yaw);
                        packet.putLFloat(packet.pitch);
                        packet.putVarInt(packet.seed);
                        packet.putLShort(0x00); // SpawnBiomeType - Default
                        packet.putString("plains"); // UserDefinedBiomeName
                        packet.putVarInt(packet.dimension);
                        packet.putVarInt(packet.generator);
                        packet.putVarInt(packet.worldGamemode);
                        packet.putVarInt(packet.difficulty);
                        packet.putBlockVector3(packet.spawnX, packet.spawnY, packet.spawnZ);
                        packet.putBoolean(packet.hasAchievementsDisabled);
                        packet.putVarInt(packet.dayCycleStopTime);
                        packet.putVarInt(packet.eduEditionOffer);
                        packet.putBoolean(packet.hasEduFeaturesEnabled);
                        packet.putString(""); // Education Edition Product ID
                        packet.putLFloat(packet.rainLevel);
                        packet.putLFloat(packet.lightningLevel);
                        packet.putBoolean(packet.hasConfirmedPlatformLockedContent);
                        packet.putBoolean(packet.multiplayerGame);
                        packet.putBoolean(packet.broadcastToLAN);
                        packet.putVarInt(packet.xblBroadcastIntent);
                        packet.putVarInt(packet.platformBroadcastIntent);
                        packet.putBoolean(packet.commandsEnabled);
                        packet.putBoolean(packet.isTexturePacksRequired);
                        packet.putGameRules(packet.gameRules);
                        packet.putLInt(0); // Experiment count
                        packet.putBoolean(false); // Were experiments previously toggled
                        packet.putBoolean(packet.bonusChest);
                        packet.putBoolean(packet.hasStartWithMapEnabled);
                        packet.putVarInt(packet.permissionLevel);
                        packet.putLInt(packet.serverChunkTickRange);
                        packet.putBoolean(packet.hasLockedBehaviorPack);
                        packet.putBoolean(packet.hasLockedResourcePack);
                        packet.putBoolean(packet.isFromLockedWorldTemplate);
                        packet.putBoolean(packet.isUsingMsaGamertagsOnly);
                        packet.putBoolean(packet.isFromWorldTemplate);
                        packet.putBoolean(packet.isWorldTemplateOptionLocked);
                        packet.putBoolean(packet.isOnlySpawningV1Villagers);
                        packet.putString(packet.vanillaVersion);
                        packet.putLInt(16); // Limited world width
                        packet.putLInt(16); // Limited world height
                        packet.putBoolean(false); // Nether type
                        packet.putBoolean(false); // Experimental Gameplay
                        packet.putString(packet.levelId);
                        packet.putString(packet.worldName);
                        packet.putString(packet.premiumWorldTemplateId);
                        packet.putBoolean(packet.isTrial);
                        packet.putVarInt(packet.isMovementServerAuthoritative ? 1 : 0); // 2 - rewind
                        packet.putLLong(packet.currentTick);
                        packet.putVarInt(packet.enchantmentSeed);
                        packet.putUnsignedVarInt(0); // Custom blocks
                        packet.put(itemData);
                        packet.putString(packet.multiplayerCorrelationId);
                        packet.putBoolean(packet.isInventoryServerAuthoritative);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLiquid(LiquidFlowEvent event) {
        Position position = Position.fromObject(new Vector3(
                event.getBlock().x, event.getBlock().y, event.getBlock().z
        ), event.getBlock().getLevel());
        Server.getInstance().getScheduler().scheduleDelayedTask(new Task() {
            @Override
            public void onRun(int i) {
                if (position.getLevelBlock().getId() == 4 || position.getLevelBlock().getId() == 1) {
                    EngineFacade.invokeEvent(new StoneSpawnEvent(position, position.getLevelBlock()), "StoneSpawnEvent");
                }
            }
        }, 5);
    }

    public static void onSlotChange(com.nukkitx.fakeinventories.inventory.FakeSlotChangeEvent event) {
        EngineFacade.invokeEvent(new FakeSlotChangeEvent(event), "FakeSlotChangeEvent");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSongEnd(SongEndEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSongDestroy(SongDestroyingEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSongStop(SongStoppedEvent event) {
        EngineFacade.invokeEvent(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChunkUnload(ChunkUnloadEvent event) {
        if (event == null || event.getChunk() == null) return;
        for (Entity entity : event.getChunk().getEntities().values()) {
            if (entity == null) continue;
            if (entity.getName() == null) continue;
            if (entity.getName().equals("BNNPC") || entity.getName().equals("BNFloatingText")) {
                event.setCancelled();
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChunkPopulateEvent(ChunkPopulateEvent event) {
        BaseRender render;
        for (int i = 0; i < Loader.levelRenderList.size(); i++) {
            render = Loader.levelRenderList.get(i);
            if (render.canRend(event.getLevel())) {
                render.rend(event.getLevel(), event.getChunk());
            }
        }
    }
}
