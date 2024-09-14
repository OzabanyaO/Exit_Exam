import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputView extends JFrame {
    private JTextField idField;
    private JButton submitButton;
    private Controller controller;

    public InputView(Controller controller) {
        this.controller = controller;
        initialize();
    }

    private void initialize() {
        setTitle("Enter Cow ID");
        setLayout(new GridLayout(2, 2));

        JLabel idLabel = new JLabel("Enter Cow ID:");
        add(idLabel);

        idField = new JTextField();
        add(idField);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cowId = idField.getText();
                controller.searchCow(cowId);
            }
        });
        add(submitButton);

        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public String getIdFieldText() {
        return idField.getText();
    }
}

