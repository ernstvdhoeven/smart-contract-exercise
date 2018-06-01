package src

data class Parties(val publicKeys: List<String>)
{
    // we cheat here, we don't use real public keys but just fake it
    // signatures are the hashes of the content and "pub" key combined

    fun checkSignaturesByTheseParties(signatures: Signatures) : Boolean
    {
        return true
    }

    fun checkAllPartiesSigned(signatures: Signatures) : Boolean
    {
        return true
    }
}
