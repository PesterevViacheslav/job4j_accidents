package ru.job4j.accidents;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldLogin() throws Exception {
        this.mockMvc.perform(get("/login?error=true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("errorMessage"));
    }

    @Test
    public void shouldReg() throws Exception {
        this.mockMvc.perform(get("/reg?err=true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("reg"))
                .andExpect(model().attributeExists("errorMessage"));
    }

    @Test
    public void shouldLogout() throws Exception {
        this.mockMvc.perform(get("/logout"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?logout=true"));
    }

}


