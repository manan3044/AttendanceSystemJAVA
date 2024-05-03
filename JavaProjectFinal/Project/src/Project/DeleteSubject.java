package Project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.sql.*;

public class DeleteSubject {

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

                String alterQuery = "ALTER TABLE "+ tableName + "_ATTENDANCE DROP COLUMN " + subName;
                Statement statement = conn.createStatement();

                statement.executeUpdate(alterQuery);

            }
        }catch (SQLException ex) {
            throw new DatabaseException("An error occurred while altering the table", ex);
        }
        catch (Exception ex) {
            ex.printStackTrace();}
    }


    public static void deleteSubject(int facultyID) throws Exception, InvalidInputException {
        Scanner scanner = new Scanner(System.in);
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/AttendanceManagement", "root", "manan@$3044");
            if (conn != null) {
                while (true) {
                    String sql = "SELECT CONCAT(SUBJECT_NAME, ' (', DIVI, ')') AS SUBJECT_DIV FROM TEACHINGLIST WHERE FACULTY_ID = ?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setInt(1, facultyID);
                    ResultSet rs = ps.executeQuery();

                    if (!rs.next())
                    {
                        System.out.println("\nNo more subjects available to delete.");
                        return;
                    }

                    System.out.println("\nSubjects Available: ");
                    do {
                        System.out.println(rs.getString("SUBJECT_DIV"));
                    } while (rs.next());

                    System.out.print("\nEnter the subject name you want to delete: ");
                    String subName = scanner.next();

                    System.out.print("Enter the branch and class of subject you want to delete: ");
                    String div = scanner.next();

                    sql = "DELETE FROM TEACHINGLIST WHERE FACULTY_ID = ? AND SUBJECT_NAME = ?";
                    ps = conn.prepareStatement(sql);
                    ps.setInt(1, facultyID);
                    ps.setString(2, subName);
                    ps.executeUpdate();

                    alterTable(div, subName);

                    System.out.print("Do you want to delete another subject? (1 for Yes, 0 for No): ");
                    int continueInput = scanner.nextInt();
                    if (continueInput == 0) {
                        break;
                    }
                }
            }else {
                throw new DatabaseConnectionException("Failed to establish connection to the database");
            }
        }
        catch (SQLException ex) {
            throw new DatabaseExecutionException("An error occurred while executing database operation", ex);
        } catch (InputMismatchException ex) {
            throw new InvalidInputException("Invalid input provided");
        } catch (NoSuchElementException ex) {
            throw new InvalidInputException("No input provided");
        } catch (IllegalStateException ex) {
            throw new InvalidInputException("Scanner is closed");
        }
        catch (Exception ex) {
            ex.printStackTrace();} {
        }
    }
    }

