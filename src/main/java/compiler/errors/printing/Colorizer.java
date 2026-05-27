package compiler.errors.printing;

/**
 * Provides the colorizing text to wrap string content in for
 * error outputs. Such as ANSI escape codes for the terminal,
 * or HTML tags for web output.
 */
public interface Colorizer {
    String blue(String content);
    String green(String content);
    String red(String content);
    String neutral(String content);
}
