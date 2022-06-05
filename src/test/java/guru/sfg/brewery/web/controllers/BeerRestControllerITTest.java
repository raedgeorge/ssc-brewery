package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.domain.Beer;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.web.model.BeerStyleEnum;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest
@SpringBootTest
public class BeerRestControllerITTest extends BaseITTest{

    @Autowired
    BeerRepository beerRepository;
    @Nested
    @DisplayName("Delete Tests")
    class DeleteTests {

        public Beer beerToDelete() {

            Random random = new Random();

            return beerRepository.saveAndFlush(Beer.builder()
                    .beerName("some beer")
                    .beerStyle(BeerStyleEnum.IPA)
                    .minOnHand(12)
                    .quantityToBrew(200)
                    .upc(String.valueOf(random.nextInt(99999999)))
                    .build());
        }


        @Test
        @Disabled
        void deleteBeerUrlFilter() throws Exception {

            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId())
                            .queryParam("key", "user")
                            .queryParam("secret", "password"))
                    .andExpect(status().is2xxSuccessful());
        }

        @Test
        @Disabled
        void deleteBeerBadCredentials() throws Exception {

            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId())
                            .header("Api-Key", "user")
                            .header("Api-Secret", "password123"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @Disabled
        void deleteBeerById() throws Exception {

            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId())
                            .header("Api-Key", "user")
                            .header("Api-Secret", "password"))
                    .andExpect(status().isOk());
        }

        @Test
        void deleteBeerHttpBasic() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId())
                            .with(httpBasic("spring", "guru")))
                    .andExpect(status().is2xxSuccessful());
        }

        @Test
        void deleteBeerHttpBasicUserRole() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId())
                            .with(httpBasic("user", "password")))
                    .andExpect(status().isForbidden());
        }


        @Test
        void deleteBeerHttpBasicCustomerRole() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId())
                            .with(httpBasic("scott", "tiger")))
                    .andExpect(status().isForbidden());
        }


        @Test
        void deleteBeerNoAuth() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId()))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        void findBeerByIdNoUser() throws Exception {

            mockMvc.perform(get("/api/v1/beer/" + beerToDelete().getId()))
                    .andExpect(status().isUnauthorized());
        }

    }
    @Test
    void findBeersAdminRole() throws Exception {

        mockMvc.perform(get("/api/v1/beer")
                        .with(httpBasic("spring", "guru")))
               .andExpect(status().isOk());
    }



    @Test
    void findBeerByUPCRoleAdmin() throws Exception {

        mockMvc.perform(get("/api/v1/beerUpc/0083783375213")
                        .with(httpBasic("spring", "guru")))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerByUPCRoleCustomer() throws Exception {

        mockMvc.perform(get("/api/v1/beerUpc/0083783375213")
                        .with(httpBasic("scott", "tiger")))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerByUPCRoleUser() throws Exception {

        mockMvc.perform(get("/api/v1/beerUpc/0083783375213")
                        .with(httpBasic("user", "password")))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerByUPCNoUser() throws Exception {

        mockMvc.perform(get("/api/v1/beerUpc/0083783375213"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void findBeerAdminUser() throws Exception {

        mockMvc.perform(get("/beers").param("beerName", "")
                .with(httpBasic("spring", "guru")))
                .andExpect(status().isOk());
    }
}
