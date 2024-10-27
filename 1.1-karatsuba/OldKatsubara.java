public class Main {
    public static void main(String[] args) {

        //System.out.println("Final sum for 100 & 100: " + Karatsuba(100,100));
        System.out.println("Final sum for 101 & 100: " + Karatsuba(101,100) + "\n");

        System.out.println(Karatsuba(96511,54831));

        //System.out.println(Karatsuba(5486157596,1524865987));

    }

    public static long Karatsuba(long a, long b) {

        if (a==0 || b==0) {
            return 0;
        }

        long lengthOfA = digitsIn(a);
        long lengthOfB = digitsIn(b);

        if (lengthOfA < lengthOfB) {
            System.out.println("A less B");
            return Karatsuba(a*(long)Math.pow(10,lengthOfB-lengthOfA),b)/(long)Math.pow(10,lengthOfB-lengthOfA);
        } else if (lengthOfB < lengthOfA){
            System.out.println("B less A");
            return Karatsuba(a,b*(long)Math.pow(10,lengthOfA-lengthOfB))/(long)Math.pow(10,lengthOfA-lengthOfB);
        }

        long length = lengthOfA;

        if (length == 1) {
            long total = a*b;
            System.out.println("Karatsuba sum for " + a + ", and " + b + ": " + total);
            return total;
        }

        if (length%2!=0) {
            System.out.println("odd length");
            return Karatsuba(a*10, b*10)/100;
        }



        System.out.println("Karatsuba with: " + a + ", and " + b);

        long a1 = (a/(long)Math.pow(10,length/2));
        long a2 = a%(long)Math.pow(10,length/2);

        long b1 = (b/(long)Math.pow(10,length/2));
        long b2 = b%(long)Math.pow(10,length/2);

        System.out.println("length is: " + length);
        System.out.println("first half a: " + a1);
        System.out.println("second half a: " + a2);
        System.out.println("first half b: " + b1);
        System.out.println("second half b: " + b2);

        long c1= Karatsuba(a1, b1);
        System.out.println("C1 for " + a1 + ", " + b1 + ": " + c1);
        long c2= Karatsuba(a2, b2);
        System.out.println("C2 for " + a2 + ", " + b2 + ": " + c2);

        long a3=a1+a2;
        long a4=b1+b2;
        long d= Karatsuba(a3, a4);
        System.out.println("D for " + a3 + ", " + a4+ ": " + d);
        long f = d-(c1+c2);
        System.out.println("F is: " +f);

        long sum = ((c1*(long)Math.pow(10,(length))))+(f*(long)Math.pow(10,length/2))+c2;
        System.out.println("Karatsuba sum for " + a + ", and " + b + ": " + sum);

        return sum;
    }

    public static long digitsIn(long num) {
        if (num/10 == 0) {
            return 1;
        } else {
            return digitsIn(num/10) + 1;
        }
    }
}