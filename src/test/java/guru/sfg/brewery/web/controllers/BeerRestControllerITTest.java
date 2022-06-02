package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class BeerRestControllerITTest extends BaseITTest{

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
