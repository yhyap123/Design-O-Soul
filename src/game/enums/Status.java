package game.enums;

/**
 * Use this enum class to give `buff` or `debuff`.
 * It is also useful to give a `state` to abilities or actions that can be attached-detached.
 */
public enum Status {
    HOSTILE_TO_ENEMY,   // use this capability to be hostile towards enemy
    HOSTILE_TO_PLAYER,  // use this capability to be hostile towards player
    IS_PLAYER,          // use this capability to identify player
    IS_YHORM,           // use this capability to identify Yhorm
    IS_ALDRICH,         // use this capability to identify Aldrich
    IS_VALLEY,          // use this capability to identify valley
    ABLE_TO_BUY,        // use this capability to identify actor that are able to buy from vendor
    RAGE_MODE,          // use this capability to identify if Yhorm is in rage mode
    IMMUNE_TO_BURN,     // use this capability to identify if actor is immune to burn
    WEAK_TO_STORMRULER, // use this capability to identify if actor is weak to storm ruler
    STUNNED,            // use this capability to identify if actor is stunned
    DISARMED,           // use this capability to identify if actor is disarmed when using storm ruler
    CHARGING,           // use this capability to check if storm ruler is charging
    FULLY_CHARGED,      // use this capability to check if storm ruler is fully charged
    DULLNESS,           // use this capability to check if storm ruler is attacking enemy other than Yhorm
    IS_PICKED_UP,       // use this capability to check if item is picked up
    TRADABLE,           // use this capability to identify if the item is tradable to vendor
    IS_SKELETON,        // use this capability to identify if the actor is a skeleton
    IS_MIMIC,           // use this capability to identify if the actor is a mimic
    BLOCKED,            // use this capability to identify if the attack is blocked
    RANDOM_DEAD         // use this capability to identify if the actor randomly died
}
