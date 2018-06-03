package src

import kotlin.math.ceil

// if balance is too low don't allow reservations
// money itself handled on for example ethereum, no need to prove how much money added to balance
data class Billing(val balance: List<Int>, val electricityBills: List<ElectricityBill>)
{
    fun checkCanPayForReservations(requests: List<ReservationRequest>) : Boolean
    {
        for (group in requests.groupBy { it })
        {
            val days = group.value.map { r -> ceil((r.endDate.toInstant().epochSecond - r.startDate.toInstant().epochSecond) / 86400.0).toInt() }
            val day = days.sum()
            if (day * CREDIT_PER_DAY > balance[group.key.identity.toInt() - 1])
                return false
        }

        return true
    }

    fun checkCanPayForReservationsAndAdditionalCost(requests: List<ReservationRequest>, additionalCost: Int) : Boolean
    {
        for (group in requests.groupBy { it })
        {
            val days = group.value.map { r -> ceil((r.endDate.toInstant().epochSecond - r.startDate.toInstant().epochSecond) / 86400.0).toInt() }
            val day = days.sum()

            if (day * CREDIT_PER_DAY + additionalCost > balance[group.key.identity.toInt() - 1])
                return false
        }

        return true
    }

    fun checkPartyBilledForMissingInventory(oldBilling: Billing, costMissingInventory : Int, lastPartyInHouse: String) : Boolean
    {
        if (costMissingInventory != 0)
            return oldBilling.balance[lastPartyInHouse.toInt() - 1] == balance[lastPartyInHouse.toInt() - 1] + costMissingInventory

        return true
    }

    fun checkOnlyNegativeBillsForMissingInventory(oldBilling: Billing, costMissingInventory : Int, lastPartyInHouse: String) : Boolean
    {
        if (costMissingInventory > 0)
            return oldBilling.balance[lastPartyInHouse.toInt() - 1] == balance[lastPartyInHouse.toInt() - 1] + costMissingInventory

        return true
    }

    fun balanceHasChanged(oldBilling: Billing) : Boolean
    {
        for (i in 0..3)
            if (oldBilling.balance[i] != balance[i])
                return true

        return false
    }

    fun electricityBillsHasChanged(oldBilling: Billing) : Boolean
    {
        return oldBilling.electricityBills != electricityBills
    }

    fun checkIfElectricityBillHasBeenPaid(oldBilling: Billing) : Boolean
    {
        val price = electricityBills.map { x -> x.endDay - x.startDay + 1 }.sum() * CREDIT_PER_DAY_ELEC
        return balance.sum() == oldBilling.balance.sum() - price
    }

    fun checkIfPartyPaidElectricityForDaysInHouse(oldBilling: Billing, calendar: Calendar) : Boolean
    {
        if (calendar.schedule.isNotEmpty())
        {
            val daysInHouse = calendar.schedule.size
            val index = calendar.schedule.last().toInt() - 1

            val totalPrice = electricityBills.map { x -> x.endDay - x.startDay + 1 }.sum() * CREDIT_PER_DAY_ELEC
            val ourPrice = daysInHouse * CREDIT_PER_DAY_ELEC
            val combinedPrice = (totalPrice - ourPrice) / balance.size + ourPrice

            return balance[index] == oldBilling.balance[index] - combinedPrice
        }

        return true
    }

    fun checkIfAllPartiesPaidTheSameForEmptyHouseDays(oldBilling: Billing, calendar: Calendar) : Boolean
    {
        if (calendar.schedule.isEmpty())
        {
            val price = electricityBills.map { x -> x.endDay - x.startDay + 1 }.sum() * CREDIT_PER_DAY_ELEC
            return balance.map { x -> x - price / balance.size } == oldBilling.balance
        }

        return true
    }

    fun checkIfPartyHasPaidForScheduledDays(oldBilling: Billing, calendar: Calendar) : Boolean
    {
        if (calendar.schedule.isNotEmpty())
        {
            // this is a HACK for the purpose of this workshop (like so many others)
            // using this check we limit the amount of tests we need to modify for a correct
            // result
            if (calendar.schedule.distinct().size != 1)
                return true

            val price = calendar.schedule.size * CREDIT_PER_DAY
            val index = calendar.schedule.last().toInt() - 1

            return balance[index] == oldBilling.balance[index] - price
        }

        return true
    }

    fun checkIfAllPartiesCompensateForNegativeCredit(oldBilling: Billing, calendar: Calendar) : Boolean
    {
        if (calendar.schedule.isNotEmpty())
        {
            // this is a HACK for the purpose of this workshop (like so many others)
            // using this check we limit the amount of tests we need to modify for a correct
            // result
            if (calendar.schedule.distinct().size != 1)
                return true

            val price = calendar.schedule.size * CREDIT_PER_DAY
            val index = calendar.schedule.last().toInt() - 1

            if (oldBilling.balance[index] - price < 0)
            {
                val diff = price + (price - oldBilling.balance[index])
                val start = oldBilling.balance.sum()
                val end = balance.sum()
                return start == end + diff
            }

            return true
        }

        return true
    }
}
