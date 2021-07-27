package com.imatia.statemachine.rest.controller;

import com.imatia.statemachine.config.StateMachineApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = StateMachineApplication.class)
@AutoConfigureMockMvc
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturn201() throws Exception {
        this.mockMvc.perform(post("/order/tracking")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"orderTrackings\":[{\"orderId\":1,\"trackingStatusId\":1,\"changeStatusDate\":\"2021-02-26T10:30:30.000+01:00\"}]}"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn400() throws Exception {
        this.mockMvc.perform(post("/order/tracking")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}