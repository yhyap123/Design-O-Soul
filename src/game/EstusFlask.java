package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Item;
import game.interfaces.Chargeable;
import game.interfaces.Resettable;

import java.util.List;

public class EstusFlask extends Item implements Resettable, Chargeable {

    private int MAX_CHARGE=3;
    private int charge;
    private Player player;
    private int healValue;

    /**
     * Constructor
     * @param player the player that has this item.
     */
    public EstusFlask(Player player) {
        super("Estus Flask", 'e', false);
        this.charge = MAX_CHARGE;
        this.player = player;
        registerInstance();
    }

    /**
     * Getter of allowable actions of Estus Flask
     *
     * Returns an unmodifiable copy of the actions list so that calling methods won't
     * be able to change what this Item can do without the Item checking.
     * @return an unmodifiable list of Actions
     */
    @Override
    public List<Action> getAllowableActions() {
        Actions allowableActions = new Actions();
        healValue = player.getMaximumHitPoints() * 40 / 100;
        allowableActions.add(new DrinkEstusFlaskAction(this, healValue));
        return allowableActions.getUnmodifiableActionList();
    }

    /**
     * Increase the number of charge of Estus Flask
     *
     * @param charges number of charges to be incremented.
     * @return transaction status. True if addition successful, otherwise False.
     */
    @Override
    public boolean addCharge(int charges){
        if (charges > 0 && this.charge < this.MAX_CHARGE){
            this.charge += charges;
            return true;
        }
        return false;
    }

    /**
     * Deduct the number of charge of Estus Flask.
     *
     * @param charges number of charges to be deducted
     * @return transaction status. True if subtraction successful, otherwise False.
     */
    @Override
    public boolean subtractCharge(int charges){
        if (charges > 0 && this.charge > 0){
            this.charge -= charges;
            return true;
        }
        return false;
    }

    /**
     * Getter of the number of charge of Estus Flask.
     * @return an integer represents the number of charge of Estus Flask
     */
    @Override
    public int getCharge(){
        return this.charge;
    }

    /**
     * To reset the number of charge of Estus Flask
     */
    @Override
    public void resetInstance() {
        this.charge= MAX_CHARGE;
    }

    /**
     * A useful method to clean up the list of instances in the ResetManager class
     * @return the existence of the instance in the game.
     * for example, true to keep it permanent, or false if instance needs to be removed from the reset list.
     */
    @Override
    public boolean isExist() {
        return true;
    }

    @Override
    public String toString(){
        return name + " (" + charge + "/" + MAX_CHARGE + ") ";
    }
}
