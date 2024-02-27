import GUI.StudentManagementGUI;
import controllers.StudentController;
import database.DatabaseConnection;
import database.interfaces.IDB;
import repositories.StudentRepository;

public class Main {
    public static void main(String[] args) {
        IDB db = new DatabaseConnection();
        StudentRepository studentRepository = new StudentRepository(db);
        StudentController studentController = new StudentController(studentRepository);
        StudentManagementGUI studentManagementGUI = new StudentManagementGUI(studentController);
    }
}