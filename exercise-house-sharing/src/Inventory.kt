package src

data class Inventory(val numberOfForks: Int, val numberOfPans: Int)
{
    /**
     * Returns the cost of all inventory items combined.
     */
    fun getTotalCostInventory() : Int
    {
        return numberOfForks * CREDIT_PER_FORK + numberOfPans * CREDIT_PER_PAN
    }

    /**
     * Returns the difference in price between the current inventory and the input inventory.
     */
    fun getValueDifference(oldInventory: Inventory) : Int
    {
        return (numberOfForks - oldInventory.numberOfForks) * CREDIT_PER_FORK + (numberOfPans - oldInventory.numberOfPans) * CREDIT_PER_PAN
    }

    /**
     * Returns true if the either the number of forks or the number of pans in the current inventory
     * is different from that of the input inventory.
     */
    fun inventoryHasChanged(oldInventory: Inventory) : Boolean
    {
        if (numberOfForks != oldInventory.numberOfForks || numberOfPans != oldInventory.numberOfPans)
            return true

        return false
    }
}
