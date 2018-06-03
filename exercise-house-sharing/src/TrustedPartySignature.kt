package src

data class TrustedPartySignature(val signature: String)
{
    fun checkTrustedPartyHasSigned() : Boolean
    {
        return signature != ""
    }
}
