package entities;

import java.util.Date;

/**
 * Created by shmuel on 13/03/2016.
 */
public class Supplier {

    // variables
    Rateing rateing;
    int supplierID;
    String name;
    Date birthday;
    Gender gender;
    String address;
    Account account;


    // constructor
    public Supplier(Rateing rateing, String name, Date birthday, Gender gender, String address, Account account) {
        this.rateing = rateing;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.address = address;
        this.account = account;

    }

    @Override
    public String toString() {
        return "Supplier{" +
                "account=" + account +
                ", rateing=" + rateing +
                ", supplierID=" + supplierID +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", gender=" + gender +
                ", address='" + address + '\'' +
                '}';
    }

    // getters and setters
    public int getSupplierID() {

        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Rateing getRateing() {
        return rateing;
    }

    public void setRateing(Rateing rateing) {
        this.rateing = rateing;
    }
}
