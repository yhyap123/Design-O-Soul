package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

/**
 * Special Action for Buying items from Vendor.
 */
public class BuyItemAction extends Action {
    private Actor vendor;
    private Item item;
    private int requiredSouls;

    /**
     * Constructor 1 (with a specified item).
     *
     * @param vendor the vendor to sell items
     * @param item the item to buy
     * @param requiredSouls the required souls to buy the item
     */
    public BuyItemAction(Actor vendor, Item item, int requiredSouls) {
        this.setVendor(vendor);
        this.setItem(item);
        this.setRequiredSouls(requiredSouls);
    }

    /**
     * Constructor 2 (without a specified item).
     *
     * @param vendor the vendor to sell items
     * @param requiredSouls the required souls to buy the item
     */
    public BuyItemAction(Actor vendor, int requiredSouls) {
        this.setVendor(vendor);
        this.setRequiredSouls(requiredSouls);
    }

    /**
     * Perform the buying action.
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return A description of the result of the purchase.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String result;

        // check if buyer has enough souls
        if (actor.asSoul().getSouls() < this.getRequiredSouls()) {
            result = actor + " does not have enough souls to buy " + item;
            return result;
        }
        else {
            // set required souls to transfer
            actor.asSoul().transferSouls(this.getVendor().asSoul(), requiredSouls);
        }

        // swapping bought weapon with current weapon
        SwapWeaponAction swapWeaponAction = new SwapWeaponAction(item);
        swapWeaponAction.execute(actor, map);

        result = vendor + " sold " + getItem() + " to " + actor;

        return result;
    }

    /**
     * Returns a descriptive string of the items to be sold
     *
     * @param actor The actor that is purchasing.
     * @return The purchasing details
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " buys " + this.getItem() + " (" + this.getRequiredSouls() + " souls)";
    }

    /**
     * Setting the vendor
     *
     * @param vendor
     */
    public void setVendor(Actor vendor) {
        this.vendor = vendor;
    }

    /**
     * Setting the item to buy
     *
     * @param item
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * Setting the required souls to buy the item
     *
     * @param souls
     */
    public void setRequiredSouls(int souls) {
        this.requiredSouls = souls;
    }

    /**
     * Getting the vendor
     *
     * @return An actor that is the vendor
     */
    public Actor getVendor() {
        return vendor;
    }

    /**
     * Getting the item to be bought
     *
     * @return the item to be bought
     */
    public Item getItem() {
        return item;
    }

    /**
     * Getting the required souls to buy the item
     *
     * @return the required souls
     */
    public int getRequiredSouls() {
        return requiredSouls;
    }
}




