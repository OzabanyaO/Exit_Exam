import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Model {
    private List<Cow> cows;
    private int totalPlainMilk;
    private int totalSourMilk;
    private int totalChocolateMilk;
    private int totalSoyMilk;
    private int totalAlmondMilk;
    private String filePath;

    public Model(String csvFilePath) {
        this.cows = new ArrayList<>();
        this.filePath = csvFilePath;
        this.totalPlainMilk = 0;
        this.totalSourMilk = 0;
        this.totalChocolateMilk = 0;
        this.totalSoyMilk = 0;
        this.totalAlmondMilk = 0;
        readCowsFromCSV();
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

    // เพิ่มข้อมูลการผลิตนม
    public String milkCow(Cow cow, boolean addLemon) {
        String milk = cow.produceMilk(addLemon);
        if (milk.equals("Plain milk")) {
            totalPlainMilk++;
        } else if (milk.equals("Sour milk")) {
            totalSourMilk++;
        } else if (milk.equals("Chocolate milk")) {
            totalChocolateMilk++;
        } else if (milk.equals("Soy milk (BSOD)")) {
            totalSoyMilk++;
        } else if (milk.equals("Almond milk (BSOD)")) {
            totalAlmondMilk++;
        }
        return milk;
    }


    // เพิ่มข้อมูลการรีเซ็ต BSOD
    public void resetBSODCows() {
        for (Cow cow : cows) {
            if (cow.isBSOD()) {
                cow.reset();
            }
        }
    }

    //แสดงรายงาน
    public String getReport() {
        return "Total Plain Milk: " + totalPlainMilk + "\n" +
               "Total Sour Milk: " + totalSourMilk + "\n" +
               "Total Chocolate Milk: " + totalChocolateMilk + "\n" +
               "Total Soy Milk: " + totalSoyMilk + "\n" +
               "Total Almond Milk: " + totalAlmondMilk;
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

    public void setBSOD(boolean isBSOD) { this.isBSOD = isBSOD; }

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