__config() -> {
    'scope' -> 'global',
    'exports' -> ['getLocaleKey'],
};

global_TranslateKeysEnUS = {
    'pig.timeout.title' -> 'Timeout!',
    'pig.timeout.subtitle' -> 'You didn\'t complete %d goals in time',
    'wolf.timeout.title' -> 'You won!',
    'wolf.timeout.subtitle' -> 'Those fools have failed, good job',

    'pig.complete.title' -> 'Congratulations!',
    'pig.complete.subtitle' -> 'You have completed all the goals',
    'wolf.complete.title' -> 'Oh no!',
    'wolf.complete.subtitle' -> 'They have completed all the goals',

    'submit.ddl.1' -> '[WARN] Submission deadline in 1 minute.',
    'submit.ddl.2' -> '[WARN] Submission deadline in %d seconds.',
    'submit.ddl.3' -> '[WARN] Submission deadline in %d second.',
    'submit.success' -> 'OK.',
    'submit.missing.before' -> ' Item ',
    'submit.missing.after' -> '  not found.',
    'submit.already.before' -> ' Item ',
    'submit.already.after' -> '  has already been collected.',

    'election.kill' -> 'Election victim %s killed.',
    'election.ddl' -> '[WARN] Vote ends in 1 minute.',
    'election.result.nobody' -> 'nobody was elected',
    'election.result.kill' -> '[WARN] You were elected! You will be killed in 10 seconds.',
    'election.new' -> 'New election round started.',
    'election.result' -> 'Election result: %s.',

    'instead' -> 'd Please use /ica instead.',

    'bossbar.prepare.title' -> 'b Preparing',
    'bossbar.time.title' -> 'b Time Left',
    'bossbar.progress.title' -> 'b Collected',

    'career.display.title' -> 'Career configs are:',
    'career.display.wolf' -> ' - Wolf: %d participants.',
    'career.display.hunter' -> ' - Hunter(Fireball): %d participants.',
    'career.display.firework_hunter' -> ' - Hunter(Firework): %d participants.',
    'career.display.builder' -> ' - Builder: %d participants.',
    'career.display.piggy' -> 'Career configs are:',
    'career.set' -> 'Career %s set to %d participants.',

    'career.title.bystander' -> 'Bystander',
    'career.title.piggy' -> 'Piggy',
    'career.title.hunter' -> 'Hunter(Fireball)',
    'career.title.firework_hunter' -> 'Hunter(Firework)',
    'career.title.builder' -> 'Builder',
    'career.title.wolf' -> 'Wolf',

    'career.help.bystander' -> 'You have nothing to do, just watch.',
    'career.help.piggy' -> 'Complete all goals in time to win!',
    'career.help.hunter' -> 'Protect piggies and kill the wolf.',
    'career.help.firework_hunter' -> 'Protect piggies and kill the wolf.',
    'career.help.builder' -> 'Build protections and complete the goals!',
    'career.help.wolf' -> 'Hide yourself and stop them to complete the goals!',

    'career.whoami' -> 'You are %s: %s',

    'ablity.title.voter' -> 'Voter',
    'ablity.title.flyer' -> 'Flyer',
    'ablity.title.fireball_spyglasser' -> 'Shooter',
    'ablity.title.firework_spyglasser' -> 'Launcher',
    'ablity.title.build_spyglasser' -> 'Builder',
    'ablity.title.coordinator' -> 'Coordinator',

    'ablity.help.voter' -> 'You can vote.',
    'ablity.help.flyer' -> 'You can fly with an elytra.',
    'ablity.help.fireball_spyglasser' -> 'You can shoot fireballs with a spyglass(except prepare stage).',
    'ablity.help.firework_spyglasser' -> 'You can launch fireworks with a spyglass(except prepare stage).',
    'ablity.help.build_spyglasser' -> 'You can build paths with a spyglass(except prepare stage).',
    'ablity.help.coordinator' -> 'You can locate other participants.',

    'ablity.format' -> '[ablity] %s: %s',

    'reject.pending' -> [' Not started. use ', 'mb /ica-admin confirm', '?/ica-admin confirm', '  to start.'],
    'reject.started' -> [' Already started, use ', 'mb /ica-admin reset clear', '?/ica-admin reset clear', '  to cancel.'],
    'reject.leckplayer' -> ' Not enough players are online.',
    'reject.goal.missing' -> 'Goal #%d is unset.',
    'reject.goal.enough' -> [' All are goals set, please use ', 'mb /ica-settings set <slot> <item>', '  to replace.'],
    'reject.spyglass' -> 'Spyglass too hot, please wait another %.2f seconds to use again.',
    'reject.unable' -> 'You don\'t have this ability.',
    'reject.bystand' -> 'You can only bystand.',
    'reject.reparing.submit' -> 'Currently preparing, please submit later.',
    'reject.reparing.switch' -> 'Currently preparing, please switch later.',

    'list.title.pending' -> 'Pending:',
    'list.title.collecting' -> 'Collecting, %d seconds left, %d are goals done:',
    'list.title.preparing' -> 'Perparing, %d seconds to collect stage:',
    'list.item.unset' -> 'unset',
    'list.item.submit' -> '[Click To Submit]',
    'list.marker.0' -> ' - ',
    'list.marker.1' -> ' + ',
    'list.item.id' -> '#%d: ',

    'vote.title' -> 'There are %d candidates:',
    'vote.deceased' -> '[deceased]',
    'vote.amount.1' -> '[%02d vote]',
    'vote.amount.2' -> '[%02d votes]',
    'vote.footer' -> 'Current elected: ',
    'vote.nobody' -> '(nobody)',
    'vote.abstain' -> '(abstain)',
    'vote.button.vote' -> '[Click To Vote]',
    'vote.button.abstain' -> '[Click To Abstain]',
    'vote.marker' -> ' - ',
    'vote.already' -> 'You have already voted.',
    'vote.notfound' -> 'Candidate %s not found.',
    'vote.success' -> 'OK.',

    'locate.notfound' -> 'Player %s not found.',
    'locate.offline' -> 'Player %s is not online.',

    'seed.title' -> 'Map Seed: ',

    'misc.clipboard' -> 'Click To Copy',

    'license.header' -> 'ICollect-Alpha, Copyright (C) 2023 方而静\nICollect-Alpha comes with ABSOLUTELY NO WARRANTY;\nfor details type \'/ica-loader show w\'.\nThis is free software, and you are welcome to redistribute it\nunder certain conditions; type \'/ica-loader show c\' for details.',
};

getLocaleKey(keyid) -> (
    if(has(global_TranslateKeysEnUS:keyid), global_TranslateKeysEnUS:keyid, 'tkey:' + keyid)
);