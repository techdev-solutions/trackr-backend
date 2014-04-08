package de.techdev.trackr.domain;

import de.techdev.trackr.core.web.MockMvcTest;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.springframework.test.web.servlet.ResultActions;

import static org.echocat.jomon.testing.BaseMatchers.isNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Moritz Schulze
 */
public class DomainResourceTestMatchers {

    private DomainResourceTestMatchers() {
    }

    /**
     * Functional interface for a consumer that can throw an exception.
     * @param <T> The type to consume.
     */
    @FunctionalInterface
    private static interface ConsumerWithException<T> {
        public void accept(T item) throws Exception;
    }

    private static class ResultActionsMatcher extends TypeSafeMatcher<ResultActions> {
        private String description;
        private ConsumerWithException<ResultActions> test;

        protected ResultActionsMatcher(String description, ConsumerWithException<ResultActions> test) {
            this.description = description;
            this.test = test;
        }

        @Override
        protected boolean matchesSafely(ResultActions item) {
            try {
                test.accept(item);
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        public void describeTo(Description description) {
            description.appendText(this.description);
        }
    }

    /**
     * @return Matcher that checks if the HTTP status is 200 and the content type is {@link de.techdev.trackr.core.web.MockMvcTest#STANDARD_CONTENT_TYPE}.
     */
    public static Matcher<? super ResultActions> isAccessible() {
        return new ResultActionsMatcher("accessible", resultActions -> {
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MockMvcTest.STANDARD_CONTENT_TYPE));
        });
    }

    /**
     * @return Matcher that checks if the HTTP status is 201 and the "id" field in the returned JSON is not null.
     */
    public static Matcher<? super ResultActions> returnsCreated() {
        return new ResultActionsMatcher("created", resultActions -> {
            resultActions
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("id", isNotNull()));
        });
    }

    /**
     * @return Matcher that checks if the HTTP status is 403.
     */
    public static Matcher<? super ResultActions> isForbidden() {
        return new ResultActionsMatcher("forbidden", resultActions -> {
            resultActions.andExpect(status().isForbidden());
        });
    }

    /**
     * @return Matcher that checks if the HTTP status is 200 and the "id" field in the returned JSON is not null.
     */
    public static Matcher<? super ResultActions> isUpdated() {
        return new ResultActionsMatcher("updated", resultActions -> {
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("id", isNotNull()));
        });
    }

    /**
     * @return Matcher that checks if the HTTP status is 204.
     */
    public static Matcher<? super ResultActions> isNoContent() {
        return new ResultActionsMatcher("no content", resultActions -> {
            resultActions
                    .andExpect(status().isNoContent());
        });
    }
}
