import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class QuickSort {

    public QuickSort() {
    }

    public int comparisons;
    public void pivotSort(Integer[] nums) {

        this.comparisons = 0;

        pivotSort(nums,0,nums.length);
//        pivotSortMedian(nums,0, nums.length);

        System.out.println("comparisons = " + comparisons);
    }

    private void pivotSort(Integer[] nums, int first, int length) {

        if (length < 2) {
            return;
        }

        comparisons += length-1;

        int temp = nums[first];
        nums[first] = nums[first+length-1];
        nums[first+length-1]= temp;

        int i = first;
        int j = i+1;

        while (j<length+first) {
            // Greater than pivot, then do a swap
            if (nums[j] < nums[first]) {
                temp = nums[j];
                nums[j] = nums[i+1];
                nums[i+1] = temp;
                i++;
            }
            j++;
        }
        // Finally, put pivot in its palce
        temp = nums[i];
        nums[i] = nums[first];
        nums[first] = temp;

        pivotSort(nums,first,i-first);
        pivotSort(nums,i+1,length-(i-first)-1);

    }

    private void pivotSortMedian(Integer[] nums, int first, int length) {

        if (length < 2) {
            return;
        }

        // Swapping code
        int firstNum = nums[first];
        int lastNum = nums[first + length - 1];
        int medianNum = nums[first + ((length - 1) / 2)];

        // Get median of three numbers
        int[] possiblePivots = {firstNum, lastNum, medianNum};
        Arrays.sort(possiblePivots);
        int pivotNum = possiblePivots[1];

        // Swap pivot number with first (if not already first)
        if (lastNum == pivotNum) {
            int temp = nums[first];
            nums[first] = lastNum;
            nums[first + length - 1] = temp;
        } else if (medianNum == pivotNum) {
            int temp = nums[first];
            nums[first] = medianNum;
            nums[first + ((length - 1) / 2)] = temp;
        }

        comparisons += length - 1;

        int i = first;
        int j = i + 1;

        while (j < length + first) {
            // Greater than pivot, then do a swap
            if (nums[j] < nums[first]) {
                int temp = nums[j];
                nums[j] = nums[i + 1];
                nums[i + 1] = temp;
                i++;
            }
            j++;
        }
        // Finally, put pivot in its place
        int temp = nums[i];
        nums[i] = nums[first];
        nums[first] = temp;

        pivotSortMedian(nums, first, i - first);
        pivotSortMedian(nums, i + 1, length - (i - first) - 1);

    }
}
