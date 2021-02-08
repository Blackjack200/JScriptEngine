# 安装

## 下载mirai-client

去 [Release](https://github.com/Blackjack200/JScriptEngine/releases) 下载最新版本

### ___mirai-client-*.jar___

机器人本体, 必须安装.

启动方法

```shell
java -jar <jar-name>
```

启动后自动在程序运行目录生成`config.json`和`device.json`还有`scripts`文件夹

## 配置文件详解

#### `config.json`

```json5
{
  //QQ号
  "account": 110,
  //QQ密码
  "password": "12345",
  //使用协议, 支持 PHONE, PAD, WATCH
  "protocol": "WATCH"
}
```

#### `device.json`

mirai使用的虚拟设备信息, 一般不需要修改

### 安装脚本

把脚本置于运行目录 `scripts`文件夹