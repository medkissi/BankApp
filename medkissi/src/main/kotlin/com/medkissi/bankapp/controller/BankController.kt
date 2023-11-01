package com.medkissi.bankapp.controller

import com.medkissi.bankapp.datasource.BankDataSource
import com.medkissi.bankapp.datasource.mock.MockBankDataSource
import com.medkissi.bankapp.model.Bank
import com.medkissi.bankapp.service.BankService
import jakarta.websocket.server.PathParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class BankController {

    private val dataSource:BankDataSource = MockBankDataSource()
    @Autowired
    lateinit var  service: BankService

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e:NoSuchElementException):ResponseEntity<String>{
        return  ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    }

    @GetMapping("/banks")
    fun getBanks ():Collection<Bank>{
        return  service.getBanks()
    }

    @GetMapping("/banks/{accountNumber}")
    fun getBank(@PathVariable accountNumber:String):Bank{
        return service.getBank(accountNumber)
    }



}