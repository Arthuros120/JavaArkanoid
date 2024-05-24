package fr.arks.exiledarkanoid.gamephysics.bonus;

import fr.arks.exiledarkanoid.gamephysics.bases.Position;

/**
 * BonusDrop
 *
 * Bonus drop on top of the screen
 *
 * @see ABonus
 *
 */
public class BonusDrop extends ABonus {

    /**
     * Constructor
     *
     * @param position Position of the bonus
     */
    public BonusDrop(Position position) {
        super(position, "bonus_drop");
    }
}
