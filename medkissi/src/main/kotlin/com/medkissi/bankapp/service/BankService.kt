package com.medkissi.bankapp.service

import com.medkissi.bankapp.datasource.BankDataSource
import com.medkissi.bankapp.datasource.mock.MockBankDataSource
import com.medkissi.bankapp.model.Bank
import org.springframework.stereotype.Service

@Service
class BankService(private val dataSource: BankDataSource) {

    fun getBanks():Collection<Bank>{
        return dataSource.retrieveBanks()
    }
    fun getBank(accountNumber:String) = dataSource.retrieveBank(accountNumber)
    fun addBank(bank: Bank): Bank {
        return  dataSource.addBank(bank)
    }

    fun updateBank(bank: Bank): Bank {
        return  dataSource.updateBank(bank)

    }

    fun deleteBank(accountNumber: String) {
        dataSource.deleteBank(accountNumber)

    }

}