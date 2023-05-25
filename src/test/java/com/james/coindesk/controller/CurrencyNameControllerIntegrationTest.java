package com.james.coindesk.controller;

import com.james.coindesk.service.CurrencyNameService;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CurrencyNameControllerIntegrationTest {

    @Autowired
    private CurrencyNameService currencyNameService;

    @Autowired
    MockMvc mockMvc;

    @Value("${version}")
    String version;

    @Test
    @Order(3)
    void findAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/" + version + "/currency/names"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Order(1)
    void insert() throws Exception {
        JSONObject requestContent = new JSONObject()
                .put("code", "USD")
                .put("name", "美金");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/" + version + "/currency/names")
                        .content(requestContent.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string("true"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Order(2)
    void update() throws Exception {
        String id = "USD";
        JSONObject requestContent = new JSONObject()
                .put("code", "USD")
                .put("name", "美元");
        mockMvc.perform(MockMvcRequestBuilders.put("/api/" + version + "/currency/names/" + id)
                        .content(requestContent.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Matchers.is("USD")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("美元")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Order(4)
    void delete() throws Exception {
        String id = "USD";
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/" + version + "/currency/names/"  + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string("true"))
                .andDo(MockMvcResultHandlers.print());
    }
}