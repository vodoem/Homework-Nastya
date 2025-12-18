package ru.rsreu.lab;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void indexReturns200() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }


    @Test
    public void indexContainsTitle() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("сервис форматирования")));
    }


    @Test
    public void indexContainsSidebar() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Регистрация")));
    }
}
