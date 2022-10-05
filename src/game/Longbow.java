package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.WeaponAction;
import game.enums.*;
import game.interfaces.Resettable;

import java.util.List;

public class Longbow extends RangedWeapon implements Resettable {

    /**
     * Constructor.
     */
    public Longbow(Actor actor) {
        super("Darkmoon Longbow", '8', 70, "pierces", 80, 3, 15);
        this.actor = actor;
        this.criticalStrikeRate = 15;
        registerInstance();
    }

    /**
     * Getter of the allowable actions of Darkmoon Longbow.
     * e.g. FireArrowAction.
     *
     * Returns an unmodifiable copy of the actions list so that calling methods won't.
     * be able to change what this Item can do without the Item checking.
     * @return an unmodifiable list of Actions.
     */
    @Override
    public List<Action> getAllowableActions() {
        Actions allowableActions = new Actions();

        if (actor.hasCapability(Status.IS_PLAYER)) {
            allowableActions.add(new FireArrowAction(this));
        }
        allowableActions.add(super.getAllowableActions());
        return allowableActions.getUnmodifiableActionList();
    }

    /**
     * Get an action or skill from Darkmoon Longbow.
     * e.g FireArrowAction
     *
     * @param target the target actor.
     * @param direction the direction of target, e.g. "north".
     * @return null by default because a weapon doesn't have any active skill. Otherwise, return a WeaponAction instance.
     */
    @Override
    public WeaponAction getActiveSkill(Actor target, String direction) {
        return new FireArrowAction(this);
    }

    /**
     * Add/remove the fire ability of the holder of this weapon.
     * Inform a carried Item of the passage of time.
     * This method is called once per turn, if the Item is being carried.
     *
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor           The actor carrying this Item.
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {
        if (this.hasCapability(Abilities.FIRE))
            actor.addCapability(Abilities.FIRE);
        else
            actor.removeCapability(Abilities.FIRE);
        super.tick(currentLocation, actor);
    }

    @Override
    public String toString(){
        if (this.hasCapability(Abilities.FIRE))
            return name +  " (ACTIVATED)";
        else
            return name +  " (DEACTIVATED)";
    }

    /**
     * Reset the active skill of Darkmoon Longbow.
     */
    @Override
    public void resetInstance() {
        this.removeCapability(Abilities.FIRE);
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
}
