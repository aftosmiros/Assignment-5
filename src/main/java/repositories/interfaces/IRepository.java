package repositories.interfaces;

import models.Student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface IRepository {
    boolean createRecord(Student student); // to create a new record in Database.
    boolean updateRecord(int id, String columnName, Object value); // to update (change) information on some column in DB.
    boolean deleteRecord(int... ids); // to delete one or several records in some table from the Database.
    Student getById(int id); // to get record by id from some table in Database.
    List<Student> getAll(); // to get all record from some table in Database.
    Student mapResultSet(ResultSet resultSet) throws SQLException; // to create new object of some Class with arguments from DB.
    void getFinallyBlock(Connection connection); /* This method is created to optimize the code by adding it in finally
                                                block in every method. They are all the same in every method.*/
}