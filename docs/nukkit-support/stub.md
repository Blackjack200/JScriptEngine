# API

_演示代码_

```javascript
internal.getAvailableProcessorCount()
file.parseJSON("")
```

## 全局常量: `logger`

```php
  function debug(String message) : void
  function error(String message) : void
  function alert(String message) : void
  function emergency(String message) : void
  function critical(String message) : void
  function notice(String message) : void
  function info(String message) : void
  function warning(String message) : void
```

## 全局常量: `server`

Nukkit的服务器实例

## 全局常量: `ds`

```php
  function list() : LinkedList
  function list(T element) : LinkedList<T>
  function map() : LinkedHashMap
  function map(KT key, VT val) : LinkedHashMap<KT, VT>
  function concurrentMap() : ConcurrentHashMap
  function concurrentMap(KT key, VT val) : ConcurrentHashMap
  function vector() : Vector
  function vector(T element) : Vector
```

## 全局常量: `file`

```php
  //success ? true : false
  function put(String name, String content) : boolean
  function read(String name) : String
  function touch(String name) : boolean
  function mkdir(String name) : boolean
  function remove(String name) : void
  function getPath() : String
  function emitYAML(Object data) : String
  function parseYAML(String content) : Object
  function parseJSON(String data) : Object
  function emitJSON(Object content) : String
```

## 全局常量: `__NAME__`

脚本的名称, 也就是文件名

## 全局常量: `command`

```php
  function createBuilder() : CommandBuilder
  //This command is a compeleted CommandBuilder
  //Demo builder.callback("callback_func").name("commandName").description("Demo command").permission("xx.xx.xx").build()
  function register(Command builder) : void
  function createInfo(String name, String description, String callback, String permission) : CommandInfo
  function createInfo(String name, String description, String callback) : CommandInfo
  function createPermission(String permission, String description, String _default) : void
  function removePermission(String permission) : void
```

## 全局常量: `__java__`

```php
  function getClass(Object object) : Class
  function getRuntime() : Runtime
```

## 全局常量: `complex`

```php
  function getSlapperBuilder() : HumanSlapperHookBuilder
  function createSlapper(Position position, String name, Skin skin, HumanSlapperHook hook) : HumanSlapper
  function createSlapper(Position position, String name, Skin skin) : HumanSlapper
  function scheduleDelayedTask(String callback, int delay, boolean asynchronous) : TaskHandler
  function scheduleRepeatingTask(String callback, int delay, boolean asynchronous) : TaskHandler
  function scheduleDelayedRepeatingTask(String callback, int period, int delay, boolean asynchronous) : TaskHandler
```

## 全局常量: `internal`

```php
  function getSystemCPULoad() : double
  function getAvailableProcessorCount() : int
  function getMemoryTotalSizeMB() : double
  function getMemoryUsedSizeMB() : double
```
