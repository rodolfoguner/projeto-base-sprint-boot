package br.fai.models.client.controller;

import br.fai.models.client.config.security.SecurityConfig;
import br.fai.models.client.config.security.providers.FaiAuthenticationProvider;
import br.fai.models.client.service.ReportService;
import br.fai.models.client.service.UserService;
import br.fai.models.entities.UserModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import({SecurityConfig.class, FaiAuthenticationProvider.class})
class UserControllerTest {

    @MockBean
    private UserService<UserModel> userService;
    @MockBean
    private ReportService reportService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldInjectBeans() {
        // asssertJ
        assertThat(userService).isNotNull();
        // junit5
        assertNotNull(reportService);
        assertNotNull(mockMvc);
    }

    @Test
    void getUsers_whenNotAuthenticated_shouldRedirectToSignInPage() throws Exception {
        mockMvc.perform(get("/user/"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/account/sign-in"));
    }
}