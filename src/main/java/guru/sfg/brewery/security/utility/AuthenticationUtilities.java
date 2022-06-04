package guru.sfg.brewery.security.utility;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface AuthenticationUtilities {

    void doFilterUtil(HttpServletRequest request,
                      HttpServletResponse response,
                      FilterChain chain,
                      Authentication authResult) throws IOException;

    void successfulAuthenticationUtil(HttpServletRequest request,
                                      HttpServletResponse response,
                                      FilterChain chain, Authentication authResult);

    void unsuccessfulAuthenticationUtil(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException;
}
