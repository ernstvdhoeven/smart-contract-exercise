package src.test

import org.junit.Before
import org.junit.Test
import src.*

class AdvancedTest
{
    private var scd: SmartContractData? = null

    @Before
    fun setup()
    {
        SmartContractData(
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
    fun `same public key found multiple times in participant list`()
    {
        SmartContract(scd!!, scd!!.copy(Parties(listOf("1", "2", "3", "4", "5", "5=")),
                signatures = Signatures(listOf("s1", "s2", "s3", "s4", "s5"))))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `adding inventory should not result in rewarding the participant with credit`()
    {
        SmartContract(scd!!, scd!!.copy(inventory = Inventory(20, 10),
                billing = Billing(balance = listOf(1090, 1500, 500, 2240, 2000), electricityBills = emptyList()),
                signatures = Signatures(listOf("s1"))))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `participant should not always be selected at random`()
    {
        // not possible to test this without calculating odds of something happening
        // this test is basically only here to spark a conversation about randomness
        // in smart contract and the issues when trying to do so
    }

    @Test(expected = IllegalArgumentException::class)
    fun `the agreed upon legal document should be validated`()
    {
        // same as previous test, spark a discussion about weak hashing
    }

    @Test(expected = IllegalArgumentException::class)
    fun `participant five should not be able to add money to his balance`()
    {
        SmartContract(scd!!, scd!!.copy(billing = Billing(balance = listOf(1000, 1500, 500, 2240, 3000), electricityBills = emptyList())))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `participants should not be able to get an extra day on top of their share`()
    {
        // same as previous test, spark a discussion about time(zones) issues
    }
}
