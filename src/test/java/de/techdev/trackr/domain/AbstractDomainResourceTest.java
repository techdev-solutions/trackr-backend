package de.techdev.trackr.domain;

import de.techdev.trackr.core.web.MockMvcTest;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.ResultActions;

import java.util.function.Function;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * @author Moritz Schulze
 */
public abstract class AbstractDomainResourceTest<T> extends MockMvcTest {

    @Autowired
    protected AbstractDataOnDemand<T> dataOnDemand;

    @Autowired
    protected CrudRepository<T, Long> repository;

    protected abstract String getResourceName();

    protected abstract String getJsonRepresentation(T item);

    @Before
    public void setUp() throws Exception {
        dataOnDemand.init();
    }

    /**
     * Access the root of a resource via GET.
     *
     * @param session The mock session to use, e.g. admin or employee
     * @return The result actions to perform further tests on.
     * @throws Exception
     */
    protected ResultActions root(MockHttpSession session) throws Exception {
        return mockMvc.perform(
                get("/" + getResourceName())
                        .session(session)
        );
    }

    /**
     * Access a single random object via GET.
     *
     * @param session The mock session to use, e.g. admin or employee
     * @return The result actions to perform further tests on.
     * @throws Exception
     */
    protected ResultActions one(MockHttpSession session) throws Exception {
        return one((object) -> session);
    }

    /**
     * Access a single random object via GET.
     *
     * @param sessionProvider Converts the random object to a {@link org.springframework.mock.web.MockHttpSession}. This can be used to set the session to a specific employee.
     * @return The result actions to perform further tests on.
     * @throws Exception
     */
    protected ResultActions one(Function<T, MockHttpSession> sessionProvider) throws Exception {
        T randomObject = dataOnDemand.getRandomObject();
        return oneUrl(sessionProvider.apply(randomObject), "/" + getResourceName() + "/" + dataOnDemand.getId(randomObject));
    }

    /**
     * Access a URL via GET.
     *
     * @param session The The mock session to use, e.g. admin or employee
     * @param url     The URL to access.
     * @return The result actions to perform further tests on.
     * @throws Exception
     */
    protected ResultActions oneUrl(MockHttpSession session, String url) throws Exception {
        return mockMvc.perform(
                get(url)
                        .session(session)
        );
    }

    /**
     * Get a new transient object and try to POST it to the resource path.
     *
     * @param session The mock session to use, e.g. admin or employee
     * @return The result actions to perform further tests on.
     * @throws Exception
     */
    protected ResultActions create(MockHttpSession session) throws Exception {
        return create((object) -> session);
    }

    /**
     * Get a new transient object and try to POST it to the resource path.
     *
     * @param sessionProvider Converts the random object to a {@link org.springframework.mock.web.MockHttpSession}. This can be used to set the session to a specific employee.
     * @return The result actions to perform further tests on.
     * @throws Exception
     */
    protected ResultActions create(Function<T, MockHttpSession> sessionProvider) throws Exception {
        T newObject = dataOnDemand.getNewTransientObject(500);
        return mockMvc.perform(
                post("/" + getResourceName() + "/")
                        .session(sessionProvider.apply(newObject))
                        .content(getJsonRepresentation(newObject))
        );
    }

    /**
     * Get a random object and try to PUT it to the resource path.
     *
     * @param session The mock session to use, e.g. admin or employee
     * @return The result actions to perform further tests on.
     * @throws Exception
     */
    protected ResultActions update(MockHttpSession session) throws Exception {
        return update((object) -> session);
    }

    /**
     * Get a random object and try to PUT it to the resource path
     *
     * @param sessionProvider Converts the random object to a {@link org.springframework.mock.web.MockHttpSession}. This can be used to set the session to a specific employee.
     * @return The result actions to perform further tests on.
     * @throws Exception
     */
    protected ResultActions update(Function<T, MockHttpSession> sessionProvider) throws Exception {
        T randomObject = dataOnDemand.getRandomObject();
        return mockMvc.perform(
                put("/" + getResourceName() + "/" + dataOnDemand.getId(randomObject))
                        .session(sessionProvider.apply(randomObject))
                        .content(getJsonRepresentation(randomObject))
        );
    }

    /**
     * Perform a PUT on a link of a random resource (with header Content-Type: text/uri-list)
     *
     * @param session     The mock session to use, e.g. admin or employee
     * @param linkName    The name of the link, will be appended to the URI of the resource (e.g. /company/0/contactPersons -> linkName = contactPersons).
     * @param linkContent The content to PUT, e.g. /contactersons/0
     * @return The result actions to perform further tests on.
     * @throws Exception
     */
    protected ResultActions updateLink(MockHttpSession session, String linkName, String linkContent) throws Exception {
        return updateLink((object) -> session, linkName, linkContent);
    }

    /**
     * Perform a PUT on a link of a random resource (with header Content-Type: text/uri-list)
     *
     * @param sessionProvider Converts the random object to a {@link org.springframework.mock.web.MockHttpSession}. This can be used to set the session to a specific employee.
     * @param linkName    The name of the link, will be appended to the URI of the resource (e.g. /company/0/contactPersons -> linkName = contactPersons).
     * @param linkContent The content to PUT, e.g. /contactersons/0
     * @return The result actions to perform further tests on.
     * @throws Exception
     */
    protected ResultActions updateLink(Function<T, MockHttpSession> sessionProvider, String linkName, String linkContent) throws Exception {
        T randomObject = dataOnDemand.getRandomObject();
        return mockMvc.perform(
                put("/" + getResourceName() + "/" + dataOnDemand.getId(randomObject) + "/" + linkName)
                        .session(sessionProvider.apply(randomObject))
                        .header("Content-Type", "text/uri-list")
                        .content(linkContent)
        );
    }

    /**
     * Get a random object and try to PATCH with the given string it to the resource path.
     *
     * @param session The mock session to use, e.g. admin or employee
     * @return The result actions to perform further tests on.
     * @throws Exception
     */
    protected ResultActions updateViaPatch(MockHttpSession session, String patch) throws Exception {
        T randomObject = dataOnDemand.getRandomObject();
        return mockMvc.perform(
                patch("/" + getResourceName() + "/" + dataOnDemand.getId(randomObject))
                        .session(session)
                        .content(patch)
        );
    }

    /**
     * Get a random object and try to DELETE it.
     *
     * @param session The mock session to use, e.g. admin or employee
     * @return The result actions to perform further tests on.
     * @throws Exception
     */
    protected ResultActions remove(MockHttpSession session) throws Exception {
        return remove((object) -> session);
    }

    /**
     * Get a random object and try to DELETE it.
     *
     * @param sessionProvider Converts the random object to a {@link org.springframework.mock.web.MockHttpSession}. This can be used to set the session to a specific employee.
     * @return The result actions to perform further tests on.
     * @throws Exception
     */
    protected ResultActions remove(Function<T, MockHttpSession> sessionProvider) throws Exception {
        T randomObject = dataOnDemand.getRandomObject();
        return removeUrl(sessionProvider.apply(randomObject), "/" + getResourceName() + "/" + dataOnDemand.getId(randomObject));
    }

    /**
     * Perform a DELETE on a URL.
     *
     * @param session The mock session to use, e.g. admin or employee
     * @param url     The URL to access.
     * @return The result actions to perform further tests on.
     * @throws Exception
     */
    protected ResultActions removeUrl(MockHttpSession session, String url) throws Exception {
        return mockMvc.perform(
                delete(url)
                        .session(session)
        );
    }
}
