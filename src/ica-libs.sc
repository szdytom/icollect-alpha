__config() -> {
    'scope' -> 'global',
    'exports' -> ['shuffleList', 'countCareer']
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