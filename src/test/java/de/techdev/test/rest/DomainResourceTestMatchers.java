package de.techdev.test.rest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;

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

    private static class ResultActionsMatcher extends TypeSafeMatcher<ResponseEntity> {
        private String description;
        private ConsumerWithException<ResponseEntity> test;

        protected ResultActionsMatcher(String description, ConsumerWithException<ResponseEntity> test) {
            this.description = description;
            this.test = test;
        }

        @Override
        protected boolean matchesSafely(ResponseEntity item) {
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
     * @return Matcher that checks if the HTTP status is 200.
     */
    public static Matcher<? super ResponseEntity> isAccessible() {
        return new ResultActionsMatcher("accessible", response -> assertEquals(HttpStatus.OK, response.getStatusCode()));
    }

    /**
     * @return Matcher that checks if the HTTP status is 201 and the "id" field in the returned JSON is not null.
     */
    public static Matcher<? super ResponseEntity> isCreated() {
        return new ResultActionsMatcher("created", response -> assertEquals(HttpStatus.CREATED, response.getStatusCode()));
    }

    /**
     * @return Matcher that checks if the HTTP status is 403.
     */
    public static Matcher<? super ResponseEntity> isForbidden() {
        return new ResultActionsMatcher("forbidden", response -> assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode()));
    }

    /**
     * @return Matcher that checks if the HTTP status is 200 and the "id" field in the returned JSON is not null.
     */
    public static Matcher<? super ResponseEntity> isUpdated() {
        return new ResultActionsMatcher("updated", response -> assertEquals(HttpStatus.OK, response.getStatusCode()));
    }

    /**
     * @return Matcher that checks if the HTTP status is 204.
     */
    public static Matcher<? super ResponseEntity> isNoContent() {
        return new ResultActionsMatcher("no content", request -> assertEquals(HttpStatus.NO_CONTENT, request.getStatusCode()));
    }

    /**
     * @return Matcher that checks if the HTTP status is 405.
     */
    public static Matcher<? super ResponseEntity> isMethodNotAllowed() {
        return new ResultActionsMatcher("method not allowed", response -> assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode()));
    }
}
