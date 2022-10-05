package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.WeaponItem;
import edu.monash.fit2099.engine.Location;
import game.enums.Abilities;
import game.enums.Status;

import java.util.Random;

/**
 * An Skeleton minion.
 */

public class Skeleton extends Enemy {
    Random rand = new Random();

    /**
     * Constructor.
     * All Skeleton are represented by an 's' and have 250 hit points.
     * @param name the name of this Skeleton
     * @param initialLocation the initial location of this Skeleton
     */
    public Skeleton(String name, Location initialLocation) {
        super(name, 's', 100, initialLocation, 250);
        this.addCapability(Abilities.REVIVE_FOR_ONCE);
        this.setInitialLocation(initialLocation);
        this.getBehaviours().add(new WanderBehaviour());
        this.addRandomizeWeapon();
        this.addCapability(Status.IS_SKELETON);
    }

    /**
     * Select and return an action to perform on the current turn of the Skeleton.
     *
     * @param actions    Collection of possible Actions for this Skeleton
     * @param lastAction The Action this Skeleton took last turn.
     * @param map        The map containing the Skeleton
     * @param display    The I/O object to which messages may be written
     * @return the Action to be performed by the Skeleton
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {

        // Skeleton has 50% chance to revive once
        if (!isConscious() && this.hasCapability(Abilities.REVIVE_FOR_ONCE) && rand.nextBoolean()) {
            this.setHitPoints(getMaxHitPoints());

            // Remove ability
            this.removeCapability(Abilities.REVIVE_FOR_ONCE);

            display.println(this + " is resurrected from death!");

            return new DoNothingAction();
        }
        else if (!isConscious()) {
            return new DyingAction(map.locationOf(this),0,null,this,null,null);
        }

        // loop through all behaviours
        return super.playTurn(actions, lastAction, map, display);
    }

    /**
     * Resetting Skeleton
     *
     */
    @Override
    public void resetInstance() {
        super.resetInstance();

        // add back wander behaviour
        this.getBehaviours().add(new WanderBehaviour());
    }

    /**
     * Adding randomize weapon (broad sword or giant axe) to
     * this Skeleton
     *
     */
    public void addRandomizeWeapon() {
        WeaponItem weapon;
        WeaponItem broadSword = new BroadSword();
        WeaponItem giantAxe = new GiantAxe();

        if (rand.nextBoolean()) {
            weapon = giantAxe;
        }
        else {
            weapon = broadSword;
        }

        this.addItemToInventory(weapon);
    }
}
