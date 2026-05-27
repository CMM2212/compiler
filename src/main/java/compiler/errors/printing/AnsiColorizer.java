package compiler.errors.printing;

/**
 * Provides ANSI escape codes to color terminal.
 */
public class AnsiColorizer implements Colorizer{
    private static final String BLUE = "\u001B[34m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String RESET = "\u001B[0m";

    @Override
    public String blue(String content) {
        return BLUE + content + RESET;
    }

    @Override
    public String green(String content) {
        return GREEN + content + RESET;
    }

    @Override
    public String red(String content) {
        return RED + content + RESET;
    }

    @Override
    public String neutral(String content) {
        return RESET + content;
    }
}
