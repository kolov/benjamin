package benjamin.controller;

import benjamin.ApplicationConfiguration;
import benjamin.persistence.SettingsRepositoryExt;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ApplicationConfiguration.class})
@TestPropertySource(locations = {"/test-application.properties"})
public class SettingsControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Mock
    private SettingsRepositoryExt settingsRepositoryExt;

    @InjectMocks
    private SettingsController settingsController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        //this.mockMvc = webAppContextSetup(this.wac).build();
        this.mockMvc = standaloneSetup(settingsController).build();
    }

    @Test
    public void list() throws Exception {
        this.mockMvc.perform(get("/v1/settings").accept("*/*"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
        ;
    }

}