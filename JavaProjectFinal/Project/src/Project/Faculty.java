package Project;

public class Faculty extends User {
    public Faculty(String username, String password, String category) {
        super(username, password, category);
    }

    @Override
    public boolean authenticate(String username, String password) {
        return DBConnection.authenticateUser(username, password, "faculty");
    }
}