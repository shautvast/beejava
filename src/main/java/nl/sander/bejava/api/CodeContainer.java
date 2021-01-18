package nl.sander.bejava.api;

import nl.sander.bejava.JavaInstruction;
import nl.sander.bejava.TypeMapper;
import nl.sander.bejava.flags.MethodAccessFlag;

import java.util.*;
import java.util.stream.Collectors;

/**
 * parent of a constructor or a method.
 */
public abstract class CodeContainer {

    protected final List<CodeLine> code = new LinkedList<>();
    protected final Set<BeParameter> formalParameters = new HashSet<>();
    protected final Set<MethodAccessFlag> accessFlags = new HashSet<>();
    private final List<JavaInstruction> expandedCode = new ArrayList<>();
    private BeSource owner;

    public List<CodeLine> getCode() {
        return code;
    }

    public String getSignature() {
        return getParametersSignature() + TypeMapper.map(getReturnType());
    }

    public abstract String getName();

    public abstract Class<?> getReturnType();

    private String getParametersSignature() {
        return formalParameters.stream()
                .map(BeParameter::getType)
                .map(TypeMapper::map)
                .collect(Collectors.joining(",", "(", ")"));
    }

    public Set<MethodAccessFlag> getAccessFlags() {
        return accessFlags;
    }

    public BeSource getOwner() {
        return owner;
    }

    public void setOwner(BeSource beSource) {
        if (owner != null) {
            throw new IllegalStateException("Owner set twice. Sue the developer!");
        }
        this.owner = beSource;
    }

    public List<JavaInstruction> getExpandedCode() {
        return Collections.unmodifiableList(expandedCode);
    }

    public void setExpandedCode(List<JavaInstruction> instructions) {
        expandedCode.clear();
        expandedCode.addAll(instructions);
    }

    public Set<BeParameter> getFormalParameters() {
        return formalParameters;
    }

    public Optional<BeParameter> getParameter(String parameterName) {
        return formalParameters.stream().filter(p -> parameterName.equals(p.getName())).findAny();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeContainer that = (CodeContainer) o;
        return formalParameters.equals(that.formalParameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formalParameters);
    }
}
