package com.blocklynukkit.loader.script;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.Skin;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerSettingsRespondedEvent;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.network.protocol.ShowCreditsPacket;
import cn.nukkit.network.protocol.ShowProfilePacket;
import cn.nukkit.utils.DummyBossBar;
import com.blocklynukkit.loader.script.bases.BaseManager;
import com.blocklynukkit.loader.script.window.Custom;
import com.blocklynukkit.loader.script.window.Modal;
import com.blocklynukkit.loader.script.window.Simple;
import com.blocklynukkit.loader.utils.Utils;
import com.creeperface.nukkit.placeholderapi.api.PlaceholderAPI;
import de.theamychan.scoreboard.api.ScoreboardAPI;
import de.theamychan.scoreboard.network.DisplaySlot;
import de.theamychan.scoreboard.network.Scoreboard;
import de.theamychan.scoreboard.network.ScoreboardDisplay;
import site.misaka.engine.EngineAdapter;

import java.awt.*;
import java.util.Map;
import java.util.UUID;

import static com.blocklynukkit.loader.Loader.boards;
import static com.blocklynukkit.loader.Loader.tipsVar;

public class WindowManager extends BaseManager {

    public WindowManager(EngineAdapter scriptEngine) {
        super(scriptEngine);
    }

    @Override
    public String toString() {
        return "BlocklyNukkit Based Object";
    }

    public void updateAllScoreBoard(String title, String text) {
        for (Player p : Server.getInstance().getOnlinePlayers().values()) {
            if (title.contains("%"))
                title = PlaceholderAPI.getInstance().translateString(title, p);
            if (text.contains("%"))
                text = PlaceholderAPI.getInstance().translateString(text, p);
            Scoreboard sb = ScoreboardAPI.createScoreboard();
            ScoreboardDisplay sbd = sb.addDisplay(DisplaySlot.SIDEBAR, "dumy", title);
            String[] l = text.split("(?<!\\\\);");
            for (int i = 0; i < l.length; ++i) {
                sbd.addLine(l[i].replaceAll("\\\\;", ";"), i);
            }
            if (boards.containsKey(p.getName())) {
                boards.get(p.getName()).hideFor(p);
                sb.showFor(p);
                boards.put(p.getName(), sb);
            } else {
                sb.showFor(p);
                boards.put(p.getName(), sb);
            }
        }
    }

    public void updateOneScoreBoard(String title, String text, Player p) {
        if (title.contains("%"))
            title = PlaceholderAPI.getInstance().translateString(title, p);
        if (text.contains("%"))
            text = PlaceholderAPI.getInstance().translateString(text, p);
        Scoreboard sb = ScoreboardAPI.createScoreboard();
        ScoreboardDisplay sbd = sb.addDisplay(DisplaySlot.SIDEBAR, "dumy", title);
        String[] l = text.split("(?<!\\\\);");

        for (int i = 0; i < l.length; ++i) {
            sbd.addLine(l[i].replaceAll("\\\\;", ";"), i);
        }
        if (boards.containsKey(p.getName())) {
            boards.get(p.getName()).hideFor(p);
            sb.showFor(p);
            boards.put(p.getName(), sb);
        } else {
            sb.showFor(p);
            boards.put(p.getName(), sb);
        }
    }

    public Simple getSimpleWindowBuilder(String title, String context) {
        Simple simple = new Simple();
        simple.setTitle(title);
        simple.setContext(context);
        return simple;
    }

    public Modal getModalWindowBuilder(String title, String context) {
        Modal modal = new Modal();
        modal.setContext(context);
        modal.setTitle(title);
        return modal;
    }

    public Custom getCustomWindowBuilder(String title) {
        Custom custom = new Custom();
        custom.setTitle(title);
        return custom;
    }

    public String getEventResponseText(PlayerFormRespondedEvent event) {
        if (event == null) {
            return "NULL";
        } else if (event.wasClosed()) {
            return "NULL";
        }
        FormResponseSimple simple = (FormResponseSimple) event.getResponse();
        if (simple != null) {
            return simple.getClickedButton().getText();
        } else {
            return "NULL";
        }
    }

    public String getEventResponseModal(PlayerFormRespondedEvent event) {
        if (event == null) {
            return "NULL";
        } else if (event.wasClosed()) {
            return "NULL";
        }
        FormResponseModal modal = (FormResponseModal) event.getResponse();
        if (modal != null) {
            return modal.getClickedButtonText();
        } else {
            return "NULL";
        }
    }

    public int getEventResponseIndex(PlayerFormRespondedEvent event) {
        if (event == null || event.wasClosed()) {
            return -2147483648;
        }
        if (event.getWindow() instanceof FormWindowSimple) {
            return ((FormResponseSimple) event.getResponse()).getClickedButtonId();
        } else if (event.getWindow() instanceof FormWindowModal) {
            return ((FormResponseModal) event.getResponse()).getClickedButtonId();
        } else {
            return -2147483648;
        }
    }

