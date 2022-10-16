Feature: Single Player Scoring
  This feature tests and validates scoring for a single player

  @R37
  Scenario: Die with three skulls on first roll
    Given The game starts with 1 player
    And The player names are the following
      | Player1 |
    When 'Player1' gets 'GOLD' fortune card
    And 'Player1' rolls the following
      | SKULL |
      | SKULL |
      | SKULL |
      | SWORD |
      | SWORD |
      | SWORD |
      | SWORD |
      | SWORD |
    Then 'Player1' gets disqualified
    And Player scores are the following
      | Player1 0 |

  @R38
  Scenario: Row 38
    Given The game starts with 1 player
    And The player names are the following
      | Player1 |
    When 'Player1' gets 'GOLD' fortune card
    And 'Player1' rolls the following
      | SKULL  |
      | PARROT |
      | PARROT |
      | PARROT |
      | PARROT |
      | SWORD  |
      | SWORD  |
      | SWORD  |
    And 'Player1' re-rolls dice with index '5 6 7' to get the following
      | SKULL |
      | SKULL |
      | SWORD |
    Then 'Player1' gets disqualified
    And Player scores are the following
      | Player1 0 |

  @R39
  Scenario: Row 39
    Given The game starts with 1 player
    And The player names are the following
      | Player1 |
    When 'Player1' gets 'GOLD' fortune card
    And 'Player1' rolls the following
      | SKULL  |
      | SKULL  |
      | PARROT |
      | PARROT |
      | PARROT |
      | PARROT |
      | SWORD  |
      | SWORD  |
    And 'Player1' re-rolls dice with index '6 7' to get the following
      | SKULL |
      | SWORD |
    Then 'Player1' gets disqualified
    And Player scores are the following
      | Player1 0 |

  @R41
  Scenario: Row 41
    Given The game starts with 1 player
    And The player names are the following
      | Player1 |
    When 'Player1' gets 'GOLD' fortune card
    And 'Player1' rolls the following
      | SKULL  |
      | PARROT |
      | PARROT |
      | PARROT |
      | PARROT |
      | SWORD  |
      | SWORD  |
      | SWORD  |
    And 'Player1' re-rolls dice with index '5 6 7' to get the following
      | SKULL  |
      | MONKEY |
      | MONKEY |
    And 'Player1' re-rolls dice with index '6 7' to get the following
      | SKULL  |
      | MONKEY |
    Then 'Player1' gets disqualified
    And Player scores are the following
      | Player1 0 |

  @R43
  Scenario: Row 43
    Given The game starts with 1 player
    And The player names are the following
      | Player1 |
    When 'Player1' gets 'GOLD' fortune card
    And 'Player1' rolls the following
      | SKULL     |
      | PARROT    |
      | PARROT    |
      | SWORD     |
      | SWORD     |
      | SWORD     |
      | GOLD_COIN |
      | GOLD_COIN |
    And 'Player1' re-rolls dice with index '1 2' to get the following
      | GOLD_COIN |
      | GOLD_COIN |
    And 'Player1' re-rolls dice with index '3 4 5' to get the following
      | GOLD_COIN |
      | GOLD_COIN |
      | GOLD_COIN |
    And 'Player1' ends turn
    Then Player scores are the following
      | Player1 4800 |

  @R44
  Scenario: Row 44
    Given The game starts with 1 player
    And The player names are the following
      | Player1 |
    When 'Player1' gets 'CAPTAIN' fortune card
    And 'Player1' rolls the following
      | MONKEY    |
      | MONKEY    |
      | PARROT    |
      | PARROT    |
      | DIAMOND   |
      | DIAMOND   |
      | GOLD_COIN |
      | GOLD_COIN |
    And 'Player1' ends turn
    Then Player scores are the following
      | Player1 800 |

  @R45
  Scenario: Row 45
    Given The game starts with 1 player
    And The player names are the following
      | Player1 |
    When 'Player1' gets 'GOLD' fortune card
    And 'Player1' rolls the following
      | MONKEY |
      | MONKEY |
      | SKULL  |
      | SKULL  |
      | SWORD  |
      | SWORD  |
      | PARROT |
      | PARROT |
    And 'Player1' re-rolls dice with index '6 7' to get the following
      | SWORD  |
      | MONKEY |
    And 'Player1' ends turn
    Then Player scores are the following
      | Player1 300 |

  @R46
  Scenario: Row 46
    Given The game starts with 1 player
    And The player names are the following
      | Player1 |
    When 'Player1' gets 'GOLD' fortune card
    And 'Player1' rolls the following
      | MONKEY |
      | MONKEY |
      | MONKEY |
      | SWORD  |
      | SWORD  |
      | SWORD  |
      | SKULL  |
      | SKULL  |
    And 'Player1' ends turn
    Then Player scores are the following
      | Player1 300 |

  @R47
  Scenario: Row 47
    Given The game starts with 1 player
    And The player names are the following
      | Player1 |
    When 'Player1' gets 'GOLD' fortune card
    And 'Player1' rolls the following
      | DIAMOND |
      | DIAMOND |
      | DIAMOND |
      | SKULL   |
      | SKULL   |
      | MONKEY  |
      | SWORD   |
      | PARROT  |
    And 'Player1' ends turn
    Then Player scores are the following
      | Player1 500 |

  @R48
  Scenario: Row 48
    Given The game starts with 1 player
    And The player names are the following
      | Player1 |
    When 'Player1' gets 'DIAMOND' fortune card
    And 'Player1' rolls the following
      | GOLD_COIN |
      | GOLD_COIN |
      | GOLD_COIN |
      | GOLD_COIN |
      | SKULL     |
      | SKULL     |
      | SWORD     |
      | SWORD     |
    And 'Player1' ends turn
    Then Player scores are the following
      | Player1 700 |

  @R49
  Scenario: Row 49
    Given The game starts with 1 player
    And The player names are the following
      | Player1 |
    When 'Player1' gets 'DIAMOND' fortune card
    And 'Player1' rolls the following
      | SWORD  |
      | SWORD  |
      | SWORD  |
      | PARROT |
      | PARROT |
      | PARROT |
      | PARROT |
      | SKULL  |
    And 'Player1' ends turn
    Then Player scores are the following
      | Player1 400 |

  @R50
  Scenario: Row 50
    Given The game starts with 1 player
    And The player names are the following
      | Player1 |
    When 'Player1' gets 'GOLD' fortune card
    And 'Player1' rolls the following
      | SKULL     |
      | GOLD_COIN |
      | GOLD_COIN |
      | PARROT    |
      | PARROT    |
      | SWORD     |
      | SWORD     |
      | SWORD     |
    And 'Player1' re-rolls dice with index '3 4' to get the following
      | GOLD_COIN |
      | SWORD     |
    And 'Player1' ends turn
    Then Player scores are the following
      | Player1 800 |

  @R51
  Scenario: Row 51
    Given The game starts with 1 player
    And The player names are the following
      | Player1 |
    When 'Player1' gets 'CAPTAIN' fortune card
    And 'Player1' rolls the following
      | SKULL     |
      | GOLD_COIN |
      | GOLD_COIN |
      | PARROT    |
      | PARROT    |
      | SWORD     |
      | SWORD     |
      | SWORD     |
    And 'Player1' re-rolls dice with index '3 4' to get the following
      | GOLD_COIN |
      | SWORD     |
    And 'Player1' ends turn
    Then Player scores are the following
      | Player1 1200 |

  @R53
  Scenario: Row 53
    Given The game starts with 1 player
    And The player names are the following
      | Player1 |
    When 'Player1' gets 'GOLD' fortune card
    And 'Player1' rolls the following
      | SKULL  |
      | MONKEY |
      | MONKEY |
      | PARROT |
      | PARROT |
      | SWORD  |
      | SWORD  |
      | SWORD  |
    And 'Player1' re-rolls dice with index '1 2' to get the following
      | SKULL |
      | SWORD |
    And 'Player1' re-rolls dice with index '3 4' to get the following
      | SWORD  |
      | MONKEY |
    And 'Player1' ends turn
    Then Player scores are the following
      | Player1 600 |

  @R54
  Scenario: Row 54
    Given The game starts with 1 player
    And The player names are the following
      | Player1 |
    When 'Player1' gets 'GOLD' fortune card
    And 'Player1' rolls the following
      | MONKEY |
      | MONKEY |
      | MONKEY |
      | MONKEY |
      | MONKEY |
      | MONKEY |
      | SKULL  |
      | SKULL  |
    And 'Player1' ends turn
    Then Player scores are the following
      | Player1 1100 |

  @R55
  Scenario: Row 55
    Given The game starts with 1 player
    And The player names are the following
      | Player1 |
    When 'Player1' gets 'GOLD' fortune card
    And 'Player1' rolls the following
      | PARROT |
      | PARROT |
      | PARROT |
      | PARROT |
      | PARROT |
      | PARROT |
      | PARROT |
      | SKULL  |
    And 'Player1' ends turn
    Then Player scores are the following
      | Player1 2100 |

  @R56
  Scenario: Row 56
    Given The game starts with 1 player
    And The player names are the following
      | Player1 |
    When 'Player1' gets 'GOLD' fortune card
    And 'Player1' rolls the following
      | GOLD_COIN |
      | GOLD_COIN |
      | GOLD_COIN |
      | GOLD_COIN |
      | GOLD_COIN |
      | GOLD_COIN |
      | GOLD_COIN |
      | GOLD_COIN |
    And 'Player1' ends turn
    Then Player scores are the following
      | Player1 5400 |

  @R57
  Scenario: Row 57
    Given The game starts with 1 player
    And The player names are the following
      | Player1 |
    When 'Player1' gets 'DIAMOND' fortune card
    And 'Player1' rolls the following
      | GOLD_COIN |
      | GOLD_COIN |
      | GOLD_COIN |
      | GOLD_COIN |
      | GOLD_COIN |
      | GOLD_COIN |
      | GOLD_COIN |
      | GOLD_COIN |
    And 'Player1' ends turn
    Then Player scores are the following
      | Player1 5400 |

  @R58
  Scenario: Row 58
    Given The game starts with 1 player
    And The player names are the following
      | Player1 |
    When 'Player1' gets 'CAPTAIN' fortune card
    And 'Player1' rolls the following
      | SWORD |
      | SWORD |
      | SWORD |
      | SWORD |
      | SWORD |
      | SWORD |
      | SWORD |
      | SWORD |
    And 'Player1' ends turn
    Then Player scores are the following
      | Player1 9000 |

  @R59
  Scenario: Row 59
    Given The game starts with 1 player
    And The player names are the following
      | Player1 |
    When 'Player1' gets 'GOLD' fortune card
    And 'Player1' rolls the following
      | MONKEY |
      | MONKEY |
      | MONKEY |
      | MONKEY |
      | MONKEY |
      | MONKEY |
      | SWORD  |
      | SWORD  |
    And 'Player1' re-rolls dice with index '6 7' to get the following
      | MONKEY |
      | MONKEY |
    And 'Player1' ends turn
    Then Player scores are the following
      | Player1 4600 |

  @R60
  Scenario: Row 60
    Given The game starts with 1 player
    And The player names are the following
      | Player1 |
    When 'Player1' gets 'DIAMOND' fortune card
    And 'Player1' rolls the following
      | MONKEY |
      | MONKEY |
      | SKULL  |
      | SKULL  |
      | SWORD  |
      | SWORD  |
      | PARROT |
      | PARROT |
    And 'Player1' re-rolls dice with index '6 7' to get the following
      | DIAMOND |
      | DIAMOND |
    And 'Player1' ends turn
    Then Player scores are the following
      | Player1 400 |

  @R61
  Scenario: Row 61
    Given The game starts with 1 player
    And The player names are the following
      | Player1 |
    When 'Player1' gets 'GOLD' fortune card
    And 'Player1' rolls the following
      | MONKEY  |
      | MONKEY  |
      | SKULL   |
      | SKULL   |
      | SWORD   |
      | SWORD   |
      | DIAMOND |
      | PARROT  |
    And 'Player1' re-rolls dice with index '0 1' to get the following
      | DIAMOND |
      | DIAMOND |
    And 'Player1' ends turn
    Then Player scores are the following
      | Player1 500 |
