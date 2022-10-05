package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.enums.Status;

import java.util.Random;


public class SpinAttackAction extends WeaponAction {

    protected Random rand = new Random();

    /**
     * Constructor.
     * @param weaponItem the weapon item that will activate SpinAttackAction.
     */
    public SpinAttackAction(WeaponItem weaponItem){
        super(weaponItem);
    }

    /**
     * Perform the SpinAttackAction.
     * Attack the actor in adjacent squares.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map){

        int damage = weapon.damage() / 2;

        String result = actor + " uses Spin Attack for " + damage + " damage.";
        Location here = map.locationOf(actor);

        for (Exit exit: here.getExits()){
            Location destination = exit.getDestination();
            if (destination.containsAnActor()) {
                Actor target = destination.getActor();

                if (actor.hasCapability(Status.IS_PLAYER) && target.hasCapability(Status.HOSTILE_TO_PLAYER) ||
                        actor.hasCapability(Status.HOSTILE_TO_PLAYER) && target.hasCapability(Status.IS_PLAYER)) {
                    if (!(rand.nextInt(100) <= weapon.chanceToHit())) {
                        result += System.lineSeparator() + actor + " misses " + target + ".";
                    }
                    target.hurt(damage);
                    result += System.lineSeparator() + actor + " " + "Spin Attack" + " " + target + " for " + damage + " damage.";
                    if (!target.isConscious() && (!target.hasCapability(Status.IS_PLAYER)) && (!target.hasCapability(Abilities.REVIVE_FOR_ONCE))) {
                        DyingAction dyingAction = new DyingAction(map.locationOf(actor), actor.asSoul().getSouls(), null, target, null, null);
                        dyingAction.execute(actor, map);
                        result += System.lineSeparator() + target + " is killed.";
                    }
                }
                else {
                    result += System.lineSeparator() + actor + " cannot attack " + target;
                }
            }
        }

        return result;
    }

    /**
     * Returns a descriptive string
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor){
        return actor + " activates Spin Attack";
    }

}
