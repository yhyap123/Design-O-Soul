package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DropItemAction;
import edu.monash.fit2099.engine.WeaponItem;
import game.enums.Abilities;

import java.util.Random;

public abstract class GameWeaponItem extends WeaponItem {

    protected  int criticalStrikeRate;
    protected Random rand = new Random();
    protected Display display = new Display();

    /**
     * Constructor.
     *
     * @param name                  name of the item
     * @param displayChar           character to use for display when item is on the ground
     * @param damage                amount of damage this weapon does
     * @param verb                  verb to use for this weapon, e.g. "hits", "zaps"
     * @param hitRate               the probability/chance to hit the target
     */
    public GameWeaponItem(String name, char displayChar, int damage, String verb, int hitRate) {
        super(name, displayChar, damage, verb, hitRate);
    }

    /**
     * Accessor for damage done by this weapon.
     * If this weapon has ability to hit critical strike, damage will be doubled.
     *
     * @return the damage
     */
    @Override
    public int damage(){
        int damage = this.damage;
        if (this.hasCapability(Abilities.CRITICAL_STRIKE)){
            if (rand.nextInt(100) <= criticalStrikeRate){
                damage *= 2;
                display.println("CRITICAL STRIKE !!!");
            }
        }
        return damage;
    }

    /**
     * Disable the DropItemAction of weapon.
     * In this game, all weapons cannot be dropped.
     *
     * @param actor an actor that will interact with this item
     * @return null because
     */
    @Override
    public DropItemAction getDropAction(Actor actor) {
        return null;
    }
}
