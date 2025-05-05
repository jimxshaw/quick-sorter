import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuickSorter {
    public enum PivotStrategy {
        FIRST_ELEMENT,
        RANDOM_ELEMENT,
        MEDIAN_OF_THREE_RANDOM_ELEMENTS,
        MEDIAN_OF_THREE_ELEMENTS
    }

    public static ArrayList<Integer> generateRandomList(int size) {
        // The input size cannot be negative. Must be 0 or greater.
        if (size < 0) {
            throw new IllegalArgumentException("Input size cannot be negative.");
        }

        Random randomNumber = new Random();
        ArrayList<Integer> myList = new ArrayList<>(size);

        // Create random integers according to the input size
        // and add them to the list.
        for (int i = 0; i< size; i++) {
            myList.add(randomNumber.nextInt());
        }

        return myList;
    }

    // In-place insertion sort on an input sub-list.
    // Sorts the elements from first, the starting index, to second, the ending index, inclusive.
    // The input T type must implement the Comparable interface.
    private static <T extends Comparable<? super T>> void insertionSort(List<T> list, int first, int second) {
        // Loop over each element in the list starting with the second one.
        for (int i = first + 1; i <= second; i++) {
            T currentElement = list.get(i); // This is the element to be inserted correctly.
            int compareIndex = i - 1; // This is the index of the element before the current one.

            // Move sorted elements that are greater than the current element towards the right.
            while (compareIndex >= first && list.get(compareIndex).compareTo(currentElement) > 0) {
                list.set(compareIndex + 1, list.get(compareIndex)); // Shift larger elements to the right.
                compareIndex--;
            }

            // Insert the current element into the correct position.
            list.set(compareIndex + 1, currentElement);
        }
    }
}
