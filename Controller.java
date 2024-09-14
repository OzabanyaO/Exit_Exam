import javax.swing.JOptionPane;

public class Controller {
    private Model model;
    private InputView inputView;
    private MilkCowView milkCowView;

    public Controller(Model model) {
        this.model = model;
        this.inputView = new InputView(this);
    }

    public void searchCow(String id) {
        Cow cow = model.getCowById(id);
        if (cow != null) {
            if (milkCowView != null) {
                milkCowView.dispose();
            }
            milkCowView = new MilkCowView(cow, this);
        } else {
            JOptionPane.showMessageDialog(inputView, "Cow with ID " + id + " not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String milkCow(boolean addLemon) {
        Cow cow = model.getCowById(inputView.getIdFieldText());
        if (cow != null) {
            return model.milkCow(cow, addLemon);
        }
        return "";
    }

    public void resetBSODCows() {
        model.resetBSODCows();
        milkCowView.updateCowInfo(model.getCowById(milkCowView.getCowId()), "BSOD Cows Reset");
        JOptionPane.showMessageDialog(milkCowView, model.getReport());
    }

    public String getReport() {
        return model.getReport();
    }

    public void showInputView() {
        inputView.setVisible(true);
    }

    public Model getModel() {
        return model;
    }
}

