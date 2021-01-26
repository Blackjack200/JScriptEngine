package com.blocklynukkit.loader.script.event;

import cn.nukkit.event.Event;

public class QQOtherEvent extends Event {
    public String selfQQ;
    public String fromGroup;
    public int type;
    public String fromQQ;
    public String seq;
    public QQOtherEvent(long selfQQ,long fromGroup,int type,long fromQQ,long seq){
        this.selfQQ=selfQQ+"";
        this.fromGroup=fromGroup+"";
        this.type=type;
        this.fromQQ=fromQQ+"";
        this.seq=seq+"";
    }

    public String getFromGroup() {
        return fromGroup;
    }

    public String getFromQQ() {
        return fromQQ;
    }

    public String getSelfQQ() {
        return selfQQ;
    }

    public String getSeq() {
        return seq;
    }

    public int getType() {
        return type;
    }

}
