package Project;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws Exception, InvalidInputException {
        Scanner sc = new Scanner(System.in);

        System.out.print("Username: ");
        String username = sc.next();

        System.out.print("Password: ");
        String password = sc.next();

        System.out.print("Category: ");
        String category = sc.next();

        User user;

        if (category.equalsIgnoreCase("faculty")) {
            user = new Faculty(username, password, category);
        } else {
            user = new Student(username, password, category);
        }

        if (user.authenticate(username, password)) {
            if (category.equalsIgnoreCase("student"))
            {
                StudentAttendanceDisplay.display(username);
            }

            else if (category.equalsIgnoreCase("faculty")) {
                Scanner scanner = new Scanner(System.in);
                int choice;
                int facultyID = Integer.parseInt(username);

                do {
                    System.out.println("\n-------------------------------------------");
                    System.out.println("1) Add Subject");
                    System.out.println("2) Delete Subject");
                    System.out.println("3) Add Attendance");
                    System.out.println("4) Search");
                    System.out.println("5) Exit");
                    System.out.print("Enter your choice: ");

                    choice = scanner.nextInt();

                    switch (choice) {
                        case 1:
                            System.out.println("\n-----------");
                            System.out.println("ADD SUBJECT");
                            System.out.println("-----------");
                            AddSubject.addSubject(facultyID);
                            break;
                        case 2:
                            System.out.println("\n--------------");
                            System.out.println("DELETE SUBJECT");
                            System.out.println("--------------");
                            DeleteSubject.deleteSubject(facultyID);
                            break;
                        case 3:
                            System.out.println("\n--------------");
                            System.out.println("ADD ATTENDANCE");
                            System.out.println("--------------");
                            AddAttendance.addAttendance(facultyID);
                            break;
                        case 4:
                            System.out.println("\n------");
                            System.out.println("SEARCH");
                            System.out.println("------");
                            System.out.print("Enter the PRN of the student you want to search for:");
                            String prnToSearch = scanner.next();
                            SearchStudent.searchPRN(prnToSearch);
                            break;

                        case 5:
                            System.out.println("\n-------------------");
                            System.out.println("THANK YOU FOR USING");
                            System.out.println("-------------------");
                            break;
                        default:
                            System.out.println("Invalid choice");
                    }
                }while(choice!=5);
            }
        } else {
            System.out.println("Login failed. Incorrect username or password.");
        }
    }
}

// Add Subject
// Delete Subject
// Add Attendance
// Update Attendance
// Search
// Exit
