package entities;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by reuvenp on 4/13/2016.
 */
public class User {
    //permissions, mail and userID may not change. there is no setters for those fields
    //if object user is null - its a guest. his permissions are like customer without access to a specific account
    Permission permission;
    String mail;
    String password;
    //userID is the ID of supplier / customer
    //you can access the object by permission and ID.
    //ID alone is not enough because it's possible that customer and supplier have the same ID.
    int userID;
    ArrayList<BookSupplier> order;

    public User(){
        this.userID = -1;
    }

    public User(Permission permission, String mail, String password, int userID) {
        this.permission = permission;
        this.mail = mail;
        this.password = password;
        this.userID = userID;
        order = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "User{" +
                "permission=" + permission +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", userID=" + userID +
                '}';
    }

    public Permission getPermission() {
        return permission;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserID() {
        return userID;
    }

    public ArrayList<BookSupplier> getOrder() {
        return order;
    }

    public void setOrder(ArrayList<BookSupplier> order) {
        this.order = order;
    }
}
