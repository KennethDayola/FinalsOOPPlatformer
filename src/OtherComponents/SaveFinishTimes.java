package OtherComponents;

import java.io.*;
import java.util.ArrayList;

public class SaveFinishTimes {
    private static final String FILE_PATH = "finish_times.txt";
    private static final ArrayList<String> finishTimes = new ArrayList<>();

    static {
        loadFinishTimes();
    }

    public static void saveFinishTimes() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String time : finishTimes) {
                writer.write(time);
                writer.newLine();
            }

            System.out.println("Finish times successfully saved to the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getFinishTimes() {
        return finishTimes;
    }

    private static void loadFinishTimes() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                finishTimes.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
