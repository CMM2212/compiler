package compiler.errors.exceptions;

/**
 * Exception thrown during the syntax analysis phase of the compiler.
 *
 * This is for things like invalid syntax and invalid grammar.
 */
public class SyntaxException extends SourceException {
    /**
     * Create a new SyntaxException with a message.
     *
     * @param message The message explaining the error that occurred.
     */
    public SyntaxException(String message) {
        super(message);
    }

    /**
     * Return a string representation of the exception.
     *
     * @return The string "SyntaxError".
     */
    public String toString() {
        return "SyntaxError";
    }
}
