package nl.sander.beejava;

import nl.sander.beejava.api.BeeField;
import nl.sander.beejava.flags.AccessFlags;

import java.lang.reflect.Field;

/**
 * Union type for an existing standard java field (already loaded) or a field for the class that is being compiled
 */
public class FieldWrapper {
    private final Field reflectField;
    private final BeeField beefield;

    public FieldWrapper(Field reflectField, BeeField beefield) {
        this.reflectField = reflectField;
        this.beefield = beefield;
    }

    public Class<?> getType() {
        if (reflectField != null) {
            return reflectField.getType();
        } else {
            return beefield.getType();
        }
    }

    public String getName() {
        if (reflectField != null) {
            return reflectField.getName();
        } else {
            return beefield.getName();
        }
    }

    public int getModifiers() {
        if (reflectField != null) {
            return reflectField.getModifiers();
        } else {
            return AccessFlags.combine(beefield.getAccessFlags());
        }
    }

    public String getOwner() {
        if (reflectField != null) {
            return reflectField.getDeclaringClass().getName();
        } else {
            return beefield.getDeclaringClass();
        }
    }
}
