package org.joshi.pirates.ui;

import org.joshi.pirates.Die;
import org.joshi.pirates.Player;
import org.joshi.pirates.cards.FortuneCard;
import org.joshi.pirates.cards.SeaBattleCard;
import org.joshi.pirates.cards.SkullCard;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleUtils {
    public static final String USER_PROMPT = "##: ";

    public static final String SYSTEM_MSG_SEPARATOR = "######";
    public static final String DICE_STATE_MSG = "DICE STATE";
    public static final String DEAD_MSG = "YOU ARE DISQUALIFIED (3 SKULLS)";

    public static final String DEAD_MSG_SKULL_ISLAND = "YOU ARE DISQUALIFIED (DID NOT ROLL SKULL)";

    public static final String SKULL_ACTIVATE_MSG = "CANNOT RE-ROLL SKULL";

    public static final String WINNER_MSG = "THIS GAME HAS A WINNER";

    public static final String SCORE_MSG = "PLAYER SCORES";

    public static final String DAMAGE_MSG = "YOU INFLICTED DAMAGE OF ";

    public static final String LOSS_MSG = "YOU SUFFERED A DEDUCTION OF ";

    public static final String SERVER_STARTED_MSG = "SERVER STARTED";

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Prints a prompt for the user.
     *
     * @param prompt message to show
     * @return user response
     */
    public static String userPrompt(String prompt) {
        System.out.println(prompt);
        System.out.println(USER_PROMPT);
        return scanner.nextLine();
    }

    public static String getStartTurnMsg(String player) {
        return getSysMsg("STARTING TURN FOR " + player);
    }

    public static String getEndTurnMsg(String player) {
        return getSysMsg("TURN ENDED FOR " + player);
    }

    public static String printRoundOptions(FortuneCard card) {
        printSysMsg("OPTIONS");
        System.out.println("1. Re roll");

        if (card.getType() == FortuneCard.Type.TREASURE_CHEST) {
            System.out.println("2. Select die to put in treasure chest");
        }

        System.out.println("0. Complete turn");
        return userPrompt("What would you like to do?");
    }

    public static void startGameMsg() {
        printSysMsg("STARTING GAME");
        System.out.print("\n\n");
    }

    public static void startTurnMsg() {
        printSysMsg("STARTING YOUR TURN");
    }

    private static String fortuneCard(FortuneCard card) {
        if (card instanceof SeaBattleCard seaBattleCard) {
            return card.getType().name() + " (SWORDS:" + seaBattleCard.getSwords() + ", BONUS:" + seaBattleCard.getBonus() + ")";
        } else if (card instanceof SkullCard skullCard) {
            return card.getType().name() + " (" + skullCard.getSkulls() + ")";
        } else {
            return card.getType().name();
        }
    }

    public static void startRoundMsg(FortuneCard card) {
        System.out.println("FORTUNE CARD: " + fortuneCard(card));
    }

    public static void printDice(List<Die> dice) {
        printSysMsg(DICE_STATE_MSG);
        for (int i = 0; i < dice.size(); i++) {
            var die = dice.get(i);
            System.out.printf("%-1s %-10s %-10s\n", i, die.getDiceSide().name(),
                    (die.getState() == Die.State.IN_TREASURE_CHEST ? "IN_TREASURE_CHEST" : " "));
        }
    }

    public static void printPlayerScores(List<Player> players) {
        printSysMsg(SCORE_MSG);
        for (var player : players) {
            System.out.printf("%-1s %-10s \n", player.getPlayerId().username(), player.getScore());
        }
    }

    public static void printPlayerScores(Map<String, Integer> players) {
        printSysMsg(SCORE_MSG);
        for (var player : players.entrySet()) {
            System.out.printf("%-1s %-10s \n", player.getKey(), player.getValue());
        }
    }

    public static void printSkullActivated() {
        printSysMsg(SKULL_ACTIVATE_MSG);
    }

    public static void printWinner(String winner) {
        printSysMsg(WINNER_MSG);
        System.out.printf("WINNER %-10s\n", winner);
    }

    public static String getSysMsg(String msg) {
        return SYSTEM_MSG_SEPARATOR + msg + SYSTEM_MSG_SEPARATOR;
    }

    public static void printDeadMsg() {
        printSysMsg(DEAD_MSG);
    }

    public static void printDeadSkullIsland() {
        printSysMsg(DEAD_MSG_SKULL_ISLAND);
    }

    public static void printDamageMsg(int damage) {
        printSysMsg(DAMAGE_MSG + damage);
    }

    public static void printLossMsg(int loss) {
        printSysMsg(LOSS_MSG + loss);
    }

    public static String getServerStartedMsg() {
        return getSysMsg(SERVER_STARTED_MSG);
    }

    public static void printServerStartedMsg() {
        System.out.println(getServerStartedMsg());
    }

    public static void printSysMsg(String msg) {
        System.out.println(getSysMsg(msg));
    }
}
