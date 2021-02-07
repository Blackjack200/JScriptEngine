# 引擎

本项目中已实现的引擎列表

- Nashorn
- LuaJ
- Jython
- JRuby
- Groovy
- Quercus

_所有公开的Java方法都可以在脚本中被调用, 引擎特殊用法主要体现在代码和注释, 请务必仔细阅读._

在目前的引擎中, Java方法和类应被映射到脚本环境中

```java
package demo;

class X {
    public static Object test;

    public static void method1();

    public static void method2(Object object);

    public static void method3(Runnable runnable);

    public static void method4(int val);
}
```

在脚本中实际上就是

```javascript
//Java中类的demo.X 映射到X_Class全局变量
extern_name('demo.X', 'X_Class')
//field访问
var test = X_Class.test
//函数调用
X_Class.method1()
//自动把String转换为Java中的对象
X_Class.method2("idk")
//闭包函数自动转换
X_Class.method3(function () {
    print(test)
})
//自动转化
X_Class.method4(123)
```

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