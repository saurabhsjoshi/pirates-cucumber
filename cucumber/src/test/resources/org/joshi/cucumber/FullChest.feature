Feature: Single Player Scoring
  This feature tests and validates scoring for a single player when they
  get a full chest i.e. all dice are used when calculating the total score

  @R89
  Scenario: Row 89
    Given The game starts with 1 player
    And The player names are the following
      | Player1 |
    When 'Player1' gets 'GOLD' fortune card
    And 'Player1' rolls the following
      | MONKEY  |
      | MONKEY  |
      | MONKEY  |
      | SWORD   |
      | SWORD   |
      | SWORD   |
      | DIAMOND |
      | PARROT  |
    And 'Player1' ends turn
    Then Player scores are the following
      | Player1 400 |