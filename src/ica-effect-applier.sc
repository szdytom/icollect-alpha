global_enabled = false;
global_effects = ['regeneration', 'resistance'];

__config() -> {
   'command_permission' -> 'ops',
   'scope' -> 'global',
   'commands' -> {
      '' -> 'getStatus',
      'enable' -> _() -> global_enabled = true,
      'disable' -> _() -> global_enabled = false,
   },
};

getStatus() -> (
   if(global_enabled, (
      print('effect-applier: on')
   ), (
      print('effect-applier: off')
   ))
);

updateEffects() -> (
   if(global_enabled, (
      for(global_effects, (
         e = _;
         for(player('all'), modify(_, 'effect', e, 300, 1, false, true));
      ));
   ));
   schedule(200, 'updateEffects');
);

__on_start() -> (
   schedule(1, 'updateEffects')
);