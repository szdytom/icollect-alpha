global_lastUseSpyglass = -500;

shootFireball(myself) -> (
    playerPos = query(myself, 'pos') + l(0, query(myself, 'eye_height'), 0);
    playerM = query(myself, 'motion');

    v = query(myself, 'look');
    fbv = v * 0.3;
    fireball = spawn('fireball', playerPos + v * 2
        , nbt(str('{ExplosionPower: 4b, power: [%fd,%fd,%fd], Motion: [%fd,%fd,%fd]}'
        , fbv:0, fbv:1, fbv:2, playerM:0, playerM:1, playerM:2)));
    return(true);
);

fireworkExplotionTags() -> (
    'FireworksItem: {Count:1b,id:"minecraft:firework_rocket",tag:{Fireworks: {Flight: 2b, Explosions: [{Type: 1b, Colors: [I; 3887386], Trail: 1b}, {Type: 1b, Colors: [I; 12801229], Trail: 1b}, {Type: 1b, Colors: [I; 11743532], Trail: 1b}, {Type: 1b, Colors: [I; 15790320], Trail: 1b}, {Type: 1b, Colors: [I; 6719955], Trail: 1b}, {Type: 1b, Colors: [I; 15435844], Trail: 1b}, {Type: 1b, Colors: [I; 3887386], Trail: 1b}, {Type: 1b, Colors: [I; 12801229], Trail: 1b}, {Type: 1b, Colors: [I; 11743532], Trail: 1b}, {Type: 1b, Colors: [I; 15790320], Trail: 1b}, {Type: 1b, Colors: [I; 6719955], Trail: 1b}, {Type: 1b, Colors: [I; 15435844], Trail: 1b}, {Type: 1b, Colors: [I; 3887386], Trail: 1b}, {Type: 1b, Colors: [I; 12801229], Trail: 1b}, {Type: 1b, Colors: [I; 11743532], Trail: 1b}, {Type: 1b, Colors: [I; 15790320], Trail: 1b}, {Type: 1b, Colors: [I; 6719955], Trail: 1b}, {Type: 1b, Colors: [I; 15435844], Trail: 1b},{Type: 0b, Colors: [I; 3887386], Trail: 1b}, {Type: 0b, Colors: [I; 12801229], Trail: 1b}, {Type: 0b, Colors: [I; 11743532], Trail: 1b}, {Type: 0b, Colors: [I; 15790320], Trail: 1b}, {Type: 0b, Colors: [I; 6719955], Trail: 1b}, {Type: 0b, Colors: [I; 15435844], Trail: 1b}, {Type: 0b, Colors: [I; 3887386], Trail: 1b}, {Type: 0b, Colors: [I; 12801229], Trail: 1b}, {Type: 0b, Colors: [I; 11743532], Trail: 1b}, {Type: 0b, Colors: [I; 15790320], Trail: 1b}, {Type: 0b, Colors: [I; 6719955], Trail: 1b}, {Type: 0b, Colors: [I; 15435844], Trail: 1b}, {Type: 0b, Colors: [I; 3887386], Trail: 1b}, {Type: 0b, Colors: [I; 12801229], Trail: 1b}, {Type: 0b, Colors: [I; 11743532], Trail: 1b}, {Type: 0b, Colors: [I; 15790320], Trail: 1b}, {Type: 0b, Colors: [I; 6719955], Trail: 1b}, {Type: 0b, Colors: [I; 15435844], Trail: 1b}]}}}'
);

shootFirework(myself) -> (
    playerPos = query(myself, 'pos') + l(0, query(myself, 'eye_height'), 0);
    playerM = query(myself, 'motion');

    v = query(myself, 'look');
    fbv = playerM + v * 2;
    rocket = spawn('firework_rocket', playerPos + v
        , nbt(str('{LifeTime: %d, Motion: [%fd,%fd,%fd], ShotAtAngle: 1b, %s}'
        , 35 + rand(10), fbv:0, fbv:1, fbv:2, fireworkExplotionTags())));
    return(true);
);

shootBuilderFirework(myself) -> (
    playerPos = query(myself, 'pos') + l(0, query(myself, 'eye_height'), 0);
    playerM = query(myself, 'motion');

    v = query(myself, 'look');
    fbv = playerM + v;
    rocket = spawn('firework_rocket', playerPos + 2 * v
        , nbt(str('{LifeTime: %d, Motion: [%fd,%fd,%fd], ShotAtAngle: 1b}'
        , 75 + rand(10), fbv:0, fbv:1, fbv:2)));
    modify(rocket, 'tag', 'ica.builder_rocket');
    return(true);
);

checkCooldown(myself) -> (
    nowTT = tick_time();
    dt = nowTT - global_lastUseSpyglass;
    if(query(myself, 'has_scoreboard_tag', 'ica.spyglasser_cooldown')
        && dt < 100, (
        display_title(player(), 'actionbar'
            , str('spyglass too hot, please wait another %.2f seconds to shoot again.'
            , (100 - dt) / 20.0), 100, 100, 100);
        return(true);
    ));
    global_lastUseSpyglass = nowTT;
    return(false);
);

shootFireballSpyglass(myself) -> (
    if(!query(myself, 'has_scoreboard_tag', 'ica.spyglass_fireball'), (
        return(null);
    ));
    shootFireball(myself);
);

shootFireworkSpyglass(myself) -> (
    if(!query(myself, 'has_scoreboard_tag', 'ica.spyglass_firework'), (
        return(null);
    ));
    shootFirework(myself);
);

shootBuilderFireworkSpyglass(myself) -> (
    if(!query(myself, 'has_scoreboard_tag', 'ica.spyglass_builder'), (
        return(null);
    ));
    shootBuilderFirework(myself);
);

__on_player_releases_item(myself, item_tuple, hand) -> (
    if(item_tuple:0 == 'spyglass',
        if(checkCooldown(myself), (return(null)));
        shootFireballSpyglass(myself);
        shootFireworkSpyglass(myself);
        shootBuilderFireworkSpyglass(myself);
    );
);
