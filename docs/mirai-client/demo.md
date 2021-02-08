# 演示代码

## JavaScript

```javascript
logger.warning("JavaScript")

function GroupMessageEvent(event) {
    event.getSender().sendMessage("Hello")
}

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

function finalize()
    --onDisable
end
```