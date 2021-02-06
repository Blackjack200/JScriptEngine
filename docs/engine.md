# Engine

本项目中已实现的引擎列表

- Nashorn
- LuaJ
- Jython
- JRuby
- Groovy
- Quercus

其中有些引擎有特殊用法, 本篇文档主要说明特殊用法的使用, 主要体现在代码和注释, 请务必仔细阅读.

## PHP

```php
<?php
//无效, 所有对标准输出的操作应使用模块提供的logger或者System.out
echo 'test';
//有效
extern('java.lang.System');
$System::out->print('test');
//有效
extern_name('java.lang.System', 'test_name');
$test_name::out->print('test');
```

## JavaScript

```javascript
//有效
print('test')
//有效
extern('java.lang.System')
System.out.print('test')
//有效
extern_name('java.lang.System', 'test_name')
test_name.out.print('test')
```

## Lua

```lua
--有效
print('test')
--有效
extern('java.lang.System')
System:out.print('test')
--有效
extern_name('java.lang.System', 'test_name')
test_name:out.print('test')
```