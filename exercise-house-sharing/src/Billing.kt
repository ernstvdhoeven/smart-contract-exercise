package src

// if balance is too low don't allow reservations
// money itself handled on for example ethereum, no need to prove how much money added to balance
data class Billing(val balance: List<Int>, val electricityBills: List<ElectricityBill>)
{
}
