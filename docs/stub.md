Stub
===

# Global Constant

## Field: `logger`

Methods:

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

## Field: `server`

Return: Server Instance

## Field: `ds`

Methods:

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

## Field: `file`

Methods:

```php
  //success ? true : false
  function put(String name, String content) : boolean
  function read(String name) : String
```

## Field: `parse`

Methods:

```php
  function emitYAML(Object data) : String
  function parseYAML(String content) : Object
  function parseJSON(String data) : Object
  function emitJSON(Object content) : String
```

## Field: `__NAME__`

the name of script

## Field: `command`

Methods:

```php
  function createBuilder() : CommandBuilder
  //This command is a compeleted CommandBuilder
  //Demo builder.callback("callback_func").name("commandName").description("Demo command").build()
  function register(Command builder) : void
```

## Field: `thread`

Methods:

```php
  function create(Runnable runnable) : Thread
  function synchronize(Runnable runnable) : void
  function aInteger(int i) : AtomicInteger
  function aLong(long l) : AtomicLong
  function aDouble(double d) : AtomicDouble
  function aBoolean(boolean b) : AtomicBoolean
```

## Field: `__java__`

Methods:

```php
  function getClass(Object object) : Class
  function getRuntime() : Runtime
```
