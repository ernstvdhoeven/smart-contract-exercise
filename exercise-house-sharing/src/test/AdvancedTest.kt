package src.test

import org.junit.Before
import org.junit.Test
import src.*
import kotlin.test.assertEquals

class AdvancedTest
{
    private var scd: SmartContractData? = null

    @Before
    fun setup()
    {
        scd = SmartContractData(
                Parties(listOf("1", "2")),
                Signatures(listOf("s1", "s2")),
                Calendar(emptyList(), emptyList()),
                Inventory(10,3),
                Billing(listOf(1000, 1500), listOf(ElectricityBill(31, 60), ElectricityBill(61, 90))),
                TrustedParty("pktp"),
                TrustedPartySignature("sigtp"))
    }

    @Test
    fun `test test`()
    {
        assertEquals(scd!!.parties.publicKeys.size, 2, "Expected two public keys in list of parties.")
    }
}
