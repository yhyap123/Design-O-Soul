package game;

import game.enums.Abilities;

public abstract class Sword extends MeleeWeapon{

    /**
     * Constructor.
     *
     * @param name                  name of the item
     * @param displayChar           character to use for display when item is on the ground
     * @param damage                amount of damage this weapon does
     * @param verb                  verb to use for this weapon, e.g. "hits", "zaps"
     * @param hitRate               the probability/chance to hit the target
     * @param criticalStrikeRate    the probability/chance to deal double damage
     */
    public Sword(String name, char displayChar, int damage, String verb, int hitRate, int criticalStrikeRate) {
        super(name, displayChar, damage, verb, hitRate);
        this.criticalStrikeRate = criticalStrikeRate;
        this.addCapability(Abilities.CRITICAL_STRIKE);
    }
}
