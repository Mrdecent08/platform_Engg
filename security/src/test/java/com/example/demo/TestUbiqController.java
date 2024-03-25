package com.example.demo;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.controller.UbiqController;
import com.example.demo.entity.Payload;


@WebMvcTest(UbiqController.class)
class TestUbiqController {


    @Autowired
    private MockMvc mockMvc;

    @Test
    void testencrypt() throws Exception {
    	Payload p = new Payload("hvk-xvskr123","postgres");
        mockMvc.perform(MockMvcRequestBuilders.post("/encrypt")
                .content(p.toString())
                .contentType(MediaType.APPLICATION_JSON));   
       
    }
}
