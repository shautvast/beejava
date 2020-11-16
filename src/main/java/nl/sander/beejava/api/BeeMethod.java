package nl.sander.beejava.api;

import nl.sander.beejava.CodeContainer;
import nl.sander.beejava.TypeMapper;
import nl.sander.beejava.flags.MethodAccessFlags;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Models a method in a BeeClass
 */
public final class BeeMethod extends CodeContainer {
    private final String name;

    private final Class<?> returnType;

    private BeeMethod(String name, Set<MethodAccessFlags> accessFlags,
                      List<BeeParameter> formalParameters,
                      Class<?> returnType, List<CodeLine> code) {
        this.name = name;
        this.accessFlags.addAll(accessFlags);
        this.formalParameters.addAll(formalParameters);
        this.returnType = returnType;
        super.code.addAll(code);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public void validate() {
        //TODO
        /*
         * here we could add checks like:
         * -If this method is in a class rather than an interface, and the name of the method is <init>, then the descriptor must denote a void method.
         * -If the name of the method is <clinit>, then the descriptor must denote avoid method, and, in a class file whose version number is 51.0 or above,a method that takes no arguments
         */
    }

    public static class Builder {
        private final Set<MethodAccessFlags> accessFlags = new HashSet<>();
        private final List<BeeParameter> formalParameters = new LinkedList<>();
        private final List<CodeLine> code = new LinkedList<>();
        private String name;
        private Class<?> returnType = Void.class;

        private Builder() {
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
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
            BeeMethod beeMethod = new BeeMethod(name, accessFlags, formalParameters, returnType, code);
            code.forEach(line -> line.setOwner(beeMethod));
            return beeMethod;
        }
    }
}
