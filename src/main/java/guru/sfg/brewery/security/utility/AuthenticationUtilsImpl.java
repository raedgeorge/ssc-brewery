package guru.sfg.brewery.security.utility;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Service
public class AuthenticationUtilsImpl implements AuthenticationUtilities{

    public void doFilterUtil(HttpServletRequest request,
                             HttpServletResponse response,
                             FilterChain chain,
                             Authentication authResult) throws IOException {

        try {
            if (authResult != null){
                successfulAuthenticationUtil(request, response, chain, authResult);
            }
            else {
                chain.doFilter(request, response);
            }

        }
        catch (AuthenticationException exception){
            unsuccessfulAuthenticationUtil(request, response, exception);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void successfulAuthenticationUtil(HttpServletRequest request,
                                             HttpServletResponse response,
                                             FilterChain chain,
                                             Authentication authResult) {

        SecurityContextHolder.getContext().setAuthentication(authResult);

    }

    @Override
    public void unsuccessfulAuthenticationUtil(HttpServletRequest request,
                                               HttpServletResponse response,
                                               AuthenticationException exception) throws IOException {

        SecurityContextHolder.clearContext();

        response.sendError(HttpStatus.UNAUTHORIZED.value(),
                           HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }
}
