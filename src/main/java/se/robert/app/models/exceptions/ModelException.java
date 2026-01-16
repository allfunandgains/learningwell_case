package se.robert.app.models.exceptions;

/**
 * Exception thrown when the model cannot complete an operation due to
 * invalid input, missing data, or other domain-related errors.
 * This exception is intended to be caught by the controller layer,
 * where the error can be presented to the user in an appropriate way.
 */
public class ModelException extends Exception {

    /**
     * Constructs the exception with the specified message.
     * @param message a String describing the error.
     */
    public ModelException(String message) {
        super(message);
    }
}
