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
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json
import org.springframework.test.web.servlet.*
import javax.print.attribute.standard.Media

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
                jsonPath("$[0].accountNumber") { value("1010") }
            }
    }

    @Test
    fun `should return a bank given a specific account number`() {
        val accountNumber = 1010

        mockMvc.get("/api/banks/$accountNumber")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.accountNumber") { value("1010") }
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
                jsonPath("$.accountNumber") { value("acc1232") }
                jsonPath("$.trust") { value(2.3) }
                jsonPath("$.transactionFee") { value("22") }

            }
    }

    @Test
    fun `should return a BAD REQUEST if a given account number already exists`() {

        val invalidBank = Bank("12345", 20.0, 1)
        mockMvc.post("/api/bank") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(invalidBank)
        }
            .andDo { print() }
            .andExpect {
                status { isBadRequest() }
                //content { contentType(MediaType.APPLICATION_JSON) }

            }
    }

    @Test
    fun `should  update an existing bank `() {
        val updatedBank = Bank("12345", 1.0, 1)
        mockMvc.patch("/api/bank/update") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updatedBank)

        }
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    json(objectMapper.writeValueAsString(updatedBank))
                }
            }
        mockMvc.get("/api/banks/${updatedBank.accountNumber}").andDo {
            print()
        }
            .andExpect {
                status { isOk() }
                content { json(objectMapper.writeValueAsString(updatedBank)) }

            }


    }

    @Test
    fun `should return NOT FOUND if  a bank doesnt have a given account number`() {

        val bank = Bank("does_not_exists", 12.0, 3)
        mockMvc.patch("/api/bank/update") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(bank)
        }
            .andDo { print() }
            .andExpect { status { isNotFound() } }
    }

    @Test
    fun `should delete the bank with a given account number `() {
        val accountNumber = "12345"

        mockMvc.delete("/api/banks/$accountNumber")
            .andDo { print()}
            .andExpect { status { isNoContent() } }

        mockMvc.get("/api/banks/$accountNumber")
            .andDo { print() }
            .andExpect { status { isNotFound() } }
    }
    @Test
    fun `should return NOT FOUND if a bank with a given account number does not exits`(){
        val accountNumber  = "does_not_exits"
        mockMvc.delete("/api/banks/$accountNumber")
            .andDo { print() }
            .andExpect { status { isNotFound() } }
    }


}