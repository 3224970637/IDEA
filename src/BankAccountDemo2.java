public class BankAccountDemo2 {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000.0);

        Withdrawer person1 = new Withdrawer(account, 200.0);
        Withdrawer person2 = new Withdrawer(account, 300.0);

        person1.start();
        person2.start();
    }
}