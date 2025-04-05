/*This code is still buggy and under development. */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

class Transaction {
    private final String type;
    private final double amount;
    private final LocalDateTime timestamp;
    private final String description;

    public Transaction(String type, double amount, String description) {
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.description = description;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("%s | %s | %s | %s", 
            timestamp.format(formatter), type, formatCurrency(amount), description);
    }

    private String formatCurrency(double amount) {
        return String.format("$%,.2f", amount);
    }
}

class BankAccount {
    private final String accountHolder;
    private final int accountNumber;
    private double balance;
    private final ArrayList<Transaction> transactionHistory;
    private String pin;
    private boolean isLocked;
    private int failedAttempts;
    private final LocalDateTime timestamp;

    public BankAccount(String accountHolder, int accountNumber, double initialBalance, String pin) {
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.pin = pin;
        this.transactionHistory = new ArrayList<>();
        this.isLocked = false;
        this.failedAttempts = 0;
        this.timestamp = LocalDateTime.now();
        addTransaction("DEPOSIT", initialBalance, "Initial deposit");
    }

    public String getAccountHolder() { return accountHolder; }
    public int getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public boolean isLocked() { return isLocked; }

    public boolean validatePin(String inputPin) {
        if (isLocked) {
            System.out.println("Account is locked. Please contact support.");
            return false;
        }
        if (pin.equals(inputPin)) {
            failedAttempts = 0;
            return true;
        }
        failedAttempts++;
        if (failedAttempts >= 3) {
            isLocked = true;
            System.out.println("Account locked due to too many failed attempts.");
        }
        return false;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            addTransaction("DEPOSIT", amount, "Deposit");
            System.out.printf("Successfully deposited %s%n", formatCurrency(amount));
        } else {
            System.out.println("Invalid deposit amount!");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            addTransaction("WITHDRAW", -amount, "Withdrawal");
            System.out.printf("Successfully withdrew %s%n", formatCurrency(amount));
        } else {
            System.out.println("Insufficient balance or invalid amount!");
        }
    }

    public void transfer(BankAccount receiver, double amount) {
        if (amount > 0 && amount <= balance) {
            this.withdraw(amount);
            receiver.deposit(amount);
            addTransaction("TRANSFER", -amount, 
                String.format("Transfer to account %d", receiver.getAccountNumber()));
            receiver.addTransaction("TRANSFER", amount, 
                String.format("Transfer from account %d", this.getAccountNumber()));
            System.out.printf("Successfully transferred %s to %s%n", 
                formatCurrency(amount), receiver.getAccountHolder());
        } else {
            System.out.println("Transfer failed! Insufficient balance or invalid amount.");
        }
    }

    private void addTransaction(String type, double amount, String description) {
        transactionHistory.add(new Transaction(type, amount, description));
    }

    public void displayAccountInfo() {
        System.out.println("\n=== Account Information ===");
        System.out.printf("Account Holder: %s%n", accountHolder);
        System.out.printf("Account Number: %d%n", accountNumber);
        System.out.printf("Current Balance: %s%n", formatCurrency(balance));
        System.out.printf("Account Status: %s%n", isLocked ? "LOCKED" : "ACTIVE");
    }

    public void displayTransactionHistory() {
        System.out.println("\n=== Transaction History ===");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        for (Transaction t : transactionHistory) {
            System.out.println(t);
        }
    }

    public void changePin(String newPin) {
        if (isValidPin(newPin)) {
            this.pin = newPin;
            System.out.println("PIN changed successfully!");
        } else {
            System.out.println("Invalid PIN format!");
        }
    }

    private boolean isValidPin(String pin) {
        return pin != null && pin.length() == 4 && pin.matches("\\d+");
    }

    private String formatCurrency(double amount) {
        return String.format("$%,.2f", amount);
    }
}

