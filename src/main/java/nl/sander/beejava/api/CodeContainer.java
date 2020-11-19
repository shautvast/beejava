package nl.sander.beejava.api;

import nl.sander.beejava.TypeMapper;
import nl.sander.beejava.api.BeeParameter;
import nl.sander.beejava.api.BeeSource;
import nl.sander.beejava.api.CodeLine;
import nl.sander.beejava.flags.MethodAccessFlag;

import java.util.*;
import java.util.stream.Collectors;

/**
 * parent of a constructor or a method.
 */
public abstract class CodeContainer {

    protected final List<CodeLine> code = new LinkedList<>();
    protected final Set<BeeParameter> formalParameters = new HashSet<>();
    protected final Set<MethodAccessFlag> accessFlags = new HashSet<>();
    private BeeSource owner;

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
                .map(BeeParameter::getType)
                .map(TypeMapper::map)
                .collect(Collectors.joining(",", "(", ")"));
    }

    public Set<MethodAccessFlag> getAccessFlags() {
        return accessFlags;
    }

    public BeeSource getOwner() {
        return owner;
    }

    public void setOwner(BeeSource beeSource) {
        if (owner != null) {
            throw new IllegalStateException("Owner set twice. Sue the developer!");
        }
        this.owner = beeSource;
    }

    public abstract boolean isConstructor();

    public Set<BeeParameter> getFormalParameters() {
        return formalParameters;
    }
}
