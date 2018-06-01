package src

// 4 weeks before a requested date the schedule for that period can be finalized
// the reservation request should be added for all the relevant days
// note that this makes it easy to avoid timestamp issues because you always have a
// specific day number to fall back to

// the trusted party key gets added to the calendar on the day of the check
// the trusted party has to sign of on the change
data class Calendar(val schedule: List<String>, val requests: List<List<ReservationRequest>>)
{
}
