shoot_fireball(myself, rm) -> (
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
        shoot_fireball(myself, true);
    );
);

__on_player_releases_item(myself, item_tuple, hand) -> (
    if(!query(myself, 'has_scoreboard_tag', 'ica.spyglass_fireball'), (
        return();
    ));
    if(item_tuple:0 == 'spyglass',
        shoot_fireball(myself, false);
    );
);
