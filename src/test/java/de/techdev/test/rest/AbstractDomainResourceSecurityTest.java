package de.techdev.test.rest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static java.util.Arrays.asList;

public abstract class AbstractDomainResourceSecurityTest extends AbstractRestIntegrationTest {

    /**
     * An SQL file that empties all tables in the trackr database.
     */
    public static final String EMPTY_DATABASE_FILE = "/de/techdev/trackr/domain/emptyDatabase.sql";

    protected abstract String getResourceName();

    private HttpEntity<String> getJsonEntity(String content) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>() {{
            put("Content-Type", asList("application/json; charset=utf-8"));
        }};
        return new HttpEntity<>(content, headers);
    }

    protected ResponseEntity root() throws Exception {
        return restTemplate.getForEntity(host + "/" + getResourceName(), String.class);
    }

    protected ResponseEntity one(Long id) throws Exception {
        return oneUrl("/" + getResourceName() + "/" + id);
    }

    protected ResponseEntity oneUrl(String url) throws Exception {
        return restTemplate.getForEntity(host + url, String.class);
    }

    protected ResponseEntity create(String payload) throws Exception {
        return restTemplate.exchange(host + "/" + getResourceName() + "/", HttpMethod.POST, getJsonEntity(payload), String.class);
    }

    protected ResponseEntity update(Long id, String payload) throws Exception {
        return restTemplate.exchange(host + "/" + getResourceName() + "/" + id, HttpMethod.PUT, getJsonEntity(payload), String.class);
    }

    /**
     * Perform a PUT on a link of a random resource (with header Content-Type: text/uri-list)
     *
     * @param linkName    The name of the link, will be appended to the URI of the resource (e.g. /company/0/contactPersons -> linkName = contactPersons).
     * @param linkContent The content to PUT, e.g. /contactPersons/0
     */
    protected ResponseEntity updateLink(Long id, String linkName, String linkContent) throws Exception {
        LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.put("Content-Type", asList("text/uri-list"));
        HttpEntity<?> request = new HttpEntity<>(linkContent, headers);
        return restTemplate.exchange(host + "/" + getResourceName() + "/" + id + "/" + linkName, HttpMethod.PUT, request, String.class);
    }

    protected ResponseEntity updateViaPatch(Long id, String patch) throws Exception {
        return restTemplate.exchange(host + "/" + getResourceName() + "/" + id, HttpMethod.PATCH, getJsonEntity(patch), String.class);
    }

    protected ResponseEntity remove(Long id) throws Exception {
        return removeUrl("/" + getResourceName() + "/" + id);
    }

    protected ResponseEntity removeUrl(String url) throws Exception {
        return restTemplate.exchange(host + url, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
    }
}