package nl.sander.beejava.testclasses;

// two references to System. System should not be in the constant pool twice.
public class BeanWithClassReferences {

    public void print1() {
        System.out.println("1");
    }

    public void print2() {
        System.out.println("2");
    }

}
