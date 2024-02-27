package repositories;

import database.interfaces.IDB;
import exception.ErrorHandler;
import models.Student;
import repositories.interfaces.IRepository;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

// StudentRepository class handles database operations related to students.
public class StudentRepository implements IRepository {
    private final IDB databaseConnection;
    private Connection connection = null; // Default value of connection.

    public StudentRepository(IDB databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    // Create a new student record in the database.
    @Override
    public boolean createRecord(Student student) {
        try {
            connection = databaseConnection.getConnection();
            String query = "INSERT INTO students(name, surname, age, major) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, student.getName());
            statement.setString(2, student.getSurname());
            statement.setInt(3, student.getAge());
            statement.setString(4, student.getMajor());

            statement.executeUpdate();

            return true;

        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } finally {
            getFinallyBlock(connection);
        }

        return false; // if creation went wrong.
    }

    // Update an existing student record in the database.
    @Override
    public boolean updateRecord(int id, String columnName, Object value) {

        try {
            connection = databaseConnection.getConnection();
            String query = "UPDATE students SET " + columnName + " = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Depending on the type of value, we use the appropriate set() method.
            if (value instanceof String)
                preparedStatement.setString(1, (String) value);
            else if (value instanceof Integer)
                preparedStatement.setInt(1, (Integer) value);

            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } finally {
            getFinallyBlock(connection);
        }

        return false; // if updating went wrong.
    }

    // Delete student records from the database.
    @Override
    public boolean deleteRecord(int... ids) {
        try {
            connection = databaseConnection.getConnection();
            String query = "DELETE FROM students WHERE id IN ("; // Start of the query.
            for (int i = 0; i < ids.length; i++) {
                query += i == 0 ? "?" : ", ?"; // amount of id gives us the same amount of '?'.
            }
            query += ")"; // End of the query.

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            for (int i = 0; i < ids.length; i++) {
                preparedStatement.setInt(i + 1, ids[i]); // setting one or many id to prepared statement.
            }
            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } finally {
            getFinallyBlock(connection);
        }

        return false;
    }

    // Retrieve a student record by ID from the database.
    @Override
    public Student getById(int id) {
        try {
            connection = databaseConnection.getConnection();
            String query = "SELECT * FROM students WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSet(resultSet);
            }

        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } finally {
            getFinallyBlock(connection);
        }

        return null;
    }

    // Retrieve all student records from the database.
    @Override
    public List<Student> getAll() {
        try {
            connection = databaseConnection.getConnection();
            String query = "SELECT * FROM students";
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
            List<Student> students = new LinkedList<>();

            while (resultSet.next()) {
                Student student = mapResultSet(resultSet);

                students.add(student);
            }

            return students;

        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } finally {
            getFinallyBlock(connection);
        }

        return null;
    }

    // Map a ResultSet to a Student object.
    @Override
    public Student mapResultSet(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setId(resultSet.getInt("id"));
        student.setName(resultSet.getString("name"));
        student.setSurname(resultSet.getString("surname"));
        student.setAge(resultSet.getInt("age"));
        student.setMajor(resultSet.getString("major"));

        return student;
    }

    // Closes the database connection.
    @Override
    public void getFinallyBlock(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        }
    }
}