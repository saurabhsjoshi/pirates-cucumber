Feature: Single Player Scoring with Monkey Business
  This feature validates single player scoring in different situations when they
  draw monkey business fortune card

  @R74
  Scenario: Row 74
    Given The game starts with 1 player
    And The player names are the following
      | Player1 |
    When 'Player1' gets 'MONKEY_BUSINESS' fortune card
    And 'Player1' rolls the following
      | MONKEY |
      | MONKEY |
      | MONKEY |
      | PARROT |
      | PARROT |
      | PARROT |
      | SKULL |
      | GOLD_COIN |
    And 'Player1' ends turn
    Then Player scores are the following
      | Player1 1100 |
