package game;

import edu.monash.fit2099.engine.*;
import game.enums.Status;
import game.interfaces.Soul;

import java.util.HashMap;

/**
 * The Vendor for selling items
 */
public class Vendor extends Actor implements Soul {
    private HashMap<Item, Integer> items = new HashMap<>();

    /**
     * Constructor.
     *
     * Initialize actor essentials and add items to sell
     */
    public Vendor() {
        super("Fire Keeper", 'F', 0);
        items.put(new BroadSword(), 500);
        items.put(new GiantAxe(), 1000);
    }

    /**
     * Vendor do nothing at every turn
     *
     * @param actions    collection of possible Actions for this Vendor
     * @param lastAction The Action this Vendor took last turn
     * @param map        the map containing the Vendor
     * @param display    the I/O object to which messages may be written
     * @return do nothing action
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        return new DoNothingAction();
    }

    /**
     * Getting all available options of items that could be bought from this Vendor
     *
     * @param otherActor the Actor that might be buying
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return list of actions
     * @see Status#ABLE_TO_BUY
     */
    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        Actions actions = new Actions();

        // check if actor can buy items
        if (otherActor.hasCapability(Status.ABLE_TO_BUY)) {

            // get available weapons to buy
            for (HashMap.Entry<Item, Integer> item: this.getItems().entrySet()){
                actions.add(new BuyItemAction(this, item.getKey(), item.getValue()));
            }

            // add action to buy increase maximum hp
            actions.add(new BuyIncreaseMaxHPAction(this));

            // add actions to trade Cinder Of Lord
            for (Item item: otherActor.getInventory()) {
                if (item.hasCapability(Status.TRADABLE)) {
                    actions.add(new TradeItemAction(this, item));
                }
            }
        }

        return actions;
    }

    /**
     * Getting all item to be sold
     *
     * @return Items to be sold
     */
    public HashMap<Item, Integer> getItems() {
        return items;
    }

    /**
     * Adding souls to Vendor
     *
     * @param souls number of souls to be incremented.
     * @return wheter the process is successful
     */
    @Override
    public boolean addSouls(int souls) {
        boolean isSuccess = false;
        if (souls > 0) {
            setSouls(getSouls()+souls);
            isSuccess = true;
        }
        else {
            isSuccess = false;
        }
        return isSuccess;
    }
}
