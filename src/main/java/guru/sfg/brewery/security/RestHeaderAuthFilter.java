package guru.sfg.brewery.security;

import guru.sfg.brewery.security.utility.AuthenticationUtilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class RestHeaderAuthFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationUtilities authenticationUtilities;
    public RestHeaderAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher, AuthenticationUtilities authenticationUtilities) {
        super(requiresAuthenticationRequestMatcher);
        this.authenticationUtilities = authenticationUtilities;
    }

    @Override
    public void doFilter(ServletRequest req,
                         ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        Authentication authResult = attemptAuthentication(request, response);
        authenticationUtilities.doFilterUtil(request, response, chain, authResult);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        authenticationUtilities.successfulAuthenticationUtil(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {

        authenticationUtilities.unsuccessfulAuthenticationUtil(request, response, failed);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        String username = getUserName(request);
        String password = getPassword(request);

        if (username == null){
            username = "";
        }

        if (password == null){
            password = "";
        }

        log.debug("Authenticating User : " + username);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        if (!StringUtils.isEmpty(username)){
            return this.getAuthenticationManager().authenticate(token);
        }
        else {
            return null;
        }
    }

    private String getUserName(HttpServletRequest request) {
        return request.getHeader("Api-Key");
    }

    private String getPassword(HttpServletRequest request) {
        return request.getHeader("Api-Secret");
    }
}