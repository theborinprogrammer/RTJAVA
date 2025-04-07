import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
//import java.util.regex.Pattern;

class DebitCard {
    private String cardNumber;
    private String cardType;
    private String expiryDate;
    private int cvv;
    private boolean isActive;

    /**
     * Creates a new debit card with the specified type (VISA or MASTERCARD)
     * Generates a unique card number, expiry date, and CVV
     * @param cardType The type of card to create (VISA or MASTERCARD)
     */
    public DebitCard(String cardType) {
        this.cardType = cardType;
        this.cardNumber = generateCardNumber(cardType);
        this.expiryDate = generateExpiryDate();
        this.cvv = generateCVV();
        this.isActive = true;
    }

    /**
     * Generates a valid card number based on the card type
     * VISA cards start with 4, Mastercard cards start with 5
     * @param cardType The type of card to generate number for (expected: "VISA" or "MASTERCARD")
     * @return A 16-digit card number string
     * @throws IllegalArgumentException if the cardType is invalid
     */
    private String generateCardNumber(String cardType) {
        if (!"VISA".equals(cardType) && !"MASTERCARD".equals(cardType)) {
            throw new IllegalArgumentException("Invalid card type. Expected 'VISA' or 'MASTERCARD'.");
        }

        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();
        
        if (cardType.equals("VISA")) {
            cardNumber.append("4");
            for (int i = 1; i < 16; i++) {
                cardNumber.append(random.nextInt(10));
            }
        } else {
            cardNumber.append("5");
            for (int i = 1; i < 16; i++) {
                cardNumber.append(random.nextInt(10));
            }
        }
        
        return cardNumber.toString();
    }

    /**
     * Generates a random expiry date for the card
     * Date is set between current year and 5 years in the future
     * @return Expiry date in MM/YY format
     */
    private String generateExpiryDate() {
        Random random = new Random();
        int month = random.nextInt(12) + 1;
        int year = 24 + random.nextInt(5);
        return String.format("%02d/%d", month, year);
    }

    /**
     * Generates a random 3-digit CVV number for the card
     * @return A 3-digit CVV number between 100 and 999
     */
    private int generateCVV() {
        Random random = new Random();
        return 100 + random.nextInt(900);
    }

    /**
     * Returns the card's 16-digit number
     * @return The card number as a string
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Returns the type of the card (VISA or MASTERCARD)
     * @return The card type as a string
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * Returns the card's expiry date
     * @return Expiry date in MM/YY format
     */
    public String getExpiryDate() {
        return expiryDate;
    }

    /**
     * Returns the card's CVV number
     * @return The 3-digit CVV number
     */
    public int getCVV() {
        return cvv;
    }

    /**
     * Checks if the card is currently active
     * @return true if the card is active, false otherwise
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Deactivates the card, preventing further use
     */
    public void deactivate() {
        isActive = false;
    }
}

class Account {
    private String accountName;
    private String password;
    private double balance;
    private String accountNumber;
    private DebitCard debitCard;

    /**
     * Creates a new bank account with the specified details
     * Generates a unique 12-digit account number
     * @param accountName The name of the account holder
     * @param password The account password
     * @param initialDeposit The initial deposit amount (must be at least $1000)
     */
    public Account(String accountName, String password, double initialDeposit) {
        this.accountName = accountName;
        this.password = password;
        this.balance = initialDeposit;
        this.accountNumber = generateAccountNumber();
        this.debitCard = null;
    }

