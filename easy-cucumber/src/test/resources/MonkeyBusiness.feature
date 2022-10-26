Feature: Single Player Scoring with Monkey Business
  This feature validates single player scoring in different situations when they
  draw monkey business fortune card

  @R74
  Scenario: Row 74
    Given The game starts with 1 player
    And The player names are the following 'Player1'
    When 'Player1' gets 'MONKEY_BUSINESS' fortune card
    And 'Player1' rolls the following 'MONKEY,MONKEY,MONKEY,PARROT,PARROT,PARROT,SKULL,GOLD_COIN'
    And 'Player1' ends turn
    Then Player scores are the following 'Player1 1100'

  @R75
  Scenario: Row 75
    Given The game starts with 1 player
    And The player names are the following 'Player1'
    When 'Player1' gets 'MONKEY_BUSINESS' fortune card
    And 'Player1' rolls the following 'MONKEY,MONKEY,SWORD,SWORD,PARROT,PARROT,GOLD_COIN,GOLD_COIN'
    And 'Player1' re-rolls dice with index '2 3' to get the following 'MONKEY,PARROT'
    And 'Player1' ends turn
    Then Player scores are the following 'Player1 1700'

  @R76
  Scenario: Row 76
    Given The game starts with 1 player
    And The player names are the following 'Player1'
    When 'Player1' gets 'MONKEY_BUSINESS' fortune card
    And 'Player1' rolls the following 'SKULL,SKULL,SKULL,MONKEY,MONKEY,MONKEY,PARROT,PARROT'
    Then 'Player1' gets disqualified
