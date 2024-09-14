public class Controller {
    private View view;        
    private Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
    }

    // ค้นหาวัวตาม id
    public void searchCow(String id) {
        Cow cow = model.getCowById(id);
        view.displayCowInfo(cow);
    }

    public void milkCow(boolean addLemon) {
        String id = view.getIdFieldText();
        Cow cow = model.getCowById(id);
        if (cow != null && !cow.isBSOD()) {
            model.milkCow(cow, addLemon);
            view.displayMilkResult("Milk produced: " + cow.produceMilk(addLemon));
        } else if (cow != null && cow.isBSOD()) {
            view.displayMilkResult("Cannot produce milk (BSOD).");
        }
        view.updateReport(model.getReport());
    }

    // Reset วัวที่เป็น BSOD
    public void resetBSODCows() {
        model.resetBSODCows();
        view.updateReport(model.getReport());
    }
}
