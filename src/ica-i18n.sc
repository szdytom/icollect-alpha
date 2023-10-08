__config() -> {
    'scope' -> 'global',
    'exports' -> ['getLocaleKey', 'useIcaInstead', 'startedReject', 'pendingReject'],
    'command_permission' -> 'ops',
    'commands' -> {
        'dump' -> 'cmdDump',
    },
};

global_TranslateKeysEnCN = {
    'pig.timeout.title' -> 'Timeout!',
    'pig.timeout.subtitle' -> 'You didn\'t complete %d goals in time',
    'wolf.timeout.title' -> 'You won!',
    'wolf.timeout.subtitle' -> 'Those fools have failed, good job',

    'pig.complete.title' -> 'Congratulations!',
    'pig.complete.subtitle' -> 'You have completed all the goals',
    'wolf.complete.title' -> 'Oh no!',
    'wolf.complete.subtitle' -> 'They have completed all the goals',

    'submit.ddl.1' -> '[WARN] Submission deadline in 1 minute.',
    'submit.ddl.2' -> '[WARN] Submission deadline in %d seconds.',
    'submit.ddl.3' -> '[WARN] Submission deadline in %d second.',
    'submit.success' -> 'OK.',
    'submit.missing.before' -> 'Item ',
    'submit.missing.after' -> ' not found.',
    'submit.already.before' -> 'Item ',
    'submit.already.after' -> ' has already been collected.',

    'election.kill' -> 'Election victim %s killed.',
    'election.ddl' -> '[WARN] Vote ends in 1 minute.',
    'election.result.nobody' -> 'nobody was elected',
    'election.result.kill' -> '[WARN] You were elected! You will be killed in 10 seconds.',
    'election.new' -> 'New election round started.',
    'election.result' -> 'Election result: %s.',

    'hint.instead.before' -> 'Please use ',
    'hint.instead.after' -> ' instead.',
    'hint.allset.before' -> 'All goals are set, you can use ',
    'hint.allset.after' -> ' to start.',

    'goal.set.before' -> 'Goal #%d set to ',
    'goal.set.after' -> '.',

    'bossbar.prepare.title' -> 'Preparing',
    'bossbar.time.title' -> 'Time Left',
    'bossbar.progress.title' -> 'Collected',

    'career.display.title' -> 'Career configs are:',
    'career.display.wolf' -> ' - Wolf: %d participants.',
    'career.display.hunter' -> ' - Hunter(Fireball): %d participants.',
    'career.display.firework_hunter' -> ' - Hunter(Firework): %d participants.',
    'career.display.builder' -> ' - Builder: %d participants.',
    'career.display.piggy' -> ' - Piggies: whatever the rest.',
    'career.set' -> 'Career %s set to %d participants.',

    'career.title.bystander' -> 'Bystander',
    'career.title.piggy' -> 'Piggy',
    'career.title.hunter' -> 'Hunter(Fireball)',
    'career.title.firework_hunter' -> 'Hunter(Firework)',
    'career.title.builder' -> 'Builder',
    'career.title.wolf' -> 'Wolf',

    'career.help.bystander' -> 'You have nothing to do, just watch.',
    'career.help.piggy' -> 'Complete all goals in time to win!',
    'career.help.hunter' -> 'Protect piggies and kill the wolf.',
    'career.help.firework_hunter' -> 'Protect piggies and kill the wolf.',
    'career.help.builder' -> 'Build protections and complete the goals!',
    'career.help.wolf' -> 'Hide yourself and stop them to complete the goals!',

    'career.whoami' -> 'You are %s: %s',

    'ablity.title.voter' -> 'Voter',
    'ablity.title.flyer' -> 'Flyer',
    'ablity.title.fireball_spyglasser' -> 'Shooter',
    'ablity.title.firework_spyglasser' -> 'Launcher',
    'ablity.title.build_spyglasser' -> 'Builder',
    'ablity.title.coordinator' -> 'Coordinator',

    'ablity.help.voter' -> 'You can vote.',
    'ablity.help.flyer' -> 'You can fly with an elytra.',
    'ablity.help.fireball_spyglasser' -> 'You can shoot fireballs with a spyglass(except prepare stage).',
    'ablity.help.firework_spyglasser' -> 'You can launch fireworks with a spyglass(except prepare stage).',
    'ablity.help.build_spyglasser' -> 'You can build paths with a spyglass(except prepare stage).',
    'ablity.help.coordinator' -> 'You can locate other participants.',

    'ablity.format' -> '[ablity] %s: %s',

    'reject.pending.before' -> 'Not started, use ',
    'reject.pending.after' -> ' to start.',
    'reject.started.before' -> 'Already started, use ',
    'reject.started.after' -> ' to cancel.',
    'reject.leckplayer' -> 'Not enough players are online.',
    'reject.goal.missing' -> 'Goal #%d is unset.',
    'reject.goal.enough.before' -> 'All are goals set, please use ',
    'reject.goal.enough.after' -> ' to replace.',
    'reject.spyglass' -> 'Spyglass too hot, please wait another %.2f seconds to use again.',
    'reject.unable' -> 'You don\'t have this ability.',
    'reject.bystand' -> 'You can only bystand.',
    'reject.reparing.submit' -> 'Currently preparing, please submit later.',
    'reject.reparing.switch' -> 'Currently preparing, please switch later.',

    'list.title.pending' -> 'Pending:',
    'list.title.collecting' -> 'Collecting, %d seconds left, %d are goals done:',
    'list.title.preparing' -> 'Perparing, %d seconds to collect stage:',
    'list.item.unset' -> 'unset',
    'list.item.submit' -> '[Click To Submit]',
    'list.marker.0' -> ' - ',
    'list.marker.1' -> ' + ',
    'list.item.id' -> '#%d: ',

    'vote.title' -> 'There are %d candidates:',
    'vote.deceased' -> '[Deceased]',
    'vote.amount.1' -> '[%02d vote]  ',
    'vote.amount.2' -> '[%02d votes] ',
    'vote.footer' -> 'Current elected: ',
    'vote.nobody' -> '(nobody)',
    'vote.abstain' -> '(abstain)',
    'vote.button.vote' -> '[Click To Vote]',
    'vote.button.abstain' -> '[Click To Abstain]',
    'vote.marker' -> ' - ',
    'vote.already' -> 'You have already voted.',
    'vote.notfound' -> 'Candidate %s not found.',
    'vote.success' -> 'OK.',

    'locate.notfound' -> 'Player %s not found.',
    'locate.offline' -> 'Player %s is not online.',

    'seed.title' -> 'Map Seed: ',

    'ps.wolf' -> '[Wolf]',
    'ps.button.locate' -> '[Click To Locate]',
    'ps.marker' -> ' - ',
    'ps.me' -> '[You]',
    'ps.offline' -> '[Offline]',
    'ps.title' -> 'There are %d participants:',

    'misc.clipboard' -> 'Click To Copy',

    'effect-applier.on' -> 'effect-applier status: enabled.',
    'effect-applier.off' -> 'effect-applier status: disabled.',

    'license.header' -> 'ICollect-Alpha, Copyright (C) 2023 %s\nICollect-Alpha comes with ABSOLUTELY NO WARRANTY;\nfor details type \'/ica-loader show w\'.\nThis is free software, and you are welcome to redistribute it\nunder certain conditions; type \'/ica-loader show c\' for details.'
};

