package nl.sander.bejava.api;

import java.util.Optional;

public enum ClassOperation {
    CLASS,
    INTERFACE,
    ENUM,
    FIELD,
    CONSTRUCTOR,
    METHOD;

    public static Optional<ClassOperation> get(String text){
        String upper = text.toUpperCase();
        for (ClassOperation val: ClassOperation.values()){
            if (val.toString().equals(upper)){
                return Optional.of(val);
            }
        }
        return Optional.empty();
    }
}
