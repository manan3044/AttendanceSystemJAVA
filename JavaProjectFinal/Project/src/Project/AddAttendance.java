package Project;

import java.sql.*;
import java.util.Scanner;

public class AddAttendance {
    public static void addAttendance(int facultyID) throws DatabaseExecutionException {
        Scanner scanner = new Scanner(System.in);
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/AttendanceManagement", "root", "manan@$3044");
            if (conn != null) {
                // Display the subjects that the faculty teaches
                String sql = "SELECT CONCAT(SUBJECT_NAME, ' (', DIVI, ')') AS SUBJECT_DIV FROM TEACHINGLIST WHERE FACULTY_ID = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, facultyID);
                ResultSet rs = ps.executeQuery();

                if (!rs.next())
                {
                    System.out.println("\nNo subjects available to add attendance.Please add subject.");
                    return;
                }

                System.out.println("\nSubjects Available: ");
                do {
                    System.out.println(rs.getString("SUBJECT_DIV"));
                } while (rs.next());

                System.out.print("\nEnter the subject name: ");
                String subName = scanner.next();

                System.out.print("\nEnter the branch and class of subject: ");
                String div = scanner.next();

                String query_get = "SELECT CLASS_CONDUCTED FROM TEACHINGLIST WHERE SUBJECT_NAME = ? AND Divi = ?";
                PreparedStatement ps_get = conn.prepareStatement(query_get);
                ps_get.setString(2, div);
                ps_get.setString(1, subName);

                ResultSet rs_get = ps_get.executeQuery();
                int class_conducted = 0;
                while (rs_get.next())
                {
                    class_conducted= rs_get.getInt("CLASS_CONDUCTED");
                }

                int new_class_conducted = class_conducted +1;
                String updateGet = "UPDATE TEACHINGLIST SET CLASS_CONDUCTED = "+new_class_conducted+" WHERE SUBJECT_NAME = ? AND Divi = ?";

                try
                {
                    PreparedStatement updateAttendance = conn.prepareStatement(updateGet);
                    updateAttendance.setString(1, subName);
                    updateAttendance.setString(2, div);
                    updateAttendance.executeUpdate();
                    updateAttendance.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }

                String sql2 = "SELECT TABLE_NAME FROM CLASSES WHERE CLASS_NAME = ?";
                PreparedStatement ps2 = conn.prepareStatement(sql2);
                ps2.setString(1, div);
                String tableName = "";
                ResultSet rs2 = ps2.executeQuery();

                if (rs2.next())
                {
                    tableName = rs2.getString("TABLE_NAME");
                }
                String query = "SELECT * FROM "+tableName+"_ATTENDANCE";
                PreparedStatement stmt = conn.prepareStatement(query);

                ResultSet rs3 = stmt.executeQuery();

                while (rs3.next()) {

                    String name = rs3.getString("STUDENT_NAME"); //name column
                    System.out.println("Student Name : "+name); // Print the name
                    System.out.println("Enter status : ");
                    int status = scanner.nextInt();

                    if (status > 1)
                    {
                        status = 1;
                    }
                    else if(status < 0)
                    {
                        status = 0;
                    }

                    int attendance = rs3.getInt(subName);
                    String prn = rs3.getString("PRN");

                    int updatedAttendance = status+attendance;


                    String currentAttendanceQuery = "SELECT " + subName + " FROM " + tableName + "_ATTENDANCE WHERE PRN = ?";
                    String updateQuery = "UPDATE "+tableName+"_ATTENDANCE SET " + subName + " = ? WHERE PRN = ?";

                    try {

                        PreparedStatement getCurrentAttendance = conn.prepareStatement(currentAttendanceQuery);
                        getCurrentAttendance.setString(1, prn);
                        ResultSet rs4 = getCurrentAttendance.executeQuery();

                        int currentAttendance = 0;
                        if (rs4.next()) {
                            currentAttendance = rs4.getInt(1);
                        }
                        rs4.close();
                        getCurrentAttendance.close();


                        int newAttendance = currentAttendance + status;


                        PreparedStatement updateAttendance = conn.prepareStatement(updateQuery);
                        updateAttendance.setInt(1, newAttendance);
                        updateAttendance.setString(2, prn);
                        updateAttendance.executeUpdate();

                        updateAttendance.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    PreparedStatement up_stmt = conn.prepareStatement(updateQuery);
                    up_stmt.setInt(1, updatedAttendance);
                    up_stmt.setString(2, prn);

                    up_stmt.executeUpdate();
            }
            }
        }
        catch (SQLException ex) {
            throw new DatabaseExecutionException("An error occurred while executing database operation", ex);
        }
        catch (Exception ex) {
            System.out.println("An error occurred while connecting to the database");
            ex.printStackTrace();}}
}
