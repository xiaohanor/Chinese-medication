import java.sql.*;

public class Main {
    public static void main(String[] args) {
        // 数据库连接信息
        String jdbcUrl = "jdbc:mysql://localhost:3306/chinese_medication";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            // 创建表（如果不存在）
            createTable(connection);

            // 插入数据
            insertData(connection, "中药1", "描述1", "用法1");
            insertData(connection, "中药2", "描述2", "用法2");

            // 查询数据
            selectData(connection);

            // 更新数据
            updateData(connection, "中药1", "新的描述1");

            // 再次查询数据
            selectData(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 创建表
    private static void createTable(Connection connection) throws SQLException {
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

    // 插入数据
    private static void insertData(Connection connection, String name, String description, String usage) throws SQLException {
        String insertSql = "INSERT INTO chinese_medication (name, description, `usage`) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(insertSql)) {
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setString(3, usage);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("数据插入成功。");
            }
        }
    }

    // 查询数据
    private static void selectData(Connection connection) throws SQLException {
        String selectSql = "SELECT * FROM chinese_medication";

        try (PreparedStatement statement = connection.prepareStatement(selectSql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String usage = resultSet.getString("usage");

                System.out.println("ID: " + id);
                System.out.println("名称: " + name);
                System.out.println("描述: " + description);
                System.out.println("用法: " + usage);
                System.out.println("-------------------");
            }
        }
    }

    // 更新数据
    private static void updateData(Connection connection, String name, String newDescription) throws SQLException {
        String updateSql = "UPDATE chinese_medication SET description = ? WHERE name = ?";

        try (PreparedStatement statement = connection.prepareStatement(updateSql)) {
            statement.setString(1, newDescription);
            statement.setString(2, name);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("数据更新成功。");
            }
        }
    }
}
