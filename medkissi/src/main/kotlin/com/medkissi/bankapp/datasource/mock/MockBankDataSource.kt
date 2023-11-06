package com.medkissi.bankapp.datasource.mock

import com.medkissi.bankapp.datasource.BankDataSource
import com.medkissi.bankapp.model.Bank
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException

@Repository
class MockBankDataSource : BankDataSource {
    private val banks = mutableListOf(
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

    override fun addBank(bank: Bank): Bank {
        if(banks.any{it.accountNumber == bank.accountNumber}){
            throw IllegalArgumentException("Bank with account number ${bank.accountNumber} already exists!")
        }
        banks.add(bank)
        return  bank
    }

    override fun updateBank(bank: Bank): Bank {
      val currentBank = banks.firstOrNull{it.accountNumber == bank.accountNumber} ?:
      throw NoSuchElementException("Couldn't find the specified bank account")
        banks.remove(currentBank)
        banks.add(bank)
        return  bank
    }

    override fun deleteBank(accountNumber: String) {
        val bankToDelete = banks.firstOrNull{it.accountNumber == accountNumber} ?:
        throw NoSuchElementException("The bank that you want to delete does not exits")
        banks.remove(bankToDelete)
    }

}