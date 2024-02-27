package controllers;

import models.Student;
import repositories.StudentRepository;

import java.util.List;

public class StudentController {
    private final StudentRepository studentRepository;

    // There is no need to import lombok to create only one constructor.
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public boolean checkStudentExistence(int id) { // method to check does user exist in database.
        return studentRepository.getById(id) != null; // if exists then it not null and return will be 1 (true) and vice-versa.
    }

    public String createStudent(String name, String surname, int age, String major) {
        Student student = new Student(name, surname, age, major);
        boolean created = studentRepository.createRecord(student);

        return (created ? "\nStudent " + student.getName() + " " + student.getSurname() + " created successfully."
                        : "\nStudent creation was failed!");
    }

    public String updateStudent(int id, String columnName, Object value) {
        if (checkStudentExistence(id)) {
            boolean updated = studentRepository.updateRecord(id, columnName, value);

            if (updated)
                return "\nStudent's information with ID " + id + " in " + columnName + " updated successfully.";
            else return "\nUpdate was failed! Press Enter to continue.";

        } else return "\nStudent with ID " + id + " not found.";
    }

    public void deleteStudent(String input) {
        String regex1 = "^\\d+(, \\d+)*$"; // For case if user enters something like "27, 28"
        String regex2 = "^\\d+(,\\d+)*$"; // For case if user enters something like "27,28"

        if (!input.matches(regex1) & !input.matches(regex2)) {
            System.err.println("\nIncorrect input. Enter IDs (or one ID) separated by comma.");
        } else {
            StringBuilder response = new StringBuilder();
            String[] studentIdsString = input.split(",");

            int[] studentIds = new int[studentIdsString.length];
            for (int i = 0; i < studentIdsString.length; i++) {
                studentIds[i] = Integer.parseInt(studentIdsString[i].trim());
            }

            for (int studentId : studentIds) {
                if (checkStudentExistence(studentId)) {
                    boolean deleted = studentRepository.deleteRecord(studentId);
                    if (deleted)
                        response.append("\nStudent with ID ").append(studentId).append(" deleted successfully.");
                    else
                        response.append("\nDelete of student with ID ").append(studentId).append(" was failed!");
                } else
                    response.append("\nStudent with ID ").append(studentId).append(" not found.");
            }
        }
    }


    public String getStudentById(int id) {
        Student student = studentRepository.getById(id);

        return (student == null ? "\nStudent with ID " + id + " not found!" : student.toString());
    }

    public String getAllStudents() {
        List<Student> students = studentRepository.getAll();
        if (students.isEmpty()) {
            return null;

        } else {
            StringBuilder response = new StringBuilder();
            for (Student student : students) {
                response.append(student.toString()).append("\n");
            }

            return response.toString();
        }
    }
}
