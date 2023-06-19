import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChineseMedicationGUI extends JFrame {
    private JTextField idField;
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField usageField;
    private JTextArea resultArea;
    private ChineseMedicationApp app;

    public ChineseMedicationGUI(ChineseMedicationApp app) {
        this.app = app;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Chinese Medication App");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel idLabel = new JLabel("ID:");
        add(idLabel, constraints);

        idField = new JTextField(20);
        constraints.gridx = 1;
        add(idField, constraints);

        JLabel nameLabel = new JLabel("名称:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(nameLabel, constraints);

        nameField = new JTextField(20);
        constraints.gridx = 1;
        add(nameField, constraints);

        JLabel descriptionLabel = new JLabel("描述:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(descriptionLabel, constraints);

        descriptionField = new JTextField(20);
        constraints.gridx = 1;
        add(descriptionField, constraints);

        JLabel usageLabel = new JLabel("用法:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(usageLabel, constraints);

        usageField = new JTextField(20);
        constraints.gridx = 1;
        add(usageField, constraints);

        JButton insertButton = new JButton("插入");
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        add(insertButton, constraints);

        JButton selectButton = new JButton("查询");
        constraints.gridy = 5;
        add(selectButton, constraints);

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.insertData(nameField.getText(), descriptionField.getText(), usageField.getText());
                clearFields();
            }
        });

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = 0;
                String name = "";
                String idText = idField.getText();
                String nameText = nameField.getText();

                if (!idText.isEmpty()) {
                    id = Integer.parseInt(idText);
                }

                if (!nameText.isEmpty()) {
                    name = nameText;
                }

                String result = app.selectData(id, name);
                resultArea.setText(result);
            }
        });

        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;

        resultArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, constraints);

        setVisible(true);
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        descriptionField.setText("");
        usageField.setText("");
    }
}
