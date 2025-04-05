import java.util.ArrayList;
import java.util.Scanner;

class BankAccount {
    private String accountHolder;
    private int accountNumber;
    private double balance;

    public BankAccount(String accountHolder, int accountNumber, double initialBalance) {
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited $" + amount + " successfully!");
        } else {
            System.out.println("Invalid deposit amount!");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrew $" + amount + " successfully!");
        } else {
            System.out.println("Insufficient balance or invalid amount!");
        }
    }

    public void transfer(BankAccount receiver, double amount) {
        if (amount > 0 && amount <= balance) {
            this.withdraw(amount);
            receiver.deposit(amount);
            System.out.println("Transferred $" + amount + " to " + receiver.getAccountHolder());
        } else {
            System.out.println("Transfer failed! Insufficient balance or invalid amount.");
        }
    }

    public void displayAccountInfo() {
        System.out.println("Account Holder: " + accountHolder);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Balance: $" + balance);
    }
}

public class BankingSystem {
    private static ArrayList<BankAccount> accounts = new ArrayList<>();
    private static int accountNumberSeed = 1001;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Banking System Menu ===");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer Money");
            System.out.println("5. Check Balance");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    depositMoney();
                    break;
                case 3:
                    withdrawMoney();
                    break;
                case 4:
                    transferMoney();
                    break;
                case 5:
                    checkBalance();
                    break;
                case 6:
                    System.out.println("Thank you for using the Banking System!");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void createAccount() {
        System.out.print("Enter Account Holder's Name: ");
        scanner.nextLine(); // Consume newline
        String name = scanner.nextLine();
        System.out.print("Enter Initial Deposit: ");
        double initialBalance = scanner.nextDouble();

        BankAccount newAccount = new BankAccount(name, accountNumberSeed++, initialBalance);
        accounts.add(newAccount);
        System.out.println("Account Created Successfully! Account Number: " + newAccount.getAccountNumber());
    }

    private static BankAccount findAccount(int accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }

    private static void depositMoney() {
        System.out.print("Enter Account Number: ");
        int accountNumber = scanner.nextInt();
        BankAccount account = findAccount(accountNumber);

        if (account != null) {
            System.out.print("Enter Deposit Amount: ");
            double amount = scanner.nextDouble();
            account.deposit(amount);
        } else {
            System.out.println("Account not found!");
        }
    }

    private static void withdrawMoney() {
        System.out.print("Enter Account Number: ");
        int accountNumber = scanner.nextInt();
        BankAccount account = findAccount(accountNumber);

        if (account != null) {
            System.out.print("Enter Withdrawal Amount: ");
            double amount = scanner.nextDouble();
            account.withdraw(amount);
        } else {
            System.out.println("Account not found!");
        }
    }

    private static void transferMoney() {
        System.out.print("Enter Your Account Number: ");
        int senderAcc = scanner.nextInt();
        BankAccount sender = findAccount(senderAcc);

        if (sender == null) {
            System.out.println("Sender account not found!");
            return;
        }

        System.out.print("Enter Recipient's Account Number: ");
        int receiverAcc = scanner.nextInt();
        BankAccount receiver = findAccount(receiverAcc);

        if (receiver == null) {
            System.out.println("Recipient account not found!");
            return;
        }

        System.out.print("Enter Transfer Amount: ");
        double amount = scanner.nextDouble();
        sender.transfer(receiver, amount);
    }

    private static void checkBalance() {
        System.out.print("Enter Account Number: ");
        int accountNumber = scanner.nextInt();
        BankAccount account = findAccount(accountNumber);

        if (account != null) {
            account.displayAccountInfo();
        } else {
            System.out.println("Account not found!");
        }
    }
}
