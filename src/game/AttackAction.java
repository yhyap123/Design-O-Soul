package game;

import java.util.Random;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.enums.Status;

/**
 * Special Action for attacking other Actors.
 */
public class AttackAction extends Action {

	/**
	 * The Actor that is to be attacked
	 */
	protected Actor target;

	/**
	 * The direction of incoming attack.
	 */
	protected String direction;

	/**
	 * Random number generator
	 */
	protected Random rand = new Random();

	/**
	 * Constructor.
	 *
	 * @param target the Actor to attack
	 */
	public AttackAction(Actor target, String direction) {
		this.target = target;
		this.direction = direction;
	}

	/**
	 * Perform the AttackAction.
	 *
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a description of what happened after attacking.
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		Weapon weapon = actor.getWeapon();

		if (target.hasCapability(Status.BLOCKED)){
			target.removeCapability(Status.BLOCKED);
			return actor + " attack is blocked";
		}

		if (!(rand.nextInt(100) <= weapon.chanceToHit())) {
			return actor + " misses " + target + ".";
		}

		if (actor.hasCapability(Abilities.FIRE)){
			Location targetLocation = map.locationOf(target);
			if (targetLocation.getGround().hasCapability(Abilities.BURN)){
				targetLocation.setGround(new BurnedGround(targetLocation.getGround(), actor));
			}
		}

		int damage = weapon.damage();
		String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";
		target.hurt(damage);

		// when the attack target is dead
		if (!target.isConscious() && (!target.hasCapability(Status.IS_PLAYER)) && (!target.hasCapability(Abilities.REVIVE_FOR_ONCE))) {
			DyingAction dyingAction = new DyingAction(map.locationOf(actor),actor.asSoul().getSouls(),null,target,null,null);
			dyingAction.execute(actor,map);
			result += System.lineSeparator() + target + " is killed.";
		}

		return result;
	}

	/**
	 * Returns a descriptive string
	 * @param actor The actor performing the action.
	 * @return the text we put on the menu
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " attacks " + target + " at " + direction;
	}
}
