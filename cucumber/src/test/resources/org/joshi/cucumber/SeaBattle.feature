Feature: Single Player Scoring with Sea Battle
  This feature validates single player scoring when the player draws a Sea Battle Fortune
  Card

  @R106
  Scenario: Row 106
    Given The game starts with 1 player
    And The player names are the following
      | Player1 |
    When 'Player1' gets 'SEA_BATTLE 2' fortune card
    And 'Player1' rolls the following
      | MONKEY |
      | MONKEY |
      | MONKEY |
      | MONKEY |
      | SKULL  |
      | SKULL  |
      | SKULL  |
      | SWORD  |
    Then 'Player1' gets disqualified
    And 'Player1' loses 300 points
    And Player scores are the following
      | Player1 0 |

  @R107
  Scenario: Row 107
    Given The game starts with 1 player
    And The player names are the following
      | Player1 |
    When 'Player1' gets 'SEA_BATTLE 3' fortune card
    And 'Player1' rolls the following
      | SWORD  |
      | SWORD  |
      | SKULL  |
      | SKULL  |
      | PARROT |
      | PARROT |
      | PARROT |
      | PARROT |
    And 'Player1' re-rolls dice with index '4 5 6 7' to get the following
      | SKULL |
      | SKULL |
      | SKULL |
      | SKULL |
    Then 'Player1' gets disqualified
    And 'Player1' loses 500 points
    And Player scores are the following
      | Player1 0 |