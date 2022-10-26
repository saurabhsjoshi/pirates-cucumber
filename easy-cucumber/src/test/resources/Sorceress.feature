Feature: Single Player Scoring with Sorceress
  This feature validates single player scoring in different situations when they
  draw sorceress

  @R69
  Scenario: Row 69
    Given The game starts with 1 player
    And The player names are the following 'Player1'
    When 'Player1' gets 'SORCERESS' fortune card
    And 'Player1' rolls the following 'DIAMOND,DIAMOND,SWORD,MONKEY,GOLD_COIN,PARROT,PARROT,PARROT'
    And 'Player1' re-rolls dice with index '5 6 7' to get the following 'SKULL,MONKEY,MONKEY'
    And 'Player1' re-rolls dice with index '5' to get the following 'MONKEY'
    And 'Player1' ends turn
    Then Player scores are the following 'Player1 500'

  @R71
  Scenario: Row 71
    Given The game starts with 1 player
    And The player names are the following 'Player1'
    When 'Player1' gets 'SORCERESS' fortune card
    And 'Player1' rolls the following 'SKULL,PARROT,PARROT,PARROT,PARROT,MONKEY,MONKEY,MONKEY'
    And 'Player1' re-rolls dice with index '5 6 7' to get the following 'SKULL,PARROT,PARROT'
    And 'Player1' re-rolls dice with index '5' to get the following 'PARROT'
    And 'Player1' ends turn