import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChineseMedicationGUI {
    private ChineseMedicationApp app;
    private JFrame frame;
    private JTextField idTextField;
    private JTextField nameTextField;
    private JTextArea resultTextArea;

    public ChineseMedicationGUI(ChineseMedicationApp app) {
        this.app = app;
    }

    public void show() {
        frame = new JFrame("Chinese Medication GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel inputPanel = createInputPanel();
        JPanel resultPanel = createResultPanel();
        JPanel buttonPanel = createButtonPanel();

        mainPanel.add(inputPanel);
        mainPanel.add(resultPanel);
        mainPanel.add(buttonPanel);

        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new FlowLayout());

        JLabel idLabel = new JLabel("ID:");
        idTextField = new JTextField(10);

        JLabel nameLabel = new JLabel("Name:");
        nameTextField = new JTextField(10);

        inputPanel.add(idLabel);
        inputPanel.add(idTextField);
        inputPanel.add(nameLabel);
        inputPanel.add(nameTextField);

        return inputPanel;
    }

    private JPanel createResultPanel() {
        JPanel resultPanel = new JPanel(new BorderLayout());

        resultTextArea = new JTextArea(10, 30);
        resultTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        resultPanel.add(scrollPane, BorderLayout.CENTER);

        return resultPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int id = 0;
                String name = nameTextField.getText();

                try {
                    id = Integer.parseInt(idTextField.getText());
                } catch (NumberFormatException ex) {
                    // Ignore if ID is not a valid integer
                }

                String result = app.selectData(id, name);
                resultTextArea.setText(result);
            }
        });

        JButton insertButton = new JButton("Insert");
        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = idTextField.getText();
                String name = nameTextField.getText();
                String description = JOptionPane.showInputDialog(frame, "Enter description:");
                String usage = JOptionPane.showInputDialog(frame, "Enter usage:");

                app.insertData(id, name, description, usage);
                JOptionPane.showMessageDialog(frame, "Data inserted successfully!");
            }
        });

        JButton exportButton = new JButton("Export");
        exportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showSaveDialog(frame);
                if (option == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    boolean success = app.exportData(filePath);
                    if (success) {
                        JOptionPane.showMessageDialog(frame, "Data exported successfully!");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to export data.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        buttonPanel.add(searchButton);
        buttonPanel.add(insertButton);
        buttonPanel.add(exportButton);

        return buttonPanel;
    }
}
