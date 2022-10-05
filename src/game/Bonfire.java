package game;

import edu.monash.fit2099.engine.*;
import game.enums.Status;

import java.util.HashMap;

/**
 * The class that handle the Bonfire in the middle of the game map.
 * @see Ground
 */
public class Bonfire extends Ground {
    private String name;
    private Boolean isActivated;
    /**
     * Constructor
     *
     */
    public Bonfire(String name,Boolean isActivated){
        super('B');
        this.isActivated = isActivated;
        this.name=name;
    }

    /**
     * This method return the allowable action that player can select from menu to perform the following action:
     * <ul>
     *     <li>If the bonfire is not activated LightUpBonfireAction</li>
     *     <li>Rest Action</li>
     *     <li>Teleport Action</li>
     * </ul>
     * @param actor the Actor acting
     * @param location the current Location
     * @param direction the direction of the Ground from the Actor
     * @return BonfireRestAction
     */
    @Override
    public Actions allowableActions(Actor actor, Location location, String direction) {
        Actions actions = new Actions();

        if (location.containsAnActor() && location.getActor().hasCapability(Status.IS_PLAYER)) {
            if (isActivated) {
                actions.add(new BonfireRestAction(name, this));
                HashMap<Location, Bonfire> bonfireHashMap = BonfireManager.getInstance().getBonfires();
                for (Location key : bonfireHashMap.keySet()) {
                    if (!key.equals(location)) {
                        if (bonfireHashMap.get(key).getIsActivated()) {
                            actions.add(new TeleportAction(key, bonfireHashMap.get(key).toString(), this));
                        }
                    }
                }
                return actions;
            } else {
                Actions lightup = new Actions();
                lightup.add(new LightUpBonfireAction(this));
                return lightup;
            }
        }

        return actions;
    }

    /**
     * toString method
     * @return name
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Set the isActivated to true
     */
    public void activateBonfire(){
        isActivated=true;
    }

    /**
     * Getter for isActivated
     * @return isActivated
     */
    public Boolean getIsActivated(){
        return isActivated;
    }
}
