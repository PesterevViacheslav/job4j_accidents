package ru.job4j.accidents;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class AccidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void shouldCreateAccident() throws Exception {
        this.mockMvc.perform(get("/accidents/formCreateAccident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accidents/createAccident"))
                .andExpect(model().attributeExists("user", "rules", "types"));
    }

    @Transactional
    @Test
    @WithMockUser
    public void shouldEditAccident() throws Exception {
        this.mockMvc.perform(get("/accidents/formEditAccident?id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accidents/editAccident"))
                .andExpect(model().attributeExists("user", "rules", "types"));
    }

    @Test
    @WithMockUser
    public void shouldErrGet() throws Exception {
        this.mockMvc.perform(get("/accidents/errGet"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accidents/errGet"));
    }

    @Test
    @WithMockUser
    public void shouldErrEdit() throws Exception {
        this.mockMvc.perform(get("/accidents/errEdit"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accidents/errEdit"));
    }
}

