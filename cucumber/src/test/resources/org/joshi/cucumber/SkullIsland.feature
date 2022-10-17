Feature: Single Player Scoring with Skull Island
  This feature validates single player scoring when they go to skull island


  @R98
  Scenario: Row 98
    Given The game starts with 1 player
    And The player names are the following
      | Player1 |
    When 'Player1' gets 'SKULLS 2' fortune card
    And 'Player1' rolls the following
      | SKULL |
      | SWORD |
      | SWORD |
      | SWORD |
      | SWORD |
      | SWORD |
      | SWORD |
      | SWORD |
    Then 'Player1' gets disqualified
    And Player scores are the following
      | Player1 0 |


  @R99
  Scenario: Row 99
    Given The game starts with 1 player
    And The player names are the following
      | Player1 |
    When 'Player1' gets 'SKULLS 1' fortune card
    And 'Player1' rolls the following
      | SKULL |
      | SKULL |
      | SWORD |
      | SWORD |
      | SWORD |
      | SWORD |
      | SWORD |
      | SWORD |
    Then 'Player1' gets disqualified
    And Player scores are the following
      | Player1 0 |