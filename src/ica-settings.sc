__config() -> {
	'scope' -> 'global',
	'command_permission' -> 'ops',
	'commands' -> {
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
		'career_type' -> { 'type' -> 'string'
			, 'options' -> [ 'wolf', 'hunter', 'builder', 'firework_hunter' ] },
		'amount' -> { 'type' -> 'int', 'min' -> 0, 'suggest' -> [1] },
	},
};

import('ica-i18n', 'getLocaleKey');
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
	print(format(getLocaleKey('instead')));
	run('/ica');
);

cmdSet(slot_id, goal_item_tuple) -> (
	if(nbt_storage('ica:data'):'Started', (
		print(format(getLocaleKey('reject.started')));
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
		print(format(getLocaleKey('reject.goal.enough')))
	));
);

cmdListCareers() -> (
	print(getLocaleKey('career.display.title'));
	for(['wolf', 'hunter', 'firework_hunter', 'builder'], (
		print(str(getLocaleKey('career.display.' + _), countCareer(_)));
	));
	print(getLocaleKey('career.display.piggy'));
);

cmdConfigCareer(cartype, ccount) -> (
	if(nbt_storage('ica:data'):'Started', (
		print(format(getLocaleKey('reject.started')));
		return(false)
	));
	pkey = str('Config[{Type: "%s"}].Count', cartype);
	etag = nbt(str('{Type: "%s", Count: %db}', cartype, ccount));

	if(nbt_storage('ica:careers'):pkey != null, delete(nbt_storage('ica:careers'):pkey));
	put(nbt_storage('ica:careers'), 'Config', etag, -1);
	print(str(getLocaleKey('career.set'), cartype, ccount))
);
