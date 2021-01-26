## 1.2.9.0

new

重构代码加载器代码，重构了bnqqbot代码，增强稳定性，精简解释器
添加bn插件包功能，.bnp(bn插件集合包)和.bnpx(bn插件复合压缩包)可以放到blocklynukkit文件夹里面直接运行
使用bnp命令可以操作包，bnp build <makefile路径>即可构建一个bn插件包
makefile是一个json格式的包配置文件，由name,compress,plugins三个字段组成
name为打包的插件包的名字(字符串)，compress为是否打出压缩插件包(boolean)，
plugins是一个字符串数组，里面标注要打进包中的bn插件的路径，加载时从上到下加载，
也可以打进去json yml xml txt格式的资源文件，资源文件将会被按顺序解压到打包时与nk核心的相对位置解压。
js中可以使用console了
现在js插件报错会显示正确的文件名了
F(Closure function)函数用于包装闭包
bnAPI中的;换行现在支持使用\\;来转义
修复了php中F(闭包)函数返回类型错误的问题
修复了logger(console)输出null或nil报错的问题

PHP

更新了基于quercusPHP6引擎的php插件编写支持
quercus提供的运行环境请看http://quercus.caucho.com/quercus-3.1/doc/quercus.xtp
兼容全部的java类、nukkit的api和bn的基对象，调用bn的api记得前面加$

export

可以通过导出标记将bn插件中的函数导出到全局环境中，然后即可使用java类
命令exportdevjar <BN插件名>将可以导出bn插件中的函数头到一个jar包里面便于开发

javascript:
```javascript
export function MyFun(arg1,arg2,arg3){}
```

python:
```python
@export
def MyFun(arg1,arg2,arg3):
    pass
```

lua:
```lua
function BlockPlaceEvent(ent)-->export

end
```

php:
```php
static function PlayerJoinEvent($event){
    
}
```

logger

- 强化了logger的功能，现在可以将对象以json格式输出

manager

- Player[] getOnlinePlayers()
- String getResource(String name) -- 读取资源
- void runCMD(String cmd) --执行命令行程序
- boolean isPathExists(String path)
- String[] getFolderFiles(String path)
- int getFileSize(String path)
- void deleteFile(String path)
- void doPathCreate(String path)
- boolean isPathReadable(String path)
- boolean isPathWritable(String path)
- void copyFile(String fromPath,String toPath)

entity

- void shootArrow(Position from,Position to,boolean canPickUp,double multiply)
- void shootArrow(Position from,Position to,double multiply)
- void shootArrow(Position from,Position to,boolean canPickUp)
- void shootArrow(Position from,Position to)
- void lookAt(Entity e,Position pos)

window

- int[] setPlayerBossBar的第二个text参数中若以#XXXXXX或者rgb(x,x,x)开头将会为boss血条设置自定义颜色
- int[] setPlayerBossBar现在可以在text参数中使用;来添加多个bossbar
- int[] setPlayerBossBar(Player player,String text,float len) --现在返回数组，包含每个创建的bossbar的id
- void removePlayerBossBar(Player player,long id) --相关api现在支持指定bossbar的id操作
- double getLengthOfPlayerBossBar(Player player,long id)
- String getTextOfPlayerBossBar(Player player,long id)
- void sendPlayerXboxInfo(Player from,Player to) --向to玩家展示from玩家的xbox信息
- void startEndPoem(Player player) --让玩家屏幕上开始展示终末之诗

WindowBuilders

- void showToPlayer(Player p,String callback,boolean acceptClose)
    - acceptClose用于指定是否在窗口关闭的时候调用一次回调函数
    
SimpleWindowBuilder

-  buildButton(String text,String img)
-  button(String text,String img)
-  button(String text)
-  setAction(String actionFunctionName/F function)
-  action(String actionFunctionName/F function)
-  setTitle(String title)
-  title(String title)
-  setContext(String context)
-  context(String context)
-  showToPlayer(Player p)
-  showToPlayer(Player p,boolean acceptClose)
-  showToPlayer(Player p,String callbackFunctionName/F function)
-  showToPlayer(Player p,String callbackFunctionName/F function,boolean acceptClose)
-  show(Player p)
-  show(Player p,boolean accpetClose)
-  show(Player p,String callbackFunctionName/F function)
-  show(Player p,String callbackFunctionName/F function,boolean acceptClose)

