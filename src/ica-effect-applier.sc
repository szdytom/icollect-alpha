global_enabled = false;

__config() -> {
	'command_permission' -> 'ops',
	'scope' -> 'global',
	'commands' -> {
		'' -> 'getStatus',
		'enable' -> _() -> global_enabled = true,
		'disable' -> _() -> global_enabled = false,
	},
};

getStatus() -> (
	if(global_enabled, (
		print('effect-applier: on')
	), (
		print('effect-applier: off')
	))
);

updateEffects() -> (
	if(global_enabled, (
		for(player('all'), (
			modify(_, 'effect', 'regeneration', 600, 1, false, true);
			modify(_, 'effect', 'absorption', 600, 2, false, true);
		));
	));
	schedule(400, 'updateEffects');
);

setBlock(pos, use_upper_half) -> (
	set(pos, str('petrified_oak_slab[type=%s]', if(use_upper_half, 'top', 'bottom')));
);

updatePath() -> (
	for(entity_selector('@e[tag=ica.builder_rocket,distance=0..]'), (
		rk_pos = query(_, 'pos') - [0, 2, 0];
		rd_pos = [round(rk_pos:0), round(rk_pos:1), round(rk_pos:2)];
		upper_half = (rk_pos:1) % 1 < 0.5;
		b = block(rd_pos);
		if(block_tags(b, 'replaceable'), (
			schedule(5, 'setBlock', rd_pos, upper_half);
		));
	));
);

updateBuilder() -> (
	updatePath();
	in_dimension('nether', updatePath());
	in_dimension('end', updatePath());
	schedule(1, 'updateBuilder');
);

__on_start() -> (
	schedule(1, 'updateBuilder');
	schedule(1, 'updateEffects');
);