import java.io.*;

public class Main {

    int inversions;

    public static void main(String[] args) {

        int[] arr1 = {1, 20, 6, 4, 5};

        MergeSort sort1 = new MergeSort(arr1);
        System.out.println(sort1);
        sort1.sortAndCount();
        System.out.println(sort1.getSorted());
        System.out.println(sort1.getInversions());

        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/numbers.txt"));
            int[] nums = new int[100000];
            String line;
            int index = 0;
            while ((line = reader.readLine()) != null) {
                nums[index] = Integer.parseInt(line);
                index++;
            }

            MergeSort numSort = new MergeSort(nums);
            numSort.sortAndCount();
            System.out.printf("%.1f",numSort.getInversions());

            reader.readLine();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

}