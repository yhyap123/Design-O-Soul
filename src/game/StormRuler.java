package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.PickUpItemAction;
import edu.monash.fit2099.engine.WeaponAction;
import game.enums.*;

import java.util.List;


public class StormRuler extends Sword {

    private int numberOfCharge;
    private int MAX_NUMBER_OF_CHARGE = 3;
    private Actor actor;

    /**
     * Constructor.
     *
     * @param actor the holder of this weapon.
     */
    public StormRuler(Actor actor) {
        super("Storm Ruler", '7', 70, "strikes", 60, 20);
        this.numberOfCharge = 0;
        this.actor = actor;
        if (!actor.hasCapability(Status.IS_PLAYER)){
            display.println(actor + " cannot equip " + this);
        }
    }

    /**
     * Accessor for damage done by this weapon.
     *
     * If this weapon is in DULLNESS state, damage reduced by half.
     *
     * @return the damage.
     */
    @Override
    public int damage(){
        int damage = super.damage();
        if (this.hasCapability(Status.DULLNESS)){
            damage /= 2;
        }
        return damage;
    }

    /**
     * Getter of the allowable actions of StormRuler.
     * e.g. ChargeAction
     *
     * Returns an unmodifiable copy of the actions list so that calling methods won't.
     * be able to change what this Item can do without the Item checking.
     * @return an unmodifiable list of Actions.
     */
    @Override
    public List<Action> getAllowableActions() {
        Actions allowableActions = new Actions();
        if (this.hasCapability(Status.IS_PICKED_UP)) {
            if (!this.hasCapability(Status.FULLY_CHARGED) && !this.hasCapability(Status.CHARGING)){
                allowableActions.add(new ChargeAction(this));
            }
        }
        return allowableActions.getUnmodifiableActionList();
    }

    /**
     * Get an action or skill from StormRuler.
     * If StormRuler is fully charged and Yhorm is nearby, return WindSlashAction.
     * If target is not WEAK_TO_YHORM, change StormRuler to DULLNESS state.
     *
     * @param target the target actor.
     * @param direction the direction of target, e.g. "north".
     * @return null by default because a weapon doesn't have any active skill. Otherwise, return a WeaponAction instance.
     */
    @Override
    public WeaponAction getActiveSkill(Actor target, String direction){

        if (target.hasCapability(Status.WEAK_TO_STORMRULER)){
            this.removeCapability(Status.DULLNESS);
        }

        // return WindSlashAction by Yhorm
        if (target.hasCapability(Status.IS_YHORM) && numberOfCharge == MAX_NUMBER_OF_CHARGE){
            return new WindSlashAction(this, target);
        }

        return null;
    }

    /**
     * Create and return an action to pick this Item up only to player.
     *
     * @param actor an actor that will interact with this item.
     * @return a new PickUpItemAction if this Item is portable, null otherwise.
     */
    @Override
    public PickUpItemAction getPickUpAction(Actor actor) {
        if(actor.hasCapability(Status.IS_PLAYER)) {
            return new SwapWeaponAction(this);
        }
        return null;
    }

    /**
     * Inform the StormRuler to charge its weapon.
     * This method is called once per turn, if the Item is being carried.
     *
     * @param currentLocation The location of the ground on which we lie.
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {
        this.addCapability(Status.DULLNESS);
        if (this.hasCapability(Status.CHARGING)){
            numberOfCharge += 1;
            if (numberOfCharge == MAX_NUMBER_OF_CHARGE) {
                this.removeCapability(Status.CHARGING);
                this.addCapability(Status.FULLY_CHARGED);
                actor.removeCapability(Status.DISARMED);
            }
        }

        if (this.hasCapability(Abilities.RESET_CHARGE)){
            numberOfCharge = 0;
            this.removeCapability(Abilities.RESET_CHARGE);
        }
    }

    @Override
    public String toString(){
        if (this.hasCapability(Status.CHARGING))
            return name + " (" + numberOfCharge + "/" + MAX_NUMBER_OF_CHARGE + ") " + "(CHARGING)";
        else if (this.hasCapability(Status.FULLY_CHARGED))
            return name + " (" + numberOfCharge + "/" + MAX_NUMBER_OF_CHARGE + ") " + "(FULLY_CHARGED)";
        else
            return name + " (" + numberOfCharge + "/" + MAX_NUMBER_OF_CHARGE + ") ";
    }
}

