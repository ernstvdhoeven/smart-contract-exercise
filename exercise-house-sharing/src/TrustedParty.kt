package src

// don't trust a trusted party to update itself
data class TrustedParty(val publicKey: String)
{
    /**
     * Returns true if the trusted party has been modified (has a new identity/public key).
     */
    fun trustedPartyHasChanged(oldTrustedParty: TrustedParty) : Boolean
    {
        return oldTrustedParty.publicKey != publicKey
    }
}
