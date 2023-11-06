package com.medkissi.bankapp.datasource

import com.medkissi.bankapp.model.Bank


interface BankDataSource {
    fun retrieveBanks():Collection<Bank>
    fun retrieveBank(accountNumber: String):Bank
    fun addBank(bank: Bank): Bank
    abstract fun updateBank(bank: Bank): Bank
    abstract fun deleteBank(accountNumber: String)
}