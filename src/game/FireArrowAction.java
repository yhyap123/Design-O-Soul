package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.WeaponAction;
import edu.monash.fit2099.engine.WeaponItem;
import game.enums.Abilities;

public class FireArrowAction extends WeaponAction {

    /**
     * Constructor.
     *
     * @param weaponItem the weapon item that has capabilities
     */
    public FireArrowAction(WeaponItem weaponItem){
        super(weaponItem);
    }

    /**
     * Perform the FireArrowAction.
     * Add/remove the fire ability to this weapon.
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map){
        String result = actor + " activates Fire Arrow";
        if (weapon.hasCapability(Abilities.FIRE)){
            weapon.removeCapability(Abilities.FIRE);
            result = actor + " deactivates Fire Arrow";
        }
        else {
            weapon.addCapability(Abilities.FIRE);
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
        if (weapon.hasCapability(Abilities.FIRE)){
            return actor + " deactivates Fire Arrow";
        }
        else {
            return actor + " activates Fire Arrow";
        }
    }

}
