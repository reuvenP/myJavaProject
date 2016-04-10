package entities;

import java.util.Date;

/**
 * Created by shmuel on 13/03/2016.
 */
public class Customer {

    // variables
    CustomerType customerType;
    int customerID;
    String name;
    Date birthday;
    Gender gender;
    String address;
    Account account;

    // constructor
    public Customer(CustomerType customerType, String name, Date birthday, Gender gender, String address, Account account) {
        this.customerType = customerType;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.address = address;
        this.account = account;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "account=" + account +
                ", customerType=" + customerType +
                ", customerID=" + customerID +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", gender=" + gender +
                ", address='" + address + '\'' +
                '}';
    }

    // getters and setters
    public int getCustomerID() {

        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
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


    public CustomerType getCustomerType() {

        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }
}
