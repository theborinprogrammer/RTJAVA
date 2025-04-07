import java.util.Scanner;

public class JoiningStrings {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //String accouncements = "I'm hungry";
        String Winner;
        System.out.print("Which year would you like to choose? ");
        int year = sc.nextInt();
        if(year == 2010)
            {Winner = "Spain";
            String announcements = "The winner of World Cup " + year + " is " + Winner;
            System.out.print(announcements);}
        else 
            {Winner = "Can be any of the teams who won prior or after Spain in 2010.";
            System.out.println("Winner: " + Winner);}
    }
}
