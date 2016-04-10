package entities;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by shmuel on 13/03/2016.
 */
public class Order {

    // variables

    Customer customer;
    int orderID;
    ArrayList<BooksForOrder> booksForOrders;
    Date orderDate;
    float totalPrice;
    boolean orderCompleted;


    // constructor
    public Order(Customer customer, ArrayList<BooksForOrder> booksForOrders, Date orderDate,float totalPrice, boolean orderCompleted) {
        this.customer = customer;
        this.booksForOrders = booksForOrders;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.orderCompleted = orderCompleted;
    }

    // functions
    @Override
    public String toString() {
        return "Order{" +
                "booksForOrders=" + booksForOrders +
                ", customer=" + customer +
                ", orderID=" + orderID +
                ", orderDate=" + orderDate +
                ", totalPrice=" + totalPrice +
                ", orderCompleted=" + orderCompleted +
                '}';
    }

    // add booksForOrder to the list.
    public void addBooksForOrder (BooksForOrder booksForOrder) throws Exception {
//        if(!supplierList.contains(order.getSupplier())){
//            throw new Exception("This supplier(" + order.getSupplier().getSupplierID() + " " + order.getSupplier().getName() + ") does not exist in supplierList\n first add the supplier to supplierList!");
//        }
        this.booksForOrders.add(booksForOrder);
    }

    // delete booksForOrder from the list.
    public void deleteBooksForOrder (BooksForOrder booksForOrder) throws Exception {
        this.booksForOrders.remove(booksForOrder);
    }



    // getters and setters
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ArrayList<BooksForOrder> getBooksForOrders() {
        return booksForOrders;
    }

    public void setBooksForOrders(ArrayList<BooksForOrder> booksForOrders) {
        this.booksForOrders = booksForOrders;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isOrderCompleted() {
        return orderCompleted;
    }

    public void setOrderCompleted(boolean orderCompleted) {
        this.orderCompleted = orderCompleted;
    }
}
