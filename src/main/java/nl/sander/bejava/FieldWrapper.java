package nl.sander.bejava;

import nl.sander.bejava.api.BeField;
import nl.sander.bejava.flags.AccessFlags;

import java.lang.reflect.Field;

/**
 * Union type for an existing standard java field (already loaded) or a field for the class that is being compiled
 */
public class FieldWrapper {
    private final Field reflectField;
    private final BeField beefield;

    public FieldWrapper(Field reflectField, BeField beefield) {
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
}
