import java.util.Arrays;

public class MergeSort {

    int[] arr;
    int[] sorted;
    double inversions;

    public MergeSort(int[] arr_) {
        this.arr = arr_;
        this.inversions = 0;
    }

    public void sortAndCount() {
        sorted = sortAndCount(arr);
    }

    private int[] sortAndCount(int[] nums) {
        if (nums.length == 1) {
            return nums;
        } else {
            int mid = nums.length/2;
            int[] left = sortAndCount(Arrays.copyOfRange(nums,0,mid));
            int[] right = sortAndCount(Arrays.copyOfRange(nums,mid,nums.length));
            int[] ret = new int[nums.length];

            int l=0;
            int r=0;

            for (int i=0; i<ret.length; i++) {
                if (l==left.length) {
                    ret[i]=right[r];
                    r++;
                } else if (r==right.length) {
                    System.out.println("Inversion (none in right): " + left[l]);
                    ret[i]=left[l];
                    l++;
                    //inversions++;

                } else if (left[l]<=right[r]) {
                    ret[i]=left[l];
                    l++;
                } else {
                    System.out.println("Inversion: " + right[r] + " < " + left[l]);
                    ret[i]=right[r];
                    r++;
                    inversions += (left.length-l);
                }
            }

            return ret;

        }
    }

    public String toString() {
        String ret = "{";
        for (int i=0;i<arr.length; i++) {
            ret += arr[i] + ", ";
        }
        ret += "}";
        return ret;
    }

    public String getSorted() {
        if (sorted == null) {
            System.out.println("Array not sorted yet!");
            return "";
        } else {
            return new MergeSort(this.sorted).toString();
        }
    }

    public double getInversions() {
        return this.inversions;
    }

}
