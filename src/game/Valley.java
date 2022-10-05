package game;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.enums.Abilities;
import game.enums.Status;

/**
 * The gorge or endless gap that is dangerous for the Player.
 */
public class Valley extends Ground {
	/**
	 * Constructor
	 */
	public Valley() {
		super('+');
		this.addCapability(Status.IS_VALLEY);
	}
	/**
	 * Method that check whether the actor is allow to enter valley.
	 * @param actor the Actor to check
	 * @return true for player, else false
	 */

	@Override
	public boolean canActorEnter(Actor actor){
		if (actor.hasCapability(Status.IS_PLAYER)){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * Allowable actions that will hurt actor when actor step on valley
	 * @param actor the Actor acting
	 * @param location the current Location
	 * @param direction the direction of the Ground from the Actor
	 * @return new Actions()
	 */
	@Override
	public Actions allowableActions(Actor actor, Location location, String direction) {
		if (location.containsAnActor()){
			actor.hurt(Integer.MAX_VALUE);
		}
		return new Actions();
	}
}
