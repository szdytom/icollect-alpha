__config() -> {
	'scope' -> 'global',
	'command_permission' -> 'ops',
	'commands' -> {
		'list' -> 'cmdList',
		'confirm' -> 'cmdStart',
		'reset clear' -> 'cmdResetClear',
		'reset schedule' -> 'cmdReschedule'
	},
};

import('ica-libs', 'shuffleList', 'countCareer', 'playerListNbt', 'findVoteMax', 'resetVotes');

__on_start() -> (
	if(nbt_storage('ica:data'):'Goals' == null, (
		nbt_storage('ica:data', '{Goals: [], Started: 0b, Preparing: 0b}')
	));
	if(nbt_storage('ica:careers'):'Config' == null, (
		nbt_storage('ica:careers', '{Config: []}')
	));
	if(nbt_storage('ica:voting'):'Created' == null, (
		nbt_storage('ica:voting', '{Created: 1b, Candidates: [], Votes: []}')
	))
);

clearBossbars() -> (
	// bossbar(name, 'remove') is sometimes buggy.
	run('bossbar remove ica:prepare_counter');
	run('bossbar remove ica:time_counter');
	run('bossbar remove ica:collected');
);

cleanPlayerTags() -> (	
	for(player('all'), modify(_, 'clear_tag', ['ica.piggy'
		, 'ica.wolf', 'ica.hunter', 'ica.spyglasser', 'ica.spyglass_fireball'
		, 'ica.voter', 'ica.flyer', 'ica.spyglasser_cooldown'
		, 'ica.coordinator', 'ica.deceased', 'ica.participant']));
);

endCleanup() -> (
	clearBossbars();
	cleanPlayerTags();
	put(nbt_storage('ica:data'):'Started', '0b');
	put(nbt_storage('ica:data'):'Preparing', '0b');
	run('ica-effect-applier disable');
);

createBossbar(nid, name, val, sty) -> (
	bossbar(nid);
	bossbar(nid, 'max', val);
	bossbar(nid, 'value', val);
	bossbar(nid, 'name', name);
	bossbar(nid, 'players', player('all'));

	if(sty != null, bossbar(nid, 'style', sty));
);

__on_player_dies(p) -> (
	if(nbt_storage('ica:data'):'Started'
		&& !nbt_storage('ica:data'):'Preparing', (
		modify(p, 'gamemode', 'spectator');
		modify(p, 'tag', 'ica.deceased');
	));
);

__on_player_connects(p) -> (
	if(nbt_storage('ica:data'):'Started', (
		if(!query(p, 'has_scoreboard_tag', 'ica.participant'), (
			modify(p, 'tag', 'ica.deceased');
		));

		if(p, 'has_scoreboard_tag', 'ica.deceased', (
			modify(p, 'gamemode', 'spectator');
		));
	));
);

tm_prepare() -> (
	12000
);

tm_total() -> (
	72000
);

tm_per_goal() -> (
	12000
);

getPigPlayers() -> (
	entity_selector('@a[tag=!ica.wolf,tag=ica.participant]')
);

getWolfPlayers() -> (
	entity_selector('@a[tag=ica.wolf]')
);

endGameTitle(p, m, s) -> (
	display_title(p, 'title', m, 10, 200, 10);
	display_title(p, 'subtitle', s, 10, 200, 10);
);

endTimeout(iv) -> (
	endGameTitle(getPigPlayers(), 'Timeout!'
		, str('You didn\'t complete %d goals in time.', iv));
	endGameTitle(getWolfPlayers(), 'You won!'
		, str('Those fools have failed, good job.', iv));
	endCleanup();
);

endFinish() -> (
	endGameTitle(getPigPlayers(), 'Congratulations!', 'You have completed all the goals.');
	endGameTitle(getWolfPlayers(), 'Oh no!'
		, str('They have completed all the goals.', iv));
	endCleanup();
);

getFirstUnsetGoal() -> (
	c_for(i = 0, i <= 5, i = i + 1, (
		p = nbt_storage('ica:data'):str('Goals[{Slot: %db}]', i);
		if(p == null, (
			return(i);
		))
	));
	return(6);
);

warnDeadline(dt) -> (
	if (dt == 1200, (
		print(player('all'), '[WARN] Submission deadline in 1 minute.');
	));
	if (dt == 600, (
		print(player('all'), '[WARN] Submission deadline in 30 seconds.');
	));
	if (dt == 300, (
		print(player('all'), '[WARN] Submission deadline in 15 seconds.');
	));
	if (dt <= 200 && dt % 20 == 0, (
		print(player('all'), str('[WARN] Submission deadline in %d second%s!'
			, dt / 20, if(dt > 20, 's', '')));
	));
);

electionKill(pname) -> (
	p = player(pname);
	if(p != null, (
		modify(p, 'tag', 'ica.deceased');
		modify(p, 'gamemode', 'spectator');
	));
	print(player('all'), str('Election victim %s killed.', pname))
);

