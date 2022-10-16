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
      | PARROT  |
      | PARROT |
      | PARROT |
      | PARROT |
      | SWORD |
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