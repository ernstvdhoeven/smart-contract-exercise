package src.test

import org.junit.Before
import org.junit.Test
import src.*
import src.Calendar
import java.time.Instant
import java.util.*

class SimpleTest
{
    private var scd: SmartContractData? = null

    @Before
    fun setup()
    {
        scd = SmartContractData(
                Parties(listOf("1", "2", "3", "4", "5")),
                TrustedParty("pktp"),
                Calendar(emptyList(), emptyList()),
                Inventory(10,3),
                Billing(listOf(1000, 1500, 500, 2240, 2000), emptyList()),
                LegalDocuments(emptyList()),
                Signatures(emptyList()),
                TrustedPartySignature(""))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `changing the contract parties requires enough signatures`()
    {
        SmartContract(scd!!, scd!!.copy(parties = Parties(listOf("1", "2", "3")),
                signatures = Signatures(listOf("s1", "s2", "s3"))))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `changing the contract parties requires exactly one signature per contract party`()
    {
        SmartContract(scd!!, scd!!.copy(parties = Parties(listOf("1", "2", "3", "5")),
                signatures = Signatures(listOf("s1", "s2", "s3", "s4", "s5", "s3"))))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `an added party to the contract should not be be a participant already`()
    {
        SmartContract(scd!!, scd!!.copy(parties = Parties(listOf("1", "2", "3", "4", "5", "2")),
                signatures = Signatures(listOf("s1", "s2", "s3", "s4", "s5", "s2"))))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `an added party to the contract should also provide a signature`()
    {
        SmartContract(scd!!, scd!!.copy(parties = Parties(listOf("1", "2", "3", "4", "5", "6")),
                signatures = Signatures(listOf("s1", "s2", "s3", "s4", "s5"))))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `the identity of a new party should be a public key`()
    {
        SmartContract(scd!!, scd!!.copy(parties = Parties(listOf("1", "2", "3", "4", "5", "a")),
                signatures = Signatures(listOf("s1", "s2", "s3", "s4", "s5", "sa"))))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `adding a time slot request can not be done after the deadline for it has expired`()
    {
        val start = Date.from(Instant.now())
        val end = Date.from(Instant.now().plusSeconds(604800))
        SmartContract(scd!!, scd!!.copy(calendar = Calendar(schedule = listOf(),
                requests = listOf(ReservationRequest(start, end, "1"))),
                signatures = Signatures(listOf("s1"))))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `adding a time slot request can only be done on your own behalf`()
    {
        val start = Date.from(Instant.now().plusSeconds(3592000))
        val end = Date.from(Instant.now().plusSeconds(3592000 + 604800))
        SmartContract(scd!!, scd!!.copy(calendar = Calendar(schedule = listOf(),
                requests = listOf(ReservationRequest(start, end, "2"))),
                signatures = Signatures(listOf("s1"))))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `you should have enough credits to pay for the days you reserve`()
    {
        val start = Date.from(Instant.now().plusSeconds(3592000))
        val end = Date.from(Instant.now().plusSeconds(3592000 + 604800))
        SmartContract(scd!!, scd!!.copy(calendar = Calendar(schedule = listOf(),
                requests = listOf(ReservationRequest(start, end, "3"))),
                signatures = Signatures(listOf("s3"))))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `you can not get assigned more than your share of the days`()
    {
        SmartContract(scd!!, scd!!.copy(calendar = Calendar(schedule = listOf(" ") + List(75, {i -> "1"}), requests = listOf())))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `there should be a day for the trusted party to check the house before it is reserved again`()
    {
        SmartContract(scd!!, scd!!.copy(calendar = Calendar(schedule = listOf("1", "1", "1", "2", "2", "2"), requests = listOf())))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `when multiple parties request the same day assign at random based on previous state hash`()
    {
        val start = Date.from(Instant.now().plusSeconds(1209600))
        val end = Date.from(Instant.now().plusSeconds(1209600 + 604800))
        val input = scd!!.copy(calendar = Calendar(schedule = listOf(),
                requests = listOf(ReservationRequest(start, end, "4"), ReservationRequest(start, end, "1"))),
                signatures = Signatures(listOf("s1", "s4")))
        SmartContract(input, input.copy(calendar = input.calendar.copy(schedule = listOf(" ") + List(7, {x -> "s1"}))))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `reductions in inventory should be compensated for by a deduction in participant credit`()
    {
        SmartContract(scd!!, scd!!.copy(inventory = Inventory(10, 2),
                trustedPartySignature = TrustedPartySignature("stp"),
                calendar = Calendar(schedule = listOf("2", "1", "1"), requests = emptyList())))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `you should have enough credits after a reservation to pay for missing inventory`()
    {
        val start = Date.from(Instant.now().plusSeconds(3592000))
        val end = Date.from(Instant.now().plusSeconds(3592000 + 432000))
        SmartContract(scd!!, scd!!.copy(calendar = Calendar(schedule = listOf(),
                requests = listOf(ReservationRequest(start, end, "3"))),
                signatures = Signatures(listOf("s3"))))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `only the trusted party is allowed to make changes to participants credits`()
    {
        SmartContract(scd!!, scd!!.copy(billing = Billing(balance = listOf(1000, 1000, 1000, 1000, 1000), electricityBills = listOf())))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `only the trusted party can add bills to the list of bills`()
    {
        SmartContract(scd!!, scd!!.copy(billing = scd!!.billing.copy(electricityBills = listOf(ElectricityBill(91, 120)))))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `electricity bills have to be paid`()
    {
        SmartContract(scd!!, scd!!.copy(calendar = Calendar(schedule = listOf(), requests = listOf()),
                billing = scd!!.billing.copy(electricityBills = listOf(ElectricityBill(1, 30))),
                trustedPartySignature = TrustedPartySignature("stp")))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `participants that reserved the house for days pay more of the electricity bill for that period`()
    {
        SmartContract(scd!!, scd!!.copy(calendar = Calendar(schedule = listOf("4", "4", "4", "4", "4"), requests = listOf()),
                billing = Billing(balance = listOf(970, 1470, 470, 2210, 1970), electricityBills = listOf(ElectricityBill(1, 30))),
                trustedPartySignature = TrustedPartySignature("stp")))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `electricity bills are during non reserved days split between all participants`()
    {
        SmartContract(scd!!, scd!!.copy(calendar = Calendar(schedule = listOf(), requests = listOf()),
                billing = Billing(balance = listOf(970, 1470, 470, 2200, 1980), electricityBills = listOf(ElectricityBill(1, 30))),
                trustedPartySignature = TrustedPartySignature("stp")))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `participants should get negative credit if they do not have enough to pay`()
    {
        SmartContract(scd!!, scd!!.copy(calendar = Calendar(schedule = List(12, {i -> "1"}), requests = listOf()),
                billing = Billing(listOf(0, 1450, 450, 2190, 1750), emptyList()),
                trustedPartySignature = TrustedPartySignature("stp")))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `all other participants should temporarily cover for missing credit from other participants`()
    {
        SmartContract(scd!!, scd!!.copy(calendar = Calendar(schedule = List(12, {i -> "1"}), requests = listOf()),
                billing = Billing(listOf(-200, 1500, 500, 2240, 2000), emptyList()),
                trustedPartySignature = TrustedPartySignature("stp")))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `the identity of the trusted party can only change if all the participants agree`()
    {
        SmartContract(scd!!, scd!!.copy(trustedParty = TrustedParty("tp2"),
                signatures = Signatures(listOf("s1", "s2", "s3", "s4"))))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `adding a legal document requires a signature from all the participants`()
    {
        SmartContract(scd!!, scd!!.copy(legalDocuments = LegalDocuments(listOf("123123")),
                signatures = Signatures(listOf("s1", "s2", "s4", "s5"))))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `the hash of the legal document should be properly formatted`()
    {
        SmartContract(scd!!, scd!!.copy(legalDocuments = LegalDocuments(listOf("h2")),
                signatures = Signatures(listOf("s1", "s2", "s3", "s4", "s5"))))
    }
}
