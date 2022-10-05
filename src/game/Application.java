package game;

import java.util.Arrays;
import java.util.List;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.FancyGroundFactory;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.World;
import edu.monash.fit2099.engine.Location;

/**
 * The main class for the Jurassic World game.
 *
 */
public class Application {

	public static void main(String[] args) {

			World world = new World(new Display());

			GameMap gameMap = ProfaneCapital.getInstance();
			world.addGameMap(gameMap);
			GameMap secMap= AnorLondo.getInstance();
			world.addGameMap(secMap);

			// Add Actors for Profane Capital
			Actor player = new Player("Unkindled (Player)", '@', 100);
			world.addPlayer(player, gameMap.at(38, 12));

			//Place Yhorm the Giant/boss in the map
			Location YhormInitialLocation = gameMap.at(6, 25);
			Enemy Yhorm = new Yhorm("Yhorm the Giant", 'Y', 500, YhormInitialLocation);
			YhormInitialLocation.addActor(Yhorm);

			// Place Storm Ruler in the map
			gameMap.at(7, 25).addItem(new StormRuler(player));

			// Place Cemeteries in the map
			gameMap.at(32, 7).setGround(new Cementery());
			gameMap.at(27, 14).setGround(new Cementery());
			gameMap.at(49, 12).setGround(new Cementery());
			gameMap.at(58, 5).setGround(new Cementery());
			gameMap.at(36, 15).setGround(new Cementery());

			// Place Skeleton
			Location skeleton1InitialLocation = gameMap.at(50, 15);
			Enemy skeleton1 = new Skeleton("Skeleton", skeleton1InitialLocation);
			skeleton1InitialLocation.addActor(skeleton1);

			Location skeleton2InitialLocation = gameMap.at(20, 4);
			Enemy skeleton2 = new Skeleton("Skeleton", skeleton2InitialLocation);
			skeleton2InitialLocation.addActor(skeleton2);

			Location skeleton3InitialLocation = gameMap.at(45, 18);
			Enemy skeleton3 = new Skeleton("Skeleton", skeleton3InitialLocation);
			skeleton3InitialLocation.addActor(skeleton3);

			Location skeleton4InitialLocation = gameMap.at(22, 16);
			Enemy skeleton4 = new Skeleton("Skeleton", skeleton4InitialLocation);
			skeleton4InitialLocation.addActor(skeleton4);

			Location skeleton5InitialLocation = gameMap.at(44, 8);
			Enemy skeleton5 = new Skeleton("Skeleton", skeleton5InitialLocation);
			skeleton5InitialLocation.addActor(skeleton5);

			Location skeleton6InitialLocation = gameMap.at(34, 6);
			Enemy skeleton6 = new Skeleton("Skeleton", skeleton6InitialLocation);
			skeleton6InitialLocation.addActor(skeleton6);

			gameMap.at(38,25).setGround(new FogDoor(secMap));
			secMap.at(38, 0).setGround(new FogDoor(gameMap));

			// Place Bonfire at the center of the map
			BonfireManager bn = BonfireManager.getInstance();
			bn.addBonfire();
			// Place Fire Keeper (Vendor) besides Bonfire
			gameMap.at(37,11).addActor(new Vendor());

			// Place chest
			Location chest1InitialLocation = gameMap.at(24, 8);
			Chest chest1 = new Chest(chest1InitialLocation);
			chest1InitialLocation.addActor(chest1);

			Location chest2InitialLocation = gameMap.at(50, 18);
			Chest chest2 = new Chest(chest2InitialLocation);
			chest2InitialLocation.addActor(chest2);

			Location chest3InitialLocation = gameMap.at(13, 19);
			Chest chest3 = new Chest(chest3InitialLocation);
			chest3InitialLocation.addActor(chest3);

			Location chest4InitialLocation = gameMap.at(8, 20);
			Chest chest4 = new Chest(chest4InitialLocation);
			chest4InitialLocation.addActor(chest4);

			// Add actors to Anor Londo
			//Place Aldrich the Giant/boss in the map
			Location AldrichInitialLocation = secMap.at(32, 11);
			Enemy Aldrich = new Aldrich("Aldrich the Devourer", 'A', 350, AldrichInitialLocation);
			AldrichInitialLocation.addActor(Aldrich);

			// Add cementery for undead
			secMap.at(32, 4).setGround(new Cementery());
			secMap.at(27, 14).setGround(new Cementery());
			secMap.at(49, 12).setGround(new Cementery());
			secMap.at(58, 5).setGround(new Cementery());
			secMap.at(36, 15).setGround(new Cementery());


			// Place chests
			chest1InitialLocation = secMap.at(24, 8);
			chest1 = new Chest(chest1InitialLocation);
			chest1InitialLocation.addActor(chest1);

			chest2InitialLocation = secMap.at(50, 18);
			chest2 = new Chest(chest2InitialLocation);
			chest2InitialLocation.addActor(chest2);

			chest3InitialLocation = secMap.at(13, 19);
			chest3 = new Chest(chest3InitialLocation);
			chest3InitialLocation.addActor(chest3);

			chest4InitialLocation = secMap.at(6, 20);
			chest4 = new Chest(chest4InitialLocation);
			chest4InitialLocation.addActor(chest4);

			// run the game
			world.run();
	}
}
