package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
public class CustomerControllerItTest extends BaseITTest{

    @Test
    void listCustomerAdminRole() throws Exception {

        mockMvc.perform(get("/customers")
                    .with(httpBasic("spring", "guru")))
                .andExpect(status().isOk());
    }


    @Test
    void listCustomerCustomerRole() throws Exception {

        mockMvc.perform(get("/customers")
                        .with(httpBasic("scott", "tiger")))
                .andExpect(status().isOk());
    }

    @Test
    void listCustomersNoAuth() throws Exception {

        mockMvc.perform(get("/customers")
                    .with(httpBasic("user", "password")))
                .andExpect(status().isForbidden());
    }

    @Test
    void listCustomersNotLoggedIn() throws Exception {

        mockMvc.perform(get("/customers"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Rollback
    void addCustomerAdminRole() throws Exception {

        mockMvc.perform(post("/customers/new")
                        .param("customerName", "raed")
                    .with(httpBasic("spring", "guru")))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @Rollback
    void addCustomerUserRole() throws Exception {

        mockMvc.perform(post("/customers/new")
                        .param("customerName", "chad")
                        .with(httpBasic("user", "password")))
                .andExpect(status().isForbidden());
    }


    @Test
    @Rollback
    void addCustomerCustomerRole() throws Exception {

        mockMvc.perform(post("/customers/new")
                        .param("customerName", "tomas")
                        .with(httpBasic("scott", "tiger")))
                .andExpect(status().isForbidden());
    }


    @Test
    void addCustomerNotAuth() throws Exception {

        mockMvc.perform(post("/customers/new")
                        .param("customerName", "john"))
                .andExpect(status().isUnauthorized());
    }
}
