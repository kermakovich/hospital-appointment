package solvd.laba.ermakovich.ha.domain.doctor;

public enum Specialization {
    DENTIST("dentist"),
    THERAPIST("therapist"),
    ENDOCRINOLOGIST("endocrinologist"),
    PHYSIOTHERAPIST("physiotherapist");

    private final String value;

    Specialization(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