ModalWindowBuilder

-  setTitle(String title)
-  title(String title)
-  setContext(String context)
-  context(String context)
-  setButton1(String text)
-  setButton2(String text)
-  button1(String text)
-  button2(String text)
-  setAction(String callbackFunctionName/F function)
-  action(String callbackFunctionName/F function)
-  showToPlayer(Player p)
-  showToPlayer(Player p,boolean acceptClose)
-  showToPlayer(Player p,String callbackFunctionName/F function)
-  showToPlayer(Player p,String callbackFunctionName/F function,boolean acceptClose)
-  show(Player p)
-  show(Player p,boolean accpetClose)
-  show(Player p,String callbackFunctionName/F function)
-  show(Player p,String callbackFunctionName/F function,boolean acceptClose)

CustomWindowBuilder

-  setTitle(String title)
-  showToPlayer(Player p)
-  showToPlayer(Player p,boolean acceptClose)
-  showToPlayer(Player p,String callbackFunctionName/F function)
-  show(Player p)
-  show(Player p,boolean accpetClose)
-  show(Player p,String callbackFunctionName/F function)
-  show(Player p,String callbackFunctionName/F function,boolean acceptClose)
-  showToPlayer(Player p,String callbackFunctionName/F function,boolean acceptClose)
-  showAsSetting(Player p, String callbackFunctionName/F function)
-  showAsSetting(Player p, String imageURL, String callbackFunctionName/F function)
-  buildLabel(String text)
-  label(String text)
-  buildInput(String title,String placeholder)
-  input(String title,String placeholder)
-  buildInput(String title,String placeholder,String defaulttext)
-  input(String title,String placeholder,String defaulttext)
-  buildToggle(String title)
-  toggle(String title)
-  buildToggle(String title,boolean open)
-  toggle(String title,boolean open)
-  buildDropdown(String title,String inner)
-  dropdown(String title,String inner)
-  buildDropdown(String title,String inner,int index)
-  dropdown(String title,String inner,int index)
-  buildSlider(String title,double min,double max,int step,double defaultvalue)
-  slider(String title,double min,double max,int step,double defaultvalue)
-  buildSlider(String title,double min,double max,int step)
-  slider(String title,double min,double max,int step)
-  buildSlider(String title,double min,double max)
-  slider(String title,double min,double max)
-  buildStepSlider(String title,String options)
-  stepSlider(String title,String options)
-  stepslider(String title,String options)
-  buildStepSlider(String title,String options,int index)
-  stepSlider(String title,String options,int index)
-  stepslider(String title,String options,int index)
-  setAction(String callbackFunctionName/F function)
-  action(String callbackFunctionName/F function)


    
BNNPC

- void addExtraDropItem(Item item)
- boolean hasDropItem(Item item)
- void removeExtraDropItem(Item item)
- Item[] getExtraDropItems()
- Item[] getDropItems()
- void setDropHand(boolean drop/void) --参数不填时默认为true
- void setDropOffhand(boolean drop/void) --参数不填时默认为true
- void addDropSlot(int slot) --指定背包槽位死亡掉落
- int[] getDropSlots()
- void removeDropSlot(int slot)
- void doEmote(String action/void) --让npc执行指定的表情动作
    - action可以是 Wave Punch Clap OverThere Hammer Fall Diamond Pickaxe
    - 也可以填写对应的中文 挥手 拳击 鼓掌 在那 锤子 摔倒 钻石
    - 不填的话将上面的五个动作中任选一个
    - 至于为什么只有8个: 因为剩下的要氪金购买之后才能获取uuid，冰凉没钱
    - 欢迎大家贡献uuid
- boolean directMove(Position to) --直线移动到某处

world

- void regenerateChunk(Position pos)
- void defineChunkRenderByName(String forLevel,String callback,int priority / void)
    - forLevel是渲染器能够渲染的指定世界名称
    - callback是渲染器回调函数
    - priority是优先级，优先级越大先调用，不填默认为0
    
blockitem

