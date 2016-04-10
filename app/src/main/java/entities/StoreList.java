package entities;

import java.util.ArrayList;
import java.util.List;

import model.backend.Backend;

/**
 * Created by shmuel on 29/03/2016.
 */
    public class StoreList implements Backend {

    private ArrayList<Book> bookList;
    private ArrayList<Customer> customerList;
    private ArrayList<Supplier> supplierList;
    private ArrayList<BooksInStore> booksInStoreList;
    private ArrayList<Order> orderList;
    private ArrayList<BookSupplier> bookSupplierList;


    // constructor
    public StoreList(ArrayList<BooksInStore> booksInStoreList, ArrayList<Supplier> supplierList, ArrayList<Book> bookList, ArrayList<Customer> customerList,
                     ArrayList<Order> orderList, ArrayList<BookSupplier> bookSupplierList) {
        this.booksInStoreList = booksInStoreList;
        this.supplierList = supplierList;
        this.bookList = bookList;
        this.customerList = customerList;
        this.orderList = orderList;
        this.bookSupplierList = bookSupplierList;
    }

    // functions:


    @Override
    // add book to bookList.
    public void addBook (Book book) throws Exception {
        if (bookList.contains(book)) {
            throw new Exception("This book(" + book.getBookID() + " " + book.getTitle() + ") already exists");
        }
        bookList.add(book);
    }

    @Override
    // add customer to customerList.
    public void addCustomer (Customer customer) throws Exception {
        if (customerList.contains(customer)) {
            throw new Exception("This customer(" + customer.getCustomerID() + " " + customer.getName() + ") already exists");
        }
        customerList.add(customer);
    }

    @Override
    // add supplier to supplierList.
    public void addSupplier (Supplier supplier) throws Exception {
        if (supplierList.contains(supplier)) {
            throw new Exception("This supplier(" + supplier.getSupplierID() + " " + supplier.getName() + ") already exists");
        }
        supplierList.add(supplier);
    }

    @Override
    // add order to orderList.
    public void addOrder (Order order) throws Exception{
        /*if(!supplierList.contains(order.getSupplier())){
            throw new Exception("This supplier(" + order.getSupplier().getSupplierID() + " " + order.getSupplier().getName() + ") does not exist in supplierList\n first add the supplier to supplierList!");
        }
        if(!supplierList.contains(order.getCustomer())){
            throw new Exception("This customer(" + order.getCustomer().getCustomerID() + " " + order.getCustomer().getName() + ") does not exist in customerList\n first add the customer to customerList!");
        }
        float totalPrice = 0;
        for (BooksForOrder booksForOrder :order.getBooksForOrders()) {
            if(!bookList.contains(booksForOrder.getBook())){
                throw new Exception("This book(" + booksForOrder.getBook().getBookID() + " " + booksForOrder.getBook().getTitle() + ") does not exist in bookList\n first add the book to bookList!");
            }
            try {
                totalPrice += booksForOrder.getSumOfBooks() * getBookSupplierBySupplierIDAndByBookID(order.getSupplier().getSupplierID(),booksForOrder.getBook().getBookID()).getPrice();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        order.setTotalPrice(totalPrice);
        if(orderList.contains(order)) {
            throw new Exception("This order(" + order.getOrderID() + ")  already exists");
        }
        orderList.add(order);*/
    }

    @Override
    public void addBooksForOrder(int orderID, BooksForOrder booksForOrder) throws Exception {

    }

    @Override
    // add bookSupplier to bookSupplierList.
    public void addBookSupplier (BookSupplier bookSupplier) throws Exception {
        if(!bookList.contains(bookSupplier.getBook())){
            throw new Exception("This book does not exist in bookList\n first add the book to bookList!");
        }
        if(!supplierList.contains(bookSupplier.getSupplier())){
            throw new Exception("This supplier does not exist in supplierList\n first add the supplier to supplierList!");
        }
        if (bookSupplierList.contains(bookSupplier)) {
            throw new Exception("This bookSupplier already exists");
        }
        bookSupplierList.add(bookSupplier);
    }

    @Override
    // add booksInStore to booksInStoreList.
    public void addBooksInStore (BooksInStore booksInStore) throws Exception {
        if(!bookList.contains(booksInStore.getBook())){
            throw new Exception("This book(" + booksInStore.getBook().getBookID() + " " + booksInStore.getBook().getTitle() + ") does not exist in bookList\n first add the book to the bookList!");
        }
        if (booksInStoreList.contains(booksInStore)) {
            throw new Exception("This booksInStore(" + booksInStore.getBook().getTitle() + ") already exists");
        }
        booksInStoreList.add(booksInStore);
    }

    @Override
    public void deleteBook(int bookID) throws Exception {
        try {
            Book bookTemp = getBookByBookID(bookID);
            if(getBooksInStoreByBookID(bookID).getAmount() > 0) {
                throw new Exception("There are still copies of this book in the store,\n" +
                        " First deleted from the store.");
            }
            bookList.remove(bookTemp);
            System.out.println("Book removed");
        }
        catch (Exception e){
            throw new Exception("This book already does not exist in bookList");
        }
    }

    @Override
    public void deleteBookFromStore(int bookID) throws Exception {
        try {
            BooksInStore booksInStoreTemp = getBooksInStoreByBookID(bookID);
            // Maybe we should make sure that there isn't an order to this book when there are still more copies in the store.
            booksInStoreList.remove(booksInStoreTemp);
            System.out.println("book removed");
        }
        catch (Exception e){
            throw new Exception("This book already does not exist in booksInStoreList");
        }
    }

    @Override
    public void deleteCustomer(int customerID) throws Exception {
        try {
            Customer customerTemp = getCustomerByCustomerID(customerID);
            if(getOrderByCustomerID(customerID).isOrderCompleted() == false) {
                throw new Exception("This customer have an open order(" + getOrderByCustomerID(customerID).isOrderCompleted() + ") to get first");
            }
            customerList.remove(customerTemp);
            System.out.println("Customer removed");
        }
        catch (Exception e){
            throw new Exception("This customer already does not exist in customerList");
        }
    }

    @Override
    public void deleteSupplier(int supplierID) throws Exception {
        /*try {
            Supplier supplierTemp = getSupplierBySupplierID(supplierID);
            if(getOrderBySupplierID(supplierID).isOrderCompleted() == false) {
                throw new Exception("This supplier have an open order(" + getOrderBySupplierID(supplierID).getOrderID() + ") to supply first");
            }
            customerList.remove(supplierTemp);
            System.out.println("Supplier removed");
        }
        catch (Exception e){
            throw new Exception("This supplier already does not exist in supplierList");
        }*/
    }


    @Override
    public void deleteBooksInStore(int bookID) throws Exception {
        try {
            BooksInStore booksInStoreTemp = getBooksInStoreByBookID(bookID);
            booksInStoreList.remove(booksInStoreTemp);
            System.out.println("book removed");
        }
        catch (Exception e){
            throw new Exception("This book already does not exist in booksInStoreList");
        }
    }

    @Override
    public void deleteOrder(int orderID) throws Exception {
        try {
            Order orderTemp = getOrderByOrderID(orderID);
            if(orderTemp.isOrderCompleted() == false) {
                throw new Exception("This order isn't completed");
            }
            orderList.remove(orderTemp);
            System.out.println("order removed");
        }
        catch (Exception e){
            throw new Exception("This order already does not exist in orderList");
        }
    }

    @Override
    // force order delete even if it's not completed.
    public void deleteOrderPermanently(int orderID,boolean bool) throws Exception {
        try {
            Order orderTemp = getOrderByOrderID(orderID);
            orderList.remove(orderTemp);
            System.out.println("order removed");
        }
        catch (Exception e){
            throw new Exception("This order already does not exist in orderList");
        }
    }

    @Override
    public void deleteBooksForOrder(int orderID, BooksForOrder booksForOrder) throws Exception {

    }

    @Override
    public void deleteBookSupplier(int bookID ,int supplierID) throws Exception {
        try {
            BookSupplier bookSupplierTemp = getBookSupplierBySupplierIDAndByBookID(bookID, supplierID);
            bookSupplierList.remove(bookSupplierTemp);
            System.out.println("bookSupplier removed");
        }
        catch (Exception e){
            throw new Exception("This bookSupplier already does not exist in bookSupplierList");
        }
    }

    @Override
    public void updateBook(Book book, int bookID) throws Exception {

    }

    @Override
    public void updateCustomer(Customer customer, int customerID) throws Exception {

    }

    @Override
    public void updateSupplier(Supplier supplier, int supplierID) throws Exception {

    }

    @Override
    public void updateBooksInStore(BooksInStore booksInStore, int booksInStoreID) throws Exception {

    }

    @Override
    public void updateOrder(Order order, int orderID) throws Exception {

    }

    /*@Override
    public void updateBook(Book book) throws Exception {
        try {
            Book bookTemp = getBookByBookID(book.getBookID());
            bookTemp = book;
            System.out.println("Book updated");
        }
        catch (Exception e){
            throw e;
        }
    }*/

   /* @Override
    public void updateCustomer(Customer customer) throws Exception {
        try {
            Customer customerTemp = getCustomerByCustomerID(customer.getCustomerID());
            customerTemp = customer;
            System.out.println("customer updated");
        }
        catch (Exception e){
            throw e;
        }
    }*/

   /* @Override
    public void updateSupplier(Supplier supplier) throws Exception {
        try {
            Supplier supplierTemp = getSupplierBySupplierID(supplier.getSupplierID());
            supplierTemp = supplier;
            System.out.println("supplier updated");
        }
        catch (Exception e){
            throw e;
        }
    }*/

    /*@Override
    public void updateBooksInStore(BooksInStore booksInStore) throws Exception {
        try {
            BooksInStore booksInStoreTemp = getBooksInStoreByBookID(booksInStore.getBook().getBookID());
            booksInStoreTemp = booksInStore;
            System.out.println("booksInStore updated");
        }
        catch (Exception e){
            throw e;
        }
    }*/

    /*@Override
    public void updateOrder(Order order) throws Exception {
        try {
            Order orderTemp = getOrderByOrderID(order.getOrderID());
            orderTemp = order;
            System.out.println("order updated");
        }
        catch (Exception e){
            throw e;
        }
    }*/

    @Override
    public void updateBookSupplier(BookSupplier bookSupplier) throws Exception {
        try {
            BookSupplier bookSupplierTemp = getBookSupplierBySupplierIDAndByBookID(bookSupplier.getBook().getBookID(), bookSupplier.getSupplier().getSupplierID());
            bookSupplierTemp = bookSupplier;
            System.out.println("bookSupplier updated");
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    public void updateInventory(int bookID, int newAmount) throws Exception {
        try {
            BooksInStore booksInStoreTemp = getBooksInStoreByBookID(bookID);
            booksInStoreTemp.setAmount(newAmount);
            System.out.println("inventory updated");
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    public Book getBookByBookID (int bookID) throws Exception {
        for (Book book:bookList) {
            if(book.getBookID() == bookID) {
                return book;
            }
        }
        throw new Exception("There is no such bookID");
    }

    @Override
    public Customer getCustomerByCustomerID (int customerID) throws Exception {
        for (Customer customer:customerList) {
            if(customer.getCustomerID() == customerID) {
                return customer;
            }
        }
        throw new Exception("There is no such customerID");
    }

    @Override
    public Supplier getSupplierBySupplierID (int supplierID) throws Exception {
        for (Supplier supplier:supplierList) {
            if(supplier.getSupplierID() == supplierID) {
                return supplier;
            }
        }
        throw new Exception("There is no such supplierID");
    }

    @Override
    public BooksInStore getBooksInStoreByBooksInStoreID(int booksInStoreID) throws Exception {
        return null;
    }

    @Override
    public BooksInStore getBooksInStoreByBookID (int bookID) throws Exception {
        for (BooksInStore booksInStore:booksInStoreList) {
            if(booksInStore.getBook().getBookID() == bookID) {
                return booksInStore;
            }
        }
        throw new Exception("There is no such bookID in the store");
    }

    @Override
    public Order getOrderByOrderID(int orderID) throws Exception {
        for (Order order:orderList) {
            if (order.getOrderID() == orderID) {
                return order;
            }
        }
        throw new Exception("There is no such orderID");
    }

    @Override
    public Order getOrderByCustomerID(int customerID) throws Exception {
        for (Order order:orderList) {
            if (order.getCustomer().getCustomerID() == customerID) {
                return order;
            }
        }
        throw new Exception("There is no such customerID");
    }

    @Override
    public ArrayList<BooksForOrder> getActiveBooksForOrderFromSupplierBySupplierID(int supplierID) throws Exception {
        return null;
    }

    /*@Override
    public Order getOrderBySupplierID(int supplierID) throws Exception {
        for (Order order:orderList) {
            if (order.getSupplier().getSupplierID() == supplierID) {
                return order;
            }
        }
        throw new Exception("There is no such supplierID");
    }*/

    @Override
    public BookSupplier getBookSupplierBySupplierIDAndByBookID (int supplierID, int bookID) throws Exception {
        for (BookSupplier bookSupplier:bookSupplierList) {
            if((bookSupplier.getSupplier().getSupplierID() == supplierID) && (bookSupplier.getBook().getBookID() == bookID) ) {
                return bookSupplier;
            }
        }
        throw new Exception("There is no such supplierID Linked to that bookID");
    }

    @Override
    public BookSupplier getBookSupplierByBookID (int bookID) throws Exception {
        for (BookSupplier bookSupplier:bookSupplierList) {
            if(bookSupplier.getBook().getBookID() == bookID) {
                return bookSupplier;
            }
        }
        throw new Exception("There is no such bookID Linked to supplier");
    }

    @Override
    public BookSupplier getBookSupplierBySupplierID (int supplierID) throws Exception {
        for (BookSupplier bookSupplier:bookSupplierList) {
            if(bookSupplier.getSupplier().getSupplierID() == supplierID) {
                return bookSupplier;
            }
        }
        throw new Exception("There is no such supplierID Linked to a book");
    }



    // getters and setters
    public ArrayList<BooksInStore> getBooksInStoreList() {
        return booksInStoreList;
    }

    public void setBooksInStoreList(ArrayList<BooksInStore> booksInStoreList) {
        this.booksInStoreList = booksInStoreList;
    }

    @Override
    public ArrayList<Supplier> getSupplierList() {
        return supplierList;
    }

    public void setSupplierList(ArrayList<Supplier> supplierList) {
        this.supplierList = supplierList;
    }

    @Override
    public ArrayList<Book> getBookList() {
        return bookList;
    }

    public void setBookList(ArrayList<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public ArrayList<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(ArrayList<Customer> customerList) {
        this.customerList = customerList;
    }

    @Override
    public ArrayList<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<Order> orderList) {
        this.orderList = orderList;
    }

    @Override
    public ArrayList<BookSupplier> getBookSupplierList() {
        return bookSupplierList;
    }

    @Override
    public ArrayList<Book> getBookListByTitle(String title) throws Exception {
        return null;
    }

    @Override
    public ArrayList<BooksInStore> getBooksInStoreByTitle(String title) throws Exception {
        return null;
    }

    @Override
    public ArrayList<Order> getOrderListOfSpecificCustomerByCustomerID(int customerID) throws Exception {
        return null;
    }

    @Override
    public ArrayList<Customer> getCustomerListByName(String name) throws Exception {
        return null;
    }

    @Override
    public void printList(ArrayList arrayList) {

    }

    @Override
    public void createLists() throws Exception {

    }

    @Override
    public ArrayList<BooksForOrder> getBooksForOrderArrayList() throws Exception {
        return null;
    }

    @Override
    public void updateLists() throws Exception {

    }

    @Override
    public void deleteLists() throws Exception {

    }

    public void setBookSupplierList(ArrayList<BookSupplier> bookSupplierList) {
        this.bookSupplierList = bookSupplierList;
    }
}
