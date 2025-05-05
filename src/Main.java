import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // The command line argument must be exactly 4.
        if (args.length != 4) {
            System.err.println("Input: java Main <arraySize> <reportFile> <unsortedFile> <sortedFile>");
            return;
        }

        try {
            // Parse input arguments.
            int arraySize = Integer.parseInt(args[0]);
            String reportFilename = args[1];
            String unsortedFilename = args[2];
            String sortedFilename = args[3];

            // Generate the random list.
            ArrayList<Integer> originalList = QuickSorter.generateRandomList(arraySize);

            // Write the unsorted array to the file.
            writeToFile(originalList, unsortedFilename);

            // Initiate the report builder.
            StringBuilder report = new StringBuilder();
            report.append("Array Size = ").append(arraySize).append("\n");

            // Test each pivot strategy.
            for (QuickSorter.PivotStrategy pivotStrategy : QuickSorter.PivotStrategy.values()) {
                // Copy the original list.
                ArrayList<Integer> copyOfOriginal = new ArrayList<>(originalList);

                // Get the time after sorting.
                Duration duration = QuickSorter.timedQuickSort(copyOfOriginal, pivotStrategy);

                // Record the result.
                report.append(pivotStrategy.name()).append(" : ").append(duration.toString()).append("\n");

                // We have to save one of the sorted versions to the file. We pick the MEDIAN_OF_THREE_ELEMENTS because
                // it's the last pivot strategy in the execution loop. All the strategies output a sorted list so any of
                // them could be written to sorted.txt. Choosing this last one avoids saving multiple versions
                // and keeps memory usage low.
                if (pivotStrategy == QuickSorter.PivotStrategy.MEDIAN_OF_THREE_ELEMENTS) {
                    writeToFile(copyOfOriginal, sortedFilename);  // Save final sorted version
                }
            }

            // Save report to file.
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFilename))) {
                writer.write(report.toString());
            }

            System.out.println("Completed sorting. The results are saved to output files.");
        } catch (NumberFormatException e) {
            System.err.println("Array size must be an integer.");
        } catch (IOException e) {
            System.err.println("File writing error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unknown error: " + e.getMessage());
        }
    }

    private static void writeToFile(ArrayList<Integer> list, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int value : list) {
                writer.write(value + "\n");
            }
        }
    }
}