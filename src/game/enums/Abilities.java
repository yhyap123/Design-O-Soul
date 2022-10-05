package game.enums;

/**
 * Enum that represents an ability of Actor, Item, or Ground.
 */
public enum Abilities {
    REVIVE_FOR_ONCE,  // Use this ability if the actor can revive once
    CRITICAL_STRIKE,  // Use this ability if the actor has a critical strike
    BURN,             // Use this ability if the ground can be burnt
    DROP,             // Use this ability if the item can be dropped
    REST,             // Use this ability if the actor can rest
    DRINK,            // Use this ability if the actor can drink
    RESET_CHARGE,     // Use this ability to reset the charge of StormRuler
    FIRE              // Use this ability if the attack of weapon can burn ground
}
