package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * Special Action for Buying increase maximum hitpoints from Vendor.
 */
public class BuyIncreaseMaxHPAction extends BuyItemAction {
    private int increaseValue;

    /**
     * Constructor.
     *
     * @param vendor the vendor to sell items
     */
    public BuyIncreaseMaxHPAction(Actor vendor) {
        super(vendor, 200);
        setIncreaseValue(25);
    }

    /**
     * Perform the buying action of increase maximum hitpoints.
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return A description of the result of the purchase.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String result;

        // check if buyer has enough souls
        if (actor.asSoul().getSouls() < getRequiredSouls()) {
            result = actor + " does not have enough souls to increase maximum HP";
            return result;
        }
        else {
            actor.asSoul().transferSouls(this.getVendor().asSoul(), this.getRequiredSouls());
        }

        actor.increaseMaxHp(25);

        result = actor + " maximum HP increase by 25";

        return result;
    }

    /**
     * Returns a descriptive string of the increase maximum hitpoints details

     * @return The purchasing details
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " buys Max HP modifier (+" + getIncreaseValue() + "HP): "+ getRequiredSouls() + " souls";
    }

    /**
     * Getting the value to be increase after buying this
     *
     * @return the increase value
     */
    public int getIncreaseValue() {
        return increaseValue;
    }

    /**
     * Setting the value to be increase after buying this
     *
     * @param increaseValue The increase value
     */
    public void setIncreaseValue(int increaseValue) {
        this.increaseValue = increaseValue;
    }
}