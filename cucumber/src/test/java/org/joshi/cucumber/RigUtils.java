package org.joshi.cucumber;

import org.joshi.pirates.Die;
import org.joshi.pirates.Turn;
import org.joshi.pirates.cards.FortuneCard;
import org.joshi.pirates.cards.SeaBattleCard;
import org.joshi.pirates.cards.SkullCard;

import java.util.ArrayList;
import java.util.List;

public class RigUtils {
    public static FortuneCard getCard(String card) {
        var split = card.split("\\s+");

        // Normal cards
        if (split.length == 1) {
            return new FortuneCard(FortuneCard.Type.valueOf(split[0]));
        }

        String name = split[0];

        switch (name) {
            case "SEA_BATTLE" -> {
                return new SeaBattleCard(Integer.parseInt(split[1]));
            }
            case "SKULLS" -> {
                return new SkullCard(Integer.parseInt(split[1]));
            }

        }
        return null;
    }

    public static List<Turn.RiggedDie> getDice(List<String> dieList) {
        List<Turn.RiggedDie> dice = new ArrayList<>();
        for (int i = 0; i < dieList.size(); i++) {
            dice.add(new Turn.RiggedDie(i, new Die(Die.Side.valueOf(dieList.get(i)))));
        }
        return dice;
    }
}
