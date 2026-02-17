package model.records;

import model.enums.Nationality;

import java.time.LocalDate;

public record Coach (
    String name,
    Nationality nationality,
    LocalDate dateOfBirth
) {}
