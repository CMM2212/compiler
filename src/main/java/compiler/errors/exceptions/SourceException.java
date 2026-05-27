package compiler.errors.exceptions;

/**
 * Exception thrown during the syntax analysis phase of the compiler.
 *
 * This is for things like invalid syntax and invalid grammar.
 */
public abstract class SourceException extends CompilerException {
    public SourceException(String message) {
        super(message);
    }
}
