package src

data class Signatures(val signatures: List<String>)
{
    fun checkNumberOfSignatures(number: Int) : Boolean
    {
        return signatures.size == number
    }

    fun checkNumberOfUniqueSignatures(number: Int) : Boolean
    {
        return signatures.distinct().size == number
    }

    fun checkSignaturesAreSame(otherSignatures: Signatures) : Boolean
    {
        return signatures == otherSignatures.signatures
    }

    fun checkAllPartiesHaveSigned(parties: Parties) : Boolean
    {
        return signatures.containsAll(parties.publicKeys.map { x -> 's' + x })
    }

    fun checkIfPartiesHaveSigned(parties: List<String>) : Boolean
    {
        return signatures.containsAll(parties.map { x -> 's' + x })
    }
}
