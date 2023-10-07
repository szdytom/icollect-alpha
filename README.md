# icollect-alpha

ICollect-Alpha 是一个使用 [Carpet 脚本](https://github.com/gnembon/fabric-carpet) 的“物品收集x狼人杀 Minecraft小游戏”实现。

## 规则

欢迎来到物品收集x狼人杀 Minecraft小游戏！以下是游戏规则：

【职业】
1. 狼人：坏人阵营，可以使用全部望远镜的特殊能力，可以定位任意玩家的精确坐标。
2. 平民：好人阵营，没有特殊技能。
3. 猎人/烟花猎人/建筑师：好人阵营，可以对应的望远镜的特殊能力。

【流程】
1. 游戏开始时，会给出一个包含6个物品的列表，并进入10分钟的准备阶段，准备阶段内不能发射弹药。每个玩家都会获得鞘翅和无限烟花。
2. 准备阶段结束后，进入60分钟的收集阶段。好人阵营在10分钟内必须收集完列表中的1个物品，20分钟内必须收集完2个，以此类推，60分钟结束前必须收集满6个物品。坏人阵营则要阻挠好人阵营收集物品。
3. 每10分钟会有一个投票环节，投票为一人一票，不记名，投票后不可撤销。所有玩家都可以把票投给一个自己怀疑是狼人的玩家，票数最高者出局。可以投弃权票，如果票数最高者有多人得票数相同，或票数最高者得票数不严格多于弃权票数量，则该轮投票无人出局。
4. 如果好人阵营没能在规定时间内完成收集任务，则坏人阵营获胜。反之，如果好人阵营成功完成6个物品的收集，则好人阵营获胜。
5. 每个玩家只有一条生命，被杀死、票死或者因为环境而死的玩家将变成旁观者模式。在准备阶段内死亡不会变成旁观者，而是会正常复活。
6. 游戏过程中，玩家获得持续的生命回复II和伤害吸收III（每20秒更新一次状态效果为30秒）。

【望远镜的特殊能力】

1. 发射火球：发射与TNT爆炸威力相同的恶魂火球。
2. 发射烟花：发射可以造成大量伤害的烟花火箭。
3. 建筑烟花：发射在其下方生成道路的烟花火箭（道路建筑材料为 _石化橡木台阶_）。

【指令使用】
1. `/ica`：列出物品收集目标和时限信息
2. `/ica submit <slot>`：递交一个目标要求的物品
3. `/ica me`：查看自己的身份
4. `/ica seed`：查看地图种子
5. `/ica refill`：刷新自己的烟花
6. `/ica locate <name>`：定位某一玩家位置，空手使用时可获得指向位置的指南针（不能跨维度定位）
7. `/ica spyglass <feature>`：切换望远镜的能力
7. `/ica ps`：查看玩家列表，狼人可看到队友
7. `/ica-vote`：查看当前投票状态信息
8. `/ica-vote abstain`：投弃权票
9. `/ica-vote sus <name>`：投票给某一玩家

祝您游戏愉快！

## 管理员命令

首先需要安装 Fabric 和 Fabric Carpet 模组，然后将 `src` 目录下的 `.sc` 代码复制到世界的 `script` 文件夹下。打开世界后，使用 `/script load ica-loader` 加载加载脚本，使用命令 `/ica-loader` 加载各个模块：

- `/ica-setting career <career> <number>` 设置职业的玩家数量
- `/ica-setting add <item>` 设置一个未使用的收集目标
- `/ica-setting set <slot> <item>` 设置一个指定的收集目标
- `/ica-admin confirm` 开始游戏，开始时请保证所有参与者在线

## 调试命令

- `/ica-effect-applier enable` 强制启用状态效果广播
- `/ica-effect-applier enable` 强制禁用状态效果广播
- `/ica-admin reset` 终止游戏
- `/ica-i18n dump` 向 JSON 文件中写入本地化值
- `/bossbar set ica:prepare_counter value 20` 强制跳过准备阶段
