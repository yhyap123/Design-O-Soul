package game;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import game.enums.Status;

import java.util.Random;

public class Chest extends Enemy {

    private Random rand = new Random();

    /**
     * Constructor.
     *
     * @param initialLocation the initial location of chest
     */
    public Chest(Location initialLocation) {
        super("Chest", '?', 9999, initialLocation, 200);
        this.setInitialLocation(initialLocation);
        addRandomTokenOfSouls();
        this.removeCapability(Status.HOSTILE_TO_PLAYER);
    }

    /**
     * Available actions that other actor can do to Chest.
     * e.g. OpenChestAction
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  string representing the direction of the other Actor
     * @param map        current GameMap
     * @return a list of actions
     */
    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        Actions actions = new Actions();

        // it can be opened only by the HOSTILE opponent
        if(otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            actions.add(new OpenChestAction(this, direction));
        }

        return actions;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Equip chest with random number of tokens of soul with a minimum of 1 and maximum of 3.
     */
    public void addRandomTokenOfSouls() {
        int randomNumber;
        randomNumber = rand.nextInt(3);

        for (int i = 0; i < randomNumber+1; i++) {
            TokenOfSoul tokenOfSouls = new TokenOfSoul("Token of Soul", '$', false, this);
            tokenOfSouls.setSouls(100);
            this.addItemToInventory(tokenOfSouls);
        }
    }

}
