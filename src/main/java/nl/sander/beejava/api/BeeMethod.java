package nl.sander.beejava.api;

import nl.sander.beejava.CodeContainer;
import nl.sander.beejava.flags.MethodAccessFlags;

import java.util.*;

public final class BeeMethod extends CodeContainer {
    private final Set<MethodAccessFlags> accessFlags = new HashSet<>();
    private final Set<BeeParameter> formalParameters = new HashSet<>();
    private final Class<?> returnType;

    private BeeMethod(Set<MethodAccessFlags> accessFlags,
                      List<BeeParameter> formalParameters,
                      Class<?> returnType, List<CodeLine> code) {
        this.accessFlags.addAll(accessFlags);
        this.formalParameters.addAll(formalParameters);
        this.returnType = returnType;
        super.code.addAll(code);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Set<MethodAccessFlags> accessFlags = new HashSet<>();
        private final List<BeeParameter> formalParameters = new LinkedList<>();
        private final List<CodeLine> code = new LinkedList<>();
        private Class<?> returnType;

        private Builder() {
        }

        public Builder withAccessFlags(MethodAccessFlags... accessFlags) {
            this.accessFlags.addAll(Arrays.asList(accessFlags));
            return this;
        }

        public Builder withFormalParameters(BeeParameter... formalParameters) {
            this.formalParameters.addAll(Arrays.asList(formalParameters));
            return this;
        }

        public Builder withReturnType(Class<?> returnType) {
            this.returnType = returnType;
            return this;
        }

        public Builder withCode(CodeLine... lines) {
            this.code.addAll(Arrays.asList(lines));
            return this;
        }

        public BeeMethod build() {
            return new BeeMethod(accessFlags, formalParameters, returnType, code);
        }
    }
}
