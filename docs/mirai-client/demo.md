# 演示代码

## JavaScript

```javascript
logger.warning("JavaScript")

function GroupMessageEvent(event) {
    event.getSender().sendMessage("Hello")
}

extern("java.lang.System")
System.out.println("TEST")
extern_name("java.lang.Runtime", "r")
r.getRuntime().gc()

function finalize() {
    //onDisable
}
```

## Groovy

```groovy
logger.warning("Groovy")

void GroupMessageEvent(GroupMessageEvent event) {
    event.getSender().sendMessage("Hello")
}

void finalize() {
    //onDisable
}
```

## Lua

```lua
logger:warning("Lua");
function GroupMessageEvent(event)
    event.getSender().sendMessage("Hello");
end

extern("java.lang.System")
System.out:println("TEST")
extern_name("java.lang.Runtime", "r")
r:getRuntime():gc()

function finalize()
    --onDisable
end
```