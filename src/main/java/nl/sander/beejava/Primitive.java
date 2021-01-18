package nl.sander.beejava;

import java.util.Arrays;

public enum Primitive {
    INT, SHORT, BYTE, LONG, FLOAT, DOUBLE, CHAR, BOOLEAN, STRING;

    public static Primitive from(String value) {
        return Arrays.stream(values())
                .filter(e -> e.toString().equals(value))
                .findFirst()
                .orElse(null);
    }
}