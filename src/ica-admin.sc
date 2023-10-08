__config() -> {
	'scope' -> 'global',
	'command_permission' -> 'ops',
	'commands' -> {
		'' -> 'cmdList',
		'list' -> 'cmdList',
		'confirm' -> 'cmdStart',
		'reset' -> 'cmdResetClear'
	},
};

import('ica-libs', 'shuffleList', 'countCareer', 'playerListNbt'
	, 'findVoteMax', 'resetVotes', 'getFirstUnsetGoal');
import('ica-i18n', 'getLocaleKey', 'useIcaInstead', 'startedReject');

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
		, 'ica.wolf', 'ica.hunter', 'ica.firework_hunter'
		, 'ica.fireball_spyglasser', 'ica.spyglass_fireball'
		, 'ica.voter', 'ica.flyer', 'ica.spyglasser_cooldown'
		, 'ica.coordinator', 'ica.deceased', 'ica.participant'
		, 'ica.firework_spyglasser', 'ica.build_spyglasser'
		, 'ica.spyglass_builder', 'ica.spyglass_firework']));
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
	endGameTitle(getPigPlayers(), getLocaleKey('pig.timeout.title')
		, str(getLocaleKey('pig.timeout.subtitle'), iv));
	endGameTitle(getWolfPlayers(), getLocaleKey('wolf.timeout.title')
		, str(getLocaleKey('wolf.timeout.subtitle'), iv));
	endCleanup();
);

endFinish() -> (
	endGameTitle(getPigPlayers(), getLocaleKey('pig.complete.title')
		, getLocaleKey('pig.complete.subtitle'));
	endGameTitle(getWolfPlayers(), getLocaleKey('wolf.complete.title')
		, getLocaleKey('wolf.complete.subtitle'));
	endCleanup();
);

actionbarMessage(msg) -> (
	display_title(player('all'), 'actionbar', msg, 100, 100, 100);
);

warnDeadline(dt) -> (
	if (dt == 1200, (
		actionbarMessage(getLocaleKey('submit.ddl.1'));
	));
	if (dt == 600, (
		actionbarMessage(str(getLocaleKey('submit.ddl.2'), 30));
	));
	if (dt == 300, (
		actionbarMessage(str(getLocaleKey('submit.ddl.2'), 15));
	));
	if (dt <= 200 && dt % 20 == 0, (
		actionbarMessage(str(getLocaleKey(if(dt > 20, 'submit.ddl.2'
			, 'submit.ddl.3')), dt / 20));
	));
);

electionKill(pname) -> (
	p = player(pname);
	if(p != null, (
		modify(p, 'tag', 'ica.deceased');
		modify(p, 'gamemode', 'spectator');
	));
	print(player('all'), str(getLocaleKey('election.kill'), pname))
);

checkVotes(tm) -> (
	if(tm % 12000 == 3600, (
		actionbarMessage(getLocaleKey('election.ddl'));
		return(false);
	));
	if(tm % 12000 == 2400, (
		max_p = findVoteMax();
		phint = if(max_p == null, (
			getLocaleKey('election.result.nobody')
		), (
			ele_p = player(max_p);
			schedule(200, 'electionKill', ele_p);
			print(ele_p, getLocaleKey('election.result.kill'));
			max_p
		));
		resetVotes();
		print(player('all'), str(getLocaleKey('election.result'), phint));
		print(player('all'), getLocaleKey('election.new'))
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
	createBossbar('ica:time_counter', format('b ' + getLocaleKey('bossbar.time.title'))
		, tm_total(), 'notched_6');
	createBossbar('ica:collected', format('b ' + getLocaleKey('bossbar.progress.title'))
		, 6, 'notched_6');
	bossbar('ica:collected', 'value', 0);

	for(player('all'), (
		if(query(_, 'has_scoreboard_tag', 'ica.fireball_spyglasser'), (
			modify(_, 'tag', 'ica.spyglass_fireball');
		), if(query(_, 'has_scoreboard_tag', 'ica.firework_spyglasser'), (
			modify(_, 'tag', 'ica.spyglass_firework');
		), if(query(_, 'has_scoreboard_tag', 'ica.build_spyglasser'), (
			modify(_, 'tag', 'ica.spyglass_builder');
		))));
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
		print(str(getLocaleKey('reject.goal.missing'), missing_goal));
		return(false)
	));

	if(nbt_storage('ica:data'):'Started', (
		startedReject();
		return(false)
	));

	participants_list = player('all');
	participants_list = shuffleList(participants_list);
	wolf_n = countCareer('wolf');
	hunter_n = countCareer('hunter') + wolf_n;
	builder_n = countCareer('builder') + hunter_n;
	firework_hunter_n = countCareer('firework_hunter') + builder_n;
	if(firework_hunter_n > length(participants_list), (
		print(getLocaleKey('reject.leckplayer'));
		return(false);
	));

	put(nbt_storage('ica:data'):'Started', '1b');
	put(nbt_storage('ica:data'):'Preparing', '1b');
	put(nbt_storage('ica:data'):'Goals[].Completed', '0b');

	createBossbar('ica:prepare_counter', format('b ' + getLocaleKey('bossbar.prepare.title'))
		, tm_prepare(), null);
	schedule(20, 'runUpdatePrepare');

	cleanPlayerTags();
	put(nbt_storage('ica:voting'), 'Candidates', playerListNbt(participants_list));

	for(participants_list, (
		modify(_, 'tag', ['ica.voter', 'ica.flyer', 'ica.participant']);
		career_tag = 'ica.piggy';
		if(_i < firework_hunter_n, career_tag = ['ica.firework_hunter', 'ica.firework_spyglasser', 'ica.spyglasser_cooldown']);
		if(_i < builder_n, career_tag = ['ica.builder', 'ica.build_spyglasser']);
		if(_i < hunter_n, career_tag = ['ica.hunter', 'ica.fireball_spyglasser', 'ica.spyglasser_cooldown']);
		if(_i < wolf_n, career_tag = ['ica.wolf', 'ica.fireball_spyglasser', 'ica.firework_spyglasser', 'ica.build_spyglasser', 'ica.coordinator']);

		modify(_, 'tag', career_tag);
	));

	run('execute as @a run clear');
	for(participants_list, playerInit(_));

	run('ica-effect-applier enable');
	run('time set day');
	run('gamerule keepInventory true');
	run('gamerule playersSleepingPercentage 0');
	run('gamerule randomTickSpeed 12');
	run('worldborder set 20000');
);

cmdList() -> (
	useIcaInstead();
	run('/ica list');
);
