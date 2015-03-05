package de.techdev.test.rest;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.protocol.HttpContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.*;
import org.springframework.util.ClassUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.*;

/**
 * Convenient subclass of {@link org.springframework.web.client.RestTemplate} that is suitable for integration tests.
 * They are fault tolerant, and optionally can carry OAuth2 authentication headers. If
 * Apache Http Client 4.3.2 or better is available (recommended) it will be used as the
 * client, and by default configured to ignore cookies and redirects.
 *
 * @author Moritz Schulze
 * @author Dave Syer
 * @author Phillip Webb
 */
public class TestRestTemplate extends RestTemplate {

    /**
     * Create a new {@link TestRestTemplate} instance with the specified credentials.
     * @param token the token to use (or {@code null})
     * @param httpClientOptions client options to use if the Apache HTTP Client is used
     */
    public TestRestTemplate(String token, HttpClientOption... httpClientOptions) {
        if (ClassUtils.isPresent("org.apache.http.client.config.RequestConfig", null)) {
            setRequestFactory(new CustomHttpComponentsClientHttpRequestFactory(
                    httpClientOptions));
        }
        addAuthentication(token);
        setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
            }
        });

    }

    private void addAuthentication(String token) {
        if (token == null) {
            return;
        }
        List<ClientHttpRequestInterceptor> interceptors = Collections
                .<ClientHttpRequestInterceptor> singletonList(new BearerAuthorizationInterceptor(
                        token));
        setRequestFactory(new InterceptingClientHttpRequestFactory(getRequestFactory(),
                interceptors));
    }

    /**
     * Options used to customize the Apache Http Client if it is used.
     */
    public static enum HttpClientOption {

        /**
         * Enable cookies.
         */
        ENABLE_COOKIES,

        /**
         * Enable redirects.
         */
        ENABLE_REDIRECTS

    }

    private static class BearerAuthorizationInterceptor implements
            ClientHttpRequestInterceptor {

        private final String token;

        public BearerAuthorizationInterceptor(String token) {
            this.token = token;
        }

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                            ClientHttpRequestExecution execution) throws IOException {
            request.getHeaders().add("Authorization", "Bearer " + this.token);
            return execution.execute(request, body);
        }

    }

    protected static class CustomHttpComponentsClientHttpRequestFactory extends
            HttpComponentsClientHttpRequestFactory {

        private final String cookieSpec;

        private final boolean enableRedirects;

        public CustomHttpComponentsClientHttpRequestFactory(
                HttpClientOption[] httpClientOptions) {
            Set<HttpClientOption> options = new HashSet<>(
                    Arrays.asList(httpClientOptions));
            this.cookieSpec = (options.contains(HttpClientOption.ENABLE_COOKIES) ? CookieSpecs.STANDARD
                    : CookieSpecs.IGNORE_COOKIES);
            this.enableRedirects = options.contains(HttpClientOption.ENABLE_REDIRECTS);
        }

        @Override
        protected HttpContext createHttpContext(HttpMethod httpMethod, URI uri) {
            HttpClientContext context = HttpClientContext.create();
            context.setRequestConfig(getRequestConfig());
            return context;
        }

        protected RequestConfig getRequestConfig() {
            Builder builder = RequestConfig.custom().setCookieSpec(this.cookieSpec)
                                           .setAuthenticationEnabled(false)
                                           .setRedirectsEnabled(this.enableRedirects);
            return builder.build();
        }

    }

}
