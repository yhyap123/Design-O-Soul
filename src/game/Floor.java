package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import game.enums.Abilities;
import game.enums.Status;

/**
 * A class that represents the floor inside a building.
 */
public class Floor extends Ground {

	public Floor() {
		super('_');
	}

	/**
	 * Method to check whether the actor can enter
	 * @param actor the Actor to check
	 * @return
	 */
	@Override
	public boolean canActorEnter(Actor actor) {
		if(actor.hasCapability(Status.IS_PLAYER)){
			return true;
		}
		else{
			return false;
		}
	}
}
