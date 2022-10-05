package game;

import edu.monash.fit2099.engine.*;
import game.enums.Status;
import game.interfaces.Behaviour;

import java.util.Random;


/**
 * A class that handles Enemy attack behaviours, figuring out which AttackAction or WeaponAction
 * of the actor to perform.
 */
public class AttackBehaviour implements Behaviour {
    private Actor target;
    private Actions actions = new Actions();

    Random rand = new Random();

    /**
     * Getting the actions of the actor that can perform attack
     *
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return an Action that actor can perform, or null if actor can't do this.
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        Location here = map.locationOf(actor);
        this.actions = new Actions();

        // actor is Aldrich, add range weapon actions
        if (actor.hasCapability(Status.IS_ALDRICH)) {

            // search exits of the actor to find the target
            this.scanExits(actor, here);

            // search for player in expanded range
            if (actions.size() > 0) {
                return actions.get(0);
            }
        }

        else {
            // search exits of the actor to find the target
            this.scanExits(actor, here);

            // randomly selects a action to return
            if (actions.size() > 0) {
                int randInt = rand.nextInt(actions.size());
                return actions.get(randInt);
            }
        }

        return null;
    }

    /**
     * Scanning exits of the actor to find target to attack
     *
     * @param actor the Actor acting
     * @param here the current location of the actor
     */
    public void scanExits(Actor actor, Location here) {
        // scanning exits of the actor
        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();

            // check if there is a Player
            if (destination.containsAnActor() && destination.getActor().hasCapability(Status.IS_PLAYER)) {
                target = destination.getActor();
                actions.add(new AttackAction(target, exit.getName()));

                // add in all actions of the actor item
                if (!actor.hasCapability(Status.IS_MIMIC)) {
                    this.addItemActions(actor);
                }

                break;
            }
        }

        // Aldrich has wider range to attack
        // its weapon would perform the searching of the wider range
        if (actor.hasCapability(Status.IS_ALDRICH)) {
            this.addItemActions(actor);
        }
    }


    /**
     * Adding available actions from the actor's item
     *
     * @param actor the Actor acting
     */
    public void addItemActions(Actor actor) {
        // loop through inventory and get allowable actions of each item
        for (Item item : actor.getInventory()) {
            actions.add(item.getAllowableActions());
        }
    }
}

