package src

data class Signatures(val signatures: List<String>)
{
    /**
     * Returns true if the current state has as many signatures as the number provided as argument.
     */
    fun checkNumberOfSignatures(number: Int) : Boolean
    {
        return signatures.size == number
    }

    /**
     * Returns true if the current state has as many unique signatures as the number provided as argument.
     */
    fun checkNumberOfUniqueSignatures(number: Int) : Boolean
    {
        return signatures.distinct().size == number
    }

    /**
     * Returns true if the signatures in the current state are the same as the provided signatures.
     */
    fun checkSignaturesAreSame(otherSignatures: Signatures) : Boolean
    {
        return signatures == otherSignatures.signatures
    }

    /**
     * Returns true if all the provided parties have signed the current state.
     */
    fun checkAllPartiesHaveSigned(parties: Parties) : Boolean
    {
        return signatures.containsAll(parties.publicKeys.map { x -> 's' + x })
    }

    /**
     * Returns true if all the listed parties have signed the current state.
     */
    fun checkIfPartiesHaveSigned(parties: List<String>) : Boolean
    {
        return signatures.containsAll(parties.map { x -> 's' + x })
    }
}
