__config() -> {
	'commands' -> {
		'' -> 'cmdList',
		'list' -> 'cmdList',
		'submit <slot>' -> 'cmdSubmit',
		'refill' -> 'cmdRefill',
		'seed' -> 'cmdSeed',
		'me' -> 'cmdMe',
		'whoami' -> 'cmdMe',
		'locate <participant>' -> 'cmdLocate',
		'spyglass <spyglass_feature>' -> 'cmdSpyglassSwitch',
		'ps' -> 'cmdListPalyer',
		'playerlist' -> 'cmdListPalyer',
	},
	'arguments' -> {
		'slot' -> { 'type' -> 'int', 'min' -> 0, 'max' -> 5
			, 'suggest' -> [0, 1, 2, 3, 4, 5] },
		'participant' -> { 'type' -> 'string', 'suggester' -> _(arg) -> (
			if(nbt_storage('ica:data'):'Started',
				parse_nbt(nbt_storage('ica:voting'):'Candidates'),
				[]
			)
		)},
		'spyglass_feature' -> { 'type' -> 'string',
			'options' -> [ 'builder', 'firework', 'fireball' ] },
		'message' -> { 'type' -> 'text' },
	}
};

import('ica-libs', 'listContain');
import('ica-i18n', 'getLocaleKey', 'startedReject', 'pendingReject');

global_capMarkerMap = {
	'builder' -> 'ica.build_spyglasser',
	'fireball' -> 'ica.fireball_spyglasser',
	'firework' -> 'ica.firework_spyglasser',
};

lackOfAbilityReject() -> (
	print(getLocaleKey('reject.unable'));
);

bystandReject() -> (
	print(getLocaleKey('reject.bystand'));
);

cmdLocate(pname) -> (
	if(!nbt_storage('ica:data'):'Started', (
		pendingReject();
		return(false)
	));
	myself = player();
	if(query(myself, 'has_scoreboard_tag', 'ica.deceased'), (
		bystandReject();
		return(false);
	));
	if(!query(player(), 'has_scoreboard_tag', 'ica.coordinator'), (
		lackOfAbilityReject();
		return(false);
	));

	cand_names = parse_nbt(nbt_storage('ica:voting'):'Candidates');
	if(!listContain(cand_names, pname), (
		print(str(getLocaleKey('locate.notfound'), pname));
		return(false);
	));

	p = player(pname);
	if(p == null, (
		print(str(getLocaleKey('locate.offline'), pname));
		return(false);
	));

	ppos = query(p, 'pos');
	pdim = query(p, 'dimension');
	mydim = query(myself, 'dimension');
	if(pdim == mydim, (
		print(str('%s: %.1f %.1f %.1f.', pdim, ppos:0, ppos:1, ppos:2));
		sslot = query(myself, 'selected_slot');
		sitem = inventory_get(myself, sslot);
		if(sitem == null || sitem:0 == 'compass', (
			inventory_set(myself, sslot, 1, 'compass', 
				str('{LodestonePos: {X: %d, Y: %d, Z: %d}, LodestoneDimension: "%s", LodestoneTracked: 0b}'
					, floor(ppos:0), floor(ppos:1), floor(ppos:2), pdim)); 
		));
	), (
		print(str('%s: ? ? ?', pdim));
	));
);

cmdSeed() -> (
	seed_val = str(system_info('world_seed'));
	print(format(' ' + getLocaleKey('seed.title')
		, 'c ' + seed_val, '&' + seed_val, '^ ' + getLocaleKey('misc.clipboard')));
);

cmdRefill() -> (
	if(!nbt_storage('ica:data'):'Started', (
		pendingReject();
		return(false)
	));
	myself = player();
	if(query(myself, 'has_scoreboard_tag', 'ica.deceased'), (
		bystandReject();
		return(false);
	));
	if(query(myself, 'has_scoreboard_tag', 'ica.flyer'), (
		run('give @s minecraft:firework_rocket 64');
	));
);

cmdList() -> (
	if(nbt_storage('ica:data'):'Started', (
		if(!nbt_storage('ica:data'):'Preparing', (
			print(str(getLocaleKey('list.title.collecting')
				, bossbar('ica:time_counter', 'value') / 20
				, bossbar('ica:collected', 'value')));
		), (
			print(str(getLocaleKey('list.title.preparing')
				, bossbar('ica:prepare_counter', 'value') / 20));
		));
	), (
		print(getLocaleKey('list.title.pending'));
	));

	c_for(i = 0, i <= 5, i = i + 1, (
		p = nbt_storage('ica:data'):str('Goals[{Slot: %db}]', i);
		if(p == null, (
			print(format(' ' + getLocaleKey('list.marker.0')
				+ str(getLocaleKey('list.item.id'), i)
				, 'gi ' + getLocaleKey('list.item.unset')));
		), (
			if(nbt_storage('ica:data'):'Started' && p:'Completed', (
				print(format(' ' + getLocaleKey('list.marker.1')
					+ str(getLocaleKey('list.item.id'), i)
					, 's ' + item_display_name(p:'Item')
					, '^ minecraft:' + p:'Item'));
			), (
				if(nbt_storage('ica:data'):'Started' && !nbt_storage('ica:data'):'Preparing', (
					print(format(' ' + getLocaleKey('list.marker.0')
						+ str(getLocaleKey('list.item.id'), i)
						, 'b ' + item_display_name(p:'Item')
						, '^ minecraft:' + p:'Item'
						, '   '
						, 'mb ' + getLocaleKey('list.item.submit')
						, '!/ica submit ' + str(i)));
				), (
					print(format(' ' + getLocaleKey('list.marker.0')
						+ str(getLocaleKey('list.item.id'), i)
						, 'b ' + item_display_name(p:'Item')
						, '^ minecraft:' + p:'Item'));
				)); 
			))
		));
	));
);

