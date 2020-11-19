package nl.sander.beejava.apiv2;

import nl.sander.beejava.flags.MethodAccessFlag;

import java.util.*;

/**
 * Models a constructor
 */
public final class BeeConstructor extends CodeContainer {

    public BeeConstructor(Set<MethodAccessFlag> accessFlags,
                          Set<BeeParameter> formalParameters,
                          List<CodeLine> code) {
        this.formalParameters.addAll(formalParameters);
        this.accessFlags.addAll(accessFlags);
        super.code.addAll(code);
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
        return Void.class;
    }

    @Override
    public boolean isConstructor() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeeConstructor that = (BeeConstructor) o;
        return formalParameters.equals(that.formalParameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formalParameters);
    }

}