- void registerSolidBlock(int id,String name,double hardness,double resistance,int toolType,boolean isSilkTouchable,int dropMinExp,int dropMaxExp,int mineTier)
    - 注册固体方块，参数分别为方块id(可覆写原版方块)，方块名称，方块硬度，方块抗爆炸度，挖掘工具，是否受精准采集影响，最小掉落经验，最大掉落经验，挖掘等级
    - 方块硬度越大挖掘时间越长，抗爆炸度越高越不容易被炸
    - 挖掘工具0-无,1-剑,2-铲,3-镐,4-斧,5-剪刀
    - 挖掘等级0-空手,1-木,2-金,3-石,4-铁,5-钻石
- void registerSimpleItem(int id,String name)
    - 注册简单的物品堆，但是现在无法显示材质，只能在覆写教育版物品时显示材质


## 1.2.8.4
new

更新了qq机器人对接模块，使用小栗子qq机器人框架（因为这是唯一一个没跑路的免费机器人了），配好的包在bn群内下载
您也可以从官网下载框架并自行安装tcpapi.dll插件到机器人框架中，bn通过tcpapi来与其交互
添加了com.blocklynukkit.JavaAPI类来提供bn对java的api
添加了调试工具，使用命令bndebug打开调试工具，可以查看变量和监控命令情况

Lua

添加了lua语言支持，版本为luaj5.2，可以通过lua来制作bn插件，接口与js和py完全相同
您需要使用:来访问基对象函数，此外还提供了用于和java交互的luajava对象和asTable asList asMap三个全局函数
详见bn开发文档

Bug Fixed

- 修复了熔炉配方nbt丢失问题
- 修复了设置箱子和漏斗物品栏不好使的问题
- 修复了py插件不可用问题
- 修复了BNNPC在路径移动时被击退遁地问题
- 修复了BNNPC导致的区块报错问题

BNNPC

- Array<Player> getPlayersIn(double distance)
- Array<Entity> getEntitiesIn(double distance)
- Player getNearestPlayer(double far)
- Player getNearestPlayer()
- void setEntityRideOn(Entity entity)
- void isEntityRideOn(Entity entity)
- void setEntityRideOff(Entity entity)
- Player getRidingPlayer()

window

- setPauseScreenList(String list) --设置暂停界面右侧显示在线玩家区域的文字，用;分割多行

CustomWindowBuilder

-  this showAsSetting(Player p, String imageURL, String callback) --支持图标，和按钮图标书写方式相同

manager

- void qq.startBot() 启动qq机器人进程
- void qq.reDirectBot(String ip) 
    - 将机器人重定向到指定ip地址，并使用那台电脑的小栗子qq机器人框架
    - 要求目标电脑开放8404-TCP端口，并且在小栗子的tcpapi插件中允许远程控制
- void qq.sendFriendMessage(String fromQQ,String toQQ,String message) 发送好友信息
- void qq.sendGroupMessage(String fromQQ,String toGroup,String message) 发送群信息
- void qq.sendGroupPicMessage(String fromQQ,String toGroup,String picturePaths,String message)
    - 发送qq图文消息
    - picturePaths用;分割多个本地图片路径
    - 消息中使用图片只需用%picture数字%即可，数字指代第几个路径的图片，从0开始算起
- void qq.kickGroupMember(String fromQQ,String toGroup,String toQQ) --踢了指定群员,fromQQ是机器人账号
- void qq.banSpeakGroupMember(String fromQQ,String toGroup,String toQQ,int second) --禁言指定群员
- String getPlayerDeviceID(Player player) --获取玩家的手机或电脑设备标识码
- String getPlayerDeviceModal(Player player) --获取玩家的设备型号
- int getPlayerDeviceOS(Player player) -- 获取玩家的操作系统id
- double getMoney(String player)
- void reduceMoney(String player,double money)
- void addMoney(String player,double money)
- void setMoney(String player,double money)
- void setNukkitCodeVersion(String string) -- 修改version命令显示的nk版本
- void nodejs.eval(String str,boolean isPath) -- 使用nodejs运行str
    - 运行nodejs代码是隔离在nodejs环境运行的，而非java环境
    - 若isPath为true，则执行该路径的文件
    - 否则将str作为nodejs代码执行
    - 其中可以使用callFunction(String BNFunctionName,String args...)来调用bn插件的函数
