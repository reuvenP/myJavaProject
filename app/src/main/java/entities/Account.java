package entities;
//TODO: enum for permissions.
//TODO: variable for supplier / customer ID
//TODO: add category
//TODO: add to supplier and customer mail and password for login or create DB for login.
import java.util.Date;

/**
 * Created by shmuel on 13/03/2016.
 */
public class Account {

    // variables
    float balance;
    Date lastActionDate;
    float lastActionSum;

    // default constructor
    public Account() {
        this.balance = 0;
        this.lastActionDate = null;
        this.lastActionSum = 0;
    }

    // constructor
    public Account(float balance, Date lastActionDate, float lastActionSum) {
        this.balance = balance;
        this.lastActionDate = lastActionDate;
        this.lastActionSum = lastActionSum;
    }

    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance +
                ", lastActionDate=" + lastActionDate +
                ", lastActionSum=" + lastActionSum +
                '}';
    }

    // getters and setters
    public float getBalance() {

        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public Date getLastActionDate() {
        return lastActionDate;
    }

    public void setLastActionDate(Date lastActionDate) {
        this.lastActionDate = lastActionDate;
    }

    public float getLastActionSum() {
        return lastActionSum;
    }

    public void setLastActionSum(float lastActionSum) {
        this.lastActionSum = lastActionSum;
    }
}
