package com.medkissi.bankapp.datasource.mock


import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.*

class MockBankDataSourceTest{
    private val mockBankDataSource = MockBankDataSource()

    @Test
    fun `should provide a collection of banks`(){
        val banks = mockBankDataSource.retrieveBanks()
        assertThat(banks.size).isGreaterThanOrEqualTo(3)

    }

    @Test
    fun `should provide some mock data `(){
        val banks  = mockBankDataSource.retrieveBanks()
        assertThat(banks).allMatch{ it.accountNumber.isNotBlank()}
        assertThat(banks).allMatch{ it.transactionFee != 0 }
        assertThat(banks).allMatch{ it.trust != 0.0 }

    }

}