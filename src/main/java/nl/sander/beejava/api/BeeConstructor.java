package nl.sander.beejava.api;

import nl.sander.beejava.CodeContainer;
import nl.sander.beejava.flags.MethodAccessFlags;

import java.util.*;

/**
 * Models a constructor
 */
public final class BeeConstructor extends CodeContainer {

    private BeeConstructor(Set<MethodAccessFlags> accessFlags,
                           List<BeeParameter> formalParameters,
                           List<CodeLine> code) {
        this.formalParameters.addAll(formalParameters);
        this.accessFlags.addAll(accessFlags);
        super.code.addAll(code);
    }

    public static Builder builder() {
        return new Builder();
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

    public static class Builder {
        private final Set<MethodAccessFlags> accessFlags = new HashSet<>();
        private final List<BeeParameter> formalParameters = new LinkedList<>();
        private final List<CodeLine> code = new LinkedList<>();

        private Builder() {

        }

        public Builder withFormalParameters(BeeParameter... formalParameters) {
            this.formalParameters.addAll(Arrays.asList(formalParameters));
            return this;
        }

        public Builder withAccessFlags(MethodAccessFlags... accessFlags) {
            this.accessFlags.addAll(Arrays.asList(accessFlags));
            return this;
        }

        public Builder withCode(CodeLine... lines) {
            this.code.addAll(Arrays.asList(lines));
            return this;
        }

        public BeeConstructor build() {
            BeeConstructor beeConstructor = new BeeConstructor(accessFlags, formalParameters, code);
            code.forEach(line -> line.setOwner(beeConstructor));
            return beeConstructor;
        }
    }
}
