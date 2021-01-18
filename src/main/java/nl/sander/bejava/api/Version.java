package nl.sander.bejava.api;

import java.util.Optional;

public enum Version {
    V1_0_2(45),
    V1_1(45),
    V1_2(46),
    V1_3(47),
    V1_4(48),
    V5_0(49),
    V6(50),
    V7(51),
    V8(52),
    V9(53),
    V10(54),
    V11(55),
    V12(56),
    V13(57),
    V14(58),
    V15(59);

    private final int major;

    Version(int major) {
        this.major = major;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor(){
        return 0;
    }

    public static Optional<Version> get(String text){
        for (Version version : Version.values()) {
            if (version.toString().equals(text)) {
                return Optional.of(version);
            }
        }
        return Optional.empty();
    }
}