- void nodejs.newDocker(String dockerName,String str,boolean isPath) --开启一个常驻nodejs容器
    - dockerName是创建的nodejs容器的名字，容器一旦创建就会立即开始执行其中的代码
    - 重启创建后执行完代码不会被销毁，而是可以继续通过callDockerFunction调用其中方法
    - 如果需要在其他bn插件调用其中的nodejs函数，需要使用registerFunction(String 函数名,Function 函数)注册
    - 其余同nodejs.eval函数
- void nodejs.closeDocker(String dockerName) --关闭指定的nodejs容器并释放占用资源
- String nodejs.callDockerFunction(String function,String... args)
    - 调用指定容器中的指定函数并向其传参，调用的函数必须先注册再使用，否则bn无法获取此函数内存地址进行调用
    - 返回值将自动被转为字符串，如果被调函数无返回值则返回字符串"null"
    - function指定调用的函数，格式为 容器名::函数名（同其他地方的调用格式）
    - 若直接输入函数名，则将在所有未关闭容器中随机寻找一个有此名称函数的容器调用，若找不到，返回NO FUNCTION
    - args参数只接受字符串，数量不限，也可没有
- TaskHandler createTask(String functionName, int delay ,\<E+\>... args)
- TaskHandler createLoopTask(String functionName, int delay,\<E+\>... args)
    - 支持2-128个任意数量的参数，第一个参数为回调函数名，第二个为回调间隔tick，其余的是在调用函数时向函数传递的参数
- void newPlugin(String path) --加载指定路径上的bn插件
- void newJSPlugin(String name,String code) --根据代码字符串创建一个新的bn插件
- void newPYPlugin(String name,String code)
- void newLUAPlugin(String name,String code)
- void addCommandCompleter(String cmd,String id,String completer)
    - 创建命令补全器，将被发送给玩家用作命令提示和tab补全
    - cmd为要添加给的命令的名称，id为补全器标识符，随意只要不重复即可
    - completer是补全器内容，由\<必选单元>和\[可选单元]由空格连缀组成
    - 每个单元的内部格式为 名称:@类型=参数1;参数2;... 等于号及其后面的部分不是必须的
    - 类型有：@target @blockpos @pos @int @float @string @text 
    - 类型有：@message @command @json @filepath @operator
    - 例如：<player:@target> <sentence:@message=BNNB!;blocklynukkit> [color:@text=red;green]

EventLoader

- QQGroupMessageEvent --机器人收到qq群消息事件
    - String getSelfQQ() --获取收到消息的qq账号
    - String getFromQQ() --获取发送消息的qq账号
    - String getFromGroup() --获取消息事件的qq群号
    - String getMessage() -获取消息
- QQFriendMessageEvent --机器人收到qq好友消息事件
    - String getEventId() --获取事件id
    - String getEventSeed() --获取事件中的群消息标识码
    - String getFromQQ() --获取发送消息的qq
    - String getSelfQQ() --获取接受到群消息的qq账号
    - String getMessage() --获取事件的消息
- QQOtherEvent
    - String getFromGroup() --获取触发事件的qq群号
    - String getFromQQ() --获取触发事件的qq账号
    - String getSelfQQ() --获取收到事件的qq账号
    - String getSeq() --获取收到事件的标识id
    - int getType() --获取事件类型码
    
Entity

- void setPlayerExp(Player player,int exp)
- int getPlayerExp(Player player)
- void setPlayerExpLevel(Player player,int lel)
- int getPlayerExpLevel(Player player)
- void setPlayerHunger(Player player,int hunger)
- int getPlayerHunger(Player player)
- void makeSoundToPlayer(Player player,String sound)
- Entity spawnEntity(String name,Position pos) --返回值更改


## 1.2.8.3

Bugs Fixed

- 修复了bnnpc浮空走路bug
- 修复了浮空物品空事件报错
- 现在的报错信息比以前好看多了

manager

- <E> callFunction(String functionname,Object... args) --callFunction会返回函数的返回值了
- <E> getVariableFrom(String scriptName,String varName) --根据bn插件名和变量名获取变量内容
- void putVariableTo(String scriptName,String varName,<E> var) --把变量值以指定变量名放到指定bn插件中
- double getCPULoad()
- int getCPUCores()
- double getMemoryTotalSizeMB()
- double getMemoryUsedSizeMB()
- void forceDisconnect(Player player)
- Array<String> getEventFunctions(Event event)
- void getServerMotd(String host, int port, String callback) --根据服务器IP和端口获取在线人数信息

