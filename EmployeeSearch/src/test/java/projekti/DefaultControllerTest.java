package projekti;

import org.fluentlenium.adapter.junit.FluentTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DefaultControllerTest extends FluentTest {

    @Autowired
    private MockMvc mockMvc;

    // DefaultController - TESTS
    @Test
    public void getRequestToRootReturnsRedirect() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isFound());
    }

    @Test
    public void getRequestToEmployeeSearchReturnsRedirect() throws Exception {
        this.mockMvc.perform(get("/EmployeeSearch")).andDo(print()).andExpect(status().isFound());
    }

    @Test
    public void getRequestToEmployeeSearchSlashReturnsRedirect() throws Exception {
        this.mockMvc.perform(get("/EmployeeSearch/")).andDo(print()).andExpect(status().isFound());
    }
}
