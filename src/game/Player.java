package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.enums.Status;
import game.interfaces.Resettable;
import game.interfaces.Soul;

/**
 * Class representing the Player.
 */
public class Player extends Actor implements Soul, Resettable {

	private final Menu menu = new Menu();
	private int souls;
	private Location previousLocation;
	private TokenOfSoul previousTokenOfSoul;
	private TokenOfSoul ts;

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
		this.addCapability(Status.IS_PLAYER);
		this.addCapability(Status.HOSTILE_TO_ENEMY);
		this.addCapability(Status.ABLE_TO_BUY);
		this.addCapability(Abilities.REST);
		this.addCapability(Abilities.DRINK);

		this.setSouls(0);
		this.addItemToInventory(new BroadSword());
		this.addItemToInventory(new EstusFlask(this));

		registerInstance();

	}

	/**
	 * Every single playturn will call this method to get the player's Action
	 *
	 * @param actions    collection of possible Actions for this Actor
	 * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
	 * @param map        the map containing the Actor
	 * @param display    the I/O object to which messages may be written
	 * @return the action object that player need to perform in this play turn.
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		if (!isConscious()) {
			ts = new TokenOfSoul("Token of Soul", '$', false, this);
			return new DyingAction(map.locationOf(this), souls, previousLocation, this, ts, previousTokenOfSoul);
		}
		this.previousTokenOfSoul = ts;
		this.previousLocation = map.locationOf(this);
		// Handle multi-turn Actions

		if (lastAction.getNextAction() != null) {
			return lastAction.getNextAction();
		}

		String holdWeaponMessage = ", holding " + getWeapon();
		String soulCountMessage = ", " + getSouls() + " souls";
		display.println(this + " (" + getHitPoints() + "/" + getMaximumHitPoints() + ")" + holdWeaponMessage + soulCountMessage);

		return menu.showMenu(this, actions, display);
	}

	/**
	 * @param otherActor the Actor that might be performing attack
	 * @param direction  String representing the direction of the other Actor
	 * @param map        current GameMap
	 * @return
	 */
	@Override
	public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
		Actions actions = new Actions();
		return actions;
	}

	/**
	 * @param soulObject a target souls.
	 */
	@Override
	public void transferSouls(Soul soulObject, int souls) {
		soulObject.addSouls(souls);
		subtractSouls(souls);
	}

	/**
	 * @param souls number of souls to be incremented.
	 * @return
	 */
	@Override
	public boolean addSouls(int souls) {
		boolean isSuccess = false;

		if (souls > 0) {
			this.setSouls(this.getSouls()+souls);
			isSuccess = true;
		}
		else {
			isSuccess = false;
		}

		return isSuccess;
	}

	/**
	 * @param souls number souls to be deducted
	 * @return
	 */
	@Override
	public boolean subtractSouls(int souls) {
		boolean isSuccess = false;

		if (this.souls >= souls) {
			this.souls -= souls;
			isSuccess = true;
		}
		else {
			isSuccess = false;
		}

		return isSuccess;
	}

	/**
	 * Setter for soul
	 * @param souls
	 */
	@Override
	public void setSouls(int souls) {
		this.souls = souls;
	}

	/**
	 * Getter of soul
	 * @return int soul
	 */
	@Override
	public int getSouls() {
		return this.souls;
	}

	/**
	 * Setter for hitPoints
	 * @param hitPoints
	 */
	public void setHitPoints(int hitPoints) {
		this.hitPoints = hitPoints;
	}

	/**
	 * Getter for hitPoints
	 * @return hitPoints
	 */
	public int getHitPoints() {
		return this.hitPoints;
	}

	/**
	 * @param maxHitPoints
	 */
	public void setMaxHitPoints(int maxHitPoints) {
		this.maxHitPoints = maxHitPoints;
	}

	/**
	 * @return
	 */
	public int getMaximumHitPoints() {
		return this.maxHitPoints;
	}

	/**
	 *
	 */
	@Override
	public void resetInstance() {
		this.hitPoints = this.getMaximumHitPoints();
	}

	/**
	 * @return
	 */
	@Override
	public boolean isExist() {
		return true;
	}
}
