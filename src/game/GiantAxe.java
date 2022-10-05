package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;

import java.util.List;

public class GiantAxe extends Axe {

    public GiantAxe(){
        super("Giant Axe", 'A', 50, "axes", 80);
    }

    /**
     * Getter of the allowable actions of Giant Axe.
     * e.g. SpinAttackAction
     *
     * Returns an unmodifiable copy of the actions list so that calling methods won't.
     * be able to change what this Item can do without the Item checking.
     * @return an unmodifiable list of Actions.
     */
    @Override
    public List<Action> getAllowableActions() {
        Actions allowableActions = new Actions();
        allowableActions.add(new SpinAttackAction(this));
        return allowableActions.getUnmodifiableActionList();
    }
}
