package com.medkissi.bankapp.controller

import com.jayway.jsonpath.JsonPath
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
class BankControllerTest{
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `should return list of banks`(){
        mockMvc.get("/api/banks")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$[0].accountNumber"){value("12345")}
            }
    }

    @Test
    fun `should return a bank given a specific account number`(){
        val accountNumber = 12345

        mockMvc.get("/api/banks/$accountNumber")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content{ contentType(MediaType.APPLICATION_JSON)}
                jsonPath("$.accountNumber") {value("12345")}
            }
    }

    @Test
    fun `should return NOT FOUND if the account Number does not exist `(){
        val accountNumber = "does_not_exit"

        mockMvc.get("/api/banks/$accountNumber")
            .andDo { print() }
            .andExpect { status { isNotFound() } }

    }
}