package Project;

import java.sql.*;

public class DBConnection {
    static boolean authenticateUser(String username, String password, String category) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/AttendanceManagement", "root", "manan@$3044");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM USERDATA WHERE username=? AND password=? AND category=?")) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, category);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}