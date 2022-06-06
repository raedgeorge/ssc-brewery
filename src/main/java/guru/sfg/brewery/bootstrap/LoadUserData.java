package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.Role;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.AuthorityRepository;
import guru.sfg.brewery.repositories.security.RoleRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.Set;

@AllArgsConstructor
@Component
@Slf4j
public class LoadUserData implements CommandLineRunner {

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        if (ObjectUtils.isEmpty(userRepository.findAll())){

            loadUserData();
        }
    }

    private void loadUserData(){

        //beer auths
        Authority createBeer = authorityRepository.save(Authority.builder().permission("beer.create").build());
        Authority readBeer = authorityRepository.save(Authority.builder().permission("beer.read").build());
        Authority updateBeer = authorityRepository.save(Authority.builder().permission("beer.update").build());
        Authority deleteBeer = authorityRepository.save(Authority.builder().permission("beer.delete").build());

        //brewery auths
        Authority createBrewery = authorityRepository.save(Authority.builder().permission("brewery.create").build());
        Authority readBrewery = authorityRepository.save(Authority.builder().permission("brewery.read").build());
        Authority updateBrewery = authorityRepository.save(Authority.builder().permission("brewery.update").build());
        Authority deleteBrewery = authorityRepository.save(Authority.builder().permission("brewery.delete").build());

        //customer auths
        Authority createCustomer = authorityRepository.save(Authority.builder().permission("customer.create").build());
        Authority readCustomer = authorityRepository.save(Authority.builder().permission("customer.read").build());
        Authority updateCustomer = authorityRepository.save(Authority.builder().permission("customer.update").build());
        Authority deleteCustomer = authorityRepository.save(Authority.builder().permission("customer.delete").build());

        //associate authorities with roles and persist them to the database
        Role adminRole = roleRepository.save(Role.builder().roleName("ADMIN").build());
        Role customerRole = roleRepository.save(Role.builder().roleName("CUSTOMER").build());
        Role userRole = roleRepository.save(Role.builder().roleName("USER").build());

        adminRole.setAuthorities(Set.of(createBeer, readBeer, updateBeer, deleteBeer,
                            createBrewery, readBrewery, updateBrewery, deleteBrewery,
                            createCustomer, readCustomer, updateCustomer, deleteCustomer));

        userRole.setAuthorities(Set.of(readBeer));
        customerRole.setAuthorities(Set.of(readBeer, readBrewery, readCustomer));

        roleRepository.saveAll(Arrays.asList(adminRole, userRole, customerRole));

        //associate roles to user
        User spring  = User.builder()
                        .username("spring")
                        .password(passwordEncoder.encode("guru"))
                        .role(adminRole).build();

        User user  = User.builder()
                        .username("user")
                        .password(passwordEncoder.encode("password"))
                        .role(userRole).build();

        User scott  = User.builder()
                        .username("scott")
                        .password(passwordEncoder.encode("tiger"))
                        .role(customerRole).build();

        //persist users to the database
        userRepository.save(spring);
        userRepository.save(user);
        userRepository.save(scott);

        log.debug("================================================");
        log.debug("Database Initialized With Security Users Data...");
        log.debug("Users Count : " + userRepository.count());
        log.debug("================================================");
    }
}
