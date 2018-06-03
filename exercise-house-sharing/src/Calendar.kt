package src

import java.time.Instant
import java.util.*

// 4 weeks before a requested date the schedule for that period can be finalized
// the reservation request should be added for all the relevant days
// note that this makes it easy to avoid timestamp issues because you always have a
// specific day number to fall back to

// the trusted party key gets added to the calendar on the day of the check
// the trusted party has to sign of on the change
data class Calendar(val schedule: List<String>, val requests: List<ReservationRequest>)
{
    fun reservationHasBeenAdded(oldCalender: Calendar) : Boolean
    {
        return oldCalender.requests != requests
    }

    fun checkReservationsAreBeforeDeadline() : Boolean
    {
        val deadline = Instant.now().plusSeconds(2592000)

        for (reservation in requests)
            if (reservation.startDate.before(Date.from(deadline)))
                return false

        return true
    }

    fun getPartiesWithReservations() : List<String>
    {
        return requests.map { r -> r.identity }
    }

    fun scheduleHasChanged(oldCalender: Calendar) : Boolean
    {
        return oldCalender.schedule != schedule
    }

    fun checkScheduleEquality(parties: Parties) : Boolean
    {
        val allowedDaysPerParty = 365 / parties.getNumberOfParties()

        for (group in schedule.groupBy { it })
            if (group.value.size > allowedDaysPerParty)
                return false

        return true
    }

    fun getAllReservations() : List<ReservationRequest>
    {
        return requests
    }

    fun checkScheduleHasTimeForInspection() : Boolean
    {
        var current = 0
        for (assigned in schedule)
        {
            val next = assigned.toIntOrNull()
            if (next != null)
            {
                if (current != 0 && current != next)
                    return false
                else
                    current = next
            }
            else
                current = 0
        }

        return true
    }

    fun checkScheduleChangeBasedOnReservations() : Boolean
    {
        if (requests.isNotEmpty())
            return requests.map { x -> x.identity }.containsAll(schedule)

        return true
    }

    fun checkScheduleChangeRandomReservation() : Boolean
    {
        if (requests.isNotEmpty())
            return listOf(requests.map { x -> x.identity.toInt() }.max()).containsAll(schedule.mapNotNull { x -> x.toIntOrNull() })

        return true
    }

    fun getLastPartyInHouse() : String
    {
        return schedule.mapNotNull { x -> x.toIntOrNull() }.last().toString()
    }
}
