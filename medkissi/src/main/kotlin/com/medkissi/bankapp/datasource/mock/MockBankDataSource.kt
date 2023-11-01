package com.medkissi.bankapp.datasource.mock

import com.medkissi.bankapp.datasource.BankDataSource
import com.medkissi.bankapp.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource : BankDataSource {
    private val banks: Collection<Bank> = listOf(
        Bank("12345", 3.1, 1),
        Bank("1010", 2.5, 2),
        Bank("0011", 1.1, 3),
        )

    override fun retrieveBanks(): Collection<Bank> {
        return banks
    }

    override fun retrieveBank(accountNumber: String): Bank {
        return  banks.firstOrNull{ it.accountNumber == accountNumber }
            ?: throw NoSuchElementException(" Could not find a bank with the account number $accountNumber")
    }

}