package src

data class Parties(val publicKeys: List<String>)
{
    fun partiesHaveChanged(oldParties: Parties) : Boolean
    {
        return oldParties.publicKeys != publicKeys
    }

    fun partiesHaveBeenRemoved(oldParties: Parties) : Boolean
    {
        if (partiesHaveChanged(oldParties))
            return oldParties.getNumberOfParties() > this.getNumberOfParties()
        else
            return false
    }

    fun partiesHaveBeenAdded(oldParties: Parties) : Boolean
    {
        if (partiesHaveChanged(oldParties))
            return oldParties.getNumberOfParties() < this.getNumberOfParties()
        else
            return false
    }

    fun getNumberOfParties() : Int
    {
        return publicKeys.size
    }

    fun hasNoDoubleParties() : Boolean
    {
        return publicKeys.distinct().size == publicKeys.size
    }

    fun hasNoDoublePartiesAfterCleanup() : Boolean
    {
        val input = publicKeys.map { x -> if (x.last() == '=') x.dropLast(1) else x }
        return input.distinct().size == publicKeys.size
    }

    fun allIdentitiesAreValid() : Boolean
    {
        return publicKeys.mapNotNull { k -> k.toIntOrNull() }.size == publicKeys.size
    }
}
