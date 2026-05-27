package compiler.errors.printing;

import compiler.errors.exceptions.SourceException;
import compiler.errors.exceptions.TypeException;

import java.io.IOException;
import java.io.Writer;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Utility class for printing compiler error messages cleanly.
 *
 * This provides colored formatting and context for error messaging when printing
 * compiler errors to the console. For lexical/syntax errors it prints the line and
 * underlines the problem, and for type errors it prints the surrounding lines as well.
 */
public class ErrorPrinter {

    /**
     * Print a lexical or syntax error message to the console.
     *
     * This will print the filename, line number, position, and underline the token that caused the error.
     * Additionally, it will print the type of error and the error message.
     *
     * Example:
     *  File "input.txt", line 4 position 8
     *         a = 1.b;
     *             ^^^
     * LexicalError: invalid decimal literal
     *
     * @param context The context of the error.
     * @param e The exception that was thrown.
     */
    public static void printError(SourceException e, Writer writer, Colorizer color) throws IOException {
        String lineText = e.context.lines.get(e.context.line);
        writer.write(
            color.blue("  File \"" + e.context.filename +"\", line ") +
            color.green(String.valueOf(e.context.line + 1)) +
            color.blue(" position ") +
            color.green(e.context.position + "\n    ") +
            color.red(lineText) +
            color.neutral("    " + " ".repeat(e.context.position) + "^".repeat(e.context.length) + "\n") +
            color.red(e + ": ") +
            color.neutral(e.getMessage()));
    }

    /**
     * Prints a type error message to the console.
     *
     * This will print the filename, line number, and the surrounding lines of the error. The
     * line containing the error will have an arrow pointed to it. Additionally, it will print
     * the type of error and the error message.
     *
     * Example:
     *   File "input.txt", line 6
     *       4
     *       5     b = a + 1;
     * ----> 6     a = b + 1;
     *       7     a = a + 5;
     *
     * TypeError: type mismatch: cannot assign 'float' to 'int'
     *
     * @param lines A list of strings from the source code representing each line.
     * @param context The context of the error.
     * @param e The exception that was thrown.
     */
    public static void printError(TypeException e, Writer writer, Colorizer color) throws IOException {
        // Retrieve the target line and calculate the surrounding lines to print, making sure to
        // not go out of bounds by using min/max.
        int targetLine = e.line;
        int firstLine = max(targetLine - 2, 0);
        int lastLine = min(targetLine + 3, e.context.lines.size());

        writer.write(
                color.blue("  File \"" + e.context.filename +"\", line ") +
                color.neutral(String.valueOf(targetLine + 1)) + "\n"
        );
        // Print the preceding lines and their line number.
        for (int i = firstLine; i < targetLine; i++) {
           writer.write(
                    color.green("      " + (i + 1) + " ") +
                    color.neutral(e.context.lines.get(i))
            );
        }
        // Print the target line with an arrow pointing to it.
        writer.write(
                color.green("----> " + (targetLine + 1) + " ") +
                color.red(e.context.lines.get(targetLine))
        );
        // Print the lines after the target line.
        for (int i = targetLine + 1; i < lastLine; i++) {
            writer.write(
                    color.green("      " + (i + 1) + " ") +
                    color.neutral(e.context.lines.get(i))
            );
        }
       writer.write(
                color.red(e + ": ") +
                color.neutral(e.getMessage()) + "\n"
        );
    }
}