blockitem

- void setItemColor(Item item,int r,int g,int b)
- void setItemUnbreakable(Item item,boolean unbreakable)

inventory

- Item getEntityHelmet(Entity entity)
- Item getEntityChestplate(Entity entity)
- Item getEntityLeggings(Entity entity)
- Item getEntityBoots(Entity entity)
- Item getEntityItemInHand(Entity entity)
- Item getEntityItemInOffHand(Entity entity)
- void setEntityItemHelmet(Entity entity,Item item)
- void setEntityItemChestplate(Entity entity,Item item)
- void setEntityItemLeggings(Entity entity,Item item)
- void setEntityItemBoots(Entity entity,Item item)
- void setEntityItemInHand(Entity entity,Item item)
- void setEntityItemInOffHand(Entity entity,Item item)
- Item getInventorySlot(Inventory inv,int slot)

window

- void forceClearWindow(Player player)
- int getEventResponseIndex(PlayerFormRespondedEvent event)

entity

- boolean isPlayer(Entity e)
- void spawnFallingBlock(Position pos, Block block, boolean enableGravity,boolean canBePlaced)

gameapi --新的基对象

- void createGame(String name,boolean useTeam,String startGameCallBack,String endGameCallBack,String mainLoopCallBack,String deathCallBack)
    -- 创建一个小游戏房间
- void joinGame(Player player, String gameName) --让玩家进入指定名称的小游戏，自动匹配房间
- void leaveGame(Player player) --让玩家从小游戏房间离开
- boolean isPlayerInGame(Player player) --玩家是否正在玩某个小游戏
- GameBase getPlayerRoom(Player player) --获取玩家正在玩的小游戏对象
- Array<GameBase> getAllRoomByName(String gameName) --获取游戏名称相同的所有小游戏房间对象组成的数组
- Array<String> getAllGameNames() --获取所有正在运行的小游戏房间的名字组成的数组
- Messager getMessager(String prefix)
- Messager getGameMessager(GameBase game)
- Multiline getMultiline(String messageType)
- InventoryMenu createInventoryMenu(String inventoryType, String title)
- FormMenu createFormMenu(String title, String content)
- void addMenuItem(InventoryMenu menu, int slot, Item item, String inventoryCallback)
- void addMenuButton(FormMenu menu,String buttonText,String imageData,String formCallback)
- Scoreboard getScoreboard(Player p)
- void setObjective(Scoreboard sb, String objectiveName,String displayName)

GameBase --小游戏房间对象


EventLoader --73 new

- BlockFadeEvent
- BlockFallEvent
- BlockFromToEvent
- BlockGrowEvent
- BlockIgniteEvent
- BlockPistonChangeEvent
- BlockRedstoneEvent
- DoorToggleEvent
- CreatureSpawnEvent
- CreeperPowerEvent
- EntityArmorChangeEvent
- EntityBlockChangeEvent
- EntityCombustByBlockEvent
- EntityCombustByEntityEvent
- EntityCombustEvent
- EntityDamageByBlockEvent
- EntityDamageByChildEntityEvent
- EntityExplodeEvent
- EntityMotionEvent
- EntityPortalEnterEvent
- EntityRegainHealthEvent
- EntityShootBowEvent
- EntityVehicleEnterEvent
- EntityVehicleExitEvent
- ExplosionPrimeEvent
- BrewEvent
- EnchantItemEvent
- InventoryMoveItemEvent
- StartBrewEvent
- ChunkLoadEvent
- ChunkPopulateEvent
- LevelInitEvent
- LevelLoadEvent
- LevelSaveEvent
- LevelUnloadEvent
- SpawnChangeEvent
- ThunderChangeEvent
- WeatherChangeEvent
- PlayerAchievementAwardedEvent
- PlayerAnimationEvent
- PlayerAsyncPreLoginEvent
- PlayerBlockPickEvent
- PlayerBucketEmptyEvent
- PlayerBucketFillEvent
- PlayerChangeSkinEvent
- PlayerChunkRequestEvent
- PlayerCreationEvent
- PlayerDropItemEvent
- PlayerEatFoodEvent
- PlayerEditBookEvent
- PlayerFoodLevelChangeEvent
- PlayerGameModeChangeEvent
- PlayerGlassBottleFillEvent
- PlayerInvalidMoveEvent
- PlayerItemConsumeEvent
- PlayerLocallyInitializedEvent
- PlayerMapInfoRequestEvent
- PlayerMouseOverEntityEvent
- PlayerServerSettingsRequestEvent
- PlayerSettingsRespondedEvent
- PluginDisableEvent
- PluginEnableEvent
- PotionApplyEvent
- PotionCollideEvent
- PlayerDataSerializeEvent
- RemoteServerCommandEvent
- EntityEnterVehicleEvent
- EntityExitVehicleEvent
- VehicleCreateEvent
- VehicleDamageEvent
- VehicleDestroyEvent
- VehicleMoveEvent
- VehicleUpdateEvent
- LightningStrikeEvent

