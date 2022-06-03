package guru.sfg.brewery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {

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


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}password").roles("ADMIN")
                .and()
                .withUser("user2").password("{noop}password2").roles("USER")
                .and()
                .withUser("scott").password("{noop}tiger").roles("CUSTOMER");
    }
}
