package com.james.coindesk.controller;

import com.james.coindesk.service.CoindeskClientService;
import com.james.coindesk.service.CurrencyNameService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CoindeskControllerTest {
    @Autowired
    private CoindeskClientService coindeskClientService;

    @Autowired
    MockMvc mockMvc;

    @Value("${version}")
    String version;

    @Test
    @Order(1)
    void callCoindeskApi() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/" + version + "/coindesk"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Order(2)
    void callCoindeskApiAndTransform() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/" + version + "/coindesk/transformations"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.updated").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bpiList").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bpiList[0].code").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bpiList.[0].name").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bpiList[0].rate_float").isNumber())
                .andDo(MockMvcResultHandlers.print());
    }
}