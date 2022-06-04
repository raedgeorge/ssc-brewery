package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.AuthorityRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@AllArgsConstructor
@Component
@Slf4j
public class LoadUserData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (ObjectUtils.isEmpty(userRepository.findAll())){

            loadUserData();
        }
    }

    private void loadUserData(){

        Authority ADMIN = Authority.builder().role("ADMIN").build();
        Authority USER = Authority.builder().role("USER").build();
        Authority CUSTOMER = Authority.builder().role("CUSTOMER").build();

        User spring  = User.builder()
                .username("spring")
                .password(passwordEncoder.encode("guru"))
                .authority(ADMIN).build();

        User user  = User.builder()
                .username("user")
                .password(passwordEncoder.encode("password"))
                .authority(USER).build();

        User scott  = User.builder()
                .username("scott")
                .password(passwordEncoder.encode("tiger"))
                .authority(CUSTOMER).build();

        authorityRepository.save(ADMIN);
        authorityRepository.save(USER);
        authorityRepository.save(CUSTOMER);

        userRepository.save(spring);
        userRepository.save(user);
        userRepository.save(scott);

        log.debug("================================================");
        log.debug("Database Initialized With Security Users Data...");
        log.debug("Users Count : " + userRepository.count());
        log.debug("================================================");
    }
}
