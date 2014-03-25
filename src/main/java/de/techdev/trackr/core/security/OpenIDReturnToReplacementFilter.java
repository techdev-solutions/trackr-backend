package de.techdev.trackr.core.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

/**
 * This filter will modify the url of the request in the case the application is currently processing an OpenID login. This has to be done so
 * it works behind a reverse proxy.
 *
 * Normally one would overwrite {@link org.springframework.security.openid.OpenIDAuthenticationFilter#buildReturnToUrl(javax.servlet.http.HttpServletRequest)}, but
 * unfortunately this is not accessible via JavaConfig.
 * @author Moritz Schulze
 */
@Slf4j
public class OpenIDReturnToReplacementFilter implements Filter {

    @Value("${proxy.path}")
    private String proxyPath;

    static class FilteredRequest extends HttpServletRequestWrapper {

        private String proxyPath;

        public FilteredRequest(HttpServletRequest request, String proxyPath) {
            super(request);
            this.proxyPath = proxyPath;
        }

        @Override
        public java.lang.StringBuffer getRequestURL() {
            StringBuffer sb = super.getRequestURL();

            int index = sb.indexOf("/login/openid");
            if (index != -1 && !proxyPath.isEmpty()) {
                log.debug("Changing the getRequestURL to inject the correct host so openid login could work behind proxy");
                log.debug("Original getRequestURL: " + sb.toString());
                sb.replace(index, sb.length(), "/" + proxyPath + "/login/openid");
                log.debug("New getRequestURL: " + sb.toString());
            }
            return sb;
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new FilteredRequest((HttpServletRequest) request, proxyPath), response);
    }

    @Override
    public void destroy() {

    }
}
