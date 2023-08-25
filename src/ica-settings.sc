__config() -> {
	'scope' -> 'global',
	'command_permission' -> 'ops',
	'commands' -> {
		'' -> 'cmdList',
		'list' -> 'cmdList',
		'set <slot> <item>' -> 'cmdSet',
		'add <item>' -> 'cmdSetAppend',
		'career' -> 'cmdListCareers',
		'career <career_type> <amount>' -> 'cmdConfigCareer',
	},
	'arguments' -> {
		'slot' -> { 'type' -> 'int', 'min' -> 0, 'max' -> 5
			, 'suggest' -> [0, 1, 2, 3, 4, 5] },
		'item' -> { 'type' -> 'item' },
		'career_type' -> { 'type' -> 'string', 'options' -> [ 'wolf', 'hunter' ] },
		'amount' -> { 'type' -> 'int', 'min' -> 0, 'suggest' -> [1] },
	},
};

import('ica-libs', 'countCareer');

getFirstUnsetGoal() -> (
	c_for(i = 0, i <= 5, i = i + 1, (
		p = nbt_storage('ica:data'):str('Goals[{Slot: %db}]', i);
		if(p == null, (
			return(i);
		))
	));
	return(6);
);

cmdList() -> (
	print('please use /ica instead');
	run('/ica');
);

cmdSet(slot_id, goal_item_tuple) -> (
	if(nbt_storage('ica:data'):'Started', (
		print('Already started, use /ica-admin reset clear to cancel.');
		return(false)
	));
	pkey = str('Goals[{Slot: %db}]', slot_id);
	etag = nbt(str('{Slot: %db, Item: "%s"}', slot_id, goal_item_tuple:0));

	if(nbt_storage('ica:data'):pkey != null, delete(nbt_storage('ica:data'):pkey));
	put(nbt_storage('ica:data'), 'Goals', etag, -1);
	print(str('Goal #%d set to %s.', slot_id, goal_item_tuple:0))
);

cmdSetAppend(goal_item_tuple) -> (
	missing_goal = getFirstUnsetGoal();
	if(missing_goal <= 5, (
		cmdSet(missing_goal, goal_item_tuple)
	), (
		print('All are goals set, please use /ica-settings set <slot> <item>.')
	));
);

cmdListCareers() -> (
	print('Career configs are:');
	print(str(' - Wolf: %d participants.', countCareer('wolf')));
	print(str(' - Hunter: %d participants.', countCareer('hunter')));
	print(' - Piggy: Whatever the rest.');
);

cmdConfigCareer(cartype, ccount) -> (
	if(nbt_storage('ica:data'):'Started', (
		print('Already started, use /ica-admin reset clear to cancel.');
		return(false)
	));
	pkey = str('Config[{Type: "%s"}].Count', cartype);
	etag = nbt(str('{Type: "%s", Count: %db}', cartype, ccount));

	if(nbt_storage('ica:careers'):pkey != null, delete(nbt_storage('ica:careers'):pkey));
	put(nbt_storage('ica:careers'), 'Config', etag, -1);
	print(str('Career %s set to %d participants.', cartype, ccount))
);
