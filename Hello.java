import java.util.Scanner;

class Hello {
   public static void main(String[] args) {
       System.out.println("Hello World");

       try (Scanner sc = new Scanner(System.in)) {
           System.out.println("Enter the size of the array:");
           int n = sc.nextInt();
           sc.nextLine();

           String[] arr = new String[n];

           System.out.println("Enter the elements of the array:");
           for (int i = 0; i < n; i++) {
               while (true) {
                   String input = sc.nextLine().trim();
                   if (!input.isEmpty()) {
                       arr[i] = input;
                       break;
                   }
                   System.out.println("Input cannot be empty. Please try again:");
               }
           }

           System.out.println("The elements of the array are:");
           for (String str : arr) {
               System.out.println(str);
           }
       }
   }
}