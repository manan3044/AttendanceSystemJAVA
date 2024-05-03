package Project;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class AddSubject {
    public static void alterTable(String division, String subName)  throws DatabaseException
    {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/AttendanceManagement", "root", "manan@$3044");
            if (conn != null) {
                String sql = "SELECT TABLE_NAME FROM CLASSES WHERE CLASS_NAME = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, division);
                String tableName = "";
                ResultSet rs = ps.executeQuery();
                if (rs.next())
                {
                    tableName = rs.getString("TABLE_NAME");
                }

                String alterQuery = "ALTER TABLE "+ tableName + "_ATTENDANCE ADD COLUMN " + subName + " INT DEFAULT 0";
                Statement statement = conn.createStatement();

                statement.executeUpdate(alterQuery);

            }
        }catch (SQLException ex) {
            throw new DatabaseException("An error occurred while altering the table", ex);
        }
        catch (Exception ex) {
            ex.printStackTrace();}
    }


    public static void addSubject(int facultyID) throws Exception, InvalidInputException {
        Scanner scanner = new Scanner(System.in);
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/AttendanceManagement", "root", "manan@$3044");
            if (conn != null) {

                // Prepare the SQL statement
                String sql = "INSERT INTO TEACHINGLIST (FACULTY_ID, SUBJECT_NAME, divi) VALUES (?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);

                while (true) {
                    System.out.print("Enter Subject Name: ");
                    String subName = scanner.next();

                    System.out.print("Enter Branch and Division: ");
                    String divi = scanner.next();


                    ps.setInt(1, facultyID);
                    ps.setString(2, subName);
                    ps.setString(3, divi);

                    ps.executeUpdate();
                    alterTable(divi, subName);

                    System.out.print("Do you want to enter another subject? (1 for Yes, 0 for No)");
                    int continueInput = scanner.nextInt();
                    if (continueInput == 0) {
                        break;
                    }
                }

                System.out.println("Data inserted successfully");
            }

            else {
                throw new DatabaseConnectionException("Failed to establish connection to the database");
            }
        }

        catch (SQLException ex) {
            throw new DatabaseException("An error occurred while connecting to the database", ex);
        } catch (InputMismatchException ex) {
            throw new InvalidInputException("Invalid input provided");
        } catch (NoSuchElementException ex) {
            throw new InvalidInputException("No input provided");
        } catch (IllegalStateException ex) {
            throw new InvalidInputException("Scanner is closed");
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
