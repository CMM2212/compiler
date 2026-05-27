package compiler.symbols;

import compiler.parser.ast.nodes.declarations.TypeNode;
import compiler.parser.ast.nodes.terminals.IdNode;

import java.util.HashMap;
import java.util.Map;

/**
 * A symbol representing a variable declaration.
 *
 * Stores both the type and the identifier.
 */
public class Symbol {
    public TypeNode type;
    public IdNode id;
    public String tacName;
    public static Map<String, Integer> nameCounts = new HashMap<>();

    /**
     * Creates a symbol with the given type and identifier.
     *
     * @param type TypeNode containing the basic type and array dimensions.
     * @param id IdNode containing the identifier string.
     */
    public Symbol(TypeNode type, IdNode id) {
        this.type = type;
        this.id = id;

        String base = id.id;
        int count = nameCounts.getOrDefault(base, 0);
        if (count == 0)
            this.tacName = base;
        else
            this.tacName = base + "_" + count;

        nameCounts.put(base, count + 1);
    }
}