checkVotes(tm) -> (
	if(tm % 12000 == 3600, (
		print(player('all'), '[WARN] Vote ends in 1 minute');
		return(false);
	));
	if(tm % 12000 == 2400, (
		max_p = findVoteMax();
		phint = if(max_p == null, (
			'nobody was elected.'
		), (
			ele_p = player(max_p);
			schedule(200, 'electionKill', ele_p);
			print(ele_p, '[WARN] You were elected! You will be killed in 10 seconds.');
			str('elected player %s', max_p)
		));
		resetVotes();
		print(player('all'), str('Election result: %s.\nNew eletion round started.', phint));
	));
);

runUpdateCollect() -> (
	if(!nbt_storage('ica:data'):'Started', return());
	tm = bossbar('ica:time_counter', 'value') - 20;
	bossbar('ica:time_counter', 'value', tm);
	iv = bossbar('ica:collected', 'value') + 1;
	if(iv == 7, endFinish());
	dt = tm - (6 - iv) * tm_per_goal();
	if(dt <= 0, (
		endTimeout(iv);
	), (
		warnDeadline(dt);
		checkVotes(tm);
		schedule(20, 'runUpdateCollect');
	))
);

startCollectStage() -> (
	schedule(20, 'runUpdateCollect');

	clearBossbars();
	createBossbar('ica:time_counter', 'Time Left', tm_total(), 'notched_6');
	createBossbar('ica:collected', 'Collected', 6, 'notched_6');
	bossbar('ica:collected', 'value', 0);

	for(player('all'), (
		if(query(_, 'has_scoreboard_tag', 'ica.spyglasser'), (
			modify(_, 'tag', 'ica.spyglass_fireball');
		));
	));

	put(nbt_storage('ica:data'):'Preparing', '0b');
);

setElytra(p) -> (
	// 38 is where the player's elytra should be at.
	inventory_set(p, 8, 64, 'minecraft:firework_rocket');
	inventory_set(p, 38, 1, 'minecraft:elytra', '{Unbreakable: 1b}');
);

clearExp(p) -> (
	modify(p, 'xp_level', 0);
	modify(p, 'xp_progress', 0);
	modify(p, 'xp_score', 0);
);

playerInit(p) -> (
	modify(p, 'gamemode', 'survival');
	if(query(p, 'has_scoreboard_tag', 'ica.flyer'), setElytra(p));
	clearExp(p);
	inventory_set(p, 2, 1, 'minecraft:spyglass');
);

cmdResetClear() -> (
	endCleanup();
);

cmdReschedule() -> (
	if(!nbt_storage('ica:data'):'Started', (
		print('Not started. use /ica-admin confirm to start.');
		return(false)
	));
	schedule('runUpdateCollect', 20);
);

runUpdatePrepare() -> (
	if(!nbt_storage('ica:data'):'Started', return());
	if(!nbt_storage('ica:data'):'Preparing', return());
	tm = bossbar('ica:prepare_counter', 'value') - 20;
	bossbar('ica:prepare_counter', 'value', tm);
	if(tm <= 0, (
		startCollectStage();
	), (
		schedule(20, 'runUpdatePrepare');
	))
);

cmdStart() -> (
	missing_goal = getFirstUnsetGoal();
	if(missing_goal <= 5, (
		print(str('Goal #%d is unset.', missing_goal));
		return(false)
	));

	if(nbt_storage('ica:data'):'Started', (
		print('Already started, use /ica-admin reset clear to cancel.');
		return(false)
	));

	participants_list = player('all');
	participants_list = shuffleList(participants_list);
	wolf_n = countCareer('wolf');
	hunter_n = countCareer('hunter') + wolf_n;
	if(hunter_n > length(participants_list), (
		print('Not enough players online.');
		return(false);
	));

	put(nbt_storage('ica:data'):'Started', '1b');
	put(nbt_storage('ica:data'):'Preparing', '1b');
	put(nbt_storage('ica:data'):'Goals[].Completed', '0b');

	createBossbar('ica:prepare_counter', 'Preparing', tm_prepare(), null);
	schedule(20, 'runUpdatePrepare');

	cleanPlayerTags();
	put(nbt_storage('ica:voting'), 'Candidates', playerListNbt(participants_list));

	for(participants_list, (
		modify(_, 'tag', ['ica.voter', 'ica.flyer', 'ica.participant']);
		career_tag = 'ica.piggy';
		if(_i < hunter_n, career_tag = ['ica.hunter', 'ica.spyglasser', 'ica.spyglasser_cooldown']);
		if(_i < wolf_n, career_tag = ['ica.wolf', 'ica.spyglasser', 'ica.coordinator']);

		modify(_, 'tag', career_tag);
	));

	run('execute as @a run clear');
	for(participants_list, playerInit(_));

	run('ica-effect-applier enable');
	run('time set day');
	run('gamerule keepInventory false');
	run('gamerule playersSleepingPercentage 0');
	run('worldborder set 10000');
);

cmdList() -> (
	print('please use /ica instead');
	run('/ica');
);
