package ru.job4j.accidents;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class AccidentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccidentService accidentService;

    @Test
    @WithMockUser
    public void shouldCreateAccident() throws Exception {
        this.mockMvc.perform(get("/accidents/formCreateAccident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accidents/createAccident"))
                .andExpect(model().attributeExists("user", "rules", "types"));
    }

    @Test
    @WithMockUser
    public void shouldCreateAccidentPost() throws Exception {
        Set<Rule> rIds = new HashSet<>(Arrays.asList(
                new Rule(1, "rule1"),
                new Rule(2, "rule2"),
                new Rule(3, "rule3")));
        Accident accident = new Accident();
        accident.setRules(rIds);
        this.mockMvc.perform(post("/accidents/createAccident")
                        .flashAttr("accident", accident)
                        .param("rIds", "1")
                        .param("rIds", "2")
                        .param("rIds", "3"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"));

        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        verify(accidentService, times(1))
                .create(argument.capture(), eq(Arrays.asList(1, 2, 3)));
        assertThat(argument.getValue()).isEqualTo(accident);
    }

    @Transactional
    @Test
    @WithMockUser
    public void shouldEditAccident() throws Exception {
        when(accidentService.findById(1)).thenReturn(Optional.of(new Accident()));
        this.mockMvc.perform(get("/accidents/formEditAccident?id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accidents/editAccident"))
                .andExpect(model().attributeExists("user", "rules", "types"));
    }

    @Test
    @WithMockUser
    public void shouldEditAccidentPost() throws Exception {
        Set<Rule> rIds = new HashSet<>(Arrays.asList(
                new Rule(1, "rule1"),
                new Rule(2, "rule2"),
                new Rule(3, "rule3")));
        Accident accident = new Accident();
        accident.setRules(rIds);
        accident.setId(1);
        this.mockMvc.perform(post("/accidents/editAccident")
                        .flashAttr("accident", accident)
                        .param("id", "1")
                        .param("rIds", "1")
                        .param("rIds", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/accidents/errEdit"));

        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        verify(accidentService, times(1))
                .update(argument.capture(), eq(1), eq(Arrays.asList(1, 2)));
        assertThat(argument.getValue()).isEqualTo(accident);
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

