package nl.sander.beejava.api;

/**
 * Contains the name of the package for a class
 */
public final class BeePackage {

    private final String name;

    BeePackage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
