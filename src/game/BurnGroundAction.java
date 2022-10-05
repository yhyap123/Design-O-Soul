package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.WeaponAction;
import edu.monash.fit2099.engine.WeaponItem;
import game.enums.Abilities;

public class BurnGroundAction extends WeaponAction {

    /**
     * Constructor.
     * @param weaponItem the weapon item that has capabilities.
     */
    public BurnGroundAction(WeaponItem weaponItem){
        super(weaponItem);
    }

    /**
     * Perform the BurnGroundAction.
     * Burn the ground in adjacent squares.
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map){
        String result = actor + " uses Burn Ground.";
        Location here = map.locationOf(actor);
        for (Exit exit: here.getExits()){
            Location destination = exit.getDestination();

            if (destination.getGround().hasCapability(Abilities.BURN)){
                destination.setGround(new BurnedGround(destination.getGround(), actor));
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
        return actor + " activates Burn Ground.";
    }
}
