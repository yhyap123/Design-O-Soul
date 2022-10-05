package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.enums.Abilities;
import game.interfaces.Chargeable;

/**
 * The action class to handle drinking of Estus Flask.
 */
public class DrinkEstusFlaskAction extends Action {

    private Chargeable item;
    private int healValue;

    /**
     * Constructor
     * @param item an item that is chargeable.
     * @param healValue an integer which represents the amount of value that will be healed.
     * Bind the parameter with the class variable.
     */
    public DrinkEstusFlaskAction(Chargeable item, int healValue) {
        this.item = item;
        this.healValue = healValue;
    }

    /**
     * Execute method that handle the checking of charges of Estus Flask and healing action.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return String to indicate the current Action that being executed.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String result = actor + " cannot drink " + item;
        if (actor.hasCapability(Abilities.DRINK)) {
            if (item.subtractCharge(1)) {
                actor.heal(healValue);
                result = actor + " drank " + item + " and healed for " + healValue;
            }
            else {
                result = item + " has no charge";
            }
        }
        return result;
    }

    /**
     * Estus Flask Menu Description method
     * @param actor The actor performing the action.
     * @return String that being show in the menu to allow user to choose.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " drinks " + item;
    }
}
