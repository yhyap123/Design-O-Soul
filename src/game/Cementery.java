package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

import java.util.ArrayList;
import java.util.Random;

/**
 * The class for the Cementery on map that responsible for the spawning of the undeads
 * @see Ground
 */
public class Cementery extends Ground {
    /**
     * Constructor
     */
    public Cementery(){
        super('c');
    }

    /**
     * void method of tick that will random generate the undead object and put it on the game map.
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location){
        Random rand= new Random();
        if (rand.nextInt(100)<=25){
            if (!location.containsAnActor()){
                location.map().addActor(new Undead("Undead",location),location);
            }
        }
    }
}
