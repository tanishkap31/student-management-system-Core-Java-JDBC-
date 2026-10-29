import java.util.List;
import java.util.Scanner;

/**
 * Console menu for the Student Management System.
 */
public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final StudentDAO dao = new StudentDAO();

    public static void main(String[] args) {
        int choice;
        do {
            printMenu();
            choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1: addStudent(); break;
                case 2: viewAll(); break;
                case 3: findById(); break;
                case 4: searchByName(); break;
                case 5: updateStudent(); break;
                case 6: deleteStudent(); break;
                case 0: System.out.println("Goodbye!"); break;
                default: System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);
        sc.close();
    }

    private static void printMenu() {
        System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
        System.out.println("1. Add student");
        System.out.println("2. View all students");
        System.out.println("3. Find student by ID");
        System.out.println("4. Search students by name");
        System.out.println("5. Update student");
        System.out.println("6. Delete student");
        System.out.println("0. Exit");
    }

    private static void addStudent() {
        String name = readNonEmpty("Name: ");
        String email = readEmail("Email: ");
        String course = readNonEmpty("Course: ");
        int age = readInt("Age: ");
        if (age <= 0) {
            System.out.println("Age must be positive.");
            return;
        }
        if (dao.addStudent(new Student(name, email, course, age))) {
            System.out.println("Student added successfully.");
        }
    }

    private static void viewAll() {
        printTable(dao.getAllStudents());
    }

    private static void findById() {
        int id = readInt("Student ID: ");
        Student s = dao.getStudentById(id);
        if (s == null) {
            System.out.println("No student found with ID " + id);
        } else {
            printHeader();
            System.out.println(s);
        }
    }

    private static void searchByName() {
        String keyword = readNonEmpty("Name contains: ");
        printTable(dao.searchByName(keyword));
    }

    private static void updateStudent() {
        int id = readInt("ID of student to update: ");
        Student s = dao.getStudentById(id);
        if (s == null) {
            System.out.println("No student found with ID " + id);
            return;
        }
        System.out.println("Press Enter to keep the current value.");
        String name = readOptional("Name [" + s.getName() + "]: ");
        String email = readOptional("Email [" + s.getEmail() + "]: ");
        String course = readOptional("Course [" + s.getCourse() + "]: ");
        String age = readOptional("Age [" + s.getAge() + "]: ");

        if (!name.isEmpty()) s.setName(name);
        if (!email.isEmpty()) s.setEmail(email);
        if (!course.isEmpty()) s.setCourse(course);
        if (!age.isEmpty()) {
            try {
                int a = Integer.parseInt(age);
                if (a <= 0) throw new NumberFormatException();
                s.setAge(a);
            } catch (NumberFormatException e) {
                System.out.println("Invalid age, keeping the old value.");
            }
        }
        if (dao.updateStudent(s)) {
            System.out.println("Student updated successfully.");
        }
    }

    private static void deleteStudent() {
        int id = readInt("ID of student to delete: ");
        String confirm = readNonEmpty("Delete student " + id + "? (y/n): ");
        if (confirm.equalsIgnoreCase("y")) {
            System.out.println(dao.deleteStudent(id)
                    ? "Student deleted."
                    : "No student found with ID " + id);
        } else {
            System.out.println("Cancelled.");
        }
    }

    // ---------- helpers ----------

    private static void printHeader() {
        System.out.println(String.format("%-5s %-20s %-28s %-18s %-3s",
                "ID", "NAME", "EMAIL", "COURSE", "AGE"));
        System.out.println("-".repeat(80));
    }

    private static void printTable(List<Student> list) {
        if (list.isEmpty()) {
            System.out.println("No records found.");
            return;
        }
        printHeader();
        for (Student s : list) {
            System.out.println(s);
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("This field cannot be empty.");
        }
    }

    private static String readOptional(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    private static String readEmail(String prompt) {
        while (true) {
            String e = readNonEmpty(prompt);
            if (e.matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$")) return e;
            System.out.println("Invalid email format.");
        }
    }
}
