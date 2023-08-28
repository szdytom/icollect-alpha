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
	},
	'arguments' -> {
		'slot' -> { 'type' -> 'int', 'min' -> 0, 'max' -> 5
			, 'suggest' -> [0, 1, 2, 3, 4, 5] },
		'participant' -> { 'type' -> 'players' },
	}
};

tm_total() -> (
	72000
);

tm_per_goal() -> (
	12000
);

cmdLocate(pname) -> (
	if(!nbt_storage('ica:data'):'Started', (
		print('Not started. use /ica-admin confirm to start.');
		return(false)
	));
	myself = player();
	if(query(myself, 'has_scoreboard_tag', 'ica.deceased'), (
		print('You can only bystand.');
		return(false);
	));
	if(!query(player(), 'has_scoreboard_tag', 'ica.coordinator'), (
		print('You don\'t have this ability.');
		return(false)
	));

	p = player(pname:0);
	if(p == null, (
		print('Not found');
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
	print(str('%s: %d', system_info('world_name'), system_info('world_seed')));
);

cmdRefill() -> (
	if(!nbt_storage('ica:data'):'Started', (
		print('Not started. use /ica-admin confirm to start.');
		return(false)
	));
	myself = player();
	if(query(myself, 'has_scoreboard_tag', 'ica.deceased'), (
		print('You can only bystand.');
		return(false);
	));
	if(query(myself, 'has_scoreboard_tag', 'ica.flyer'), (
		run('give @s minecraft:firework_rocket 64');
	));
	if(query(myself, 'has_scoreboard_tag', 'ica.spyglasser')
		&& !nbt_storage('ica:data'):'Preparing', (
			modify(myself, 'tag', 'ica.spyglass_fireball');
	));
);

cmdList() -> (
	if(nbt_storage('ica:data'):'Started', (
		if(!nbt_storage('ica:data'):'Preparing', (
			print(str('Collecting, %d seconds left, %d are goals done:',
				bossbar('ica:time_counter', 'value') / 20,
				bossbar('ica:collected', 'value')));
		), (
			print(str('Perparing, %d seconds to collect:'
				, bossbar('ica:prepare_counter', 'value') / 20));
		));
	), (
		print('Pending:');
	));

	c_for(i = 0, i <= 5, i = i + 1, (
		p = nbt_storage('ica:data'):str('Goals[{Slot: %db}]', i);
		if(p == null, (
			print(str('- #%d: unset', i));
		), (
			print(str('%s #%d: %s(%s)', if(nbt_storage('ica:data'):'Started' && p:'Completed', '+', '-')
				, i, item_display_name(p:'Item'), p:'Item'))
		))
	));
);

cmdMe() -> (
	if(!nbt_storage('ica:data'):'Started', (
		print('Not started. use /ica-admin confirm to start.');
		return(false)
	));
	myself = player();
	career_hints = {
		'Bystander' -> 'You have nothing to do, just watch.',
		'Piggy' -> 'Complete all goals in time to win!',
		'Hunter' -> 'Protect piggies and kill the wolf.',
		'Wolf' -> 'Stop them complete the goals!',
	};
	career = 'Bystander';

	if(query(myself, 'has_scoreboard_tag', 'ica.piggy'), (
		career = 'Piggy';
	));
	if(query(myself, 'has_scoreboard_tag', 'ica.hunter'), (
		career = 'Hunter';
	));
	if(query(myself, 'has_scoreboard_tag', 'ica.wolf'), (
		career = 'Wolf';
	));
	print(str('You are "%s": %s', career, career_hints:career));

	if(query(myself, 'has_scoreboard_tag', 'ica.voter'), (
		print('[ability] voter: you can vote.');
	));
	if(query(myself, 'has_scoreboard_tag', 'ica.flyer'), (
		print('[ability] flyer: you can fly with an elytra.');
	));
	if(query(myself, 'has_scoreboard_tag', 'ica.spyglasser'), (
		print('[ability] spyglasser: You can shoot fireballs with a spyglass(except in prepare stage).');
	));
	// if(query(myself, 'has_scoreboard_tag', 'ica.kungfu_master'), (
	// 	print('[ability] kungfu master: You can get a temporary slow-falling effect by using a feather.');
	// ));
	if(query(myself, 'has_scoreboard_tag', 'ica.coordinator'), (
		print('[ability] coordinator: you can locate other participants.');
	));
);

cmdSubmit(slot_id) -> (
	if(!nbt_storage('ica:data'):'Started', (
		print('Not started. use /ica-admin confirm to start.');
		return(false)
	));
	if(nbt_storage('ica:data'):'Preparing', (
		print('Currently preparing, please submit later.');
		return(false)
	));
	me = player();
	if(query(me, 'has_scoreboard_tag', 'ica.deceased'), (
		print('You can only bystand.');
		return(false);
	));

	pkey = str('Goals[{Slot: %db}]', slot_id);
	if(nbt_storage('ica:data'):pkey:'Completed', (
		print(str('%s has already been collected.', item_display_name(
			nbt_storage('ica:data'):pkey:'Item')));
		return()
	));

	if(inventory_remove(me, nbt_storage('ica:data'):pkey:'Item', 1) == 0, (
		print(str('%s not found.', item_display_name(
			nbt_storage('ica:data'):pkey:'Item')));
		return()
	));
	put(nbt_storage('ica:data'):(pkey+'.Completed'), '1b');
	iv = bossbar('ica:collected', 'value') + 1;
	print(iv);
	bossbar('ica:collected', 'value', iv);
	print('OK.');
);
