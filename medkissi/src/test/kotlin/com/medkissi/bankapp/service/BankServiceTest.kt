package com.medkissi.bankapp.service

import com.medkissi.bankapp.datasource.BankDataSource
import com.medkissi.bankapp.datasource.mock.MockBankDataSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.*

class BankServiceTest{
    private val dataSource:BankDataSource = mockk()
    val bankService = BankService(dataSource)

    @Test
    fun `should calls its data source to retrieve banks`(){
        // given
        every { dataSource.retrieveBanks() } returns  emptyList()
        val banks =  bankService.getBanks()
        verify { dataSource.retrieveBanks() }


    }
}