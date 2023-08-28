__config() -> {
	'commands' -> {
		'' -> 'cmdInfo',
		'list' -> 'cmdInfo',
		'info' -> 'cmdInfo',
		'sus <candidate_name>' -> 'cmdVotePlayer',
		'abstain' -> 'cmdVoteAbstain',
	},
	'arguments' -> {
		'candidate_name' -> { 'type' -> 'string', 'suggester' -> _(arg) -> (
			if(nbt_storage('ica:data'):'Started',
				parse_nbt(nbt_storage('ica:voting'):'Candidates'),
				[]
			)
		)},
	}
};

import('ica-libs', 'listContain', 'countVotes', 'findVoteMax', 'countAbstainVotes');


cmdInfo() -> (
	if(!nbt_storage('ica:data'):'Started', (
		print('Not started. use /ica-admin confirm to start.');
		return(false)
	));
	cand_names = parse_nbt(nbt_storage('ica:voting'):'Candidates');
	cand_n = length(cand_names);
	print(str('There are %d candidates:', cand_n));
	for(cand_names, (
		p = player(_);
		v_cnt = countVotes(_);
		print(str(' - %02d vote%s %s%s', v_cnt, if(v_cnt > 1, 's', ' ')
			, _, if(query(p, 'has_scoreboard_tag', 'ica.deceased'), ' [deceased]', '')));
	));
	abv_cnt = countAbstainVotes();
	print(' - %02d vote%s (abstain)', abv_cnt, if(abv_cnt > 1, 's', ' '));

	max_p = findVoteMax();
	print(str('Current elected: %s', if(max_p == null, '(nobody)', max_p)));
);

beforeVoteChecks() -> (
	if(!nbt_storage('ica:data'):'Started', (
		print('Not started. use /ica-admin confirm to start.');
		return(false)
	));
	myself = player();
	if(query(myself, 'has_scoreboard_tag', 'ica.deceased'), (
		print('You can only bystand.');
		return(false);
	));
	if(!query(myself, 'has_scoreboard_tag', 'ica.voter'), (
		print('You don\'t have this ability');
		return(false);
	));
	myname = query(myself, 'command_name');
	if(has(nbt_storage('ica:voting'), str('Votes[{From: "%s"}]', myname)), (
		print('You have already voted.');
		return(false);
	));
	true
);

cmdVotePlayer(sus_name) -> (
	if(!beforeVoteChecks(), return(false));

	cand_names = parse_nbt(nbt_storage('ica:voting'):'Candidates');
	if(!listContain(cand_names, sus_name), (
		print(str('Candidate %s not found.', sus_name));
		return(false);
	));

	myself = player();
	myname = query(myself, 'command_name');

	etags = nbt(str('{From: "%s", To: "%s", Abstain: 0b}', myname, sus_name));
	put(nbt_storage('ica:voting'), 'Votes', etags, -1);
	print('OK.');
);

cmdVoteAbstain() -> (
	if(!beforeVoteChecks(), return(false));
	myself = player();
	myname = query(myself, 'command_name');

	etags = nbt(str('{From: "%s", To: "", Abstain: 1b}', myname));
	put(nbt_storage('ica:voting'), 'Votes', etags, -1);
	print('OK.');
);
