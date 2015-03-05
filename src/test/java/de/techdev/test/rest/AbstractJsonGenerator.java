package de.techdev.test.rest;

import javax.json.Json;
import javax.json.stream.JsonGeneratorFactory;
import java.util.function.Consumer;

/**
 * Generate JSON from one of our entities. Just common operations.
 * @param <T> The entity class like Employee or Project.
 * @param <B> The type of the builder so we can return it in the base build methods with the correct type.
 */
public abstract class AbstractJsonGenerator<T, B extends AbstractJsonGenerator> {

    private T object;

    protected final JsonGeneratorFactory jsonGeneratorFactory;

    public AbstractJsonGenerator() {
        jsonGeneratorFactory = Json.createGeneratorFactory(null);
    }

    public B start() {
        reset();
        object = getNewTransientObject(500);
        return getSelf();
    }

    /**
     * With this function you can change the created object in any way you like with a lambda.
     * @param function Function to change the state of the object being generated.
     */
    public final B apply(Consumer<T> function) {
        function.accept(object);
        return getSelf();
    }

    /**
     * @return A String containing the JSON representation of the object.
     */
    public String build() {
        return getJsonRepresentation(object);
    }

    /**
     * The actual transformation to JSON.
     */
    protected abstract String getJsonRepresentation(T object);

    /**
     * Generate a new object with prefilled fields.
     * @param i Is used to add a number to fields that maybe must be unique.
     */
    protected abstract T getNewTransientObject(int i);

    /**
     * Just a technical method to get the correct type of the builder in the base builder methods..
     */
    protected abstract B getSelf();

    /**
     * If a new build starts and this builder needs a reset you can do it here.
     */
    protected void reset() {
        // default no-op
    }

}
