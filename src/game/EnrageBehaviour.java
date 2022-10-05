package game;

import edu.monash.fit2099.engine.*;
import game.enums.Status;
import game.interfaces.Behaviour;


/**
 * A Behaviour that is called when Lord Of Cinder is in enrage mode (Hp < 50%)
 *
 */
public class EnrageBehaviour implements Behaviour {
    private Actor target;
    private String direction;
    private boolean isActivate;

    /**
     * Constructor.
     *
     */
    public EnrageBehaviour() {
        this.isActivate = false;
    }

    /**
     * Getting the action Lord Of Cinder is performing when enrage mode is activated
     *
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return The available action of his weapon
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        if (!isActivate) {
            Location here = map.locationOf(actor);

            // search Player from exits
            for (Exit exit : here.getExits()) {
                Location destination = exit.getDestination();
                if (destination.containsAnActor() && destination.getActor().hasCapability(Status.HOSTILE_TO_ENEMY)) {
                    target = destination.getActor();
                    direction = exit.getName();
                }
            }
            isActivate = true;
            return actor.getWeapon().getActiveSkill(target, direction);
        }

        return null;
    }
}