Demo
===

# Original

```java
class DEMO implements Listener {
    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent event) {
        event.getPlayer().sendMessage("Hello");
    }

    public void finalize() {
        //onDisable
    }
}
```

## JavaScript

```javascript
logger.warning("JavaScript")

function PlayerJoinEvent(event) {
    event.getPlayer().sendMessage("Hello")
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

void PlayerJoinEvent(PlayerJoinEvent event) {
    event.getPlayer().sendMessage("Hello")
}

void finalize() {
    //onDisable
}
```

## Python

```python
logger.warning("Python")
def PlayerJoinEvent(event) :
    event.getPlayer().sendMessage("Hello")
def finalize():
    #onDisable
```

## Lua

```lua
logger:warning("Lua");
function PlayerJoinEvent(event)
    event.getPlayer().sendMessage("Hello");
end

extern("java.lang.System")
System.out:println("TEST")
extern_name("java.lang.Runtime", "r")
r:getRuntime():gc()

function finalize()
    --onDisable
end
```

## Ruby

```ruby
logger.warning("Ruby")
def PlayerJoinEvent(event)
    event.getPlayer().sendMessage("Hello") 
end

def finalize()
    #onDisable
end
```

## PHP

```PHP
$logger->warning("PHP");
function PlayerJoinEvent($event) {
    $event->getPlayer()->sendMessage("Hello"); 
}

function finalize() {
    //onDisable
}
```