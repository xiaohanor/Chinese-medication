import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ExportDataExporter {
    public void exportData(List<String> data) {
        if (data.isEmpty()) {
            System.out.println("No data available to export.");
            return;
        }

        String filename = "exported_data.csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (String rowData : data) {
                writer.println(rowData);
            }

            System.out.println("Data exported successfully. Please check the file: " + filename);
        } catch (IOException e) {
            System.out.println("Error occurred while exporting data.");
            e.printStackTrace();
        }
    }
}
