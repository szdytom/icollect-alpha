global_lastUseSpyglass = -1000;

shootFireball(myself, rm) -> (
    playerPos = query(myself, 'pos') + l(0, query(myself, 'eye_height'), 0); // Set the position where the fireball will spawn
    playerM = query(myself, 'motion');

    v = query(myself, 'look');
    fbv = v * 0.3;
    fireball = spawn('fireball', playerPos + v * 2
        , nbt(str('{ExplosionPower: 4b, power: [%fd,%fd,%fd], Motion: [%fd,%fd,%fd]}'
        , fbv:0, fbv:1, fbv:2, playerM:0, playerM:1, playerM:2)));
    gamemode = myself ~ 'gamemode';
    if(rm && (gamemode == 'survival' || gamemode == 'adventure'),
        inventory_remove(myself, 'fire_charge');
    );
    return(true);
);

__on_player_uses_item(myself, item_tuple, hand) -> (
    if(item_tuple:0 == 'fire_charge',
        shootFireball(myself, true);
    );
);

shootFireballSpyglass(myself) -> (
    if(!query(myself, 'has_scoreboard_tag', 'ica.spyglass_fireball'), (
        return(null);
    ));
    nowTT = tick_time();
    dt = nowTT - global_lastUseSpyglass;
    if(query(myself, 'has_scoreboard_tag', 'ica.spyglasser_cooldown')
        && dt < 200, (
        print(str('spyglass too hot, please wait another %.2f seconds to shoot again.'
            , (200 - dt) / 20.0));
        return(null);
    ));
    shootFireball(myself, false);
    global_lastUseSpyglass = nowTT;
);

__on_player_releases_item(myself, item_tuple, hand) -> (
    if(item_tuple:0 == 'spyglass',
        shootFireballSpyglass(myself);
    );
);
