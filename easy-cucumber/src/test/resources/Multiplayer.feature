Feature: Multiplayer Scenarios
  This feature tests and validates scoring in a multiplayer game
  which would also validate the networking aspects

  @row124
  Scenario: Row 124
    Given The game starts with 3 player
    And The player names are the following 'Player1 Player2 Player3'
    When 'Player1' gets 'CAPTAIN' fortune card
    And 'Player1' rolls the following 'SWORD,SWORD,SWORD,SWORD,SWORD,SWORD,SWORD,SKULL'
    And 'Player1' ends turn
    And Player scores are the following 'Player1 4000 Player2 0 Player3 0'
    And 'Player2' gets 'SKULLS 1'
    And 'Player2' rolls the following 'SWORD,SWORD,SWORD,SWORD,SWORD,SWORD,SWORD,SKULL'
    And 'Player2' ends turn
    And Player scores are the following 'Player1 4000 Player2 2000 Player3 0'
    And 'Player3' gets 'GOLD' fortune card
    And 'Player3' rolls the following 'SKULL,SKULL,SKULL,MONKEY,MONKEY,MONKEY,MONKEY,MONKEY'
    And 'Player3' gets disqualified
    And Player scores are the following 'Player1 4000 Player2 2000 Player3 0'
    Then 'Player1' is declared winner

  @row137
  Scenario: Row 137
    Given The game starts with 3 player
    And The player names are the following 'Player1 Player2 Player3'
    When 'Player1' gets 'CAPTAIN' fortune card
    And 'Player1' rolls the following 'SKULL,SKULL,SKULL,MONKEY,MONKEY,MONKEY,MONKEY,MONKEY'
    And 'Player1' gets disqualified
    And Player scores are the following 'Player1 0 Player2 0 Player3 0'
    And 'Player2' gets 'CAPTAIN' fortune card
    And 'Player2' rolls the following 'SWORD,SWORD,SWORD,SWORD,SWORD,SWORD,SWORD,SKULL'
    And 'Player2' ends turn
    And Player scores are the following 'Player1 0 Player2 4000 Player3 0'
    And 'Player3' gets 'SKULLS 2' fortune card
    And 'Player3' rolls the following 'SWORD,SWORD,SWORD,SWORD,SWORD,SWORD,SWORD,SKULL'
    And 'Player3' gets disqualified
    And Player scores are the following 'Player1 0 Player2 4000 Player3 0'
    And 'Player1' gets 'CAPTAIN' fortune card
    And 'Player1' rolls the following 'SWORD,SWORD,SWORD,SWORD,SWORD,SWORD,SWORD,SWORD'
    And 'Player1' ends turn
    And Player scores are the following 'Player1 9000 Player2 4000 Player3 0'
    Then 'Player1' is declared winner

  @row142
  Scenario: Row 142
    Given The game starts with 3 player
    And The player names are the following 'Player1 Player2 Player3'
    When 'Player1' gets 'GOLD' fortune card
    And 'Player1' rolls the following 'SWORD,SWORD,SWORD,SWORD,SWORD,SWORD,SKULL,SKULL'
    And 'Player1' ends turn
    And Player scores are the following 'Player1 1100 Player2 0 Player3 0'
    And 'Player2' gets 'SORCERESS' fortune card
    And 'Player2' rolls the following 'SKULL,SKULL,SKULL,SKULL,SKULL,SKULL,SKULL,GOLD_COIN'
    And 'Player2' re-rolls dice with index '0' to get the following 'PARROT'
    And 'Player2' re-rolls dice with index '0 7' to get the following 'SKULL,SKULL'
    And 'Player2' ends turn
    Then 'Player2' inflicts 800 damage
    And Player scores are the following 'Player1 300 Player2 0 Player3 0'