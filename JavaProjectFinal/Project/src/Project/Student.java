package Project;


public class Student extends User {
    public Student(String username, String password, String category) {
        super(username, password, category);
    }

    @Override
    public boolean authenticate(String username, String password) {
        return DBConnection.authenticateUser(username, password, "student");
    }
}



