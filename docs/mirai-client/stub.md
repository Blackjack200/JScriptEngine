# API

_演示代码_

```javascript
internal.getAvailableProcessorCount()
file.parseJSON("")
```

## 全局常量: `server`

```php
  function getLogger() : MiraiLogger
  function getBot() : Bot
```

## 全局常量: `logger`

```php
  function info(String message) : void
  function warning(String message) : void
  function error(String message) : void
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

## 全局常量: `internal`

```php
  function getSystemCPULoad() : double
  function getAvailableProcessorCount() : int
  function getMemoryTotalSizeMB() : long
  function getMemoryUsedSizeMB() : long
```