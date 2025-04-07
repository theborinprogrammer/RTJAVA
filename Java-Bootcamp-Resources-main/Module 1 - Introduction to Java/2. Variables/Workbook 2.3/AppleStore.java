public class AppleStore {
    public static void main(String[] args) {
        int numOfApples = 0;
        int numOfCustomers = 0;
        double profit = 0;
        int sales = 0;
        double pricePerApple = .40;

        // Instructions for this workbook are on Learn the Part (see the Udemy Video: Workbook 2.3 to access the link).
        System.out.println("");
        System.out.println("You picked 500 apples from an apple orchard");
        numOfApples += 500;
        System.out.println("Time for business! You're selling each apple for 40 cents");
        System.out.println("One customer walked in. He bought 4 apples!");
        sales = 4;
        numOfApples -=4;
        profit += sales*pricePerApple;
        numOfCustomers++;
        System.out.println("Another customer walked in. He bought 20 apples!");
        sales = 20;
        numOfApples -= 20;
        profit += sales*pricePerApple;
        numOfCustomers++;
        System.out.println("Another customer walked in. She bought 200 apples!");
        sales = 200;
        numOfApples -= 200;
        profit += sales*pricePerApple;
        numOfCustomers++;

        System.out.println("Wow! So far, you made: " + profit);
        System.out.println(numOfCustomers +" customers love your apples.");
        System.out.println("You have "+ numOfApples +" apples left." + " We will sell more tommorow");

        // Compare your result to what's on Learn the Part.  
        
    }

}
