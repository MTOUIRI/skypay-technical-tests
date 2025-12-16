import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Account class implementing core banking operations
 * Supports deposits, withdrawals, and statement printing
 * 
 * @author Mouad TOUIRI
 * @version 1.0
 */
public class Account {
    
    // Transaction class to store individual transactions
    private static class Transaction {
        private final LocalDate date;
        private final int amount;
        private final TransactionType type;
        private final int balanceAfter;
        
        public Transaction(LocalDate date, int amount, TransactionType type, int balanceAfter) {
            this.date = date;
            this.amount = amount;
            this.type = type;
            this.balanceAfter = balanceAfter;
        }
        
        public LocalDate getDate() {
            return date;
        }
        
        public int getAmount() {
            return amount;
        }
        
        public TransactionType getType() {
            return type;
        }
        
        public int getBalanceAfter() {
            return balanceAfter;
        }
    }
    
    // Enum for transaction types
    private enum TransactionType {
        DEPOSIT, WITHDRAWAL
    }
    
    // Instance variables
    private int balance;
    private final ArrayList<Transaction> transactions;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
    /**
     * Constructor - Initializes account with zero balance
     */
    public Account() {
        this.balance = 0;
        this.transactions = new ArrayList<>();
    }
    
    /**
     * Deposits money into the account
     * 
     * @param amount Amount to deposit (must be positive)
     * @param date Date of the deposit
     * @throws IllegalArgumentException if amount is negative or zero
     */
    public void deposit(int amount, LocalDate date) {
        // Validation
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive. Received: " + amount);
        }
        
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        
        // Update balance
        balance += amount;
        
        // Record transaction
        transactions.add(new Transaction(date, amount, TransactionType.DEPOSIT, balance));
        
        System.out.println("Deposit successful: " + amount + " on " + date.format(DATE_FORMATTER));
    }
    
    /**
     * Withdraws money from the account
     * 
     * @param amount Amount to withdraw (must be positive and not exceed balance)
     * @param date Date of the withdrawal
     * @throws IllegalArgumentException if amount is invalid
     * @throws IllegalStateException if insufficient balance
     */
    public void withdraw(int amount, LocalDate date) {
        // Validation
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive. Received: " + amount);
        }
        
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        
        if (amount > balance) {
            throw new IllegalStateException(
                "Insufficient balance. Available: " + balance + ", Requested: " + amount
            );
        }
        
        // Update balance
        balance -= amount;
        
        // Record transaction
        transactions.add(new Transaction(date, amount, TransactionType.WITHDRAWAL, balance));
        
        System.out.println("Withdrawal successful: " + amount + " on " + date.format(DATE_FORMATTER));
    }
    
    /**
     * Prints the bank statement showing all transactions
     * Format: Date | Amount | Balance
     * Transactions are listed in reverse chronological order (newest first)
     */
    public void printStatement() {
        System.out.println("Date       | Amount | Balance");
        
        // Print transactions in reverse order (newest first)
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            
            String dateStr = t.getDate().format(DATE_FORMATTER);
            String amountStr = formatAmount(t.getAmount(), t.getType());
            String balanceStr = String.valueOf(t.getBalanceAfter());
            
            System.out.println(dateStr + " | " + amountStr + " | " + balanceStr);
        }
    }
    
    /**
     * Formats amount with appropriate sign
     * Deposits are positive, withdrawals are negative
     */
    private String formatAmount(int amount, TransactionType type) {
        if (type == TransactionType.DEPOSIT) {
            return "+" + amount;
        } else {
            return "-" + amount;
        }
    }
    
    /**
     * Gets current balance
     * 
     * @return Current account balance
     */
    public int getBalance() {
        return balance;
    }
    
    /**
     * Gets number of transactions
     * 
     * @return Total number of transactions
     */
    public int getTransactionCount() {
        return transactions.size();
    }
    
    // ============================================
    // MAIN METHOD - TEST CASE
    // ============================================
    
    /**
     * Main method demonstrating the desired behaviour from the test specification
     */
    public static void main(String[] args) {
        System.out.println("========== BANKING SERVICE TEST ==========\n");
        
        // Create account
        Account account = new Account();
        
        try {
            System.out.println("--- Performing Transactions ---\n");
            
            // Given a client makes a deposit of 1000 on 10-01-2012
            account.deposit(1000, LocalDate.of(2012, 1, 10));
            
            // And a deposit of 2000 on 13-01-2012
            account.deposit(2000, LocalDate.of(2012, 1, 13));
            
            // And a withdrawal of 500 on 14-01-2012
            account.withdraw(500, LocalDate.of(2012, 1, 14));
            
            // When they print their bank statement
            System.out.println("\n--- Bank Statement ---\n");
            account.printStatement();
            
            System.out.println("\n--- Account Summary ---");
            System.out.println("Current Balance: " + account.getBalance());
            System.out.println("Total Transactions: " + account.getTransactionCount());
            
            // Additional test cases
            System.out.println("\n\n========== ADDITIONAL TEST CASES ==========\n");
            testExceptionHandling();
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Tests exception handling scenarios
     */
    private static void testExceptionHandling() {
        Account testAccount = new Account();
        
        System.out.println("--- Testing Exception Handling ---\n");
        
        // Test 1: Negative deposit
        try {
            System.out.println("Test 1: Attempting negative deposit...");
            testAccount.deposit(-100, LocalDate.now());
            System.out.println("❌ FAILED: Should have thrown exception");
        } catch (IllegalArgumentException e) {
            System.out.println("✅ PASSED: " + e.getMessage());
        }
        
        // Test 2: Withdrawal with insufficient balance
        try {
            System.out.println("\nTest 2: Attempting withdrawal with insufficient balance...");
            testAccount.withdraw(1000, LocalDate.now());
            System.out.println("❌ FAILED: Should have thrown exception");
        } catch (IllegalStateException e) {
            System.out.println("✅ PASSED: " + e.getMessage());
        }
        
        // Test 3: Null date
        try {
            System.out.println("\nTest 3: Attempting deposit with null date...");
            testAccount.deposit(100, null);
            System.out.println("❌ FAILED: Should have thrown exception");
        } catch (IllegalArgumentException e) {
            System.out.println("✅ PASSED: " + e.getMessage());
        }
        
        // Test 4: Zero amount
        try {
            System.out.println("\nTest 4: Attempting zero deposit...");
            testAccount.deposit(0, LocalDate.now());
            System.out.println("❌ FAILED: Should have thrown exception");
        } catch (IllegalArgumentException e) {
            System.out.println("✅ PASSED: " + e.getMessage());
        }
        
        // Test 5: Valid sequence
        System.out.println("\nTest 5: Valid transaction sequence...");
        testAccount.deposit(1000, LocalDate.now());
        testAccount.withdraw(500, LocalDate.now());
        System.out.println("✅ PASSED: Balance = " + testAccount.getBalance());
        
        System.out.println("\n--- All Exception Tests Completed ---");
    }
}