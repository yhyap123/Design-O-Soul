package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.WeaponAction;
import game.enums.Status;

import java.util.List;

public class GreatMachete extends Axe{

    protected Actor actor;

    /**
     * Constructor.
     *
     * @param actor the holder of this weapon
     */
    public GreatMachete(Actor actor){
        super("Yhorm's Great Machete", 'M', 95, "smashes", 60);
        this.actor = actor;
    }

    /**
     * A chance to hit the target (this is a dividend part of percentage).
     * If the holder is in RAGE_MODE, hit rate of Great Machete will increase 30%.
     *
     * @return the chance, in integer for probability with nextInt(), e.g 100 = 100% hit rate.
     */
    @Override
    public int chanceToHit(){
        int hitRate = this.hitRate;
        if (actor.hasCapability(Status.RAGE_MODE)){
            hitRate += 30;
        }
        return hitRate;
    }

    /**
     * Getter of the allowable actions of Great Machete.
     * e.g. BurnGroundAction
     *
     * Returns an unmodifiable copy of the actions list so that calling methods won't.
     * be able to change what this Item can do without the Item checking.
     * @return an unmodifiable list of Actions.
     */
    @Override
    public List<Action> getAllowableActions() {
        Actions allowableActions = new Actions();
        if (actor.hasCapability(Status.IS_PLAYER)) {
            allowableActions.add(new BurnGroundAction(this));
        }
        return allowableActions.getUnmodifiableActionList();
    }

    /**
     * Getter of active skills of Great Machete.
     * e.g. BurnGroundAction.
     *
     * Returns an unmodifiable copy of the actions list so that calling methods won't
     * be able to change what this Item can do without the Item checking.
     * @return an unmodifiable list of Actions
     */
    @Override
    public WeaponAction getActiveSkill(Actor target, String direction) {
        return new BurnGroundAction(this);
    }
}
