__config() -> {
	'scope' -> 'global',
	'command_permission' -> 'ops',
	'commands' -> {
		'' -> 'cmdLoad',
	},
};

cmdLoad() -> (
	run('script load ica-admin');
	run('script load ica-effect-applier');
	run('script load ica-fireball');
	run('script load ica');
	run('script load ica-vote');
	run('script load ica-settings');
);
