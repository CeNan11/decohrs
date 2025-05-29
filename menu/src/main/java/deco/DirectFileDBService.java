package deco; // Or your main package

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp; // For handling TIMESTAMP from DB
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DirectFileDBService {
    /**
     * Saves file information to the EmployeeFiles table.
     *
     * @param conn The active database Connection.
     * @param fileInfo The EmployeeFileInfo object containing data to save.
     * @return The generated file_id if successful, or -1 if an error occurred.
     * @throws SQLException If a database access error occurs.
     */
    public int saveFileInfo(Connection conn, EmployeeFileInfo fileInfo) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new IllegalArgumentException("Database connection is null or closed.");
        }

        String sql = "INSERT INTO EmployeeFiles (employee_id, file_path, original_filename, mime_type, file_size_bytes, file_type) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement pstmt = null;
        ResultSet generatedKeys = null;
        int generatedFileId = -1;

        try {
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, fileInfo.getEmployeeId());
            pstmt.setString(2, fileInfo.getFilePath());
            pstmt.setString(3, fileInfo.getOriginalFilename());
            pstmt.setString(4, fileInfo.getMimeType()); // Can be null
            if (fileInfo.getFileSizeBytes() != null) {
                pstmt.setLong(5, fileInfo.getFileSizeBytes());
            } else {
                pstmt.setNull(5, java.sql.Types.BIGINT);
            }
            pstmt.setString(6, fileInfo.getFileType());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    generatedFileId = generatedKeys.getInt(1);
                }
            }
            return generatedFileId;
        } finally {
            if (generatedKeys != null) try { generatedKeys.close(); } catch (SQLException e) { /* ignore */ }
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { /* ignore */ }
            // Connection is managed by the caller
        }
    }

    /**
     * Retrieves the latest file of a specific type for an employee.
     *
     * @param conn The active database Connection.
     * @param employeeId The ID of the employee.
     * @param fileType The type of file (e.g., "profile_picture").
     * @return EmployeeFileInfo object if found, null otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public EmployeeFileInfo getLatestFileByType(Connection conn, int employeeId, String fileType) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new IllegalArgumentException("Database connection is null or closed.");
        }

        String sql = "SELECT file_id, employee_id, file_path, original_filename, mime_type, file_size_bytes, file_type, uploaded_at " +
                "FROM EmployeeFiles " +
                "WHERE employee_id = ? AND file_type = ? " +
                "ORDER BY uploaded_at DESC LIMIT 1"; // Get the newest one

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        EmployeeFileInfo fileInfo = null;

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, employeeId);
            pstmt.setString(2, fileType);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                fileInfo = new EmployeeFileInfo(
                        rs.getInt("file_id"),
                        rs.getInt("employee_id"),
                        rs.getString("file_path"),
                        rs.getString("original_filename"),
                        rs.getString("mime_type"),
                        rs.getLong("file_size_bytes"), // Be careful if it can be NULL in DB
                        rs.getString("file_type"),
                        rs.getTimestamp("uploaded_at") // Convert Timestamp to Date
                );
                // Handle potential NULL for file_size_bytes if your DB allows it
                if (rs.wasNull()) {
                    // If you want to set fileSizeBytes to null in DTO if DB was null
                    // you'd need to adjust the DTO and this logic.
                    // For now, it will be 0 if null and rs.getLong() is used.
                }
            }
            return fileInfo;
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { /* ignore */ }
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { /* ignore */ }
            // Connection is managed by the caller
        }
    }

    /**
     * Retrieves all files for a specific employee.
     * @param conn The active database Connection.
     * @param employeeId The ID of the employee.
     * @return A list of EmployeeFileInfo objects.
     * @throws SQLException If a database access error occurs.
     */
    public List<EmployeeFileInfo> getAllFilesForEmployee(Connection conn, int employeeId) throws SQLException {
        // Similar logic to getLatestFileByType, but without LIMIT 1 and fileType filter
        // ... implementation left as an exercise, but would be very similar ...
        List<EmployeeFileInfo> files = new ArrayList<>();
        String sql = "SELECT file_id, employee_id, file_path, original_filename, mime_type, file_size_bytes, file_type, uploaded_at " +
                "FROM EmployeeFiles WHERE employee_id = ? ORDER BY uploaded_at DESC";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, employeeId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                files.add(new EmployeeFileInfo(
                        rs.getInt("file_id"),
                        rs.getInt("employee_id"),
                        rs.getString("file_path"),
                        rs.getString("original_filename"),
                        rs.getString("mime_type"),
                        rs.getLong("file_size_bytes"),
                        rs.getString("file_type"),
                        rs.getTimestamp("uploaded_at")
                ));
            }
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { /* ignore */ }
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { /* ignore */ }
        }
        return files;
    }
}