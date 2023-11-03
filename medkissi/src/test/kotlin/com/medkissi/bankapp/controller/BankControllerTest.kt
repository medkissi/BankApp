package com.medkissi.bankapp.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.JsonPath
import com.medkissi.bankapp.model.Bank
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class BankControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {

    @Test
    fun `should return list of banks`() {
        mockMvc.get("/api/banks")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$[0].accountNumber") { value("12345") }
            }
    }

    @Test
    fun `should return a bank given a specific account number`() {
        val accountNumber = 12345

        mockMvc.get("/api/banks/$accountNumber")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.accountNumber") { value("12345") }
            }
    }

    @Test
    fun `should return NOT FOUND if the account Number does not exist `() {
        val accountNumber = "does_not_exit"

        mockMvc.get("/api/banks/$accountNumber")
            .andDo { print() }
            .andExpect { status { isNotFound() } }

    }

    @Test
    fun `should add the new bank`() {
        val newBank = Bank("acc1232", 2.3, 22)

        mockMvc.post("/api/bank") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(newBank)
        }
            .andDo { print() }
            .andExpect {
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.accountNumber"){value("acc1232")}
                jsonPath("$.trust"){value(2.3)}
                jsonPath("$.transactionFee"){value("22")}

            }
    }
    @Test 
    fun `should return a BAD REQUEST if a given account number already exists`(){

        val invalidBank = Bank("12345",20.0,1)
        mockMvc.post("/api/bank"){
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(invalidBank)
        }
            .andDo { print() }
            .andExpect {
                status {isBadRequest()}
                content { contentType(MediaType.APPLICATION_JSON) }

            }
    }

}