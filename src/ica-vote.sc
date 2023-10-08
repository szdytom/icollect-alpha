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
import('ica-i18n', 'getLocaleKey', 'pendingReject');

cmdInfo() -> (
	if(!nbt_storage('ica:data'):'Started', (
		pendingReject();
		return(false)
	));
	cand_names = parse_nbt(nbt_storage('ica:voting'):'Candidates');
	cand_n = length(cand_names);
	print(str(getLocaleKey('vote.title'), cand_n));
	for(cand_names, (
		p = player(_);
		v_cnt = countVotes(_);
		if(query(p, 'has_scoreboard_tag', 'ica.deceased'), (
			print(format(' ' + getLocaleKey('vote.marker')
				+ str(getLocaleKey('vote.amount.' + if(v_cnt > 1, '2', '1')), v_cnt)
				, 's ' + _
				, 'n  ') + getLocaleKey('vote.deceased'));
		), (
			print(format(' ' + getLocaleKey('vote.marker')
				+ str(getLocaleKey('vote.amount.' + if(v_cnt > 1, '2', '1')), v_cnt)
				, 'b ' + _
				, 'mb  ' + getLocaleKey('vote.button.vote')
				, '!/ica-vote sus ' + _));
		));
	));
	abv_cnt = countAbstainVotes();
	print(format(' ' + getLocaleKey('vote.marker')
				+ str(getLocaleKey('vote.amount.' + if(abv_cnt > 1, '2', '1')), abv_cnt)
				, 'gb ' + getLocaleKey('vote.abstain')
				, 'mb  ' + getLocaleKey('vote.button.abstain')
				, '!/ica-vote abstain'));

	max_p = findVoteMax();
	print(format(' ' + getLocaleKey('vote.footer')
		, if(max_p == null, 'gi ' + getLocaleKey('vote.nobody'), 'b ' + max_p)));
);

beforeVoteChecks() -> (
	if(!nbt_storage('ica:data'):'Started', (
		pendingReject();
		return(false)
	));
	myself = player();
	if(query(myself, 'has_scoreboard_tag', 'ica.deceased'), (
		print(getLocaleKey('reject.bystand'));
		return(false);
	));
	if(!query(myself, 'has_scoreboard_tag', 'ica.voter'), (
		print(getLocaleKey('reject.unable'));
		return(false);
	));
	myname = query(myself, 'command_name');
	if(has(nbt_storage('ica:voting'), str('Votes[{From: "%s"}]', myname)), (
		print(getLocaleKey('vote.already'));
		return(false);
	));
	true
);

cmdVotePlayer(sus_name) -> (
	if(!beforeVoteChecks(), return(false));

	cand_names = parse_nbt(nbt_storage('ica:voting'):'Candidates');
	if(!listContain(cand_names, sus_name), (
		print(str(getLocaleKey('vote.notfound'), sus_name));
		return(false);
	));

	myself = player();
	myname = query(myself, 'command_name');

	etags = nbt(str('{From: "%s", To: "%s", Abstain: 0b}', myname, sus_name));
	put(nbt_storage('ica:voting'), 'Votes', etags, -1);
	print(getLocaleKey('vote.success'));
);

cmdVoteAbstain() -> (
	if(!beforeVoteChecks(), return(false));
	myself = player();
	myname = query(myself, 'command_name');

	etags = nbt(str('{From: "%s", To: "", Abstain: 1b}', myname));
	put(nbt_storage('ica:voting'), 'Votes', etags, -1);
	print(getLocaleKey('vote.success'));
);
