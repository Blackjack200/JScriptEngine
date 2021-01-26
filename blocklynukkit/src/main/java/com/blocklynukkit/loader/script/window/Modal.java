package com.blocklynukkit.loader.script.window;

import cn.nukkit.Player;
import cn.nukkit.form.window.FormWindowModal;
import com.blocklynukkit.loader.Loader;
import com.blocklynukkit.loader.script.window.windowCallbacks.ModalCallback;

public class Modal {
    public int id = (int) Math.floor(Math.random() * 10000000);
    public String title = "";
    public String context = "";
    public String btn1 = "";
    public String btn2 = "";
    public String btn1Callback = null;
    public String btn2Callback = null;
    public short previous = 0;

    public Modal setTitle(String title) {
        this.title = title;
        return this;
    }

    public Modal title(String title) {
        return setTitle(title);
    }

    public Modal setContext(String context) {
        this.context = context;
        return this;
    }

    public Modal context(String context) {
        return setContext(context);
    }

    public Modal setButton1(String text) {
        btn1 = text;
        previous = 1;
        return this;
    }

    public Modal setButton2(String text) {
        btn2 = text;
        previous = 2;
        return this;
    }

    public Modal button1(String text) {
        return setButton1(text);
    }

    public Modal button2(String text) {
        return setButton2(text);
    }

    public Modal setAction(String callback) {
        if (previous == 0) return this;
        if (previous == 1) btn1Callback = callback;
        if (previous == 2) btn2Callback = callback;
        return this;
    }

    public Modal action(String callback) {
        return setAction(callback);
    }

    public Modal showToPlayer(Player p) {
        return this.showToPlayer(p, null, false);
    }

    public Modal showToPlayer(Player p, boolean acceptClose) {
        return this.showToPlayer(p, null, acceptClose);
    }

    public Modal showToPlayer(Player p, String callback) {
        return this.showToPlayer(p, callback, false);
    }

    public Modal showToPlayer(Player p, String callback, boolean acceptClose) {
        synchronized (Loader.windowCallbackMap) {
            ModalCallback windowCallback = new ModalCallback(acceptClose);
            FormWindowModal window = new FormWindowModal(title, context, btn1, btn2);
            if (callback != null) {
                windowCallback.setDefaultCallback(callback);
            }
            windowCallback.setYesCallback(btn1Callback);
            windowCallback.setNoCallback(btn2Callback);
            Loader.windowCallbackMap.put(id, windowCallback);
            p.showFormWindow(window, id);
        }
        return this;
    }

    public Modal show(Player p) {
        return this.showToPlayer(p);
    }

    public Modal show(Player p, boolean accpetClose) {
        return this.showToPlayer(p, accpetClose);
    }

    public Modal show(Player p, String callback) {
        return this.showToPlayer(p, callback);
    }

    public Modal show(Player p, String callback, boolean acceptClose) {
        return this.showToPlayer(p, callback, acceptClose);
    }

    //    public Modal showToPlayer(Player p, String callback){
//        synchronized (Loader.functioncallback){
//            Loader.functioncallback.put(id,callback);
//            FormWindowModal modal=new FormWindowModal(title,context,btn1,btn2);
//            p.showFormWindow(modal,id);
//        }
//        return this;
//    }
//    public Modal showToPlayer(Player p,String callback,boolean acceptClose){
//        if(acceptClose){
//            synchronized (Loader.acceptCloseCallback){
//                Loader.acceptCloseCallback.put(callback,true);
//            }
//        }
//        return this.showToPlayer(p, callback);
//    }
//    @Deprecated
//    public Modal showToPlayerCallLambda(Player p,ScriptObjectMirror mirror){
//        synchronized (Loader.scriptObjectMirrorCallback){
//            if(mirror!=null){
//                Loader.scriptObjectMirrorCallback.put(id,mirror);
//            }
//            FormWindowModal modal=new FormWindowModal(title,context,btn1,btn2);
//            p.showFormWindow(modal,id);
//        }
//        return this;
//    }
//    @Deprecated
//    public Modal showToPlayerCallLambda(Player p,ScriptObjectMirror mirror,boolean acceptClose){
//        if(acceptClose){
//            synchronized (Loader.acceptCloseCallback){
//                Loader.acceptCloseCallback.put(mirror.toString(),true);
//            }
//        }
//        return this.showToPlayerCallLambda(p, mirror);
//    }
    @Override
    public String toString() {
        FormWindowModal modal = new FormWindowModal(title, context, btn1, btn2);
        return "ModalWindowForm: " + modal.getJSONData() + "";
    }
}