## 1.2.8.2

Bug Fixed

- 修复了bnnpc和bn浮空字莫名其妙消失的问题

New

- 现在可以在js代码的开头加上一行注释//pragma es9来开启es9语言特性，但是会有些许性能损失，损失不大，可以放心使用
- 此功能仍然是试验功能，如果报错，请立即反馈，感谢
- bninstall命令已经弃用，所有库全部打包进bn解释器jar，这样做是为了节约内存空间。

CustomWindowBuilder

- Custom showAsSetting(Player p, String callback)
- Custom buildDropdown(String title,String inner,int index)

EventLoader

- ChunkUnloadEvent

entity

- void setPlayerExp(Player player,int exp)
- int getPlayerExp(Player player)
- void setPlayerExpLevel(Player player,int lel)
- int getPlayerExpLevel(Player player)
- void setPlayerHunger(Player player,int hunger)
- int getPlayerHunger(Player player)

window

- void makeTipsVar(String varname,String providerCallback)
- void makeTipsStatic(String varname,String toReplace)

## 1.2.8.1

Entity

- BNNPC buildNPC(Position pos,String name,String skinID,int calltick,String callfunction,String attackfunction)
- void showFloatingItem(Position pos,Item item)
- void removeFloatingItem(Position pos,Item item)

windowbuilder

- Custom buildSlider(String title,double min,double max)
- Custom buildSlider(String title,double min,double max,int step)
- Custom buildSlider(String title,double min,double max,int step,double defaultvalue)
- Custom buildStepSlider(String title,String options)
- Custom buildStepSlider(String title,String options,int index)

window

- String getEventCustomVar(PlayerFormRespondedEvent event,int id,String mode)
- mode可以为input toggle dropdown slider stepslider

BNNPC

- void displaySwing()
- void setSwim(boolean swim)
- void setSwim()
- void setTickCallback(String callback)
- void setAttackCallback(String callback)

manager

- void bStats(String pluginName,String pluginVer,String authorName,int pluginid)
- void callFunction(String functionname,Object... args) --修复了错误的拼写

world

- void loadScreenTP(Player player,Position pos)
- void loadScreenTP(Player player,Position pos,int loadScreenTick)
- void clearChunk(Position pos)

EventLoader

- PlayerHeldEvent
- InventoryClickEvent

Bug Fixed

- manager.kickPlayer不再会显示"kicked by admin"前缀了
- bnnpc打人会正确地摇动手臂了
- callFunction拼写是正确的了
- database现在真的可用了，所有库都会被正确安装

## 1.2.8.0_LTS
类库管理器

- 现在类库管理器可以直接安装模块了，暂时只有python和database两个模块
- 使用命令 bninstall 模块名 安装这个类库

window

- void setBelowName(Player player,String str)

manager

- void loadJar(String path)

world

- void setOceanGenerator(int seaLevel)

entity

- BNNPC buildNPC(Position pos,String name,String skinID)
- BNNPC buildNPC(Position pos,String name,String skinID,int calltick,String callfunction)

BNNPC

