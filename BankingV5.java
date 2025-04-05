import java.util.HashMap;
import java.util.Scanner;

class Account {
    private String accountName;
    private String password;
    private double balance;

    public Account(String accountName, String password, double initialDeposit) {
        this.accountName = accountName;
        this.password = password;
        this.balance = initialDeposit;
    }

    public String getAccountName() {
        return accountName;
    }

    public boolean verifyPassword(String password) {
        return this.password.equals(password);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Successfully deposited $" + amount);
            System.out.println("New balance: $" + balance);
        } else {
            System.out.println("Invalid deposit amount!");
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Successfully withdrew $" + amount);
            System.out.println("New balance: $" + balance);
            return true;
        } else {
            System.out.println("Invalid withdrawal amount or insufficient funds!");
            return false;
        }
    }

    public boolean transfer(Account recipient, double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            recipient.balance += amount;
            System.out.println("Successfully transferred $" + amount + " to " + recipient.getAccountName());
            System.out.println("New balance: $" + balance);
            return true;
        } else {
            System.out.println("Invalid transfer amount or insufficient funds!");
            return false;
        }
    }
}

class BankingV5 {
    private static HashMap<String, Account> accounts = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    private static Account currentAccount = null;

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            if (currentAccount == null) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }

    private static void showLoginMenu() {
        System.out.println("\n=== Welcome to Banking System ===");
        System.out.println("1. Login");
        System.out.println("2. Create Account");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                createAccount();
                break;
            case 3:
                System.out.println("Thank you for using our banking system!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option!");
        }
    }

    private static void showMainMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer Money");
        System.out.println("5. Logout");
        System.out.println("6. Exit");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.println("Current balance: $" + currentAccount.getBalance());
                break;
            case 2:
                System.out.print("Enter amount to deposit: $");
                double depositAmount = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                currentAccount.deposit(depositAmount);
                break;
            case 3:
                System.out.print("Enter amount to withdraw: $");
                double withdrawAmount = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                currentAccount.withdraw(withdrawAmount);
                break;
            case 4:
                transferMoney();
                break;
            case 5:
                currentAccount = null;
                System.out.println("Successfully logged out!");
                break;
            case 6:
                System.out.println("Thank you for using our banking system!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option!");
        }
    }

    private static void transferMoney() {
        System.out.print("Enter recipient's account name: ");
        String recipientName = scanner.nextLine();
        
        Account recipient = accounts.get(recipientName);
        if (recipient == null) {
            System.out.println("Recipient account not found!");
            return;
        }
        
        if (recipient.getAccountName().equals(currentAccount.getAccountName())) {
            System.out.println("Cannot transfer to your own account!");
            return;
        }

        System.out.print("Enter amount to transfer: $");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        currentAccount.transfer(recipient, amount);
    }

    private static void login() {
        System.out.print("Enter account name: ");
        String accountName = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Account account = accounts.get(accountName);
        if (account != null && account.verifyPassword(password)) {
            currentAccount = account;
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid account name or password!");
        }
    }

    private static void createAccount() {
        System.out.print("Enter new account name: ");
        String accountName = scanner.nextLine();

        if (accounts.containsKey(accountName)) {
            System.out.println("Account name already exists!");
            return;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        double initialDeposit;
        do {
            System.out.print("Enter initial deposit (minimum $1000): $");
            initialDeposit = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            if (initialDeposit < 1000) {
                System.out.println("Initial deposit must be at least $1000!");
            }
        } while (initialDeposit < 1000);

        Account newAccount = new Account(accountName, password, initialDeposit);
        accounts.put(accountName, newAccount);
        System.out.println("Account created successfully!");
    }
}