    public String getEventCustomVar(PlayerFormRespondedEvent event, int id, String mode) {
        if (event == null) {
            return "NULL";
        } else if (event.wasClosed()) {
            return "NULL";
        }
        FormResponseCustom custom = (FormResponseCustom) event.getResponse();
        if (mode.equals("input")) {
            return custom.getInputResponse(id);
        } else if (mode.equals("toggle")) {
            return custom.getToggleResponse(id) ? "TRUE" : "FALSE";
        } else if (mode.equals("dropdown")) {
            return custom.getDropdownResponse(id).getElementContent();
        } else if (mode.equals("slider")) {
            return custom.getSliderResponse(id) + "";
        } else if (mode.equals("stepslider") || mode.equals("step")) {
            return custom.getStepSliderResponse(id).getElementContent();
        } else {
            return "NULL";
        }
    }

    public String getEventCustomVar(PlayerSettingsRespondedEvent event, int id, String mode) {
        FormResponseCustom custom = (FormResponseCustom) event.getResponse();
        if (mode.equals("input")) {
            return custom.getInputResponse(id);
        } else if (mode.equals("toggle")) {
            return custom.getToggleResponse(id) ? "TRUE" : "FALSE";
        } else if (mode.equals("dropdown")) {
            return custom.getDropdownResponse(id).getElementContent();
        } else if (mode.equals("slider")) {
            return custom.getSliderResponse(id) + "";
        } else if (mode.equals("stepslider") || mode.equals("step")) {
            return custom.getStepSliderResponse(id).getElementContent();
        } else {
            return "NULL";
        }
    }

    public long[] setPlayerBossBar(Player player, String text, float len) {
        for (long bar : player.getDummyBossBars().keySet()) {
            player.removeBossBar(bar);
        }
        String[] bossbars = text.split("(?<!\\\\);");
        long[] ids = new long[bossbars.length];
        for (int i = 0; i < bossbars.length; i++) {
            String each = bossbars[i].replaceAll("\\\\;", ";");
            if (each.startsWith("#")) {
                String hex = each.substring(0, 7);
                Color color = Utils.hex2rgb(hex);
                ids[i] = player.createBossBar(new DummyBossBar.Builder(player).text(each.replaceFirst(hex, "")).color(color.getRed(), color.getGreen(), color.getBlue()).length(len).build());
            } else if (text.startsWith("rgb(")) {
                String[] rgb = each.split("\\)", 2)[0].replaceFirst("rgb\\(", "").split(",");
                ids[i] = player.createBossBar(new DummyBossBar.Builder(player).text(each.split("\\)", 2)[1]).length(len)
                        .color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2])).build());
            } else {
                ids[i] = player.createBossBar(new DummyBossBar.Builder(player).text(each).length(len).build());
            }
        }
        return ids;
    }

    public void removePlayerBossBar(Player player) {
        for (long bar : player.getDummyBossBars().keySet()) {
            player.removeBossBar(bar);
        }
    }

    public void removePlayerBossBar(Player player, long id) {
        player.removeBossBar(id);
    }

    public double getLengthOfPlayerBossBar(Player player) {
        for (long bar : player.getDummyBossBars().keySet()) {
            return player.getDummyBossBar(bar).getLength();
        }
        return -1.0d;
    }

    public double getLengthOfPlayerBossBar(Player player, long id) {
        DummyBossBar bar = player.getDummyBossBar(id);
        if (bar == null) return -1.0d;
        else return bar.getLength();
    }

    public String getTextOfPlayerBossBar(Player player) {
        for (long bar : player.getDummyBossBars().keySet()) {
            return player.getDummyBossBar(bar).getText();
        }
        return "NULL";
    }

    public String getTextOfPlayerBossBar(Player player, long id) {
        DummyBossBar bar = player.getDummyBossBar(id);
        if (bar == null) return "NULL";
        else return bar.getText();
    }

    //here 6/26
    public void setBelowName(Player player, String str) {
        player.setScoreTag(str);
    }

    public void makeTipsVar(String varname, String provider) {
        tipsVar.put(varname, "function->" + provider);
    }

    public void makeTipsStatic(String varname, String toReplace) {
        tipsVar.put(varname, toReplace);
    }

    //here 8/7
    public void forceClearWindow(Player player) {
        player.removeAllWindows();
    }

    //here 8/18
    public void setPauseScreenList(String list) {
        Map p = Server.getInstance().getOnlinePlayers();
        for (String each : list.split("(?<!\\\\);")) {
            each = each.replaceAll("\\\\;", ";");
            if (p.keySet().contains(each)) {
                Player player = Server.getInstance().getPlayer(each);
                Server.getInstance().updatePlayerListData(UUID.randomUUID(), Entity.entityCount++, each, player.getSkin(), player.getLoginChainData().getXUID());
            } else {
                Server.getInstance().updatePlayerListData(UUID.randomUUID(), Entity.entityCount++, each, new Skin());
            }
        }
    }

    //here 11/21
    public void sendPlayerXboxInfo(Player from, Player to) {
        ShowProfilePacket profilePacket = new ShowProfilePacket();
        profilePacket.xuid = from.getLoginChainData().getXUID();
        to.dataPacket(profilePacket);
    }

    public void startEndPoem(Player player) {
        ShowCreditsPacket creditsPacket = new ShowCreditsPacket();
        creditsPacket.eid = player.getId();
        creditsPacket.status = ShowCreditsPacket.STATUS_START_CREDITS;
        player.dataPacket(creditsPacket);
    }
}
