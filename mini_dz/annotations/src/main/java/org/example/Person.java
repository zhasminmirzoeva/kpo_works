package org.example;

import org.example.annotations.Published;
import lombok.Data;
import java.time.LocalDate;
import java.time.Period;

@Data
public class Person {
    @Published
    private final String firstName;
    @Published
    private final String lastName;
    @Published
    private final LocalDate birthDate;

    int getAge(){
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}