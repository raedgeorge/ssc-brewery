package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest
@SpringBootTest
public class BeerControllerIntegratedTest extends BaseITTest{

    @Test
    @WithMockUser("user")
    @Disabled
    void findBeers() throws Exception {

        mockMvc.perform(get("/beers/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("beers/findBeers"))
                .andExpect(model().attributeExists("beer"));
    }

    @Test
    void findBeersWithHttpBasic() throws Exception {

        mockMvc.perform(get("/beers/find")
                            .with(httpBasic("user", "password")))
               .andExpect(status().isOk())
               .andExpect(view().name("beers/findBeers"))
               .andExpect(model().attributeExists("beer"));
    }

    @Test
    @Disabled
    void findBeersUsingAntMatchersNoUsers() throws Exception {

        mockMvc.perform(get("/beers/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("beers/findBeers"))
                .andExpect(model().attributeExists("beer"));
    }

    @Test
    void initCreationFormUserRole() throws Exception {

        mockMvc.perform(
                get("/beers/new").with(httpBasic("user", "password")))
                .andExpect(status().isForbidden());
    }

    @Test
    void initCreationFormAdminRole() throws Exception {

        mockMvc.perform(
                        get("/beers/new").with(httpBasic("spring", "guru")))
                .andExpect(status().isOk())
                .andExpect(view().name("beers/createBeer"))
                .andExpect(model().attributeExists("beer"));
    }

    @Test
    void initCreationFormWithScott() throws Exception {

        mockMvc.perform(
                        get("/beers/new").with(httpBasic("scott", "tiger")))
                .andExpect(status().isForbidden());
    }

}
