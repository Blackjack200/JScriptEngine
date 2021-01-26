package com.blocklynukkit.loader.script;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import com.blocklynukkit.gameAPI.API.BNGame;
import com.blocklynukkit.gameAPI.GameBase;
import com.blocklynukkit.gameAPI.Language.Messager;
import com.blocklynukkit.gameAPI.Language.Multiline;
import com.blocklynukkit.gameAPI.Menu.Form.FormMenu;
import com.blocklynukkit.gameAPI.Menu.Inventory.InventoryMenu;
import com.blocklynukkit.gameAPI.Scoreboard.Scoreboard;
import com.blocklynukkit.loader.script.bases.BaseManager;
import site.misaka.engine.EngineAdapter;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class GameManager extends BaseManager {
    public GameManager(EngineAdapter scriptEngine) {
        super(scriptEngine);
    }

    @Override
    public String toString() {
        return "BlocklyNukkit Based Object";
    }

    public void createGame(String name, boolean useTeam, String startGameCallBack, String endGameCallBack, String mainLoopCallBack, String deathCallBack) {
        try {
            BNGame.newGame(name, useTeam, startGameCallBack, endGameCallBack, mainLoopCallBack, deathCallBack);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void joinGame(Player player, String gameName) {
        BNGame.joinGame(player, gameName);
    }

    public void leaveGame(Player player) {
        BNGame.leaveGame(player);
    }

    public boolean isPlayerInGame(Player player) {
        return BNGame.isPlayerInGame(player);
    }

    public GameBase getPlayerRoom(Player player) {
        return BNGame.getPlayerRoom(player);
    }

    public List<GameBase> getAllRoomByName(String gameName) {
        return BNGame.getAllRoomByName(gameName);
    }

    public List<String> getAllGameNames() {
        return BNGame.getAllGameNames();
    }

    public Messager getMessager(String prefix) {
        return BNGame.getMessager(prefix);
    }

    public Messager getGameMessager(GameBase game) {
        return BNGame.getGameMessager(game);
    }

    public Multiline getMultiline(String messageType) {
        return BNGame.getMultiline(messageType);
    }

    public InventoryMenu createInventoryMenu(String inventoryType, String title) {
        return BNGame.createInventoryMenu(inventoryType, title);
    }

    public FormMenu createFormMenu(String title, String content) {
        return BNGame.createFormMenu(title, content);
    }

    public void addMenuItem(InventoryMenu menu, int slot, Item item, String inventoryCallback) {
        BNGame.addMenuItem(menu, slot, item, inventoryCallback);
    }

    public void addMenuButton(FormMenu menu, String buttonText, String imageData, String formCallback) {
        BNGame.addMenuButton(menu, buttonText, imageData, formCallback);
    }

    public Scoreboard getScoreboard(Player p) {
        return BNGame.getScoreboard(p);
    }

    public void setObjective(Scoreboard sb, String objectiveName, String displayName) {
        BNGame.setObjective(sb, objectiveName, displayName);
    }
}
