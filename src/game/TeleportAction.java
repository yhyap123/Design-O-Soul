/**
 * Teleport Action class to manage the teleport function of bonfire.
 */
package game;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.MoveActorAction;


public class TeleportAction extends MoveActorAction {
    private Bonfire bonfire;

    /**
     * Constructor
     * @param moveToLocation
     * @param direction
     * @param bonfire
     */
    public TeleportAction(Location moveToLocation, String direction,Bonfire bonfire) {
        super(moveToLocation, direction);
        this.bonfire=bonfire;
    }

    /**
     * execute method to perform update last interacted bonfire and move actor.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        BonfireManager bm = BonfireManager.getInstance();
        bm.updateLastInteract(moveToLocation.map().at(moveToLocation.x(), moveToLocation.y()+1));
        return super.execute(actor, map);
    }
}
