# MCInfoPlugin
一个可以获取mc玩家档案和皮肤的mirai机器人插件

这是一个基于[mirai](https://github.com/mamoe/mirai) 框架的qq机器人插件

语法/编译版本: `Java11`

用到的依赖 [MinecraftInfoAPI](https://github.com/MX233/MinecraftInfoAPI)

### 功能
- `获取Minecraft java版玩家档案信息(UUID,曾用名)`
- `获取Minecraft java版玩家皮肤`

#### 指令

`/mcskin [名称/uuid]` 获取玩家皮肤

使用示例:

/mcskin CuteStarX

/mcskin e3beb716afa1451b96c6ddfebd1ce1fb


`/mcinfo [名称/uuid]` 获取玩家档案信息

`/mcinfofile [名称/uuid]` 获取玩家皮肤原文件 

注:只支持群聊使用,问就是懒，既然开源了你可以自己动手
