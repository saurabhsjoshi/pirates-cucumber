package org.joshi.pirates;


import org.joshi.pirates.cards.FortuneCard;
import org.joshi.pirates.cards.SeaBattleCard;
import org.joshi.pirates.cards.SkullCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Turn {

    public enum State {
        DISQUALIFIED,
        OK,

        SKULL_ISLAND_DISQUALIFIED
    }

    private boolean isFirstRoll = true;
    private boolean isOnIslandOfSkulls = false;
    private boolean sorceressUsed = false;

    /**
     * Indicates that previous roll was done using sorceress.
     */
    private boolean sorceressRoll = false;
    private State state = State.OK;
    private FortuneCard fortuneCard;

    /**
     * Exception that is thrown if the player tries to re-roll a skull.
     */
    public static class SkullReRolledException extends Exception {
    }

    /**
     * Exception that is thrown when the user does not re-roll enough dice.
     */
    public static class NotEnoughDieException extends Exception {
    }

    /**
     * Maximum number of dice that can be played in a turn.
     */
    private static final int MAX_DICE = 8;

    /**
     * Map of the id and dice for this turn.
     */
    private final List<Die> dice = new ArrayList<>(MAX_DICE);

    private final List<Integer> rolledIndex = new ArrayList<>();

    /**
     * Class that allows rigging of die.
     */
    public record RiggedDie(int index, Die die) {
    }

    /**
     * First roll.
     */
    public void roll() {
        var diceSides = Die.Side.values();

        for (int i = 0; i < MAX_DICE; i++) {
            dice.add(new Die(diceSides[new Random().nextInt(diceSides.length)]));
            rolledIndex.add(i);
        }
    }

    public void roll(Set<Integer> index) throws SkullReRolledException, NotEnoughDieException {
        int skullCount = 0;
        boolean sorceressInvoked = false;

        // Check for skulls
        for (int i : index) {
            if (dice.get(i).getDiceSide() == Die.Side.SKULL) {
                skullCount++;
            }
        }

        if (skullCount > 0) {
            // Check if sorceress can be used
            if (fortuneCard.getType() == FortuneCard.Type.SORCERESS && !sorceressUsed && skullCount == 1) {
                sorceressUsed = true;
                sorceressInvoked = true;
                sorceressRoll = true;
            } else {
                throw new SkullReRolledException();
            }
        }

        if (index.size() < 2 && !sorceressInvoked) {
            throw new NotEnoughDieException();
        }

        var diceSides = Die.Side.values();

        for (int i : index) {
            rolledIndex.add(i);
            dice.set(i, new Die(diceSides[new Random().nextInt(diceSides.length)]));
        }

        isFirstRoll = false;
    }

    public void postRoll() {
        int currentRollSkulls = 0;

        for (var i : rolledIndex) {
            if (dice.get(i).getDiceSide() == Die.Side.SKULL) {
                currentRollSkulls++;
            }
        }

        rolledIndex.clear();

        int skulls = 0;

        if (fortuneCard instanceof SkullCard skullCard) {
            skulls += skullCard.getSkulls();
        }

        for (var die : dice) {
            if (die.getDiceSide() == Die.Side.SKULL) {
                skulls++;
            }
        }

        if (isFirstRoll && skulls > 3 && fortuneCard.getType() != FortuneCard.Type.SEA_BATTLE) {
            isOnIslandOfSkulls = true;
        } else if (skulls > 2 && !isOnIslandOfSkulls) {
            state = State.DISQUALIFIED;
        }

        // If player is on island of skull and has not rolled a skull they are disqualified
        if (!isFirstRoll && !sorceressRoll && isOnIslandOfSkulls && currentRollSkulls == 0) {
            state = State.SKULL_ISLAND_DISQUALIFIED;
        }
        sorceressRoll = false;
    }

    public TurnResult complete() {
        var expectedChestSize = dice.size();

        // If player is dead they get no score
        if (state == State.DISQUALIFIED) {
            if (fortuneCard instanceof SeaBattleCard seaBattleCard) {
                var count = dice.stream()
                        .filter(s -> s.getDiceSide() == Die.Side.SWORD)
                        .count();
                if (count != seaBattleCard.getSwords()) {
                    return new TurnResult(false, -seaBattleCard.getBonus());
                }
            }

            if (fortuneCard.getType() != FortuneCard.Type.TREASURE_CHEST) {
                return new TurnResult(false, 0);
            }
            // Only die that are protected will be used for scoring
            dice.removeIf(die -> die.getState() != Die.State.IN_TREASURE_CHEST);
        }

        Stream<Die> bonusObj = Stream.empty();

        if (fortuneCard.getType() == FortuneCard.Type.GOLD) {
            bonusObj = Stream.of(new Die(Die.Side.GOLD_COIN, Die.State.HELD));
            expectedChestSize++;
        } else if (fortuneCard.getType() == FortuneCard.Type.DIAMOND) {
            bonusObj = Stream.of(new Die(Die.Side.DIAMOND, Die.State.HELD));
            expectedChestSize++;
        }

        List<Die> sides = Stream
                .concat(bonusObj, dice.stream()
                        .filter(s -> s.getDiceSide() != Die.Side.SKULL))
                .collect(Collectors.toList());

        boolean hasSkulls = sides.size() != expectedChestSize;

        if (fortuneCard.getType() == FortuneCard.Type.MONKEY_BUSINESS) {
            sides = sides.stream()
                    .peek(s -> {
                        if (s.getDiceSide() == Die.Side.PARROT) {
                            s.setDiceSide(Die.Side.MONKEY);
                        }
                    })
                    .collect(Collectors.toList());
        }

        var score = Score.getIdenticalDiceScore(sides);
        score += Score.getBonusDieScore(sides);

        // Sea battle
        if (fortuneCard instanceof SeaBattleCard seaBattleCard) {
            var count = dice.stream()
                    .filter(die -> die.getDiceSide() == Die.Side.SWORD)
                    .count();

            if (count < seaBattleCard.getSwords()) {
                return new TurnResult(false, -seaBattleCard.getBonus());
            }

            // Only in case of two swords we need to mark these dice as used for full chest calculation
            if (count == 2) {
                for (var die : sides) {
                    if (die.getDiceSide() == Die.Side.SWORD) {
                        die.setUsed(true);
                    }
                }
            }

            score += seaBattleCard.getBonus();
        }

        boolean isFullChest = !hasSkulls;

        for (var die : sides) {
            if (!die.isUsed()) {
                isFullChest = false;
                break;
            }
        }

        if (isFullChest) {
            score += 500;
        }

        if (isOnIslandOfSkulls) {
            int skullsRolled = 0;

            for (var die : dice) {
                if (die.getDiceSide() == Die.Side.SKULL) {
                    skullsRolled++;
                }
            }

            score = -(skullsRolled * 100);

            if (fortuneCard instanceof SkullCard skullCard) {
                score -= (skullCard.getSkulls() * 100);
            }
        }

        // Captain card
        if (fortuneCard.getType() == FortuneCard.Type.CAPTAIN) {
            return new TurnResult(isOnIslandOfSkulls, score * 2);
        }

        return new TurnResult(isOnIslandOfSkulls, score);
    }

    /**
     * Add die with given indices to the treasure chest.
     *
     * @param dieIndex list of indices
     */
    public void addToChest(List<Integer> dieIndex) {
        for (var i : dieIndex) {
            dice.get(i).setState(Die.State.IN_TREASURE_CHEST);
        }
    }

    /**
     * Rig the dice.
     *
     * @param riggedDice list of rigged die
     */
    public void setRiggedDice(List<RiggedDie> riggedDice) {
        for (var roll : riggedDice) {
            dice.set(roll.index, roll.die);
        }
    }

    public void setFortuneCard(FortuneCard fortuneCard) {
        this.fortuneCard = fortuneCard;
    }

    public boolean isOnIslandOfSkulls() {
        return isOnIslandOfSkulls;
    }

    public State getState() {
        return state;
    }

    public List<Die> getDice() {
        return dice;
    }

    public boolean isDisqualified() {
        return state == State.DISQUALIFIED || state == State.SKULL_ISLAND_DISQUALIFIED;
    }

}
