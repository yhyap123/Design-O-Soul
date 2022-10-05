package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.enums.Status;

/**
 * The class that handles the action when the Actor is dying.
 */
public class DyingAction extends Action {
    private Location location;
    private int soul;
    private Actor target;
    private Location previousLocation;
    private TokenOfSoul tokenOfSoul;
    private TokenOfSoul previousTokenOfSoul;

    /**
     *
     * @param location
     * @param soul
     * @param previousLocation
     * @param target
     * @param tokenOfSoul
     * @param previousTokenOfSoul
     * Bind all the parameter with the class variable
     */
    public DyingAction(Location location,int soul,Location previousLocation,Actor target,TokenOfSoul tokenOfSoul,TokenOfSoul previousTokenOfSoul){
        this.previousLocation=previousLocation;
        this.location=location;
        this.soul=soul;
        this.tokenOfSoul=tokenOfSoul;
        this.previousTokenOfSoul=previousTokenOfSoul;
        this.target=target;
    }

    /**
     * Core of the class that handle few implementations of the dying mechanism:
     * When player dies, it will remove from the map and drop tokenOfSoul and also reset.
     * When enemy dies, it will transfer soul to the target(Player) and remove from map.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return String indicate the current action.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_BLUE = "\u001B[34m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_RED = "\u001B[31m";

        Display display = new Display();
        String res = actor + " is dead";

        if (actor.hasCapability(Status.RANDOM_DEAD)) {
            map.removeActor(actor);
            return actor.toString() + " died instantly by chance";
        }

        if (actor.hasCapability(Status.IS_PLAYER) && target.hasCapability(Status.IS_PLAYER)) {
            display.println(ANSI_RED+
                    "------------    ------      ********   ------------        --------   ---    ---  ------------ -----------\n"+ANSI_RESET+
                    ANSI_BLUE+"************   ********    ----------  ************       **********  ***    ***  ************ ***********\n"+ANSI_RESET+
                    ANSI_BLUE+"----          ----------  ************ ----              ----    ---- ---    ---  ----         ----    ---\n"+ANSI_RESET+
                    ANSI_RED+"****  ****** ****    **** ---  --  --- ************      ***      *** ***    ***  ************ *********\n"+ANSI_RESET+
                    ANSI_RED+"----  ------ ------------ ***  **  *** ------------      ---      --- ---    ---  ------------ ---------\n"+ANSI_RESET+
                    ANSI_BLUE+"****    **** ************ ---  --  --- ****              ****    ****  ********   ****         ****  ****\n"+ANSI_RESET+
                    ANSI_BLUE+"------------ ----    ---- ***  **  *** ------------       ----------    ------    ------------ ----   ----\n"+ANSI_RESET+
                    ANSI_RED+"************ ****    **** ---      --- ************        ********      ****     ************ ****    ****"+ANSI_RESET);
            BonfireManager bm = BonfireManager.getInstance();
            map.moveActor(actor,bm.getLastInteract());
            if (previousTokenOfSoul != null) {
                previousTokenOfSoul.getLocation().removeItem(previousTokenOfSoul);
            }
            tokenOfSoul.setSouls(soul);
            if (location.getGround().hasCapability(Status.IS_VALLEY)) {
                tokenOfSoul.setLocation(previousLocation);
                previousLocation.addItem(tokenOfSoul);
            } else {
                tokenOfSoul.setLocation(location);
                location.addItem(tokenOfSoul);
            }
            actor.asSoul().subtractSouls(soul);

            ResetManager.getInstance().run();
        }

        else {
            System.out.println(actor + ", " + target);

            if (target.hasCapability(Abilities.REVIVE_FOR_ONCE)) {
                // scan normal exits for player to transfer soul
                this.scanExitsTransferSoul();

                // scan expanded exits for player to transfer soul
                this.scanExpandedExitsTransferSoul(map, actor);
            }
            else {
                target.asSoul().transferSouls(actor.asSoul());
            }

            Actions dropActions = new Actions();

            // drop all items
            for (Item item : target.getInventory()) {
                if (target.hasCapability(Status.IS_MIMIC)) {
                    item.asSoul().setSouls(100);
                    dropActions.add(new DropItemAction(item));
                }
                else {
                    dropActions.add(item.getDropAction(actor));
                }
            }

            for (Action drop : dropActions) {
                drop.execute(target, map);
            }

            map.removeActor(target);

            if (target.hasCapability(Status.IS_YHORM) || target.hasCapability(Status.IS_ALDRICH)){
                display.println(ANSI_YELLOW+
                        "----           --------   -----------  ----------          --------   ------------      ------------ --------  ----    ---- ----------   ------------ -----------\n"+
                        "****          **********  ***********  ************       **********  ************      ************ ********  *****   **** ************ ************ ***********\n"+
                        "----         ----    ---- ----    ---  --        --      ----    ---- ----              ---            ----    ------  ---- --        -- ----         ----    ---\n"+
                        "****         ***      *** *********    **        **      ***      *** ************      ***            ****    ************ **        ** ************ *********\n"+
                        "----         ---      --- ---------    --        --      ---      --- ------------      ---            ----    ------------ --        -- ------------ ---------\n"+
                        "************ ****    **** ****  ****   **        **      ****    **** ****              ***            ****    ****  ****** **        ** ****         ****  ****\n"+
                        "------------  ----------  ----   ----  ------------       ----------  ----              ------------ --------  ----   ----- ------------ ------------ ----   ----\n"+
                        "************   ********   ****    **** **********          ********   ****              ************ ********  ****    **** **********   ************ ****    ****\n"+ANSI_RESET);
            }
        }

        return res;
    }

    /**
     * Scan normal exits for the player to transfer souls
     *
     */
    public void scanExitsTransferSoul() {
        for (Exit exit: location.getExits()) {
            if (exit.getDestination().containsAnActor()) {
                Actor exitActor = exit.getDestination().getActor();
                if (exit.getDestination().getActor().hasCapability(Status.IS_PLAYER)) {
                    target.asSoul().transferSouls(exitActor.asSoul());
                    break;
                }
            }
        }
    }

    /**
     * Scan expanded exits for the player to transfer souls
     *
     * @param map the current map of the actor is at
     * @param actor the actor that is transferring souls to the target
     */
    public void scanExpandedExitsTransferSoul(GameMap map, Actor actor) {
        Location here = map.locationOf(actor);
        NumberRange xs, ys;
        int range = 3;
        xs = new NumberRange(here.x() - range, range * 2 + 1);
        ys = new NumberRange(here.y() - range, range * 2 + 1);

        // scanning surroundings
        for (int x: xs) {
            for (int y: ys) {
                if (map.getXRange().contains(x) && map.getYRange().contains(y)) {
                    if (!(x == here.x() && y == here.y()) && map.at(x, y).containsAnActor()){
                        Actor target = map.at(x, y).getActor();

                        // check if target is Player
                        if (target.hasCapability(Status.IS_PLAYER)){
                            actor.asSoul().transferSouls(target.asSoul());
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Menu Description method
     * @param actor The actor performing the action.
     * @return null
     */
    @Override
    public String menuDescription(Actor actor) {
        return null;
    }


}
