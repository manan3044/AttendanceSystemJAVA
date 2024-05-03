package Project;

import java.sql.*;
import java.util.Scanner;

public class SearchStudent {


    public static void searchPRN(String prnToSearch) {
        Scanner scanner = new Scanner(System.in);
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/AttendanceManagement", "root", "manan@$3044");
            if (conn != null) {
                String sql = "SELECT DIVI FROM STUDENT_LIST WHERE PRN = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, prnToSearch);
                String div = "";
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    div = rs.getString("DIVI");
                }

                String sql2 = "SELECT TABLE_NAME FROM CLASSES WHERE CLASS_NAME = ?";
                PreparedStatement ps2 = conn.prepareStatement(sql2);
                ps2.setString(1, div);
                String tableName = "";
                ResultSet rs2 = ps2.executeQuery();
                if (rs2.next()) {
                    tableName = rs2.getString("TABLE_NAME");
                }

                String query = "SELECT * FROM " + tableName + "_ATTENDANCE WHERE PRN = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, prnToSearch);

                ResultSet rs3 = stmt.executeQuery();

                String query_get = "SELECT CLASS_CONDUCTED FROM TEACHINGLIST WHERE Divi = ?";
                PreparedStatement ps_get = conn.prepareStatement(query_get);
                ps_get.setString(1, div);
                ResultSet rs_get = ps_get.executeQuery();
                int class_conducted = 0;
                while (rs_get.next())
                {
                    class_conducted= rs_get.getInt("CLASS_CONDUCTED") + class_conducted;
                }

                int totalAttendance = 0;

                if (rs3.next()) {
                    ResultSetMetaData rsmd = rs3.getMetaData();
                    int columnCount = rsmd.getColumnCount();

                    System.out.println("\nPRN: " + rs3.getString("PRN"));
                    System.out.println("Name: " + rs3.getString("STUDENT_NAME"));

                    System.out.println("\nSubject Wise Attendance: ");

                    for (int i = 3; i <= columnCount; i++) {
                        String columnName = rsmd.getColumnName(i);
                        int attendance = rs3.getInt(columnName);
                        totalAttendance = attendance+totalAttendance;
                        System.out.println("Attendance in " + columnName + ": " + attendance);
                    }

                    System.out.println("\nTotal Classes Attended: "+totalAttendance);
                    System.out.println("Total Classes Conducted: "+class_conducted);
                    double attendancePercent = (totalAttendance / (double) class_conducted) * 100;
                    if (attendancePercent > 100)
                    {
                        attendancePercent = 100;
                    }
                    String formattedAttendancePercent = String.format("%.2f", attendancePercent);
                    System.out.println("Total Attendance Percent: " + formattedAttendancePercent +"%");
                } else {
                    System.out.println("No student found with PRN: " + prnToSearch);
                }
            }
        } catch (SQLException ex) {
            System.out.println("An error occurred while connecting to the database");
            ex.printStackTrace();
        }
    }
}
