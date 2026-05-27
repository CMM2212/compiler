package compiler.errors.exceptions;

import compiler.errors.ErrorContext;

/**
 * Base class for all compiler exceptions.
 *
 * This provides a common base class for all exceptions that are thrown by the compiler.
 *
 * It extends from RuntimeException to simplify the exception handling.
 */
public class CompilerException extends RuntimeException{
    public ErrorContext context;
    /**
     * Create a new CompilerException with a message.
     *
     * @param message The message explaining the error that occurred.
     */
    public CompilerException(String message) {
        super(message);
    }

    /**
     * Create a new CompilerException with a message.
     *
     * @param message The message explaining the error that occurred.
     */
    public CompilerException(String message, ErrorContext context) {
        super(message);
        this.context = context;
    }

    /**
     * Return a string representation of the exception.
     *
     * @return The string "CompilerError".
     */
    public String toString() {
        return "CompilerError";
    }
}
