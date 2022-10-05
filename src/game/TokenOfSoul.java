package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.enums.Status;
import game.interfaces.Soul;

import java.util.ArrayList;
import java.util.List;

/***
 * Class for Token Of Soul
 */
public class TokenOfSoul extends Item implements Soul {
    private Actor actor;
    private int soul;
    private Location location;

    /**
     * Constructor
     * @param name
     * @param displayChar
     * @param portable
     * @param actor
     * @see Abilities
     */
    public TokenOfSoul(String name, char displayChar, boolean portable, Actor actor) {
        super(name, displayChar, portable);
        this.actor = actor;
        this.addCapability(Abilities.DROP);
    }

    /**
     * getAllowableAction to get the pick up action when player approach it.
     * @return An action list the contain pick up Action
     */
    @Override
    public List<Action> getAllowableActions() {
        List<Action> pickup = new ArrayList<Action>();
        pickup.add(new PickUpTokenAction(this));
        return pickup;
    }

    @Override
    public DropItemAction getDropAction(Actor actor) {
        if (!actor.isConscious()) {
            return new DropItemAction(this);
        }
        return null;
    }

    /**
     * This methos is use to manage the process of the transfer of soul between actor(souls)
     * @param soulObject a target souls.
     */
    @Override
    public void transferSouls(Soul soulObject) {
        soulObject.addSouls(getSouls());
        subtractSouls(getSouls());
    }

    /**
     * Substract soul method
     * @param soul
     * @return boolean that indicate whether the subtraction is successful.
     */
    @Override
    public boolean subtractSouls(int soul) {
        boolean isSuccess = false;
        if (soul > 0) {
            this.soul -= soul;
            isSuccess = true;
        }
        else {
            isSuccess = false;
        }
        return isSuccess;
    }

    /**
     * Setter
     * @param souls
     */
    @Override
    public void setSouls(int souls) {
        this.soul=souls;
    }

    /**
     * Getter
     * @return soul
     */
    @Override
    public int getSouls() {
        return soul;
    }

    /**
     * Location Getter
     * @return location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Location Setter
     * @param location
     */
    public void setLocation(Location location) {
        this.location = location;
    }
}
