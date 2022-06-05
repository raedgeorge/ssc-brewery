package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.repositories.BeerInventoryRepository;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.repositories.CustomerRepository;
import guru.sfg.brewery.services.BeerService;
import guru.sfg.brewery.services.BreweryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

public abstract class BaseITTest {

    @Autowired
    WebApplicationContext applicationContext;

    MockMvc mockMvc;

//    @MockBean
//    BeerRepository beerRepository;
//
//    @MockBean
//    BreweryService breweryService;
//
//    @MockBean
//    BeerInventoryRepository beerInventoryRepository;
//
//    @MockBean
//    CustomerRepository customerRepository;
//
//    @MockBean
//    BeerService beerService;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    public static Stream<Arguments> getStreamOfAllUsers(){

        return Stream.of(
                Arguments.of("spring", "guru"),
                Arguments.of("user", "password"),
                Arguments.of("scott", "tiger"));
    }

    public static Stream<Arguments> getStreamOfUsersWithoutAdmin(){

        return Stream.of(
                Arguments.of("user", "password"),
                Arguments.of("scott", "tiger"));
    }
}
