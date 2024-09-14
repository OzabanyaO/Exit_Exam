import javax.swing.*;
import java.awt.*;

public class View {
    private JFrame frame;
    private JTextField idField;
    private JTextArea resultArea;
    private JScrollPane scrollPane;
    private JButton milkButton;
    private JButton addLemonButton;
    private JButton resetButton;
    private JButton submitButton;
    private Controller controller;

    public View() {
    }

    public void setController(Controller controller) {
        this.controller = controller;
        initialize();
    }

    public void initialize() {
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create panel for input and buttons
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel idLabel = new JLabel("Enter Cow ID (8 digits, first digit not 0):");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        inputPanel.add(idLabel, gbc);

        idField = new JTextField();
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        inputPanel.add(idField, gbc);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String id = idField.getText();
            if (validateId(id)) {
                controller.searchCow(id);
            } else {
                displayCowInfo(null); // Show error message
            }
        });
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        inputPanel.add(submitButton, gbc);

        resetButton = new JButton("Reset BSOD Cows");
        resetButton.addActionListener(e -> controller.resetBSODCows());
        gbc.gridx = 1;
        inputPanel.add(resetButton, gbc);

        // Create result panel for displaying cow info
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());

        resultArea = new JTextArea(10, 30); // Set preferred size for the text area
        resultArea.setEditable(false);
        scrollPane = new JScrollPane(resultArea);
        resultPanel.add(scrollPane, BorderLayout.CENTER);

        // Create panel for milk and lemon buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        milkButton = new JButton("Milk Cow");
        milkButton.setVisible(false);
        milkButton.addActionListener(e -> controller.milkCow(false));
        buttonPanel.add(milkButton);

        addLemonButton = new JButton("Add Lemon (for sour milk)");
        addLemonButton.setVisible(false);
        addLemonButton.addActionListener(e -> controller.milkCow(true));
        buttonPanel.add(addLemonButton);

        // Add panels to the frame
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(resultPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setSize(400, 600);
        frame.setVisible(true);
    }

    public boolean validateId(String id) {
        return id.matches("[1-9][0-9]{7}");
    }

    // แสดงข้อมูลของวัวใน resultArea
    public void displayCowInfo(Cow cow) {
        if (cow == null) {
            resultArea.setText("Invalid Cow ID. Please enter a valid 8-digit number (first digit not 0).");
            milkButton.setVisible(false);
            addLemonButton.setVisible(false);
        } else {
            String resultText = "Cow ID: " + cow.getId() + "\nBreast: " + cow.getBreast() + "\nAge: " + cow.getAgeYear() + " years, " + cow.getAgeMonth() + " months";
            if (cow.isBSOD()) {
                resultText += "\nStatus: BSOD (Cannot produce milk)";
                milkButton.setVisible(false);
                addLemonButton.setVisible(false);
            } else {
                resultText += "\nStatus: Active";
                if (cow.getBreast().equalsIgnoreCase("white")) {
                    addLemonButton.setVisible(true);
                } else {
                    addLemonButton.setVisible(false);
                }
                milkButton.setVisible(true);
            }
            resultArea.setText(resultText);
        }
    }

    public void displayMilkResult(String result) {
        resultArea.append("\n" + result);
    }

    public void updateReport(String report) {
        resultArea.append("\nReport:\n" + report);
    }

    public String getIdFieldText() {
        return idField.getText();
    }
}
