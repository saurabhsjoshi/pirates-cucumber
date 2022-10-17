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

  @R101
  Scenario: Row 101
    Given The game starts with 1 player
    And The player names are the following
      | Player1 |
    When 'Player1' gets 'SKULLS 2' fortune card
    And 'Player1' rolls the following
      | SKULL  |
      | SKULL  |
      | PARROT |
      | PARROT |
      | PARROT |
      | MONKEY |
      | MONKEY |
      | MONKEY |
    And 'Player1' re-rolls dice with index '2 3 4' to get the following
      | SKULL |
      | SKULL |
      | SWORD |
    And 'Player1' re-rolls dice with index '4 5 6 7' to get the following
      | SKULL |
      | SKULL |
      | SKULL |
      | SWORD |
    And 'Player1' ends turn
    Then 'Player1' inflicts 900 damage
    And Player scores are the following
      | Player1 0 |

  @R102
  Scenario: Row 102
    Given The game starts with 1 player
    And The player names are the following
      | Player1 |
    When 'Player1' gets 'CAPTAIN' fortune card
    And 'Player1' rolls the following
      | SKULL  |
      | SKULL  |
      | SKULL  |
      | SKULL  |
      | SKULL  |
      | MONKEY |
      | MONKEY |
      | MONKEY |
    And 'Player1' re-rolls dice with index '5 6 7' to get the following
      | SKULL     |
      | SKULL     |
      | GOLD_COIN |
    And 'Player1' ends turn
    Then 'Player1' inflicts 1400 damage
    And Player scores are the following
      | Player1 0 |