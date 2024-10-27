import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        printOut("Please enter two numbers to find the product of:");
//        char[] num1 = input.nextLine().toCharArray();
//        char[] num2 = input.nextLine().toCharArray();

        char[] num1 = "3141592653589793238462643383279502884197169399375105820974944592".toCharArray();
        char[] num2 = "2718281828459045235360287471352662497757247093699959574966967627".toCharArray();

        printNum(Karatsuba(num1,num2));


    }

    public static void printOut(String message) {
        System.out.println(message);
    }

    public static char[] Karatsuba(char[] numA, char[] numB) {

        int length1 = numA.length;
        int length2 = numB.length;

        if (length1<length2) {
            int diff = length2-length1;
            return trimNumber(Karatsuba(appendZeroes(numA,diff),numB),diff);
        } else if (length2<length1) {
            int diff = length1-length2;
            return trimNumber(Karatsuba(numA,appendZeroes(numB,diff)),diff);
        } else if (length1 == 1) {
            // Return the product number of single digits.
            Integer product = Integer.parseInt(Character.toString(numA[0]))*Integer.parseInt(Character.toString(numB[0]));
            return Integer.toString(product).toCharArray();
        } else if (length1 %2 != 0) {
            return  trimNumber(Karatsuba(appendZeroes(numA,1),appendZeroes(numB,1)),2);
        }

        int mid = length1/2;

        char[] a1 = Arrays.copyOfRange(numA,0,mid);
        char[] a2 = Arrays.copyOfRange(numA,mid,numA.length);

        char[] b1 = Arrays.copyOfRange(numB,0,mid);
        char[] b2 = Arrays.copyOfRange(numB,mid,numB.length);

        char[] c1 = Karatsuba(a1,b1);
        char[] c2 = Karatsuba(a2,b2);
        char[] d = Karatsuba(addNums(a1,a2),addNums(b1,b2));
        try {
            char[] f = diffNums(d,addNums(c1,c2));
            char[] firstTerm = appendZeroes(c1,length1);
            char[] secondTerm = c2;
            char[] thirdTerm = appendZeroes(f,mid);
            return addNums(addNums(firstTerm,secondTerm),thirdTerm);

        } catch (NumberFormatException e) {
            System.out.println("numA = " + Arrays.toString(numA) + ", numB = " + Arrays.toString(numB));
            System.out.println("d = " + Arrays.toString(d));

            System.out.println("a1 = " + Arrays.toString(a1));
            System.out.println("a2 = " + Arrays.toString(a2));
            System.out.println("b1 = " + Arrays.toString(b1));
            System.out.println("b2 = " + Arrays.toString(b2));
            System.out.println("sum a1 a2 = " + Arrays.toString(addNums(a1,a2)));
            System.out.println("sum b1 b2" + Arrays.toString(addNums(b1,b2)));
            System.out.println("karat: " + Arrays.toString(Karatsuba(addNums(a1,a2),addNums(b1,b2))));


            System.out.println(new BigInteger(new String(numA)));
            System.out.println(new BigInteger(new String(numB)));
            BigInteger sum = new BigInteger(new String(numA)).add(new BigInteger(new String(numB)));
            e.printStackTrace();
        }
        return null;




    }

    public static char[] appendZeroes(char[] arr1, int numZeroes) {
        char[] zeroes = new char[numZeroes];
        Arrays.fill(zeroes,'0');
        return mergeArrays(arr1, zeroes);
    }

    public static char[] mergeArrays(char[] arr1, char[] arr2) {
        char[] ret = new char[(arr1.length+arr2.length)];
        for (int i=0;i<arr1.length;i++) {
            ret[i]=arr1[i];
        }
        for (int i=0;i<arr2.length;i++) {
            ret[arr1.length+i]=arr2[i];
        }
        return ret;
    }

    public static char[] trimNumber(char[] arr1, int digits) {
        if (arr1[0]=='0') {
            return new char[] {'0'};
        }
        return Arrays.copyOfRange(arr1,0,(arr1.length-digits));
    }

    public static void printNum(char[] num) {
        for (int i=0; i< num.length; i++) {
            System.out.print(num[i]);
        }
        System.out.println();
    }

    public static char[] addNums(char[] a, char[] b) {
        BigInteger num1 = new BigInteger(new String(a));
        BigInteger num2 = new BigInteger(new String(b));
        BigInteger sum = num1.add(num2);
        return sum.toString().toCharArray();
    }

    public static char[] diffNums(char[] a, char[] b) {

        if (a.length==0) {
            a = new char[] {'0'};
        }
        if (b.length==0) {
            b = new char[] {'0'};
        }

        BigInteger num1 = new BigInteger(new String(a));
        BigInteger num2 = new BigInteger(new String(b));
        BigInteger sum = num1.subtract(num2);
        return sum.toString().toCharArray();
    }

}