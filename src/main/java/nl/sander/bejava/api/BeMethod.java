package nl.sander.bejava.api;

import nl.sander.bejava.flags.MethodAccessFlag;

import java.util.*;

/**
 * Models a method in a BeeClass
 */
public final class BeMethod extends CodeContainer {
    private final String name;

    private final Class<?> returnType;

    public BeMethod(BeSource beSource, String name, Set<MethodAccessFlag> accessFlags,
                    Set<BeParameter> formalParameters,
                    Class<?> returnType, List<CodeLine> code) {
        this.name = name;
        this.accessFlags.addAll(accessFlags);
        this.formalParameters.addAll(formalParameters);
        this.returnType = returnType;
        super.code.addAll(code);
        setOwner(beSource);
    }

    public String getName() {
        return name;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

//    public void validate() {
//        //TODO
//        /*
//         * here we could add checks like:
//         * -If this method is in a class rather than an interface, and the name of the method is <init>, then the descriptor must denote a void method.
//         * -If the name of the method is <clinit>, then the descriptor must denote avoid method, and, in a class file whose version number is 51.0 or above,a method that takes no arguments
//         */
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BeMethod beMethod = (BeMethod) o;
        return name.equals(beMethod.name) &&
                returnType.equals(beMethod.returnType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, returnType);
    }
}
