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
    /**
     * Returns true if the list of reservation requests has changed.
     */
    fun reservationHasBeenAdded(oldCalender: Calendar) : Boolean
    {
        return oldCalender.requests != requests
    }

    /**
     * Returns true if all the reservation requests have been added before their respective deadlines.
     */
    fun checkReservationsAreBeforeDeadline() : Boolean
    {
        val deadline = Instant.now().plusSeconds(2592000)

        for (reservation in requests)
            if (reservation.startDate.before(Date.from(deadline)))
                return false

        return true
    }

    /**
     * Returns a list of parties that currently have an outstanding reservation request.
     */
    fun getPartiesWithReservations() : List<String>
    {
        return requests.map { r -> r.identity }
    }

    /**
     * Returns true if the actual schedule (assignment of parties to days of the year) has changed.
     */
    fun scheduleHasChanged(oldCalender: Calendar) : Boolean
    {
        return oldCalender.schedule != schedule
    }

    /**
     * Returns true if no one party has more than his or her share of days of the year according to the schedule.
     */
    fun checkScheduleEquality(parties: Parties) : Boolean
    {
        val allowedDaysPerParty = 365 / parties.getNumberOfParties()

        for (group in schedule.groupBy { it })
            if (group.value.size > allowedDaysPerParty)
                return false

        return true
    }

    /**
     * Returns a list of all the reservation requests.
     */
    fun getAllReservations() : List<ReservationRequest>
    {
        return requests
    }

    /**
     * Returns true if there is enough time in the current schedule for an inspection to occur between
     * two different parties visiting the house.
     */
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

    /**
     * Returns true if the schedule has been based on the actual reservation requests instead of ignoring those.
     */
    fun checkScheduleChangeBasedOnReservations() : Boolean
    {
        if (requests.isNotEmpty())
            return requests.map { x -> x.identity }.containsAll(schedule)

        return true
    }

    /**
     * Returns true if the correct party has been randomly selected if multiple parties wanted to reserve
     * the house for the same period.
     */
    fun checkScheduleChangeRandomReservation() : Boolean
    {
        if (requests.isNotEmpty())
            return listOf(requests.map { x -> x.identity.toInt() }.max()).containsAll(schedule.mapNotNull { x -> x.toIntOrNull() })

        return true
    }

    /**
     * Returns the last party that has stayed in the house.
     */
    fun getLastPartyInHouse() : String
    {
        return schedule.mapNotNull { x -> x.toIntOrNull() }.last().toString()
    }
}
