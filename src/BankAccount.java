/*
BankAccount类表示银行账户，有两个方法：deposit用于存钱，withdraw用于取钱。Depositor和Withdrawer类分别表示存钱和取钱的线程。
deposit和withdraw方法都使用synchronized来确保线程安全，以避免并发访问问题。
 */

public class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        balance = initialBalance;
    }

    public synchronized void deposit(double amount) {
        balance += amount;
        System.out.println("存钱：" + amount + "，余额：" + balance);
    }

    public synchronized void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println("取钱：" + amount + "，余额：" + balance);
        } else {
            System.out.println("余额不足，无法取钱：" + amount);
        }
    }

    public double getBalance() {
        return balance;
    }
}

class Depositor extends Thread {
    private BankAccount account;
    private double amount;

    public Depositor(BankAccount account, double amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void run() {
        account.deposit(amount);
    }
}

class Withdrawer extends Thread {
    private BankAccount account;
    private double amount;

    public Withdrawer(BankAccount account, double amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void run() {
        account.withdraw(amount);
    }
}

