package src


val CREDIT_PER_DAY = 100
val CREDIT_PER_FORK = 2
val CREDIT_PER_PAN = 10
val CREDIT_PER_DAY_ELEC = 5

data class SmartContractData(
        val parties: Parties,
        val trustedParty: TrustedParty,
        val calendar: Calendar,
        val inventory: Inventory,
        val billing: Billing,
        val legalDocuments: LegalDocuments,
        val signatures: Signatures,
        val trustedPartySignature: TrustedPartySignature)


// only address the case where the status of the contract has been changed,
// not when it is initially is created
fun SmartContract(oldData: SmartContractData, newData: SmartContractData)
{
    //require(newData.signatures.checkNumberOfSignatures(5)) {"Expected 5 signatures in updated state."}
    //require(newData.signatures.checkNumberOfUniqueSignatures(5))

    if (newData.parties.partiesHaveBeenRemoved(oldData.parties))
    {
    }

    if (newData.parties.partiesHaveBeenAdded(oldData.parties))
    {
    }

    if (newData.calendar.reservationHasBeenAdded(oldData.calendar))
    {
    }

    if (newData.calendar.scheduleHasChanged(oldData.calendar))
    {
    }

    if (newData.inventory.inventoryHasChanged(oldData.inventory))
    {
    }

    if (newData.billing.balanceHasChanged(oldData.billing))
    {
    }

    if (newData.billing.electricityBillsHasChanged(oldData.billing))
    {
    }

    if (newData.trustedParty.trustedPartyHasChanged(oldData.trustedParty))
    {
    }

    if (newData.legalDocuments.legalDocumentsHaveBeenAdded(oldData.legalDocuments))
    {
    }
}