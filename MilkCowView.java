import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MilkCowView extends JFrame {
    private JTextArea resultArea;
    private JButton milkButton;
    private JButton addLemonButton;
    private JButton resetButton;
    private JButton backButton;
    private Cow cow;
    private Controller controller;

    public MilkCowView(Cow cow, Controller controller) {
        this.cow = cow;
        this.controller = controller;
        initialize();
    }

    // Initializes the MilkCowFrame layout and components.
    private void initialize() {
        setTitle("Cow Milk Production");
        setLayout(new BorderLayout());

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4)); // 4 columns to accommodate all buttons

        milkButton = new JButton("Milk");
        milkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result = controller.milkCow(false); // Milk
                updateCowInfo(cow, result);
            }
        });
        buttonPanel.add(milkButton);

        if (cow.getBreast().equals("white")) {
            addLemonButton = new JButton("Add Lemon");
            addLemonButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String result = controller.milkCow(true); // Milk with lemon
                    updateCowInfo(cow, result);
                }
            });
            buttonPanel.add(addLemonButton);
        }

        resetButton = new JButton("Reset BSOD");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.resetBSODCows();
                updateCowInfo(cow, "BSOD Cows Reset");
            }
        });
        buttonPanel.add(resetButton);

        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.showInputView();
                dispose();
            }
        });
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setSize(500, 400); // Increased size to accommodate additional information
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        updateCowInfo(cow, "Cow information loaded.");
    }

    public void updateCowInfo(Cow cow, String milkResult) {
        this.cow = cow;
        String milkInfo = controller.getModel().getReport();
        resultArea.setText("Cow ID: " + cow.getId() + "\n" +
                           "Color: " + cow.getBreast() + "\n" +
                           "Milk Result: " + milkResult + "\n" +
                           "Milk Info:\n" + milkInfo);
        milkButton.setEnabled(!cow.isBSOD());
        if (addLemonButton != null) {
            addLemonButton.setEnabled(!cow.isBSOD());
        }
        resetButton.setEnabled(cow.isBSOD());
    }

    public String getCowId() {
        return cow.getId();
    }
}

