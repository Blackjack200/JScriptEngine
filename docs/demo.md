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
function PlayerJoinEvent(e) {
    event.getPlayer().sendMessage("Hello")
}
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
```

## Ruby

```ruby
logger.warning("Ruby")
def PlayerJoinEvent(event)
    event.getPlayer().sendMessage("Hello") 
end
```