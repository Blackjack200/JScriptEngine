package com.blocklynukkit.loader.script.event;

import cn.nukkit.event.Event;

public class QQFriendMessageEvent extends Event {
    public String selfQQ;
    public String fromQQ;
    public String eventSeed;
    public String eventId;
    public String message;
    public QQFriendMessageEvent(long selfQQ,long fromQQ,long eventSeed,long eventId,String message){
        this.selfQQ=selfQQ+"";
        this.fromQQ=fromQQ+"";
        this.eventSeed=eventSeed+"";
        this.eventId=eventId+"";
        this.message=message;
    }

    public String getEventId() {
        return eventId;
    }

    public String getEventSeed() {
        return eventSeed;
    }

    public String getFromQQ() {
        return fromQQ;
    }

    public String getSelfQQ() {
        return selfQQ;
    }

    public String getMessage() {
        return message;
    }
}
