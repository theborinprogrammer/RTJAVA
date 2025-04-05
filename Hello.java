import java.util.Scanner;

class Hello {
   public static void main(String[] args) {
       System.out.println("Hello World");
       Scanner sc = new Scanner(System.in);

       System.out.println("Enter the size of the array");
       int n = sc.nextInt();
       sc.nextLine();

       String[] arr = new String[n];

       System.out.println("Enter the elements of the array");
       for (int i = 0; i < n; i++) {
           String input;
           do {
               input = sc.nextLine().trim();
           } while (input.isEmpty());
           arr[i] = input;
       }

       System.out.println("The elements of the array are:");
       for (String str : arr) {
           System.out.println(str);
       }

       sc.close();
   } 
}
