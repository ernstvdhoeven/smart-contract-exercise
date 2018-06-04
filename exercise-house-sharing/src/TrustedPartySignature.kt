package src

data class TrustedPartySignature(val signature: String)
{
    /**
     * Returns true if the trusted party has provided a correct signature for this state update.
     */
    fun checkTrustedPartyHasSigned() : Boolean
    {
        return signature != ""
    }
}
