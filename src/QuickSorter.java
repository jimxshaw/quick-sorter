import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuickSorter {
    public enum PivotStrategy {
        FIRST_ELEMENT,
        RANDOM_ELEMENT,
        //MEDIAN_OF_THREE_RANDOM_ELEMENTS, // NOT IMPLEMENTED.

        // First, Middle, Last
        MEDIAN_OF_THREE_ELEMENTS
    }

    // Takes input list and sort it in-place using the QuickSort
    // algorithm with a different pivot selection strategy. It returns
    // the time in nanoseconds of how long it took to sort the list.
    public static <E extends Comparable<E>>Duration timedQuickSort(ArrayList<E> list, PivotStrategy pivotStrategy) {
        validateInput(list, pivotStrategy);

        // Mark the start time.
        long startTime = System.nanoTime();

        // Call the quickSort method that uses recursion.
        quickSort(list, 0, list.size() - 1, pivotStrategy);

        // Mark the end time.
        long endTime = System.nanoTime();

        return Duration.ofNanos(endTime - startTime);
    }

    // Generates and returns a new integer array list with the given size that
    // consists of random unsorted values.
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

    private static <E extends Comparable<E>> void validateInput(ArrayList<E> list, PivotStrategy pivotStrategy) {
        if (list == null) {
            throw new NullPointerException("Input list cannot be null.");
        }

        if (pivotStrategy == null) {
            throw new NullPointerException("Pivot strategy cannot be null.");
        }
    }

    // Use recursion to sort a portion of the input list using quick sort.
    // Arrays with less than 20 elements will use insertion sort.
    private static <E extends Comparable<E>> void quickSort(ArrayList<E> list, int leftStartingIndex, int rightEndingIndex, PivotStrategy pivotStrategy) {
        // Use insertion sort if input list is small.
        if (rightEndingIndex - leftStartingIndex + 1 < 20) {
            insertionSort(list, leftStartingIndex, rightEndingIndex);

            // A return statement must be here to
            // prevent the rest of the method from running.
            return;
        }

        E pivotElement;

        // Figure out the pivot strategy.
        switch (pivotStrategy) {
            case FIRST_ELEMENT:
                // Use the first element as the pivot element.
                pivotElement = list.get(leftStartingIndex);
                break;

            case RANDOM_ELEMENT:
                // Select random element between the left and the right.
                int randomIndex = new Random().nextInt(rightEndingIndex - leftStartingIndex + 1) + leftStartingIndex;

                // Swap the random element to the front then re-use the partition logic.
                swap(list, leftStartingIndex, randomIndex);
                pivotElement = list.get(leftStartingIndex);
                break;

            case MEDIAN_OF_THREE_ELEMENTS:
                // This is the "book approach", which uses First, Middle, Last elements.
                int midpoint = (leftStartingIndex + rightEndingIndex) / 2;

                E firstElement = list.get(leftStartingIndex);
                E middleElement = list.get(midpoint);
                E lastElement = list.get(rightEndingIndex);

                // Figure out the median among the elements.
                int compareFirstAndMiddle = firstElement.compareTo(middleElement);
                int compareFirstAndLast = firstElement.compareTo(lastElement);
                int compareMiddleAndLast = middleElement.compareTo(lastElement);

                int medianIndex;

                if ((compareFirstAndMiddle <= 0 && compareMiddleAndLast <= 0 && compareFirstAndMiddle >= compareFirstAndLast) || (compareFirstAndMiddle >= 0 && compareMiddleAndLast >= 0 && compareFirstAndMiddle <= compareFirstAndLast)) {
                    medianIndex = leftStartingIndex;
                } else if ((compareFirstAndMiddle >= 0 && compareMiddleAndLast <= 0 && compareFirstAndLast >= compareMiddleAndLast) || (compareFirstAndMiddle <= 0 && compareMiddleAndLast >= 0 && compareFirstAndLast <= compareMiddleAndLast)) {
                    medianIndex = midpoint;
                } else {
                    medianIndex = rightEndingIndex;
                }

                // Swap the median to the front. It will be the pivot element.
                swap(list, leftStartingIndex, medianIndex);
                pivotElement = list.get(leftStartingIndex);
                break;

            default:
                throw new UnsupportedOperationException("Unsupported Pivot Strategy.");
        }

        // PARTITION LOGIC
        // Variables used for partitioning.
        int i = leftStartingIndex + 1; // Begin right after the pivot element.
        int j = rightEndingIndex; // Begin at the end of the list.

        // Re-arranged the elements around the pivot element when partitioning.
        while (i <= j) {
            // Move i to the right as long as elements are <= pivot element.
            while (i <= j && list.get(i).compareTo(pivotElement) <= 0) {
                i++;
            }

            // Move j to the left as long as elements are > pivot element.
            while (i <= j && list.get(j).compareTo(pivotElement) > 0) {
                j--;
            }

            // Swap elements if they're out of order and i & j haven't crossed.
            if (i < j) {
                swap(list, i, j);
                i++;
                j--;
            }
        }

        // Put the pivot element in the right position by swapping with list[j].
        swap(list, leftStartingIndex, j);

        // Use recursion to sort elements less than pivot element.
        quickSort(list, leftStartingIndex, j - 1, pivotStrategy);

        // Use recursion to sort elements greater than pivot element.
        quickSort(list, j + 1, rightEndingIndex, pivotStrategy);

    }

    // Swap the elements at the indices given within the input list.
    private static <E> void swap(ArrayList<E> list, int firstIndex, int secondIndex) {
        // A temporary variable is needed to "hold" oen of the
        // elements when it comes to swapping.
        E temporary = list.get(firstIndex);
        list.set(firstIndex, list.get(secondIndex));
        list.set(secondIndex, temporary);
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
