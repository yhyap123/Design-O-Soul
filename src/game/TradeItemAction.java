package game;

import edu.monash.fit2099.engine.*;
import game.enums.Status;

import java.util.HashMap;

/**
 * The action to be performed when trading items with Vendor
 *
 */
public class TradeItemAction extends Action {
    private Actor vendor;
    private Item tradeItem;
    private Item returnItem;

    /**
     * Constructor.
     *
     * @param vendor the Actor to sell, trade items
     * @param item the item that is used for trading
     */
    public TradeItemAction(Actor vendor, Item item) {
        this.vendor = vendor;
        this.tradeItem = item;
    }

    /**
     * Perform the trading action.
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return A description of the result of the trading.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String result;

        // check the type of item that is being traded
        this.checkTradeItem(tradeItem, actor);

        // removing item that is used to trade
        vendor.addItemToInventory(tradeItem);
        actor.removeItemFromInventory(tradeItem);

        // swapping traded weapon with current weapon
        SwapWeaponAction swapWeaponAction = new SwapWeaponAction(this.getReturnItem());
        swapWeaponAction.execute(actor, map);

        result = vendor + " traded " + this.getReturnItem() + " to " + actor;

        return result;
    }

    /**
     * Check the item that is used to trade and determine the return item.
     *
     * @param tradeItem The map the actor is on.
     * @param actor The actor performing the action.
     */
    public void checkTradeItem(Item tradeItem, Actor actor) {
        // check trade item
        if (tradeItem.hasCapability(Status.IS_YHORM)) {
            GreatMachete greatMachete = new GreatMachete(actor);
            this.setReturnItem(greatMachete);
        }
        else if (tradeItem.hasCapability(Status.IS_ALDRICH)) {
            Longbow longBow = new Longbow(actor);
            this.setReturnItem(longBow);
        }
    }

    /**
     * Returns a descriptive string
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " trades " + tradeItem;
    }

    /**
     * Setting the trading item
     *
     * @param item the item to set
     */
    public void setReturnItem(Item item) {
        this.returnItem = item;
    }

    /**
     * Getting the trading item
     *
     * @return the traded item
     */
    public Item getReturnItem() {
        return returnItem;
    }
}