cmdMe() -> (
	if(!nbt_storage('ica:data'):'Started', (
		pendingReject();
		return(false)
	));
	myself = player();

	career = 'bystander';
	for(['piggy', 'hunter', 'firework_hunter', 'builder', 'wolf'], (
		if(query(myself, 'has_scoreboard_tag', 'ica.' + _), (
			career = _;
		));
	));

	print(str(getLocaleKey('career.whoami'), getLocaleKey('career.title.' + career)
		, getLocaleKey('career.help.' + career)));

	for(['voter', 'flyer', 'fireball_spyglasser', 'firework_spyglasser'
		, 'build_spyglasser', 'coordinator'], (
		if(query(myself, 'has_scoreboard_tag', 'ica.' + _), (
			print(str(getLocaleKey('ablity.format')
				, getLocaleKey('ablity.title.' + _), getLocaleKey('ablity.help.' + _)));
		));
	));
);

disableAllSpyglassAbilities(me) -> (
	modify(me, 'clear_tag', [ 'ica.spyglass_fireball', 'ica.spyglass_firework'
		, 'ica.spyglass_builder' ]);
);

cmdSpyglassSwitch(feature_id) -> (
	if(!nbt_storage('ica:data'):'Started', (
		pendingReject();
		return(false)
	));
	me = player();
	if(nbt_storage('ica:data'):'Preparing', (
		print(getLocaleKey('reject.reparing.switch'));
		return(false)
	));
	if(query(me, 'has_scoreboard_tag', 'ica.deceased'), (
		bystandReject();
		return(false);
	));

	if(query(me, 'has_scoreboard_tag', global_capMarkerMap:feature_id), (
		disableAllSpyglassAbilities(me);
		modify(me, 'tag', str('ica.spyglass_%s', feature_id));
	), (
		lackOfAbilityReject();
	))
);

cmdSubmit(slot_id) -> (
	if(!nbt_storage('ica:data'):'Started', (
		pendingReject();
		return(false)
	));
	if(nbt_storage('ica:data'):'Preparing', (
		print(getLocaleKey('reject.reparing.submit'));
		return(false)
	));
	me = player();
	if(query(me, 'has_scoreboard_tag', 'ica.deceased'), (
		bystandReject();
		return(false);
	));

	pkey = str('Goals[{Slot: %db}]', slot_id);
	g_item = nbt_storage('ica:data'):pkey:'Item';
	if(nbt_storage('ica:data'):pkey:'Completed', (
		print(format(' ' + getLocaleKey('submit.already.before')
			, 'b ' + item_display_name(g_item)
			, '^ minecraft:' + g_item
			, ' ' + getLocaleKey('submit.already.after')));
		return()
	));

	if(inventory_remove(me, g_item, 1) == 0, (
		print(format(' ' + getLocaleKey('submit.missing.before')
			, 'b ' + item_display_name(g_item)
			, '^ mincraft:' + g_item
			, ' ' + getLocaleKey('submit.missing.after')));
		return()
	));
	put(nbt_storage('ica:data'):(pkey+'.Completed'), '1b');
	iv = bossbar('ica:collected', 'value') + 1;
	bossbar('ica:collected', 'value', iv);
	print(getLocaleKey('submit.success'));
);

cmdListPalyer() -> (
	if(!nbt_storage('ica:data'):'Started', (
		pendingReject();
		return(false)
	));
	me = player();

	cand_names = parse_nbt(nbt_storage('ica:voting'):'Candidates');
	cand_n = length(cand_names);
	print(str(getLocaleKey('ps.title'), cand_n));
	for(cand_names, (
		p = player(_);
		fcomp = [' ' + getLocaleKey('ps.marker')];
		if(p == null, (
			put(fcomp, null, 'g ' + _);
			put(fcomp, null, '  ' + getLocaleKey('ps.offline'));
		), (
			put(fcomp, null, if(query(p, 'has_scoreboard_tag', 'ica.deceased')
				, 's ', 'b ') + _);
			if(p == me, (
				put(fcomp, null, '  ' + getLocaleKey('ps.me'));
			));
			if(query(me, 'has_scoreboard_tag', 'ica.wolf')
				&& query(p, 'has_scoreboard_tag', 'ica.wolf'), (
				put(fcomp, null, '  ' + getLocaleKey('ps.wolf'));
			));
		));
		print(format(fcomp));
	));
);
