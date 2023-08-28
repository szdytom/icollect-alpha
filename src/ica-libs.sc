__config() -> {
    'scope' -> 'global',
    'exports' -> ['shuffleList', 'countCareer', 'findVoteMax', 'resetVotes'
        , 'playerListNbt', 'listContain', 'countVotes', 'countAbstainVotes'],
};

shuffleList(list) -> (
    if (length(list) <= 1, list,
        c_for(i = length(list) - 1, i >= 1, i = i - 1,
            random_index = floor(rand(i + 1));
            
            // swap elements
            temp = list:i;
            list:i = list:random_index;
            list:random_index = temp;
        );
        list
    )
);

countCareer(cartype) -> (
    if(nbt_storage('ica:careers'):str('Config[{Type: "%s"}]', cartype) == null, (
        0
    ),
        nbt_storage('ica:careers'):str('Config[{Type: "%s"}].Count', cartype)
    )
);

playerListNbt(plist) -> (
    res = [];
    for(plist, (
        put(res, null, query(_, 'command_name'), 'extend');
    ));
    encode_nbt(res)
);

listContain(plist, pele) -> (
    for(plist, if(_ == pele, return(true)));
    false
);

nbtLength(l) -> (
    if(l == null, return(0));
    lp = parse_nbt(l);
    if(type(l) == 'list', length(l), 1)
);

countVotes(pname) -> (
    l = nbt_storage('ica:voting'):str('Votes[{Abstain: 0b, To: "%s"}]', pname);
    nbtLength(l)
);

countAbstainVotes() -> (
    l = nbt_storage('ica:voting'):'Votes[{Abstain: 1b}]';
    nbtLength(l)
);

findVoteMax() -> (
    cand_names = parse_nbt(nbt_storage('ica:voting'):'Candidates');
    max_p = null;
    max_c = countAbstainVotes();
    for(cand_names, (
        v_n = countVotes(_);
        if(v_n == max_c, (
            max_p = null;
        ));
        if(v_n > max_c, (
            max_p = _;
            max_c = v_n;
        ));
    ));
    max_p
);

resetVotes() -> (
    put(nbt_storage('ica:voting'), 'Votes', '[]');
);
