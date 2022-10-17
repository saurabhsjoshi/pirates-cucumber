package org.joshi.pirates.ui;

import org.joshi.pirates.Die;
import org.joshi.pirates.Turn;
import org.joshi.pirates.TurnResult;
import org.joshi.pirates.cards.FortuneCard;
import org.joshi.pirates.cards.SeaBattleCard;
import org.joshi.pirates.cards.SkullCard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PlayerTurn {
    private FortuneCard fortuneCard;

    private final boolean rigged;

    public PlayerTurn(FortuneCard card, boolean rigged) {
        this.fortuneCard = card;
        this.rigged = rigged;
    }

    private void rolled(Turn turn) {
        if (rigged) {
            String result = ConsoleUtils.userPrompt("Rig dice?");
            if (!result.isBlank()) {
                List<Turn.RiggedDie> riggedDice = new ArrayList<>();

                var split = result.split("\\s+");

                for (int i = 0; i < split.length; i += 2) {
                    var idx = Integer.parseInt(split[i]);
                    var object = Die.Side.values()[Integer.parseInt(split[i + 1])];
                    riggedDice.add(new Turn.RiggedDie(idx, new Die(object)));
                }
                turn.setRiggedDice(riggedDice);
            }
        }
    }

    public TurnResult start() {
        Turn turn = new Turn();

        if (rigged) {
            String result = ConsoleUtils.userPrompt("Rig fortune card?");
            if (!result.isBlank()) {
                var split = result.split("\\s+");
                switch (split[0]) {
                    case "3" -> fortuneCard = new SeaBattleCard(Integer.parseInt(split[1]));
                    case "7" -> fortuneCard = new SkullCard(Integer.parseInt(split[1]));
                    default -> fortuneCard = new FortuneCard(FortuneCard.Type.values()[Integer.parseInt(split[0])]);
                }
            }
        }

        turn.setFortuneCard(fortuneCard);
        ConsoleUtils.startTurnMsg();

        turn.roll();

        boolean reRolled = true;

        while (true) {

            // Only perform post roll steps if player has re-rolled
            if (reRolled) {
                rolled(turn);
                turn.postRoll();
                reRolled = false;
            }

            if (turn.isOnIslandOfSkulls()) {
                ConsoleUtils.printSysMsg("YOU ARE ON ISLAND OF DEAD");
            }

            ConsoleUtils.startRoundMsg(fortuneCard);
            ConsoleUtils.printDice(turn.getDice());

            if (turn.isDisqualified()) {
                if (turn.getState() == Turn.State.DISQUALIFIED) {
                    ConsoleUtils.printDeadMsg();
                } else {
                    ConsoleUtils.printDeadSkullIsland();
                }
                return complete(turn);
            }

            var result = ConsoleUtils.printRoundOptions(fortuneCard);

            if (result.isBlank()) {
                continue;
            }

            var split = result.split("\\s+");
            List<Integer> index = new ArrayList<>();

            switch (result.charAt(0)) {
                case '1':
                    // Player re-rolled
                    for (int i = 1; i < split.length; i++) {
                        index.add(Integer.valueOf(split[i]));
                    }

                    try {
                        turn.roll(new HashSet<>(index));
                        reRolled = true;
                    } catch (Turn.SkullReRolledException e) {
                        ConsoleUtils.printSkullActivated();
                    } catch (Turn.NotEnoughDieException e) {
                        ConsoleUtils.printSysMsg("NOT ENOUGH DIE SELECTED FOR RE-ROLL");
                    }
                    break;

                case '2':
                    // Player added dice to treasure chest
                    for (int i = 1; i < split.length; i++) {
                        index.add(Integer.valueOf(split[i]));
                    }
                    turn.addToChest(index);
                    break;

                case '0':
                    return complete(turn);
            }
        }
    }

    private TurnResult complete(Turn turn) {
        var result = turn.complete();

        if (result.score() < 0) {
            var score = -1 * result.score();
            if (result.islandOfDead()) {
                ConsoleUtils.printDamageMsg(score);
            } else {
                ConsoleUtils.printSysMsg("You suffered a deduction of " + score);
            }
        } else {
            ConsoleUtils.printSysMsg("You scored " + result.score());
        }
        return result;
    }
}
