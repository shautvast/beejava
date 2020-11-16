package nl.sander.beejava.classinfo.attributes;

/**
 * see §4.7.3 The code attribute
 */
public class ExceptionHandler {
    private int startPc; //u2
    private int endPc; //u2
    private int handlerPc; //u2
    private int catchType; //u2
}
