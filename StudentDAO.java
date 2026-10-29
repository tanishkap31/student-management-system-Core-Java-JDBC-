import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object: all SQL for the students table lives here.
 * Uses PreparedStatement (prevents SQL injection) and try-with-resources.
 */
public class StudentDAO {

    public boolean addStudent(Student s) {
        String sql = "INSERT INTO students (name, email, course, age) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getCourse());
            ps.setInt(4, s.getAge());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error adding student: " + e.getMessage());
            return false;
        }
    }

    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY id";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching students: " + e.getMessage());
        }
        return list;
    }

    public Student getStudentById(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error finding student: " + e.getMessage());
        }
        return null;
    }

    public List<Student> searchByName(String keyword) {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE name LIKE ? ORDER BY name";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error searching students: " + e.getMessage());
        }
        return list;
    }

    public boolean updateStudent(Student s) {
        String sql = "UPDATE students SET name = ?, email = ?, course = ?, age = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getCourse());
            ps.setInt(4, s.getAge());
            ps.setInt(5, s.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting student: " + e.getMessage());
            return false;
        }
    }

    private Student mapRow(ResultSet rs) throws SQLException {
        return new Student(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("course"),
                rs.getInt("age"));
    }
}
