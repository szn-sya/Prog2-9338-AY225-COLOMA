/**
 * Student Name: COLOMA, AYSBERG MATT A.
 * Machine Problems: MP01, MP13, MP19
 */

import java.io.*;
import java.util.*;

public class Dataset {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the dataset file path (e.g., MOCK_DATA.csv): ");
        String filePath = input.nextLine();
        
        List<String[]> records = new ArrayList<>();
        String[] headers = null;

        // Regex to handle commas inside quoted strings
        String csvSplitBy = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            if (line != null) {
                headers = line.split(csvSplitBy, -1);
            }

            while ((line = br.readLine()) != null) {
                records.add(line.split(csvSplitBy, -1));
            }

            displayMP01(records);
            List<Integer> missingRows = findMP13(records, headers);
            displayMP19(records, missingRows);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            input.close();
        }
    }

    // [MP01] Basic record count
    public static void displayMP01(List<String[]> data) {
        System.out.println("\n[MP01] Total Records: " + data.size());
    }

    // [MP13] Logic to find rows with empty/null fields
    public static List<Integer> findMP13(List<String[]> data, String[] headers) {
        List<Integer> rowsWithMissing = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            for (String field : data.get(i)) {
                if (field == null || field.trim().isEmpty()) {
                    rowsWithMissing.add(i + 1);
                    break;
                }
            }
        }
        System.out.println("[MP13] Rows with missing values: " + rowsWithMissing);
        return rowsWithMissing;
    }

    // [MP19] Summary Report - Now focused only on record totals and integrity
    public static void displayMP19(List<String[]> data, List<Integer> missingRows) {
        System.out.println("\n--- [MP19] DATASET SUMMARY REPORT ---");
        System.out.println("Total Records:   " + data.size());
        System.out.println("Incomplete Rows: " + missingRows.size());
        System.out.println("Data Integrity:  " + (data.size() - missingRows.size()) + " complete rows.");
        System.out.println("-------------------------------------");
    }
}