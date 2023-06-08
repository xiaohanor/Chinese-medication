import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ChineseMedicationApp extends JFrame {
    private JTextField idField;
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField usageField;
    private JTextArea resultArea;

    private Connection connection;

    public ChineseMedicationApp() {
        initializeUI();

        String jdbcUrl = "jdbc:mysql://localhost:3306/chinese_medication";
        String username = "root";
        String password = "";

        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeUI() {
        setTitle("Chinese Medication App");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField();
        JLabel nameLabel = new JLabel("名称:");
        nameField = new JTextField();
        JLabel descriptionLabel = new JLabel("描述:");
        descriptionField = new JTextField();
        JLabel usageLabel = new JLabel("用法:");
        usageField = new JTextField();

        JButton insertButton = new JButton("插入");
        JButton selectButton = new JButton("查询");

        resultArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(resultArea);

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertData();
            }
        });

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectData();
            }
        });

        add(idLabel);
        add(idField);
        add(nameLabel);
        add(nameField);
        add(descriptionLabel);
        add(descriptionField);
        add(usageLabel);
        add(usageField);
        add(insertButton);
        add(selectButton);
        add(scrollPane);

        setVisible(true);
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

    private void insertData() {
        String name = nameField.getText();
        String description = descriptionField.getText();
        String usage = usageField.getText();

        String insertSql = "INSERT INTO chinese_medication (name, description, `usage`) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(insertSql)) {
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setString(3, usage);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "数据插入成功。");
                clearFields();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void selectData() {
        String selectSql = "SELECT * FROM chinese_medication";

        try (PreparedStatement statement = connection.prepareStatement(selectSql);
             ResultSet resultSet = statement.executeQuery()) {
            StringBuilder sb = new StringBuilder();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String usage = resultSet.getString("usage");

                sb.append("ID: ").append(id).append("\n");
                sb.append("名称: ").append(name).append("\n");
                sb.append("描述: ").append(description).append("\n");
                sb.append("用法: ").append(usage).append("\n");
                sb.append("-------------------\n");
            }

            resultArea.setText(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        descriptionField.setText("");
        usageField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ChineseMedicationApp();
            }
        });
    }
}
