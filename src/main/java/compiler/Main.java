package compiler;

import compiler.argparse.ArgumentParser;
import compiler.errors.exceptions.*;
import compiler.errors.printing.AnsiColorizer;
import compiler.errors.printing.ErrorPrinter;

import java.io.*;

/**
 * Entry point for the compiler executable.
 *
 * Parses the command line with the expected form of "./compiler -i input.txt -o output.txt"
 *
 * The input file is compiled to three-address code and saved as the output file. Additionally,
 * the pretty printed version will be printed to the console.
 *
 * If there is a compiler error, details will be printed to the console describing what went wrong
 * and where.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        ArgumentParser argumentParser = ArgumentParser.parseArguments(args);
        try (FileInputStream input = new FileInputStream(argumentParser.inputFilename());
             FileWriter output = new FileWriter(argumentParser.outputFilename())) {
            StringWriter prettyOutput = new StringWriter();
            CompilerService.compile(input, prettyOutput, output, "main.src");
            output.flush();
            System.out.print(prettyOutput);
        } catch (SourceException e) {
                PrintWriter writer = new PrintWriter(System.out);
                ErrorPrinter.printError(e,writer, new AnsiColorizer());
                writer.flush();
        } catch (TypeException e) {
                PrintWriter writer = new PrintWriter(System.out);
                ErrorPrinter.printError(e, writer, new AnsiColorizer());
                writer.flush();
        } catch (IOException e) {
            System.out.println("Could not read input file: " + e.getMessage());
        }
    }
}
