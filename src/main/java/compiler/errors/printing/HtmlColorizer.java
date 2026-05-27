package compiler.errors.printing;

/**
 * Provides HTML tags to color the HTML output properly.
 */
public class HtmlColorizer implements Colorizer{
    private static final String BLUE = "<span class=\"error-blue\">";
    private static final String RED = "<span class=\"error-red\">";
    private static final String GREEN = "<span class=\"error-green\">";
    private static final String NEUTRAL = "<span class=\"error-neutral\">";
    private static final String END = "</span>";

    @Override
    public String blue(String content) {
        return BLUE + content + END;
    }

    @Override
    public String green(String content) {
        return GREEN + content + END;
    }

    @Override
    public String red(String content) {
        return RED + content + END;
    }

    @Override
    public String neutral(String content) {
        return NEUTRAL + content + END;
    }
}
