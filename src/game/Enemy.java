package game;

import edu.monash.fit2099.engine.*;
import game.enums.Status;
import game.interfaces.Behaviour;
import game.interfaces.Resettable;
import game.interfaces.Soul;

import java.util.ArrayList;

/**
 * The Enemy class that covers all basic methods the enemy should have
 *
 */
public abstract class Enemy extends Actor implements Soul, Resettable {
    protected ArrayList<Behaviour> behaviours;
    private int souls;
    private FollowBehaviour followBehaviour;
    private Location initialLocation;

    /**
     *
     * A default constructor for each Enemy
     *
     * @param name The name of the Enemy
     * @param displayChar The character representation of the Enemy
     * @param hitPoints The hitpoints of the Enemy
     * @param initialLocation The initial location of the Enemy
     * @param souls The number of souls of the enemy
     */
    public Enemy(String name, char displayChar, int hitPoints, Location initialLocation, int souls) {
        super(name, displayChar, hitPoints);
        this.addCapability(Status.HOSTILE_TO_PLAYER);
        this.setSouls(souls);
        this.setInitialLocation(initialLocation);
        this.registerInstance();
        this.behaviours = new ArrayList<>();
    }

    /**
     * Select and return an action to perform on the current turn of the enemy.
     *
     * @param actions    collection of possible Actions for this Enemy
     * @param lastAction the Action this Enemy took last turn.
     * @param map        the map containing the Enemy
     * @param display    the I/O object to which messages may be written
     * @return the Action to be performed by the Enemy
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        // loop through all behaviours
        for(Behaviour Behaviour : behaviours) {
            Action action = Behaviour.getAction(this, map);
            if (action != null)
                return action;
        }
        return new DoNothingAction();
    }

    /**
     * Available actions that other actor can do to this Enemy
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  string representing the direction of the other Actor
     * @param map        current GameMap
     * @return list of actions
     * @see Status#HOSTILE_TO_ENEMY
     * @see Status#DISARMED
     */
    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        Actions actions = new Actions();

        // it can be attacked only by the HOSTILE opponent, and this action will not attack the HOSTILE enemy back.
        if(otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            // add behaviours for Enemy
            this.addBehaviour(otherActor);

            // check if player is in disarmed status
            if (!otherActor.hasCapability(Status.DISARMED)) {
                if (this.hasCapability(Status.IS_YHORM)) {
                    // add WeaponAction to Player
                    actions.add(otherActor.getWeapon().getActiveSkill(this, direction));
                }

                // add AttackAction to Player
                actions.add(new AttackAction(this, direction));
            }
        }
        return actions;
    }

    /**
     * Add all behaviour an Enemy should have
     *
     * @param otherActor The actor that the action should be performing on
     */
    public void addBehaviour(Actor otherActor) {
        if (getFollowBehaviour() == null) {
            // Add AttackBehaviour
            this.getBehaviours().add(0, new AttackBehaviour());

            // add FollowBehaviour
            this.setFollowBehaviour(new FollowBehaviour(otherActor));
            this.getBehaviours().add(1, this.getFollowBehaviour());
        }
    }

    /**
     * Resetting the Enemy
     *
     */
    @Override
    public void resetInstance() {
        // remove FollowBehaviour
        setFollowBehaviour(null);
        getBehaviours().clear();

        // reset location
        getInitialLocation().map().moveActor(this, initialLocation);
        this.hitPoints = getMaxHitPoints();
    }

    /**
     * Transfer souls to the other soul object
     *
     * @param soulObject a target souls.
     */
    @Override
    public void transferSouls(Soul soulObject) {
        soulObject.addSouls(this.getSouls());
        this.subtractSouls(this.getSouls());
    }

    /**
     * Check the existence ot this Enemy in the game
     *
     * @return the existence of the instance in the game.
     */
    @Override
    public boolean isExist() {
        return true;
    }

    /**
     * Subtracting souls from this Enemy
     *
     * @param souls number souls to be deducted
     * @return Whether the process it successful
     */
    @Override
    public boolean subtractSouls(int souls) {
        boolean isSuccess = false;
        if (souls > 0) {
            this.souls -= souls;
            isSuccess = true;
        }
        else {
            isSuccess = false;
        }
        return isSuccess;
    }

    /**
     * Setting the number of souls of this Enemy
     *
     * @param souls The number of souls
     */
    @Override
    public void setSouls(int souls) {
        this.souls = souls;
    }

    /**
     * Getting the number of souls of this Enemy
     *
     * @return The number of souls
     */
    @Override
    public int getSouls() {
        return this.souls;
    }

    /**
     * Getting all behaviours of this Enemy
     *
     * @return A list of behaviours
     */
    public ArrayList<Behaviour> getBehaviours() {
        return behaviours;
    }

    /**
     * Setting the hitPoints of this Enemy
     *
     * @param hitPoints The hitPoints of this Enemy
     */
    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    /**
     * Getting the hitPoints of this Enemy
     *
     * @return The hitPoints of this Enemy
     */
    public int getHitPoints() {
        return this.hitPoints;
    }

    /**
     * Getting the maximum hitPoints of this Enemy
     *
     * @return The maximum hitPoints of this Enemy
     */
    public int getMaxHitPoints() {
        return this.maxHitPoints;
    }

    /**
     * Getting the initial location of this Enemy
     *
     * @return The initial location of this Enemy
     */
    public Location getInitialLocation() {
        return initialLocation;
    }

    /**
     * Setting the initial location of this Enemy
     *
     * @param initialLocation The initial location of this Enemy
     */
    public void setInitialLocation(Location initialLocation) {
        this.initialLocation = initialLocation;
    }

    /**
     * Getting the follow behaviour of this Enemy
     *
     * @return The follow behaviour of this Enemy
     */
    public FollowBehaviour getFollowBehaviour() {
        return followBehaviour;
    }

    /**
     * Setting the follow behaviour of this Enemy
     *
     * @param followBehaviour The follow behaviour of this Enemy
     */
    public void setFollowBehaviour(FollowBehaviour followBehaviour) {
        this.followBehaviour = followBehaviour;
    }

    /**
     * Output details of this Enemy
     *
     * @return A string of details of this Enemy
     */
    @Override
    public String toString() {
        String hitPointsStatus = "(" + this.hitPoints + "/" + this.maxHitPoints + ")";
        String weaponStatus = getWeapon().toString();
        String weaponStatusMessage = " (" + weaponStatus + ")";
        return (name + hitPointsStatus + weaponStatusMessage);
    }
}


