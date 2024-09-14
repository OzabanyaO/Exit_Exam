public class Main {
    public static void main(String[] args) {
        Model model = new Model("cows.csv");
        View view = new View();
        Controller controller = new Controller(view, model);
        view.setController(controller);
    }
}
