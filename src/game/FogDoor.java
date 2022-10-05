/**
 * Fog door class (=) to help player to move to between the maps.
 */
package game;

import edu.monash.fit2099.engine.*;
import game.enums.Status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FogDoor extends Ground {
     private GameMap nextMap;

    /**
     * Constructors
     */
    public FogDoor(){
        super('=');
    }
    public FogDoor(GameMap nextMap) {
        super('=');
        this.nextMap=nextMap;
    }

    /**
     * add the move actor action into the action to move actor to another map
     * @param actor the Actor acting
     * @param location the current Location
     * @param direction the direction of the Ground from the Actor
     * @return
     */
    @Override
    public Actions allowableActions(Actor actor, Location location, String direction) {
        Location location1 =null;
        String hotkey="" ;
        if (location.y()==0){
            location1= nextMap.at(location.x(),25);
            hotkey = "8";
        }
        else{
            location1= nextMap.at(location.x(), 0);
            hotkey="2";
        }
        Actions actions=new Actions();
        actions.add(new MoveActorAction(location1,nextMap.toString(),hotkey));
        return actions;
    }

    /**
     * To ensure only player can enter the second map by fog door.
     * @param actor the Actor to check
     * @return
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        if(actor.hasCapability(Status.IS_PLAYER)){
            return true;
        }
        else{
            return false;
        }
    }
}

