package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.WeaponAction;
import edu.monash.fit2099.engine.WeaponItem;
import game.enums.Status;

public class ChargeAction extends WeaponAction{

    /**
     * Constructor.
     * @param weaponItem the weapon item that will activate ChargeAction.
     */
    public ChargeAction(WeaponItem weaponItem){
        super(weaponItem);
    }

    /**
     * Perform the ChargeAction.
     * Add the charging status to the weapon item.
     * Add the disarmed status to the actor performing this action.
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map){
        weapon.addCapability(Status.CHARGING);
        actor.addCapability(Status.DISARMED);

        String result = actor + " charging " + weapon + ".";
        return result;
    }

    /**
     * Returns a descriptive string
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor){
        return actor + " charges " + weapon;
    }
}
