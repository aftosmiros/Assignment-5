package GUI;

import controllers.StudentController;
import database.interfaces.IDB;
import repositories.StudentRepository;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class StudentManagementGUI extends JFrame {
    private StudentController studentController;
    private JTextField idField, nameField, surnameField, ageField, majorField, columnField, valueField, deleteField;
    private JTextArea textArea;

    public StudentManagementGUI(StudentController studentController) {
        this.studentController = studentController;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setSize(500, 500);

        // Fields
        idField = new JTextField(5);
        nameField = new JTextField(10);
        surnameField = new JTextField(10);
        ageField = new JTextField(3);
        majorField = new JTextField(10);
        columnField = new JTextField(10);
        valueField = new JTextField(10);
        deleteField = new JTextField(10);
        textArea = new JTextArea(20, 40);

        // Buttons
        JButton createButton = new JButton("Create Student");
        JButton updateButton = new JButton("Update Student");
        JButton deleteButton = new JButton("Delete Student");
        JButton getButton = new JButton("Get Student");
        JButton getAllButton = new JButton("Get All Students");

        // Listeners
        createButton.addActionListener(e -> createStudent());
        updateButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        getButton.addActionListener(e -> getStudent());
        getAllButton.addActionListener(e -> getAllStudents());

        // Adding components
        add(new JLabel("ID:"));
        add(idField);
        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Surname:"));
        add(surnameField);
        add(new JLabel("Age:"));
        add(ageField);
        add(new JLabel("Major:"));
        add(majorField);
        add(new JLabel("Column:"));
        add(columnField);
        add(new JLabel("Value:"));
        add(valueField);
        add(new JLabel("Delete IDs:"));
        add(deleteField);
        add(createButton);
        add(updateButton);
        add(deleteButton);
        add(getButton);
        add(getAllButton);
        add(new JScrollPane(textArea));

        setVisible(true);
    }

    private void createStudent() {
        String name = nameField.getText();
        String surname = surnameField.getText();
        int age = Integer.parseInt(ageField.getText());
        String major = majorField.getText();
        String result = studentController.createStudent(name, surname, age, major);
        textArea.setText(result);
    }

    private void updateStudent() {
        int id = Integer.parseInt(idField.getText());
        String columnName = columnField.getText();
        String value = valueField.getText();
        String result = studentController.updateStudent(id, columnName, value);
        textArea.setText(result);
    }

    private void deleteStudent() {
        String ids = deleteField.getText();
        studentController.deleteStudent(ids);
        // Since deleteStudent prints the result instead of returning it, we cannot display it in the textArea directly.
    }

    private void getStudent() {
        int id = Integer.parseInt(idField.getText());
        String result = studentController.getStudentById(id);
        textArea.setText(result);
    }

    private void getAllStudents() {
        String result = studentController.getAllStudents();
        if (result == null) {
            textArea.setText("No students found.");
        } else {
            textArea.setText(result);
        }
    }
}
