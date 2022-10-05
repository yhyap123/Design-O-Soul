package game;

import edu.monash.fit2099.engine.Ground;

import game.enums.Abilities;

/**
 * A class that represents bare dirt.
 */
public class Dirt extends Ground {
	/**
	 * Constructor for Dirt
	 */
	public Dirt() {
		super('.');
		this.addCapability(Abilities.BURN);
	}
}
