//This is ATM interface Task.
import java.util.ArrayList;
import java.util.Scanner;
class User {
    private String userId;
    private String pin;
    private double balance;
    private ArrayList<String> transactionHistory;

    public User(String userId, String pin) {
        this.userId = userId;
        this.pin = pin;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<String> getTransactionHistory() {
        return transactionHistory;
    }

    public void addToBalance(double amount) {
        balance += amount;
    }

    public void deductFromBalance(double amount) {
        balance -= amount;
    }

    public void addTransactionToHistory(String transaction) {
        transactionHistory.add(transaction);
    }
}

class ATM {
    private ArrayList<User> users;
    private User currentUser;

    public ATM() {
        this.users = new ArrayList<>();
        
        users.add(new User("user1", "1234"));
        users.add(new User("user2", "5678"));
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        
        User authenticatedUser = authenticateUser(userId, pin);

        if (authenticatedUser != null) {
            currentUser = authenticatedUser;
            showMainMenu();
        } else {
            System.out.println("Invalid User ID or PIN. Exiting...");
        }
    }

    private User authenticateUser(String userId, String pin) {
        for (User user : users) {
            if (user.getUserId().equals(userId) && user.getPin().equals(pin)) {
                return user;
            }
        }
        return null;
    }

    private void showMainMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== ATM Main Menu =====");
            System.out.println("1. View Transactions History");
            System.out.println("2. Withdraw Money");
            System.out.println("3. Deposit Money");
            System.out.println("4. Transfer Money");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewTransactionHistory();
                    break;
                case 2:
                    withdrawMoney();
                    break;
                case 3:
                    depositMoney();
                    break;
                case 4:
                    transferMoney();
                    break;
                case 5:
                    System.out.println("Exiting ATM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private void viewTransactionHistory() {
        System.out.println("\n===== Transaction History =====");
        for (String transaction : currentUser.getTransactionHistory()) {
            System.out.println(transaction);
        }
    }

    private void withdrawMoney() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter withdrawal amount: ");
        double amount = scanner.nextDouble();

        if (amount > 0 && amount <= currentUser.getBalance()) {
            currentUser.deductFromBalance(amount);
            currentUser.addTransactionToHistory("Withdrawal: -$" + amount);
            System.out.println("Withdrawal successful. Current balance: $" + currentUser.getBalance());
        } else {
            System.out.println("Invalid withdrawal amount or insufficient balance.");
        }
    }

    private void depositMoney() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();

        if (amount > 0) {
            currentUser.addToBalance(amount);
            currentUser.addTransactionToHistory("Deposit: +$" + amount);
            System.out.println("Deposit successful. Current balance: $" + currentUser.getBalance());
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    private void transferMoney() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter recipient's User ID: ");
        String recipientId = scanner.nextLine();

        User recipient = findUserById(recipientId);

        if (recipient != null && !recipient.equals(currentUser)) {
            System.out.print("Enter transfer amount: ");
            double amount = scanner.nextDouble();

            if (amount > 0 && amount <= currentUser.getBalance()) {
                currentUser.deductFromBalance(amount);
                recipient.addToBalance(amount);

                currentUser.addTransactionToHistory("Transfer to " + recipientId + ": -$" + amount);
                recipient.addTransactionToHistory("Transfer from " + currentUser.getUserId() + ": +$" + amount);

                System.out.println("Transfer successful. Current balance: $" + currentUser.getBalance());
            } else {
                System.out.println("Invalid transfer amount or insufficient balance.");
            }
        } else {
            System.out.println("Recipient not found or cannot transfer to yourself.");
        }
    }

    private User findUserById(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }
    
}

public class Main {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }
}
