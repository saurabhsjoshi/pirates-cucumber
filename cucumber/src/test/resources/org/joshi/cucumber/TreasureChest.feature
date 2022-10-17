Feature: Single Player Scoring with Treasure Chest
  This feature validates single player scoring in different situations when they
  draw treasure chest fortune card

  @R82
  Scenario: Row 82
    Given The game starts with 1 player
    And The player names are the following
      | Player1 |
    When 'Player1' gets 'TREASURE_CHEST' fortune card
    And 'Player1' rolls the following
      | PARROT    |
      | PARROT    |
      | PARROT    |
      | SWORD     |
      | SWORD     |
      | DIAMOND   |
      | DIAMOND   |
      | GOLD_COIN |
    And 'Player1' puts dice with index '5 6 7' in treasure chest
    And 'Player1' re-rolls dice with index '3 4' to get the following
      | PARROT |
      | PARROT |
    And 'Player1' puts dice with index '0 1 2 3 4' in treasure chest
    And 'Player1' re-rolls dice with index '5 6 7' to get the following
      | SKULL     |
      | GOLD_COIN |
      | PARROT    |
    And 'Player1' ends turn
    Then Player scores are the following
      | Player1 1100 |