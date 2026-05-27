package compiler.errors.exceptions;

/**
 * Exception thrown during the lexical analysis phase of the compiler.
 *
 * This is for things like invalid characters and invalid tokens.
 */
public class LexicalException extends SourceException {
    /**
     * Create a new LexicalException with a message.
     *
     * @param message The message explaining the error that occurred.
     */
    public LexicalException(String message) {
        super(message);
    }

    /**
     * Return a string representation of the exception.
     *
     * @return The string "LexicalError".
     */
    public String toString() {
        return "LexicalError";
    }
}
