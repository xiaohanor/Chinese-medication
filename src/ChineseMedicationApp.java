import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.sql.*;

public class ChineseMedicationApp {
    private static Connection connection;

    public ChineseMedicationApp() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/chinese_medication?useUnicode=true&characterEncoding=utf8";
        String username = "root";
        String password = "";

        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable() throws SQLException {
        String createTableSql = "CREATE TABLE IF NOT EXISTS chinese_medication (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "description VARCHAR(255) NOT NULL," +
                "`usage` VARCHAR(255) NOT NULL" +
                ")";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSql);
        }
    }

    public static void insertData(String name, String description, String usage) {
        String insertSql = "INSERT INTO chinese_medication (name, description, `usage`) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(insertSql)) {
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setString(3, usage);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("数据插入成功。");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String selectData(int id, String name) {
        if (id == 0 && name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "未输入有效信息");
            return "";
        }

        String selectSql = "SELECT * FROM chinese_medication WHERE id = ? OR name = ?";

        try (PreparedStatement statement = connection.prepareStatement(selectSql)) {
            statement.setInt(1, id);
            statement.setString(2, name);

            ResultSet resultSet = statement.executeQuery();

            StringBuilder sb = new StringBuilder();

            while (resultSet.next()) {
                int medicationId = resultSet.getInt("id");
                String medicationName = resultSet.getString("name");
                String description = resultSet.getString("description");
                String usage = resultSet.getString("usage");

                sb.append("ID: ").append(medicationId).append("\n");
                sb.append("名称: ").append(medicationName).append("\n");
                sb.append("描述: ").append(description).append("\n");
                sb.append("用法: ").append(usage).append("\n");
                sb.append("-------------------\n");
            }

            return sb.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static void main(String[] args) {
        ChineseMedicationApp app = new ChineseMedicationApp();
        ChineseMedicationGUI gui = new ChineseMedicationGUI(app);
        gui.show();
    }
}