- void turnRound(double yaw)
- void headUp(double pitch)
- void setEnableAttack(boolean attack)
- void setEnableAttack()
- void setEnableHurt(boolean hurt)
- void setEnableHurt()
- void displayHurt()
- void start()
- void setEnableGravity(boolean gravity)
- void setEnableGravity()
- void setG(double newg)
- void lookAt(Position pos)
- Player getNearestPlayer()
- boolean isSneak()
- void setSneak(boolean sneak)
- void setSneak()
- void jump()
- void setJumpHigh(double j)
- void setEnableKnockBack(boolean knock)
- void setEnableKnockBack()
- void setKnockBase(double base)
- boolean canMoveTo(Position to)
- boolean findAndMove(Position to)
- void setSpeed(double s)
- void setRouteMax(int m)
- void stopMove()
- void hit(Entity entity)

bug fixed:

- entity的effect有些药水不显示问题，但是仍然有些药水效果因为nk不支持无法显示
- world生成VOID和OCEAN出错问题
- 天域世界配置丢失问题
- ssh报错问题
- 现在窗口管理器的操作函数都返回自身，可以直接在代码里连缀了


## 1.2.7.4

Languages

现在可以使用python2.7来制作插件了
添加了对python开发插件的完全支持，只需要下载额外的py支持包即可使用python插件
对于python开发插件的支持将与JavaScript保持同步，python与js使用同一套bn类库，所有js的bn类库(除了Java模块)之外都可以在python中直接调用，无需import
支持全部的python2.7原生标准语法和标准库，运行时与js相同，编译为java字节码运行，不必担心效率低下问题
pythonForBN支持模块下载：https://tools.blocklynukkit.com/pythonForBN.jar
下载后直接放到./plugins/BlocklyNukkit文件夹下面即可

EventLoader

- PlayerInteractEntityEvent
- PlayerDamageByPlayerEvent
- EntityKilledByEntityEvent
- PlayerDamageByEntityEvent
- EntityKilledByEntityEvent
- EntityKilledByPlayerEvent
- PlayerRespawnEvent

window

- void setPlayerBossBar(Player player,String text,float len)
- void removePlayerBossBar(Player player)
- double getLengthOfPlayerBossBar(Player player)
- String getTextOfPlayerBossBar(Player player)

manager

- void createPermission(String per,String description,String defaultper)
- void removePermission(String per)
- boolean checkPlayerPermission(String per,Player player)
- String MD5Encryption(String str)
- String SHA1Encryption(String str)
- void createCommand(String name, String description, String callbackFunctionName, String per)
- void newCommand(String name, String description, Function jsFunction,String per)

entity

- int getNetworkID(Entity entity)
- String getIDName(Entity entity)
- void spawnEntity(String name,Position pos)

notemusic

- HornSongPlayer buildHorn(Song song, Position pos, boolean isloop, boolean isautodestroy)
- void addPlayerToHorn(HornSongPlayer SongPlayer, Player player)
- void removePlayerToHorn(HornSongPlayer SongPlayer, Player player)
- Array getPlayerInHorn(HornSongPlayer radioSongPlayer)
- void setHornStatus(HornSongPlayer radioSongPlayer, boolean isplaying)
- Song getSongInHorn(HornSongPlayer radioSongPlayer)

world

- genLevel新增"OCEAN"海洋世界生成器

bug fixed

- setNameTagAlwaysVisable error


## 1.2.7.2
manager

- String formatJSON(String json)
- 修复writeFile函数无法自动创建路径的错误


## 1.2.7  

manager

- String readFile(String path)
- void writeFile(String path,String text)
- boolean isFileSame(String path1,String path2)
- String JSONtoYAML(String json)
- String YAMLtoJSON(String yaml)
- void newCommand(String name, String description, Function fun)
- int setTimeout(Function fun,int delay,<E+>... args)
- void clearTimeout(int id)
- int setInterval(Function fun,int delay,<E+>... args)
- void clearInterval(int id)
- void isWindows()
- int getPlayerGameMode(Player player)

Loader

- \_\_NAME\_\_ 表示加载的js文件的名称(可防御低级改名倒卖)
- 新版发布后，24小时强制更新

Custom/Modal/Simple (WindowBuilder)

- void showToPlayerCallLambda(Player p, Function fun)

blockitem

- Array<Enchantment> getItemEnchant(Item item)
- int getEnchantID(Enchantment enchantment)
- int getEnchantLevel(Enchantment enchantment)

EventLoader

- PlayerJumpEvent
- PlayerToggleGlideEvent
- PlayerToggleSwimEvent
- PlayerToggleSneakEvent
- PlayerToggleSprintEvent

