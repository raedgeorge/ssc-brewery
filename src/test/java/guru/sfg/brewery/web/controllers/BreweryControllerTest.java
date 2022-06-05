package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class BreweryControllerTest extends BaseITTest {


    @Test
    void viewBreweryCustomerRole() throws Exception {

        mockMvc.perform(get("/brewery/breweries")
                .with(httpBasic("scott", "tiger")))
                .andExpect(view().name("breweries/index"))
                .andExpect(model().attributeExists("breweries"))
                .andExpect(status().isOk());
    }

    @Test
    void viewBreweryAdminRole() throws Exception {

        mockMvc.perform(get("/brewery/breweries")
                        .with(httpBasic("spring", "guru")))
                .andExpect(status().isOk());
    }

    @Test
    void viewBreweryUserRole() throws Exception {

        mockMvc.perform(get("/brewery/breweries")
                        .with(httpBasic("user", "password")))
                .andExpect(status().isForbidden());
    }

    @Test
    void viewBreweryUnAuthorized() throws Exception {

        mockMvc.perform(get("/brewery/breweries")
                        .with(httpBasic("user2", "password")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void viewBreweryApiCustomerRole() throws Exception {

        mockMvc.perform(get("/brewery/api/v1/breweries")
                        .with(httpBasic("scott", "tiger")))
                .andExpect(status().isOk());
    }

    @Test
    void viewBreweryApiAdminRole() throws Exception {

        mockMvc.perform(get("/brewery/api/v1/breweries")
                        .with(httpBasic("spring", "guru")))
                .andExpect(status().isOk());
    }

    @Test
    void viewBreweryApiUserRole() throws Exception {

        mockMvc.perform(get("/brewery/api/v1/breweries")
                        .with(httpBasic("user", "password")))
                .andExpect(status().isForbidden());
    }

    @Test
    void viewBreweryApiUnAuthorized() throws Exception {

        mockMvc.perform(get("/brewery/api/v1/breweries")
                        .with(httpBasic("user2", "password")))
                .andExpect(status().isUnauthorized());
    }
}