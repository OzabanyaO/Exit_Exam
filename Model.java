import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Model {
    private List<Cow> cows;
    String filePath;

    // Track the total number of each type of milk produced
    private int plainMilkCount = 0;
    private int sourMilkCount = 0;
    private int chocolateMilkCount = 0;

    public Model(String csvFilePath) {
        this.cows = new ArrayList<>();
        this.filePath = csvFilePath;
        readCowsFromCSV();
        for (Cow cow : cows) {
            System.out.println(cow.getId());
        }
    }

    // อ่านข้อมูลวัวจากไฟล์ CSV
    private void readCowsFromCSV() {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");
                if (values.length == 4) {
                    String id = values[0];
                    String color = values[1];
                    int ageYear = Integer.parseInt(values[2]);
                    int ageMonth = Integer.parseInt(values[3]);
    
                    Cow cow = new Cow(id, color, ageYear, ageMonth);
                    cows.add(cow); // เพิ่มวัวเข้าไปในลิสต์
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Cow getCowById(String id) {
        for (Cow cow : cows) {
            if (cow.getId().equals(id)) {
                return cow;
            }
        }
        return null;
    }

    public void milkCow(Cow cow, boolean addLemon) {
        if (cow == null) return;

        String result = cow.produceMilk(addLemon);
        switch (result) {
            case "Plain milk":
                plainMilkCount++;
                break;
            case "Sour milk":
                sourMilkCount++;
                break;
            case "Chocolate milk":
                chocolateMilkCount++;
                break;
            case "Soy milk (BSOD)":
            case "Almond milk (BSOD)":
                cow.reset(); // Reset cow state
                break;
        }
    }

    //reset BSOD
    public void resetBSODCows() {
        for (Cow cow : cows) {
            if (cow.isBSOD()) {
                cow.reset();
            }
        }
    }

    //แสดงรายงาน
    public String getReport() {
        return String.format("Plain Milk: %d\nSour Milk: %d\nChocolate Milk: %d\nTotal: %d\n", 
                plainMilkCount, sourMilkCount, chocolateMilkCount, plainMilkCount + sourMilkCount + chocolateMilkCount);
    }
}

class Cow {
    private String id;
    private String breast;
    private int ageYear;
    private int ageMonth;
    private boolean isBSOD;
    
    public Cow(String id, String breast, int ageYear, int ageMonth) {
        this.id = id;
        this.breast = breast;
        this.ageYear = ageYear;
        this.ageMonth = ageMonth;
        this.isBSOD = false;
    }

    public String getId() { return id; }
    public String getBreast() { return breast; }
    public int getAgeYear() { return ageYear; }
    public int getAgeMonth() { return ageMonth; }
    public boolean isBSOD() { return isBSOD; }

    public String produceMilk(boolean addLemon) {
        if (isBSOD) return "Cannot produce milk (BSOD)";
        
        if (breast.equals("white")) {
            if (addLemon) return "Sour milk";
            else {
                double chanceOfSoyMilk = 0.005 * (ageMonth + (12 * ageYear));
                if (Math.random() < chanceOfSoyMilk) {
                    isBSOD = true;
                    return "Soy milk (BSOD)";
                }
                return "Plain milk";
            }
        } else if (breast.equals("brown")) {
            double chanceOfAlmondMilk = 0.01 * ageYear;
            if (Math.random() < chanceOfAlmondMilk) {
                isBSOD = true;
                return "Almond milk (BSOD)";
            }
            return "Chocolate milk";
        }
        return "Unknown breed";
    }

    public void reset() {
        isBSOD = false;
    }
}