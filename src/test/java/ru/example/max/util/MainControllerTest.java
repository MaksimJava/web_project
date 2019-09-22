package ru.example.max.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.example.max.config.CoreConfig;
import ru.example.max.config.PersistenceConfig;
import ru.example.max.config.SecurityConfig;
import ru.example.max.config.WebConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        WebConfig.class, PersistenceConfig.class, CoreConfig.class, SecurityConfig.class
})
@WebAppConfiguration
public class MainControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void homeShouldRedirectToUserPage() throws Exception {
        mockMvc.perform(get("/")).andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }

    @Test
    public void viewShouldReturnGreetingWithSpecifiedName() throws Exception {
        String name = "Max";
        String expected = "Hello " + name + "!";

        mockMvc.perform(get("/view/{name}", name))
                .andExpect(status().isOk())
                .andExpect(model().attribute("msg", expected))
                .andExpect(view().name("/index"));
    }

    @Test
    public void rawShouldReturnResponseBody() throws Exception {
        mockMvc.perform(get("/raw"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Raw data"));
    }
}
