package org.keyin.workoutclasses;

import org.keyin.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkoutClassService {

    // create class
    public void addWorkoutClass(String type, String description, int trainerId) throws SQLException {
        String sql = "INSERT INTO workout_classes (type, description, trainer_id) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, type);
            pstmt.setString(2, description);
            pstmt.setInt(3, trainerId);
            pstmt.executeUpdate();
        }
    }

    // object input
    public void addWorkoutClass(WorkoutClass workoutClass) throws SQLException {
        addWorkoutClass(workoutClass.getType(), workoutClass.getDescription(), workoutClass.getTrainerId());
    }

    // get classes by trainer
    public List<WorkoutClass> getWorkoutClassesByTrainerId(int trainerId) throws SQLException {
        List<WorkoutClass> classes = new ArrayList<>();
        String sql = "SELECT * FROM workout_classes WHERE trainer_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, trainerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    classes.add(new WorkoutClass(
                            rs.getInt("id"),
                            rs.getString("type"),
                            rs.getString("description"),
                            rs.getInt("trainer_id")));
                }
            }
        }
        return classes;
    }

    // delete class
    public void deleteWorkoutClass(int id) throws SQLException {
        String sql = "DELETE FROM workout_classes WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    // view classes
    public List<WorkoutClass> getAllWorkoutClasses() throws SQLException {
        List<WorkoutClass> classes = new ArrayList<>();
        String sql = "SELECT * FROM workout_classes";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                classes.add(new WorkoutClass(
                        rs.getInt("id"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getInt("trainer_id")));
            }
        }
        return classes;
    }
}
