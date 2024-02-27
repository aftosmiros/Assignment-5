package models;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

// Class representing a Student entity.
public class Student {
    private int id;
    private String name;
    private String surname;
    private int age;
    private String major;

    // Arguments constructor (without id).
    public Student(String name, String surname, int age, String major) {
        setName(name);
        setSurname(surname);
        setAge(age);
        setMajor(major);
    }

    // Override toString() method for better representation.
    @Override
    public String toString() {
        return "\n==============================" +
                "\n| Student with ID: " + id +
                "\n| Full name: " + name + " " + surname +
                "\n| Age: " + age +
                "\n| Major: " + major +
                "\n==============================";
    }
}