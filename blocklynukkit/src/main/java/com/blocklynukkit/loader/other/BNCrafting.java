package com.blocklynukkit.loader.other;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.item.Item;

import java.util.*;

public class BNCrafting implements Listener {
    public Map<String, ArrayList<CraftEntry>> craftEntryMap = new HashMap<>();
    public Map<String, ArrayList<CraftEntry>> playerCraftMap = new HashMap<>();
    public BNCrafting(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Iterator<Map.Entry<String, ArrayList<CraftEntry>>> entries = playerCraftMap.entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry<String, ArrayList<CraftEntry>> entry = entries.next();
                    Iterator<CraftEntry> iterator = entry.getValue().iterator();
                    while (iterator.hasNext()){
                        CraftEntry craftEntry=iterator.next();
                        if (craftEntry.timecall()){
                            if(Math.random()<craftEntry.percent){
                                for (Item item:craftEntry.output){
                                    addItemToPlayer(Server.getInstance().getPlayer(entry.getKey()),item);
                                }
                                Server.getInstance().getPlayer(entry.getKey()).sendMessage("合成 "+craftEntry.description+" 成功");
                            }else {
                                Server.getInstance().getPlayer(entry.getKey()).sendMessage("合成 "+craftEntry.description+" 失败");
                            }

                            iterator.remove();
                        }
                    }

                }
            }
        },0,100);
    }

    public void addCraft(String type,String description,Item[] input,Item[] output,int delay,double percent){
        if(craftEntryMap.get(type)==null){
            ArrayList<CraftEntry> arrayList = new ArrayList<>();
            arrayList.add(new CraftEntry(description, input, output, delay, percent));
            craftEntryMap.put(type,arrayList);
        }else {
            ArrayList<CraftEntry> arrayList = craftEntryMap.get(type);
            arrayList.add(new CraftEntry(description, input, output, delay, percent));
            craftEntryMap.put(type,arrayList);
        }
    }
    public void showTypeToPlayer(String type, Player player){
        FormWindowSimple page = new FormWindowSimple("高级工作台",type);
        for(Map.Entry<String,ArrayList<CraftEntry>> entry:craftEntryMap.entrySet()){
            if(entry.getKey().equals(type)){
                Iterator<CraftEntry> iterator = entry.getValue().iterator();
                while (iterator.hasNext()){
                    CraftEntry craftEntry=iterator.next();
                    if(PlayercontainsItem(player,1,craftEntry.input)){
                        page.addButton(new ElementButton(craftEntry.description));
                    }
                }
            }

        }
        player.showFormWindow(page,99876);
    }

    @EventHandler
    public void onTypeResponse(PlayerFormRespondedEvent event){
        if(event.getFormID()==99876&&event.getResponse()!=null){
            FormResponseSimple simple = (FormResponseSimple)event.getResponse();
            String des = simple.getClickedButton().getText();
            for(Map.Entry<String,ArrayList<CraftEntry>> entry:craftEntryMap.entrySet()){
                Iterator<CraftEntry> iterator = entry.getValue().iterator();
                while (iterator.hasNext()){
                    CraftEntry craftEntry=iterator.next();
                    if(des.equals(craftEntry.description)){
                        FormWindowCustom page = new FormWindowCustom("高级工作台");
                        page.addElement(new ElementLabel(des));
                        String in = "";in+="使用：\n";
                        for(Item item:craftEntry.input){
                            in+=(item.getCustomName()==null||item.getCustomName().length()<=1)?item.getName():item.getCustomName();
                            in+="*"+item.getCount()+";";
                        }
                        page.addElement(new ElementLabel(in));
                        String out="";out+="合成：\n";
                        for(Item item:craftEntry.output){
                            out+=(item.getCustomName()==null||item.getCustomName().length()<=1)?item.getName():item.getCustomName();
                            out+="*"+item.getCount()+";";
                        }
                        page.addElement(new ElementLabel(out));
                        page.addElement(new ElementLabel("耗时："+craftEntry.delay/20.0+"秒"));
                        page.addElement(new ElementLabel("成功率："+craftEntry.percent*100+"%%"));
                        event.getPlayer().showFormWindow(page,998765);
                    }
                }
            }
        }else if(event.getFormID()==998765&&event.getResponse()!=null){
            FormResponseCustom custom =(FormResponseCustom)event.getResponse();
            String des = custom.getLabelResponse(0);
            for(Map.Entry<String,ArrayList<CraftEntry>> entry:craftEntryMap.entrySet()){
                Iterator<CraftEntry> iterator = entry.getValue().iterator();
                while (iterator.hasNext()){
                    CraftEntry craftEntry=iterator.next();
                    if(des.equals(craftEntry.description)){
                        event.getPlayer().getInventory().removeItem(craftEntry.input);
                        if(playerCraftMap.get(event.getPlayer().getName())==null){
                            ArrayList<CraftEntry> craftEntries = new ArrayList<>();
                            craftEntries.add(craftEntry.copy());
                            playerCraftMap.put(event.getPlayer().getName(),craftEntries);
                        }else {
                            ArrayList<CraftEntry> craftEntries = playerCraftMap.get(event.getPlayer().getName());
                            craftEntries.add(craftEntry.copy());
                            playerCraftMap.put(event.getPlayer().getName(),craftEntries);
                        }
                    }
                }
            }
        }
    }

    public boolean PlayercontainsItem(Player player,int x,Item ...item){
        Item[] tmp = item;
        boolean have = true;
//        if(x!=1)
//            for (Item each:tmp){
//                each.setCount(each.getCount()*x);
//            }
        for(Item each:tmp){
            if(!have)return have;
            if (!player.getInventory().contains(each)){
                have=false;
            }
        }
        return have;
    }
    public void addItemToPlayer(Player player,Item item){
        if(player.getInventory().canAddItem(item)){
            player.getInventory().addItem(item);
        }else {
            player.sendPopup("你有一些"+item.getName()+"装不下掉到了地上");
            player.getLevel().dropItem(player,item);
        }
    }
}
class CraftEntry{
    public Item[] input;
    public Item[] output;
    public int delay;
    public double percent;
    public String description;
    public int process=0;
    public CraftEntry(String description,Item[] input,Item[] output,int delay,double percent){
        this.delay=delay;
        this.input=input;
        this.output=output;
        this.percent=percent;
        this.description=description;
    }
    public boolean timecall(){
        process+=2;
        return process>=delay;
    }
    public CraftEntry copy(){
        return new CraftEntry(description,input,output,delay,percent);
    }
}
