package src.sol

import src.*

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

/*
// only address the case where the status of the contract has been changed,
// not when it is initially is created
fun SmartContract(oldData: SmartContractData, newData: SmartContractData)
{
    //require(newData.signatures.checkNumberOfSignatures(5)) {"Expected 5 signatures in updated state."}
    //require(newData.signatures.checkNumberOfUniqueSignatures(5))

    if (newData.parties.partiesHaveBeenRemoved(oldData.parties))
    {
        require(newData.signatures.checkAllPartiesHaveSigned(oldData.parties))
        require(newData.signatures.checkNumberOfSignatures(oldData.parties.getNumberOfParties()))
    }

    if (newData.parties.partiesHaveBeenAdded(oldData.parties))
    {
        require(newData.signatures.checkAllPartiesHaveSigned(newData.parties))
        require(newData.signatures.checkNumberOfSignatures(newData.parties.getNumberOfParties()))
        require(newData.parties.hasNoDoublePartiesAfterCleanup())
        require(newData.parties.allIdentitiesAreValid())
    }

    if (newData.calendar.reservationHasBeenAdded(oldData.calendar))
    {
        require(newData.calendar.checkReservationsAreBeforeDeadline())
        require(newData.signatures.checkIfPartiesHaveSigned(newData.calendar.getPartiesWithReservations()))
        require(newData.billing.checkCanPayForReservationsAndAdditionalCost(newData.calendar.getAllReservations(), newData.inventory.getTotalCostInventory()))
    }

    if (newData.calendar.scheduleHasChanged(oldData.calendar))
    {
        require(newData.calendar.checkScheduleEquality(newData.parties))
        require(newData.calendar.checkScheduleHasTimeForInspection())
        require(newData.calendar.checkScheduleChangeBasedOnReservations())
        require(newData.calendar.checkScheduleChangeRandomReservation())
        require(newData.billing.checkIfPartyHasPaidForScheduledDays(oldData.billing, newData.calendar))
        require(newData.billing.checkIfAllPartiesCompensateForNegativeCredit(oldData.billing, newData.calendar))
    }

    if (newData.inventory.inventoryHasChanged(oldData.inventory))
    {
        require(newData.billing.checkPartyBilledForMissingInventory(oldData.billing,
                newData.inventory.getValueDifference(oldData.inventory), newData.calendar.getLastPartyInHouse()))
        require(newData.billing.checkOnlyNegativeBillsForMissingInventory(oldData.billing,
                newData.inventory.getValueDifference(oldData.inventory), newData.calendar.getLastPartyInHouse()))
    }

    if (newData.billing.balanceHasChanged(oldData.billing))
    {
        require(newData.trustedPartySignature.checkTrustedPartyHasSigned())
    }

    if (newData.billing.electricityBillsHasChanged(oldData.billing))
    {
        require(newData.trustedPartySignature.checkTrustedPartyHasSigned())
        require(newData.billing.checkIfElectricityBillHasBeenPaid(oldData.billing))
        require(newData.billing.checkIfPartyPaidElectricityForDaysInHouse(oldData.billing, newData.calendar))
        require(newData.billing.checkIfAllPartiesPaidTheSameForEmptyHouseDays(oldData.billing, newData.calendar))
    }

    if (newData.trustedParty.trustedPartyHasChanged(oldData.trustedParty))
    {
        require(newData.signatures.checkAllPartiesHaveSigned(newData.parties))
    }

    if (newData.legalDocuments.legalDocumentsHaveBeenAdded(oldData.legalDocuments))
    {
        require(newData.signatures.checkAllPartiesHaveSigned(newData.parties))
        require(newData.legalDocuments.checkNewDocumentsFormatting())
    }
}
*/
