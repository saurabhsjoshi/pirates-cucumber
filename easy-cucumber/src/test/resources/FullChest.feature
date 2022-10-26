Feature: Single Player Scoring
  This feature tests and validates scoring for a single player when they
  get a full chest i.e. all dice are used when calculating the total score

  @R89
  Scenario: Row 89
    Given The game starts with 1 player
    And The player names are the following 'Player1'
    When 'Player1' gets 'GOLD' fortune card
    And 'Player1' rolls the following 'MONKEY,MONKEY,MONKEY,SWORD,SWORD,SWORD,DIAMOND,PARROT'
    And 'Player1' ends turn
    Then Player scores are the following 'Player1 400'

  @R90
  Scenario: Row 90
    Given The game starts with 1 player
    And The player names are the following 'Player1'
    When 'Player1' gets 'CAPTAIN' fortune card
    And 'Player1' rolls the following 'MONKEY,MONKEY,MONKEY,SWORD,SWORD,SWORD,GOLD_COIN,GOLD_COIN'
    And 'Player1' ends turn
    Then Player scores are the following 'Player1 1800'

  @R91
  Scenario: Row 91
    Given The game starts with 1 player
    And The player names are the following 'Player1'
    When 'Player1' gets 'GOLD' fortune card
    And 'Player1' rolls the following 'MONKEY,MONKEY,MONKEY,SWORD,SWORD,SWORD,SWORD,DIAMOND'
    And 'Player1' ends turn
    Then Player scores are the following 'Player1 1000'

  @R94
  Scenario: Row 94
    Given The game starts with 1 player
    And The player names are the following 'Player1'
    When 'Player1' gets 'SEA_BATTLE 2' fortune card
    And 'Player1' rolls the following 'MONKEY,MONKEY,MONKEY,MONKEY,SWORD,PARROT,PARROT,GOLD_COIN'
    And 'Player1' re-rolls dice with index '5 6' to get the following 'GOLD_COIN,SWORD'
    And 'Player1' ends turn
    Then Player scores are the following 'Player1 1200'

  @R95
  Scenario: Row 95
    Given The game starts with 1 player
    And The player names are the following 'Player1'
    When 'Player1' gets 'MONKEY_BUSINESS' fortune card
    And 'Player1' rolls the following 'MONKEY,MONKEY,PARROT,GOLD_COIN,GOLD_COIN,DIAMOND,DIAMOND,DIAMOND'
    And 'Player1' ends turn
    Then Player scores are the following 'Player1 1200'
