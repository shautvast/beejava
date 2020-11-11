package nl.sander.beejava;

import nl.sander.beejava.api.CodeLine;

import java.util.LinkedList;
import java.util.List;

/**
 * parent of a constructor or a method.
 */
public abstract class CodeContainer {

    protected final List<CodeLine> code = new LinkedList<>();

    public List<CodeLine> getCode() {
        return code;
    }
}
