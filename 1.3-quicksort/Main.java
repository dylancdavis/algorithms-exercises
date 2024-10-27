import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;



public class Main {

    public static final String FILE_NAME = "numbers.txt";
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        Integer[] arr1 = {8,4,7,5,3,2,9,1};


        Integer[] givenNums = new Integer[10000];

        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                givenNums[i] = Integer.parseInt(line);
                i++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        QuickSort sorter = new QuickSort();

        System.out.println(Arrays.toString(givenNums));
        sorter.pivotSort(givenNums);
        System.out.println(Arrays.toString(givenNums));


        Integer[] testNums = {5,8,2,3,1,4,7,6};
        sorter.pivotSort(testNums);
        System.out.println(Arrays.toString(testNums));

    }


}