    /**
     * Generates a unique 12-digit account number
     * @return A 12-digit account number as a string
     */
    private String generateAccountNumber() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            accountNumber.append(random.nextInt(10));
        }
        return accountNumber.toString();
    }

    /**
     * Returns the account holder's name
     * @return The account name as a string
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Returns the account's unique number
     * @return The 12-digit account number as a string
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Verifies if the provided password matches the account password
     * @param password The password to verify
     * @return true if the password matches, false otherwise
     */
    public boolean verifyPassword(String password) {
        return this.password.equals(password);
    }

    /**
     * Returns the current account balance
     * @return The account balance as a double
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Returns the account's debit card if one exists
     * @return The DebitCard object or null if no card exists
     */
    public DebitCard getDebitCard() {
        return debitCard;
    }

    /**
     * Issues a new debit card for the account
     * Can be either VISA or MASTERCARD
     * @param cardType The type of card to issue
     */
    public void issueDebitCard(String cardType) {
        if (debitCard == null || !debitCard.isActive()) {
            debitCard = new DebitCard(cardType);
            System.out.println("Debit card issued successfully!");
            System.out.println("Card Number: " + debitCard.getCardNumber());
            System.out.println("Expiry Date: " + debitCard.getExpiryDate());
            System.out.println("CVV: " + debitCard.getCVV());
        } else {
            System.out.println("You already have an active debit card!");
        }
    }

    /**
     * Deposits money into the account
     * @param amount The amount to deposit (must be positive)
     */
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Successfully deposited $" + amount);
            System.out.println("New balance: $" + balance);
        } else {
            System.out.println("Invalid deposit amount!");
        }
    }

    /**
     * Withdraws money from the account
     * @param amount The amount to withdraw (must be positive and not exceed balance)
     * @return true if withdrawal was successful, false otherwise
     */
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

    /**
     * Transfers money from this account to another account
     * @param recipient The account to receive the money
     * @param amount The amount to transfer (must be positive and not exceed balance)
     * @return true if transfer was successful, false otherwise
     */
    public boolean transfer(Account recipient, double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            recipient.balance += amount;
            System.out.println("Successfully transferred $" + amount + " to account " + recipient.getAccountNumber());
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
    private static HashMap<String, Account> cardToAccount = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    private static Account currentAccount = null;

    /**
     * Main entry point of the banking application
     * Manages the main program loop and menu navigation
     * @param args Command line arguments (not used)
     */
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

    /**
     * Displays the login menu and handles user authentication
     * Options include login, create account, and exit
     */
    private static void showLoginMenu() {
        System.out.println("\n=== Welcome to Banking System ===");
        System.out.println("1. Login");
        System.out.println("2. Create Account");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

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

    /**
     * Displays the main banking menu and handles user transactions
     * Options include checking balance, deposits, withdrawals, transfers, etc.
     */
    private static void showMainMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer Money");
        System.out.println("5. Issue Debit Card");
        System.out.println("6. View Account Details");
        System.out.println("7. Logout");
        System.out.println("8. Exit");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.println("Current balance: $" + currentAccount.getBalance());
                break;
            case 2:
                System.out.print("Enter amount to deposit: $");
                double depositAmount = scanner.nextDouble();
                scanner.nextLine();
                currentAccount.deposit(depositAmount);
                break;
            case 3:
                System.out.print("Enter amount to withdraw: $");
                double withdrawAmount = scanner.nextDouble();
                scanner.nextLine();
                currentAccount.withdraw(withdrawAmount);
                break;
            case 4:
                transferMoney();
                break;
            case 5:
                issueDebitCard();
                break;
            case 6:
                viewAccountDetails();
                break;
            case 7:
                currentAccount = null;
                System.out.println("Successfully logged out!");
                break;
            case 8:
                System.out.println("Thank you for using our banking system!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option!");
        }
    }

    /**
     * Displays detailed information about the current account
     * Shows account name, number, balance, and debit card details if available
     */
    private static void viewAccountDetails() {
        System.out.println("\n=== Account Details ===");
        System.out.println("Account Name: " + currentAccount.getAccountName());
        System.out.println("Account Number: " + currentAccount.getAccountNumber());
        System.out.println("Current Balance: $" + currentAccount.getBalance());
        
        DebitCard card = currentAccount.getDebitCard();
        if (card != null && card.isActive()) {
            System.out.println("\nDebit Card Details:");
            System.out.println("Card Type: " + card.getCardType());
            System.out.println("Card Number: " + card.getCardNumber());
            System.out.println("Expiry Date: " + card.getExpiryDate());
            System.out.println("CVV: " + card.getCVV());
        } else {
            System.out.println("\nNo active debit card issued.");
        }
    }

    /**
     * Handles the process of issuing a new debit card
     * Allows user to choose between VISA and MASTERCARD
     */
    private static void issueDebitCard() {
        System.out.println("\n=== Issue Debit Card ===");
        System.out.println("1. VISA");
        System.out.println("2. MASTERCARD");
        System.out.print("Choose card type: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        String cardType;
        switch (choice) {
            case 1:
                cardType = "VISA";
                break;
            case 2:
                cardType = "MASTERCARD";
                break;
            default:
                System.out.println("Invalid choice!");
                return;
        }
        
        currentAccount.issueDebitCard(cardType);
    }

    /**
     * Handles the money transfer process between accounts
     * Uses card numbers for recipient identification
     */
    private static void transferMoney() {
        System.out.print("Enter recipient's card number: ");
        String cardNumber = scanner.nextLine();
        
        Account recipient = cardToAccount.get(cardNumber);
        if (recipient == null) {
            System.out.println("Recipient card not found!");
            return;
        }
        
        if (recipient.getAccountNumber().equals(currentAccount.getAccountNumber())) {
            System.out.println("Cannot transfer to your own account!");
            return;
        }

        System.out.print("Enter amount to transfer: $");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        currentAccount.transfer(recipient, amount);
    }

    /**
     * Handles the user login process
     * Verifies account name and password
     */
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

    /**
     * Handles the account creation process
     * Validates account name uniqueness and minimum initial deposit
     */
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
            scanner.nextLine();

            if (initialDeposit < 1000) {
                System.out.println("Initial deposit must be at least $1000!");
            }
        } while (initialDeposit < 1000);

        Account newAccount = new Account(accountName, password, initialDeposit);
        accounts.put(accountName, newAccount);
        System.out.println("Account created successfully!");
        System.out.println("Your account number is: " + newAccount.getAccountNumber());
    }
}