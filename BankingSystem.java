import java.util.Scanner;

// --- BASE CLASS ---
class Account {
    protected String accountHolderName;
    protected String accountNumber;
    protected double balance;

    public Account(String name, String accNum, double initialBalance) {
        this.accountHolderName = name;
        this.accountNumber = accNum;
        // Logic Improvement: Prevent negative starting balance
        if(initialBalance < 0) {
            System.out.println("Warning: Initial balance cannot be negative. Setting to RM 0.");
            this.balance = 0;
        } else {
            this.balance = initialBalance;
        }
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.printf("Success! New Balance: RM %.2f%n", balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        System.out.println("Base withdrawal logic.");
    }

    public void calculateInterest() {
        System.out.println("Base interest logic.");
    }
    
    public void displayInfo() {
        System.out.println("\n--- Account Details ---");
        System.out.println("Holder: " + accountHolderName);
        System.out.println("Account #: " + accountNumber);
        System.out.printf("Balance: RM %.2f%n", balance);
    }
}

// --- CHILD CLASS 1: Savings Account ---
class SavingsAccount extends Account { 
    private double interestRate;
    private double minimumBalance = 10.0;
    private boolean isActive; 

    public SavingsAccount(String name, String accNum, double initialBalance, double rate) {
        super(name, accNum, initialBalance);
        this.interestRate = rate;
        this.isActive = true; // Default to active
    }

    @Override
    public void withdraw(double amount) {
        if (!isActive) {
            System.out.println("Error: Account is inactive.");
            return;
        }
        if (balance - amount >= minimumBalance) {
            balance -= amount;
            System.out.printf("Withdrawn: RM %.2f. Remaining: RM %.2f%n", amount, balance);
        } else {
            System.out.printf("Error: Minimum balance of RM %.2f required.%n", minimumBalance);
        }
    }

    @Override
    public void calculateInterest() {
        double interest = balance * (interestRate / 100);
        System.out.printf("Calculated Interest: RM %.2f%n", interest);
        deposit(interest); 
    }
}

// --- CHILD CLASS 2: Current Account ---
class CurrentAccount extends Account {
    private double overdraftLimit;
    private double transactionFee = 1.50;
    public CurrentAccount(String name, String accNum, double initialBalance, double overdraft) {
        super(name, accNum, initialBalance);
        this.overdraftLimit = overdraft;
    }

    @Override
    public void withdraw(double amount) {
        if (balance + overdraftLimit >= amount) {
            balance -= amount;
            balance -= transactionFee;
            System.out.printf("Withdrawn: RM %.2f (Fee: RM %.2f)%n", amount, transactionFee);
            System.out.printf("Remaining Balance: RM %.2f%n", balance);
        } else {
            System.out.println("Error: Exceeds overdraft limit.");
        }
    }

    @Override
    public void calculateInterest() {
        System.out.println("Current accounts do not earn interest.");
    }
}

// --- MAIN CLASS ---
public class BankingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Account myAccount = null; 

        System.out.println("=== CREATE YOUR ACCOUNT ===");
        System.out.print("Enter Account Holder Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Account Number: ");
        String accNum = scanner.nextLine();

        System.out.print("Enter Initial Balance: ");
        double balance = scanner.nextDouble();
        
        System.out.println("\nSelect Account Type:");
        System.out.println("1. Savings Account");
        System.out.println("2. Current Account");
        System.out.print("Choice: ");
        int type = scanner.nextInt();

        if (type == 1) {
            System.out.print("Enter Interest Rate (%): ");
            double rate = scanner.nextDouble();
            myAccount = new SavingsAccount(name, accNum, balance, rate);
        } else if (type == 2) {
            System.out.print("Enter Overdraft Limit: ");
            double limit = scanner.nextDouble();
            myAccount = new CurrentAccount(name, accNum, balance, limit);
        } else {
            System.out.println("Invalid selection. Exiting.");
            System.exit(0);
        }

        System.out.println("\nAccount Created Successfully!");

        boolean running = true;
        while (running) {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Display Info");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Calculate Interest");
            System.out.println("5. Exit");
            System.out.print("Choose an action: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    myAccount.displayInfo();
                    break;
                case 2:
                    System.out.print("Enter deposit amount: ");
                    double depAmount = scanner.nextDouble();
                    myAccount.deposit(depAmount);
                    break;
                case 3:
                    System.out.print("Enter withdrawal amount: ");
                    double withAmount = scanner.nextDouble();
                    myAccount.withdraw(withAmount);
                    break;
                case 4:
                    myAccount.calculateInterest();
                    break;
                case 5:
                    running = false;
                    System.out.println("Thank you for using our system.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
        scanner.close();
    }
}

// Youtube Link: https://youtu.be/2kX_Yhit9AQ