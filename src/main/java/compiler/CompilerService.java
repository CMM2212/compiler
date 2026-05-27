package compiler;

import compiler.errors.exceptions.CompilerException;
import compiler.lexer.Lexer;
import compiler.parser.Parser;
import compiler.parser.ast.nodes.expressions.TempNode;
import compiler.parser.ast.nodes.terminals.LabelNode;
import compiler.prettyprinter.PrettyPrinter;
import compiler.symbols.Symbol;
import compiler.tac.IntermediateCodeGenerator;
import compiler.tac.IntermediateCodePrinter;
import compiler.typechecker.TypeChecker;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.Writer;

/**
 * The main service for handling the steps of compiling the code.
 *
 * The input text is lexed, parsed into an AST, type checked, pretty printed, converted into
 * intermediate TAC code, and then finally printed to the output writer.
 */
public class CompilerService {
    /**
     * Compile input code from the given input stream, and write the pretty printed code and
     * TAC code to the given writers.
     *
     * @param input Source code input.
     * @param prettyOutput Pretty printed code output.
     * @param tacOutput Three address code output.
     * @param filename Name of file that will be attached to error context.
     * @throws CompilerException Thrown if error during compilation. It contains context of the error.
     */
    public static void compile(InputStream input, Writer prettyOutput, Writer tacOutput, String filename) throws CompilerException {
        resetLabelCounts();
        BufferedInputStream bis = new BufferedInputStream(input);
        Lexer lexer = new Lexer(bis, filename);
        try {
            Parser parser = new Parser(lexer);
            TypeChecker typeChecker = new TypeChecker(parser);
            new PrettyPrinter(parser, prettyOutput);
            IntermediateCodeGenerator interCode = new IntermediateCodeGenerator(typeChecker);
            new IntermediateCodePrinter(interCode, tacOutput);
        } catch (CompilerException e) {
            e.context = lexer.getErrorContext();
            throw e;
        }
    }

    private static void resetLabelCounts() {
        LabelNode.label = 0;
        TempNode.num = 0;
        Symbol.nameCounts.clear();
    }
}
