package guru.sfg.brewery.config;

import guru.sfg.brewery.security.CustomPasswordEncoderFactories;
import guru.sfg.brewery.security.RestHeaderAuthFilter;
import guru.sfg.brewery.security.RestUrlAuthFilter;
import guru.sfg.brewery.security.utility.AuthenticationUtilsImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public RestHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager authenticationManager){

        RestHeaderAuthFilter filter = new RestHeaderAuthFilter(new AntPathRequestMatcher("/api/**"),
                                                               new AuthenticationUtilsImpl());

        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    public RestUrlAuthFilter restUrlAuthFilter(AuthenticationManager authenticationManager){

        RestUrlAuthFilter filter = new RestUrlAuthFilter(new AntPathRequestMatcher("/api/**"),
                                                        new AuthenticationUtilsImpl());

        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.addFilterBefore(restUrlAuthFilter(authenticationManager()),
                             UsernamePasswordAuthenticationFilter.class)
            .csrf().disable();

        http.addFilterBefore(restHeaderAuthFilter(authenticationManager()),
                             UsernamePasswordAuthenticationFilter.class);

        http
            .authorizeRequests(authorize -> {
                authorize.antMatchers(HttpMethod.GET, "/", "/webjars/**", "/resources/**").permitAll();
                authorize.antMatchers(HttpMethod.GET, "/beers/find", "/beers").permitAll();
                authorize.antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll();
                authorize.mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll();
            })
            .authorizeRequests()
                .anyRequest()
                .authenticated()
            .and()
            .formLogin().permitAll()
            .and()
            .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("user")
                    .password("{ldap}{SSHA}C9qYhu16LCwalYFREL0oXYZ2IpXKm2J6MjmY7A==")
                    .roles("ADMIN")
                .and()
                .withUser("user2")
                    .password("{sha256}8feb20fed31f3cef2b934c764fa6effc070fbb304b210ed7b8ab5f408451fb46057430668bd92c25")
                    .roles("USER")
                .and()
                .withUser("scott")
                    .password("{bcrypt10}$2a$10$0PkLdT9cRiCS7ynz24ZSeeL9xgTZFOodduC4ZtF.ftraAlsl5AkfK")
                    .roles("CUSTOMER");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return CustomPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new LdapShaPasswordEncoder();
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new StandardPasswordEncoder();
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//
//        UserDetails admin  = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user  = User.withDefaultPasswordEncoder()
//                .username("user2")
//                .password("password2")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user);

//    }

}
