package game;

import edu.monash.fit2099.engine.*;
import game.enums.Status;

import java.util.Random;

/**
 *
 * A Mimic has 50% chances of appearing when chest is opened
 *
 */
public class Mimic extends Enemy {
    Random rand = new Random();

    /**
     * Constructor.
     *
     * All Mimic are represented by an 'M' and have 100 hit points.
     * @param name the name of this Undead
     */
    public Mimic(String name, Location initialLocation) {
        super(name, 'M', 100, initialLocation, 200);
        this.setInitialLocation(initialLocation);
        this.addRandomTokenOfSouls();
        this.addCapability(Status.IS_MIMIC);
    }

    /**
     * Adding random number of Token of Souls (1 to 3) to the Mimic's inventory
     *
     */
    public void addRandomTokenOfSouls() {
        int randomNumber;
        randomNumber = rand.nextInt(3);

        for (int i = 0; i < randomNumber+1; i++) {
            TokenOfSoul tokenOfSouls = new TokenOfSoul("Token of Soul", '$', false, this);
            tokenOfSouls.setSouls(100);
            this.addItemToInventory(tokenOfSouls);
        }
    }

    /**
     * When this method is called:
     * Behavior of Mimic is cleared.
     * Mimic is removed from map.
     */
    @Override
    public void resetInstance() {
        this.getBehaviours().clear();
        this.setFollowBehaviour(null);
        this.getInitialLocation().map().removeActor(this);
    }

    /**
     * Getter for Intrinsic Weapon
     * @return IntrinsicWeapon object
     */
    @Override
    protected IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(55, "kicks");
    }

    /**
     * toString method
     * @return toString
     */
    @Override
    public String toString() {
        String hitPointsStatus = " (" + getHitPoints() + "/" + getMaxHitPoints() + ")";
        String weaponStatus = "no weapon";
        String weaponStatusMessage = " (" + weaponStatus + ")";
        return (name + hitPointsStatus + weaponStatusMessage);
    }
}
