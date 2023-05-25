import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Statement;


public class HerbalMedicineCollector {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/herbal_medicines";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        try {
            // 连接数据库
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected to the database.");

            // 创建表
            createTable(connection);

            // 收集中药数据
            collectHerbalMedicineData(connection);

            // 查询中药数据
            displayHerbalMedicineData(connection);

            // 关闭连接
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 创建中药数据表
    private static void createTable(Connection connection) throws SQLException {
        String sql = "CREATE TABLE herbal_medicines (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "description TEXT," +
                "usage TEXT)";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Table created successfully.");
        }
    }

    // 收集中药数据
    private static void collectHerbalMedicineData(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the herbal medicine:");
        String name = scanner.nextLine();

        System.out.println("Enter the description of the herbal medicine:");
        String description = scanner.nextLine();

        System.out.println("Enter the usage of the herbal medicine:");
        String usage = scanner.nextLine();

        String sql = "INSERT INTO herbal_medicines (name, description, usage) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setString(3, usage);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Data inserted successfully.");
            }
        }
    }

    // 查询中药数据
    private static void displayHerbalMedicineData(Connection connection) throws SQLException {
        String sql = "SELECT * FROM herbal_medicines";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            System.out.println("Herbal Medicine Data:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String usage = resultSet.getString("usage");

                System.out.println("ID: " + id);
                System.out.println("Name: " + name);
                System.out.println("Description: " + description);
                System.out.println("Usage: " + usage);
                System.out.println("------------------------");
            }
        }
    }
}
