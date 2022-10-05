package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.enums.Status;

/**
 * The class that handle the pick up token action and add the soul directly to actor's soul.
 * @see Action
 */
public class PickUpTokenAction extends Action {
    private TokenOfSoul tokenOfSoul;
    public PickUpTokenAction(TokenOfSoul tokenOfSoul) {
        this.tokenOfSoul=tokenOfSoul;
    }

    /**
     * Action execute method that remove token of soul from the map and transfer the soul to actor's soul.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return string that indicate the current action.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        map.locationOf(actor).removeItem(tokenOfSoul);
        tokenOfSoul.transferSouls(actor.asSoul());
        return actor.toString()+" picked up Token of Soul";
    }

    /**
     * Menu Description Method
     * @param actor The actor performing the action.
     * @return the string that will show in console
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor.toString()+" picks up Token of Soul";
    }
}
