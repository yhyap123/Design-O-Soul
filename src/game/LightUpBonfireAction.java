/**
 * Class that extend Action to enlight Bonfire and update last interact bonfire.
 *  @see edu.monash.fit2099.engine.Action
 */
package game;
import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import java.util.HashMap;

public class LightUpBonfireAction extends Action {
    private Bonfire bonfire;
    public LightUpBonfireAction(Bonfire bonfire) {
        this.bonfire=bonfire;
    }

    /**
     * <ul>
     *     <li>Execute method from Action class</li>
     *     <li>To update the lastInteract bonfire</li>
     *     <li>To activate the Bonfire</li>
     * </ul>
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        BonfireManager bm = BonfireManager.getInstance();
        HashMap<Location,Bonfire> bonfires= bm.getBonfires();
        for (Location key: bonfires.keySet()){
            if (bonfires.get(key)==bonfire){
                bm.updateLastInteract(key.map().at(key.x(),key.y()+1));
            }
        }
        bonfire.activateBonfire();
        return "Bonfire lighten";
    }

    /**
     * Menu Description method to return the string return in menu.
     * @param actor The actor performing the action.
     * @return
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Enlight Bonfire";
    }
}
