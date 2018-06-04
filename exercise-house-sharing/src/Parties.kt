package src

data class Parties(val publicKeys: List<String>)
{
    /**
     * Returns true if the list of parties has changed.
     */
    fun partiesHaveChanged(oldParties: Parties) : Boolean
    {
        return oldParties.publicKeys != publicKeys
    }

    /**
     * Returns true if parties have been removed.
     */
    fun partiesHaveBeenRemoved(oldParties: Parties) : Boolean
    {
        if (partiesHaveChanged(oldParties))
            return oldParties.getNumberOfParties() > this.getNumberOfParties()
        else
            return false
    }

    /**
     * Returns true if parties have been added.
     */
    fun partiesHaveBeenAdded(oldParties: Parties) : Boolean
    {
        if (partiesHaveChanged(oldParties))
            return oldParties.getNumberOfParties() < this.getNumberOfParties()
        else
            return false
    }

    /**
     * Returns the number of parties in the current contract state.
     */
    fun getNumberOfParties() : Int
    {
        return publicKeys.size
    }

    /**
     * Returns true if all the public key strings are unique.
     */
    fun hasNoDoubleParties() : Boolean
    {
        return publicKeys.distinct().size == publicKeys.size
    }

    /**
     * Returns true if all the public keys are unique.
     */
    fun hasNoDoublePartiesAfterCleanup() : Boolean
    {
        val input = publicKeys.map { x -> if (x.last() == '=') x.dropLast(1) else x }
        return input.distinct().size == publicKeys.size
    }

    /**
     * Returns true if the identity of the trusted party is in the list of participants.
     */
    fun checkIfTrustedPartyIsParticipant(trustedParty: TrustedParty) : Boolean
    {
        return publicKeys.contains(trustedParty.publicKey)
    }

    /**
     * Returns true if all the identities/public keys are valid.
     */
    fun allIdentitiesAreValid() : Boolean
    {
        return publicKeys.mapNotNull { k -> k.toIntOrNull() }.size == publicKeys.size
    }
}
