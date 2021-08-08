package com.zlab.futurice.calculator.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CalculatorController.class)
class CalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldTestValidExpression() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/calculator/v1/api/calculus?query=2 + 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(4))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldTestInValidExpression() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/calculator/v1/api/calculus?query=2 +Ws-va 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid Expression"))
                .andExpect(status().isBadRequest());

    }
}