public class BankingV2 {
    private static final ArrayList<BankAccount> accounts = new ArrayList<>();
    private static int accountNumberSeed = 1001;
    private static final Scanner scanner = new Scanner(System.in);
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s]{2,50}$");

    public static void main(String[] args) {
        while (true) {
            displayMainMenu();
            int choice = getValidChoice();
            
            if (choice == 7) {
                System.out.println("Thank you for using the Banking System!");
                return;
            }
            
            processChoice(choice);
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n=== Banking System Menu ===");
        System.out.println("1. Create Account");
        System.out.println("2. Login to Account");
        System.out.println("3. Deposit");
        System.out.println("4. Withdraw");
        System.out.println("5. Transfer Money");
        System.out.println("6. View Account Details");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getValidChoice() {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void processChoice(int choice) {
        switch (choice) {
            case 1 -> createAccount();
            case 2 -> loginAccount();
            case 3 -> depositMoney();
            case 4 -> withdrawMoney();
            case 5 -> transferMoney();
            case 6 -> viewAccountDetails();
            default -> System.out.println("Invalid choice! Please try again.");
        }
    }

    private static void createAccount() {
        System.out.println("\n=== Create New Account ===");
        String name = getValidName();
        String pin = getValidPin();
        double initialBalance = getValidAmount("Enter Initial Deposit: ");

        BankAccount newAccount = new BankAccount(name, accountNumberSeed++, initialBalance, pin);
        accounts.add(newAccount);
        System.out.printf("Account Created Successfully!%nAccount Number: %d%n", 
            newAccount.getAccountNumber());
    }

    private static String getValidName() {
        String name;
        do {
            System.out.print("Enter Account Holder's Name (2-50 characters): ");
            scanner.nextLine();
            name = scanner.nextLine();
            if (!NAME_PATTERN.matcher(name).matches()) {
                System.out.println("Invalid name format!");
            }
        } while (!NAME_PATTERN.matcher(name).matches());
        return name;
    }

    private static String getValidPin() {
        String pin;
        do {
            System.out.print("Enter 4-digit PIN: ");
            pin = scanner.nextLine();
            if (!pin.matches("\\d{4}")) {
                System.out.println("PIN must be 4 digits!");
            }
        } while (!pin.matches("\\d{4}"));
        return pin;
    }

    private static double getValidAmount(String prompt) {
        double amount;
        do {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            input = input.replace("$", "").replace(",", "");
            try {
                amount = Double.parseDouble(input);
                if (amount <= 0) {
                    System.out.println("Amount must be positive!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount format! Please enter a valid number.");
                amount = -1;
            }
        } while (amount <= 0);
        return amount;
    }

    private static BankAccount loginAccount() {
        System.out.print("Enter Account Number: ");
        int accountNumber = scanner.nextInt();
        BankAccount account = findAccount(accountNumber);

        if (account != null) {
            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine();
            if (account.validatePin(pin)) {
                System.out.println("Login successful!");
                return account;
            }
        }
        return null;
    }

    private static BankAccount findAccount(int accountNumber) {
        return accounts.stream()
            .filter(acc -> acc.getAccountNumber() == accountNumber)
            .findFirst()
            .orElse(null);
    }

    private static void depositMoney() {
        BankAccount account = loginAccount();
        if (account != null) {
            double amount = getValidAmount("Enter Deposit Amount: ");
            account.deposit(amount);
        }
    }

    private static void withdrawMoney() {
        BankAccount account = loginAccount();
        if (account != null) {
            double amount = getValidAmount("Enter Withdrawal Amount: ");
            account.withdraw(amount);
        }
    }

    private static void transferMoney() {
        BankAccount sender = loginAccount();
        if (sender == null) return;

        System.out.print("Enter Recipient's Account Number: ");
        int receiverAcc = scanner.nextInt();
        BankAccount receiver = findAccount(receiverAcc);

        if (receiver != null) {
            double amount = getValidAmount("Enter Transfer Amount: ");
            sender.transfer(receiver, amount);
        } else {
            System.out.println("Recipient account not found!");
        }
    }

    private static void viewAccountDetails() {
        BankAccount account = loginAccount();
        if (account != null) {
            account.displayAccountInfo();
            account.displayTransactionHistory();
        }
    }
}