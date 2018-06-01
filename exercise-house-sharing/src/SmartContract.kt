package src


data class SmartContractData(
        val parties: Parties,
        val signatures: Signatures,
        val calendar: Calendar,
        val inventory: Inventory,
        val billing: Billing,
        val trustedParty: TrustedParty,
        val trustedPartySignature: TrustedPartySignature)


// only address the case where the status of the contract has been changed,
// not when it is initially is created
// we still need to check all the signatures because every new version needs to be
// signed again
fun SmartContract(oldData: SmartContractData, newData: SmartContractData)
{
    // check signatures
    // 1. signatures belong to the contract parties
    // 2.
    // 2. all contract parties signed
    //
    require(newData.signatures.checkNumberOfSignatures(5)) {"dfjkdfjksjfd"}
    require(newData.parties.checkAllPartiesSigned(oldData.signatures)) {"asdasdasdda"}
}