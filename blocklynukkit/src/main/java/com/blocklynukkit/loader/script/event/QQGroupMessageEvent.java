package com.blocklynukkit.loader.script.event;


import cn.nukkit.event.Event;

public class QQGroupMessageEvent extends Event {
    public String selfQQ;
    public String fromGroup;
    public String fromQQ;
    public String message;
    public QQGroupMessageEvent(long selfQQ,long fromGroup,long fromQQ,String message){
        this.selfQQ=selfQQ+"";
        this.fromGroup=fromGroup+"";
        this.fromQQ=fromQQ+"";
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public String getSelfQQ() {
        return selfQQ;
    }

    public String getFromQQ() {
        return fromQQ;
    }

    public String getFromGroup() {
        return fromGroup;
    }
}
