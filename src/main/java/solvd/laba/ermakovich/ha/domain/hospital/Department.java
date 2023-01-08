package solvd.laba.ermakovich.ha.domain.hospital;

public enum Department {
    SURGERY("surgery"),
    THERAPEUTIC("therapeutic"),
    DENTAL("dental");

    private final String value;

    Department(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
