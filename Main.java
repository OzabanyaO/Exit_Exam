public class Main {
    public static void main(String[] args) {
        Model model = new Model("cows.csv");
        new Controller(model);  
    }
}
