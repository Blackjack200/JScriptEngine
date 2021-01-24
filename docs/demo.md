Demo
===

# Original

```java
class DEMO implements Listener {
	@EventHandler
	public void PlayerJoinEvent(PlayerJoinEvent event) {
		event.getPlayer().sendMessage("Hello");
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
```

## Groovy

```groovy
logger.warning("Groovy")

void PlayerJoinEvent(PlayerJoinEvent event) {
    event.getPlayer().sendMessage("Hello")
}
```

## Python

```python
logger.warning("Python")
def PlayerJoinEvent(event) :
    event.getPlayer().sendMessage("Hello")
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
```

## Ruby

```ruby
logger.warning("Ruby")
def PlayerJoinEvent(event)
    event.getPlayer().sendMessage("Hello") 
end
```