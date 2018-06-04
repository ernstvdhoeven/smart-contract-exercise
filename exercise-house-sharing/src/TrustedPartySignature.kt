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

    /**
     * Returns true if the current trusted party signature is the same as the input one.
     */
    fun checkIfTrustedPartySignatureSameAsInput(otherTrustedPartySignature: TrustedPartySignature) : Boolean
    {
        return otherTrustedPartySignature.signature == signature
    }
}
