package src

// don't trust a trusted party to update itself
data class TrustedParty(val publicKey: String)
{
    fun trustedPartyHasChanged(oldTrustedParty: TrustedParty) : Boolean
    {
        return oldTrustedParty.publicKey != publicKey
    }
}
