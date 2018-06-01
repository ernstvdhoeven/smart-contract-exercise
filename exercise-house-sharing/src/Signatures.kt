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

    fun checkSignaturesAreFromParties(otherSignatures: Signatures) : Boolean
    {
        return true //return signatures.minus(otherSignatures.signatures)
    }
}
