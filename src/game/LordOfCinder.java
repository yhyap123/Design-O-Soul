package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.enums.Status;
import game.interfaces.Resettable;
import game.interfaces.Soul;

/**
 * The boss of Design o' Souls
 */
public abstract class LordOfCinder extends Enemy {
    private EnrageBehaviour enrageBehaviour;

    /**
     * Constructor.
     */
    public LordOfCinder(String name, char displayChar, int hitPoints, Location initialLocation, int souls) {
        super(name, displayChar, hitPoints, initialLocation, souls);
    }

    /**
     * Adding Lord of Cinder's enrage behaviour to the behaviours list
     *
     */
    public void addEnrageBehaviour() {
        if (this.getEnrageBehaviour() == null) {
            this.setEnrageBehaviour(new EnrageBehaviour());
            this.getBehaviours().add(0, new EnrageBehaviour());
        }
    }

    /**
     * Resetting Lord Of Cinder
     *
     */
    @Override
    public void resetInstance() {
        this.getBehaviours().clear();

        // remove FollowBehaviour
        this.setFollowBehaviour(null);

        // remove EnrageBehaviour
        this.setEnrageBehaviour(null);
        this.removeCapability(Status.RAGE_MODE);

        // reset location
        this.getInitialLocation().map().moveActor(this, this.getInitialLocation());
        this.hitPoints = this.getMaxHitPoints();
    }

    /**
     * Setting Lord Of Cinder's enrage behaviour
     *
     * @param enrageBehaviour the enrage behaviour of the Lord Of Cinder
     */
    public void setEnrageBehaviour(EnrageBehaviour enrageBehaviour) {
        this.enrageBehaviour = enrageBehaviour;
    }

    /**
     * Getting Lord Of Cinder EnrageBehaviour
     *
     * @return Lord Of Cinder's EnrageBehaviour
     */
    public EnrageBehaviour getEnrageBehaviour() {
        return this.enrageBehaviour;
    }
}
