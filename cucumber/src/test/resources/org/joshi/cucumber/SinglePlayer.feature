Feature: Single Player Scoring
  This feature tests and validates scoring for a single player

  @R37
  Scenario: Die with three skulls on first roll
    Given The game starts with 1 player
    And The player names are the following
      | Player1 |
    When 'Player1' rolls the following
      | 3 SKULL |
      | 5 SWORD |
    Then 'Player1' gets score of 0
