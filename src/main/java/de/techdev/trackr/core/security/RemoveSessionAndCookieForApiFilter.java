package de.techdev.trackr.core.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;

/**
 * Filter that removes the Spring Security session and the cookie if the user sends a request against the
 * API without an OAuth token.
 *
 * This is done so that the logout button in the frontend works.
 *
 * As soon as the resource server and the authentication server are separate this filter can be removed as the resource
 * server won't know about any sessions from the authentication server.
 * 
 * @author Moritz Schulze
 */
@Slf4j
public class RemoveSessionAndCookieForApiFilter implements Filter {

    static class RequestWithoutCookie extends HttpServletRequestWrapper {

        public RequestWithoutCookie(HttpServletRequest request) {
            super(request);
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            if ("cookie".equalsIgnoreCase(name)) {
                return Collections.emptyEnumeration();
            }
            return super.getHeaders(name);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //empty
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;

        /*
            If it is an request for the API and the authorization header is not set, remove the Spring Security attribute and the
            cookie header.
         */
        if(request.getRequestURI().contains("/api") && request.getHeader("Authorization") == null) {
            log.debug("Request against the API without authorization token. Removing the session and ignoring the cookie.");
            request.getSession().removeAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
            if(request.getHeader("cookie") != null) {
                chain.doFilter(new RequestWithoutCookie(request), response);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        //empty
    }
}
