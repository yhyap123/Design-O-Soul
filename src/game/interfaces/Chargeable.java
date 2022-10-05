package game.interfaces;

public interface Chargeable {

    /**
     * Increase the number of charge of the current chargeable item.
     * By default, a chargeable item cannot increase its charge.
     * You may override this method to implement its functionality.
     *
     * @param charges number of charges to be incremented.
     * @return transaction status. True if addition successful, otherwise False.
     */
    default boolean addCharge(int charges){ return false;}

    /**
     * Deduct the number of charge of the current chargeable item.
     * By default, a chargeable item cannot get its own charge subtracted.
     * You may override this method to conduct subtraction on current charge.
     *
     * @param charges number of charges to be deducted
     * @return transaction status. True if subtraction successful, otherwise False.
     */
    default boolean subtractCharge(int charges){ return false;}

    /**
     * Getter of the number of charge of the current chargeable item.
     * @return an integer represents the number of charge
     */
    int getCharge();
}
