package compiler;

import compiler.errors.exceptions.SourceException;
import compiler.errors.exceptions.TypeException;
import compiler.errors.printing.ErrorPrinter;
import compiler.errors.printing.HtmlColorizer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import org.teavm.jso.JSExport;

/**
 * Entry point for web version of the compiler.
 */
public class WebMain {
    /**
     * Compile the given string to TAC code and return a JSON string containing the pretty printed code
     * and TAC code if successful, or an error message if not.
     *
     * @param inputCode Source code text to be compiled.
     * @return JSON String containing pretty printed text and TAC code if successful.
     */
    @JSExport
    public static String compile(String inputCode) {
        try {
            try {
                ByteArrayInputStream bis = new ByteArrayInputStream(inputCode.getBytes(StandardCharsets.UTF_8));
                StringWriter prettyOutput = new StringWriter();
                StringWriter tacOutput = new StringWriter();
                CompilerService.compile(bis, prettyOutput, tacOutput, "main.src");
                return jsonSuccess(prettyOutput.toString(), tacOutput.toString());
            } catch (SourceException e) {
                StringWriter output = new StringWriter();
                ErrorPrinter.printError(e, output, new HtmlColorizer());
                return jsonFailure(output.toString());
            } catch (TypeException e) {
                StringWriter output = new StringWriter();
                ErrorPrinter.printError(e, output, new HtmlColorizer());
                return jsonFailure(output.toString());
            }
        } catch (IOException e) {
                StringWriter output = new StringWriter();
                output.append(e.getMessage());
                return jsonFailure(output.toString());
        }
    }

    private static String jsonSuccess(String pretty, String tac) {
        return "{\"success\":true,\"pretty\":" + stringToJson(pretty) +
                ",\"tac\":" + stringToJson(tac) + "}";
    }

    private static String jsonFailure(String error) {
        return "{\"success\":false,\"error\":" + stringToJson(error) + "}";
    }
    private static String stringToJson(String s) {
        return "\"" + s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n") + "\"";
    }
}
