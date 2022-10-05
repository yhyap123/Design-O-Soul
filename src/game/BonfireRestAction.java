package game;

import edu.monash.fit2099.engine.*;

import java.util.HashMap;

/**
 * Rest Action method to perform resting in Bonfire
 * @see Action
 */
public class BonfireRestAction extends Action {
    private String name;
    private Bonfire bonfire;
    public BonfireRestAction(String name,Bonfire bonfire){
        this.name=name;
        this.bonfire=bonfire;
    }
    /**
     * Execute method that execute the rest action when the BonfireRestAction is called.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return String to indicate what currently execute.
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

        ResetManager.getInstance().run();
        return actor + " rest at Bonfire";
    }

    /**
     * Menu Description method.
     * @param actor The actor performing the action.
     * @return the string that will print in the console.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " rest at " + bonfire + "'s Bonfire ";
    }
}
