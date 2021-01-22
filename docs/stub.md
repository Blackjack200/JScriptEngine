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
```

## Field: `file`

Methods:

```php
  //success ? true : false
  function put(String name, String content) : boolean
  function read(String name) : String
  function getPath() : String
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