package com.blocklynukkit.loader.script.event;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.element.*;
import cn.nukkit.form.window.FormWindowCustom;

public class CustomWindowEvent extends PlayerFormRespondedEvent {
    public int index = -1;
    public CustomWindowEvent(int index,Player player, int formID, FormWindow window) {
        super(player, formID, window);
        this.index=index;
    }
    public CustomWindowEvent(int index,PlayerFormRespondedEvent event){
        super(event.getPlayer(),event.getFormID(),event.getWindow());
        assert event.getResponse() instanceof FormResponseCustom;
        this.index=index;
    }
    public CustomWindowEvent(PlayerFormRespondedEvent event){
        super(event.getPlayer(),event.getFormID(),event.getWindow());
        assert event.getResponse() instanceof FormResponseCustom;
    }
    public String getResult(){
        if(index==-1)return "CLOSED";
        FormResponseCustom response = (FormResponseCustom) window.getResponse();
        FormWindowCustom window =(FormWindowCustom) getWindow();
        Element element = window.getElements().get(index);
        if(element instanceof ElementInput){
            return response.getInputResponse(index);
        }else if(element instanceof ElementToggle){
            return response.getToggleResponse(index)?"TRUE":"FALSE";
        }else if(element instanceof ElementDropdown){
            return response.getDropdownResponse(index).getElementContent();
        }else if(element instanceof ElementSlider){
            return String.valueOf(response.getSliderResponse(index));
        }else if(element instanceof ElementStepSlider){
            return response.getStepSliderResponse(index).getElementContent();
        }else {
            return "NULL";
        }
    }
}
