/*
创建了一个银行账户对象，然后创建一个存钱线程和一个取钱线程。这两个线程并行运行，模拟了两个人同时操作一个银行账户。
 */
public class BankAccountDemo {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000.0);

        Depositor depositor = new Depositor(account, 200.0);
        Withdrawer withdrawer = new Withdrawer(account, 500.0);

        depositor.start();
        withdrawer.start();
    }
}
