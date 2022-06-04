package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest
@SpringBootTest
public class BeerRestControllerITTest extends BaseITTest{

    @Test
    void deleteBeerUrlFilter() throws Exception {

        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-456e8e19c311")
                .queryParam("key", "user")
                .queryParam("secret", "password"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void deleteBeerBadCredentials() throws Exception {

        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-456e8e19c311")
                        .header("Api-Key", "user")
                        .header("Api-Secret", "password123"))
                .andExpect(status().isUnauthorized());
    }
    @Test
    void deleteBeerById() throws Exception {

        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-456e8e19c311")
                        .header("Api-Key", "user")
                        .header("Api-Secret", "password"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBeerHttpBasic() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-456e8e19c311")
                .with(httpBasic("user", "password")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void deleteBeerNoAuth() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-456e8e19c311"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void findBeers() throws Exception {

        mockMvc.perform(get("/api/v1/beer"))
               .andExpect(status().isOk());
    }

    @Test
    void findBeerById() throws Exception {

        mockMvc.perform(get("/api/v1/beer/97df0c39-90c4-4ae0-b663-456e8e19c311"))
               .andExpect(status().isOk());
    }

    @Test
    void findBeerByUPC() throws Exception {

        mockMvc.perform(get("/api/v1/beerUpc/0083783375213"))
                .andExpect(status().isOk());
    }
}
