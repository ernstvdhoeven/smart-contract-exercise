package src

data class Inventory(val numberOfForks: Int, val numberOfPans: Int)
{
    fun getTotalCostInventory() : Int
    {
        return numberOfForks * CREDIT_PER_FORK + numberOfPans * CREDIT_PER_PAN
    }

    fun getValueDifference(oldInventory: Inventory) : Int
    {
        return (numberOfForks - oldInventory.numberOfForks) * CREDIT_PER_FORK + (numberOfPans - oldInventory.numberOfPans) * CREDIT_PER_PAN
    }

    fun inventoryHasChanged(oldInventory: Inventory) : Boolean
    {
        if (numberOfForks != oldInventory.numberOfForks || numberOfPans != oldInventory.numberOfPans)
            return true

        return false
    }
}
