package game;


import edu.monash.fit2099.engine.*;
import game.enums.Status;

import java.util.Random;


/**
 * An undead minion.
 */
public class Undead extends Enemy {
	Random rand = new Random();

	/**
	 * Constructor.
	 * All Undeads are represented by an 'u' and have 30 hit points.
	 * @param name the name of this Undead
	 */
	public Undead(String name, Location initialLocation) {
		super(name, 'u', 50, initialLocation, 50);
		this.getBehaviours().add(new WanderBehaviour());
		this.setInitialLocation(initialLocation);
	}

	/**
	 * Select and return an action to perform on the current turn of the Undead.
	 *
	 * @param actions    Collection of possible Actions for this Undead
	 * @param lastAction The Action this Undead took last turn.
	 * @param map        The map containing the Undead
	 * @param display    The I/O object to which messages may be written
	 * @return the Action to be performed by the Undead
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// 10% chance to die
		if (rand.nextInt(10)==1){
			this.addCapability(Status.RANDOM_DEAD);
			this.getBehaviours().clear();
			return new DyingAction(map.locationOf(this),0,null,this,null,null);
		}

		// loop through all behaviours
		return super.playTurn(actions, lastAction, map, display);
	}

	/**
	 * When this method is called:
	 * Behavior of Undead is cleared.
	 * Undead is removed from map.
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
		return new IntrinsicWeapon(20, "thwacks");
	}

	/**
	 * toString method
	 * @return toString
	 */
	@Override
	public String toString() {
		String hitPointsStatus = "(" + getHitPoints() + "/" + getMaxHitPoints() + ")";
		String weaponStatus = "no weapon";
		String weaponStatusMessage = " (" + weaponStatus + ")";
		return (name + hitPointsStatus + weaponStatusMessage);
	}
}




