package game;


import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.enums.*;

/**
 * The class to handle the generation of the burned ground
 */
public class BurnedGround extends Ground {

    private int round;
    private Ground previousGround;
    private Actor actor;

    /**
     * Constructor.
     * @param previousGround previous ground before it was burned.
     */
    public BurnedGround(Ground previousGround, Actor actor){
        super('v');
        this.previousGround = previousGround;
        this.round = 0;
        this.actor = actor;
        this.addCapability(Abilities.BURN);
    }

    /**
     * Hurt the actor that stand on this ground.
     * Last for three rounds.
     *
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location){
        Display display = new Display();
        super.tick(location);

        if (round < 3) {
            if (location.containsAnActor()) {
                if (!location.getActor().hasCapability(Status.IMMUNE_TO_BURN)) {
                    Actor target = location.getActor();
                    target.hurt(25);

                    if (!target.isConscious() && (!target.hasCapability(Status.IS_PLAYER)) && (!target.hasCapability(Abilities.REVIVE_FOR_ONCE))) {
                        display.println(target + " is burned to death.");
                        DyingAction dyingAction = new DyingAction(location, actor.asSoul().getSouls(),null,target,null,null);
                        dyingAction.execute(actor, location.map());
                    }
                }
            }
        }

        round++;
        if (round == 4){
            location.setGround(previousGround);
        }
    }
}