global_TranslateKeysZhHans = {
    'pig.timeout.title' -> '超时！',
    'pig.timeout.subtitle' -> '第 %d 个收集目标超时',
    'wolf.timeout.title' -> '取得胜利！',
    'wolf.timeout.subtitle' -> '他们的收集任务失败了，做得好',

    'pig.complete.title' -> '恭喜！',
    'pig.complete.subtitle' -> '全部目标物品已集齐',
    'wolf.complete.title' -> '失败',
    'wolf.complete.subtitle' -> '他们已完成全部收集任务',

    'submit.ddl.1' -> '[注意] 提交将在 1 分钟内结束。',
    'submit.ddl.2' -> '[注意] 提交将在 %d 秒钟内结束。',
    'submit.ddl.3' -> '[注意] 提交将在 %d 秒钟内结束。',
    'submit.success' -> '提交成功。',
    'submit.missing.before' -> '未找到',
    'submit.missing.after' -> '。',
    'submit.already.before' -> '重复提交',
    'submit.already.after' -> '。',

    'election.kill' -> '%s 已被票出。',
    'election.ddl' -> '[注意] 本轮投票将在 1 分钟内结束。',
    'election.result.nobody' -> '无人被票出',
    'election.result.kill' -> '[注意] 你已被票出！你将在 10 秒内出局。',
    'election.new' -> '新一轮投票已开始。',
    'election.result' -> '票选结果：%s。',

    'hint.instead.before' -> '请使用命令 ',
    'hint.instead.after' -> '。',
    'hint.allset.before' -> '全部目标已设置，你可以使用命令 ',
    'hint.allset.after' -> ' 开始游戏',

    'goal.set.before' -> '目标 %d 已设置为 ',
    'goal.set.after' -> '。',

    'bossbar.prepare.title' -> '准备阶段',
    'bossbar.time.title' -> '收集中',
    'bossbar.progress.title' -> '收集进度',

    'career.display.title' -> '身份职业配置为：',
    'career.display.wolf' -> ' - 狼人：%d 个玩家。',
    'career.display.hunter' -> ' - 火球猎人：%d 个玩家。',
    'career.display.firework_hunter' -> ' - 烟花猎人：%d 个玩家。',
    'career.display.builder' -> ' - 建筑师：%d 个玩家。',
    'career.display.piggy' -> ' - 平民：其余全部玩家。',
    'career.set' -> '%s已设为 %d 个玩家。',

    'career.title.bystander' -> '旁观者',
    'career.title.piggy' -> '平民',
    'career.title.hunter' -> '火球猎人',
    'career.title.firework_hunter' -> '烟花猎人',
    'career.title.builder' -> '建筑师',
    'career.title.wolf' -> '狼人',

    'career.help.bystander' -> '你只能旁观。',
    'career.help.piggy' -> '完成全部收集任务以取得胜利！',
    'career.help.hunter' -> '保护平民，找出并杀死狼人，完成全部收集任务。',
    'career.help.firework_hunter' -> '保护平民，找出并杀死狼人，完成全部收集任务。',
    'career.help.builder' -> '建筑防御并完成全部收集任务。',
    'career.help.wolf' -> '隐藏自己的身份，阻止他们完成收集目标。',

    'career.whoami' -> '你是%s：%s',

    'ablity.title.voter' -> '投票',
    'ablity.title.flyer' -> '飞行',
    'ablity.title.fireball_spyglasser' -> '火球',
    'ablity.title.firework_spyglasser' -> '烟花',
    'ablity.title.build_spyglasser' -> '筑路',
    'ablity.title.coordinator' -> '追踪',

    'ablity.help.voter' -> '你可以投票。',
    'ablity.help.flyer' -> '你可以使用鞘翅飞行。',
    'ablity.help.fireball_spyglasser' -> '你可以使用望远镜发射恶魂火球（准备阶段除外）。',
    'ablity.help.firework_spyglasser' -> '你可以使用望远镜发射烟花火箭（准备阶段除外）。',
    'ablity.help.build_spyglasser' -> '你可以使用望远镜建筑道路（准备阶段除外）。',
    'ablity.help.coordinator' -> '你可以定位其他玩家。',

    'ablity.format' -> '[能力] %s：%s',

    'reject.pending.before' -> '游戏还未开始，请使用命令 ',
    'reject.pending.after' -> ' 以开始游戏。',
    'reject.started.before' -> '游戏已开始，请使用命令 ',
    'reject.started.after' -> ' 以中断游戏。',
    'reject.leckplayer' -> '在线玩家数不足，游戏无法开始。',
    'reject.goal.missing' -> '第 %d 个收集目标尚未设置。',
    'reject.goal.enough.before' -> '全部目标已设置，请使用命令 ',
    'reject.goal.enough.after' -> ' 修改目标。',
    'reject.spyglass' -> '冷却中，距离可再次使用剩余 %.2f 秒。',
    'reject.unable' -> '你没有此能力。',
    'reject.bystand' -> '你只能旁观。',
    'reject.reparing.submit' -> '请在准备阶段结束后提交。',
    'reject.reparing.switch' -> '请在准备阶段结束后切换。',

    'list.title.pending' -> '等待游戏开始：',
    'list.title.collecting' -> '收集阶段剩余 %d 秒，%d 个任务已完成：',
    'list.title.preparing' -> '准备阶段剩余 %d 秒：',
    'list.item.unset' -> '未设置',
    'list.item.submit' -> '[点此提交]',
    'list.marker.0' -> ' - ',
    'list.marker.1' -> ' + ',
    'list.item.id' -> '#%d：',

    'vote.title' -> '共有 %d 个玩家：',
    'vote.deceased' -> '[已死亡]',
    'vote.amount.1' -> '[%02d票] ',
    'vote.amount.2' -> '[%02d票] ',
    'vote.footer' -> '当前得票数最多的玩家：',
    'vote.nobody' -> '（无）',
    'vote.abstain' -> '（弃权）',
    'vote.button.vote' -> '[点此投票]',
    'vote.button.abstain' -> '[点此弃权]',
    'vote.marker' -> ' - ',
    'vote.already' -> '你已投过票了。',
    'vote.notfound' -> '未找到玩家 %s。',
    'vote.success' -> '投票成功。',

    'locate.notfound' -> '未找到玩家 %s。',
    'locate.offline' -> '玩家 %s 不在线。',

    'seed.title' -> '地图种子：',

    'ps.wolf' -> '[狼人]',
    'ps.button.locate' -> '[点此定位]',
    'ps.marker' -> ' - ',
    'ps.me' -> '[你]',
    'ps.offline' -> '[离线]',
    'ps.title' -> '共有 %d 个玩家：',

    'misc.clipboard' -> '点此复制',

    'effect-applier.on' -> '状态效果广播器：已启用。',
    'effect-applier.off' -> '状态效果广播器：已禁用。',

    'license.header' -> 'ICollect-Alpha，版权所有 (C) 2023 %s\nICollect-Alpha 绝对不附带任何保证；\n请输入 \'/ica-loader show w\' 查看详情。\n这是自由软件，你可以在特定条款下重分发它。\n请输入 \'/ica-loader show c\' 查看详情。'
};

global_TranslateKeysEnabled = global_TranslateKeysZhHans;

getLocaleKey(keyid) -> (
    if(has(global_TranslateKeysEnabled:keyid), global_TranslateKeysEnabled:keyid, 'tkey:' + keyid)
);

useIcaInstead() -> (
    print(format('d ' + getLocaleKey('hint.instead.before')
        , 'mb /ica list', '?/ica list'
        , 'd ' + getLocaleKey('hint.instead.after')));
);

startedReject() -> (
    print(format(' ' + getLocaleKey('reject.started.before')
        , 'mb /ica-admin reset', '?/ica-admin reset'
        , ' ' + getLocaleKey('reject.started.after')));
);

pendingReject() -> (
    print(format(' ' + getLocaleKey('reject.pending.before')
        , 'mb /ica-admin confirm', '?/ica-admin confirm'
        , ' ' + getLocaleKey('reject.pending.after')));
);

cmdDump() -> (
    write_file('ica-i18n-table', 'shared_json', global_TranslateKeysEnabled);
);
