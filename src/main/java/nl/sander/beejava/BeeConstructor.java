package nl.sander.beejava;

import nl.sander.beejava.flags.MethodAccessFlag;

import java.util.*;

public class BeeConstructor implements ContainsCode{
    private final Set<MethodAccessFlag> accessFlags = new HashSet<>();
    private final Set<BeeParameter> formalParameters = new HashSet<>();
    private final List<CodeLine> code = new LinkedList<>();

    private BeeConstructor(Set<MethodAccessFlag> accessFlags,
                           List<BeeParameter> formalParameters,
                           List<CodeLine> code) {
        this.formalParameters.addAll(formalParameters);
        this.accessFlags.addAll(accessFlags);
        this.code.addAll(code);
    }

    public static Builder builder() {
        return new Builder();
    }

    Set<MethodAccessFlag> getAccessFlags() {
        return accessFlags;
    }

    Set<BeeParameter> getFormalParameters() {
        return formalParameters;
    }

    @Override
    public List<CodeLine> getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "BeeConstructor{" +
                "formalParameters=" + formalParameters +
                '}';
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
        private final Set<MethodAccessFlag> accessFlags = new HashSet<>();
        private final List<BeeParameter> formalParameters = new LinkedList<>();
        private final List<CodeLine> code = new LinkedList<>();

        private Builder() {

        }

        public Builder withFormalParameters(BeeParameter... formalParameters) {
            this.formalParameters.addAll(Arrays.asList(formalParameters));
            return this;
        }

        public Builder withAccessFlags(MethodAccessFlag... accessFlags) {
            this.accessFlags.addAll(Arrays.asList(accessFlags));
            return this;
        }

        public Builder withCode(CodeLine... lines) {
            this.code.addAll(Arrays.asList(lines));
            return this;
        }

        public BeeConstructor build() {
            return new BeeConstructor(accessFlags, formalParameters, code);
        }
    }
}
