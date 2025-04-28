import java.util.List;

public class QuickSorter {
    public enum PivotStrategy {
        FIRST_ELEMENT,
        RANDOM_ELEMENT,
        MEDIAN_OF_THREE_RANDOM_ELEMENTS,
        MEDIAN_OF_THREE_ELEMENTS
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
