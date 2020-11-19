package nl.sander.beejava.apiv2;

import java.util.Optional;

public enum ClassOperation {
    CLASS,
    INTERFACE,
    ENUM,
    FIELD,
    CONSTRUCTOR,
    METHOD;

    static Optional<ClassOperation> get(String text){
        String upper = text.toUpperCase();
        for (ClassOperation val: ClassOperation.values()){
            if (val.toString().equals(upper)){
                return Optional.of(val);
            }
        }
        return Optional.empty();
    }
}
