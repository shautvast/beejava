package nl.sander.beejava;

/**
 * Only used to contain void return
 */
public final class Output {

    public static final Output VOID =new Output();

    private Output() {
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return getClass() == obj.getClass();
    }
}
