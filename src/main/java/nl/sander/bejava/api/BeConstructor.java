package nl.sander.bejava.api;

import nl.sander.bejava.flags.MethodAccessFlag;

import java.util.*;

/**
 * Models a constructor
 */
public final class BeConstructor extends CodeContainer {

    public BeConstructor(BeSource beSource, Set<MethodAccessFlag> accessFlags,
                         Set<BeParameter> formalParameters,
                         List<CodeLine> code) {
        this.formalParameters.addAll(formalParameters);
        this.accessFlags.addAll(accessFlags);
        super.code.addAll(code);
        setOwner(beSource);
    }

    public String getName() {
        return "<init>";
    }

    @Override
    public String toString() {
        return "BeeConstructor{" +
                "formalParameters=" + formalParameters +
                '}';
    }

    public Class<?> getReturnType() {
        return void.class;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (BeConstructor) o;
        return formalParameters.equals(that.formalParameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formalParameters);
    }

}
