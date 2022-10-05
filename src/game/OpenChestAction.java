package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.DropItemAction;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

import java.util.Random;

public class OpenChestAction extends Action {

    private Actor chest;
    private String direction;
    private Random rand = new Random();

    /**
     * Constructor.
     *
     * @param chest      the Chest to be opened
     * @param direction  string representing the direction of the other Actor that performing this action
     */
    public OpenChestAction(Actor chest, String direction){
        this.chest = chest;
        this.direction = direction;
    }

    /**
     * Perform the OpenChestAction.
     * 50% chance to drop tokens of soul.
     * 50% chance to turn into a mimic.
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String result = menuDescription(actor);
        Location here = map.locationOf(chest);
        Actions actions = new Actions();
        boolean luck = rand.nextBoolean();

        if (luck){
            for (Item item: chest.getInventory()){
                actions.add(new DropItemAction(item));
            }
            for (Action action: actions){
                result += System.lineSeparator() + action.execute(chest, map);
            }
            map.removeActor(chest);
        }
        else{
            map.removeActor(chest);
            here.addActor(new Mimic("Mimic", here));
            result += System.lineSeparator() + "Oh no!! "+ chest + " turns into mimic.";
        }

        return result;
    }

    /**
     * Returns a descriptive string
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " opens the Chest at " + direction;
    }

}
