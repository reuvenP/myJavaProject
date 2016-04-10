package model.datasource;

import java.util.ArrayList;
import java.util.Date;

import entities.Account;
import entities.Book;
import entities.BookSupplier;
import entities.BooksForOrder;
import entities.BooksInStore;
import entities.Customer;
import entities.CustomerType;
import entities.Gender;
import entities.Order;
import entities.Rateing;
import entities.Supplier;
import model.backend.Backend;

/**
 * Created by shmuel on 30/03/2016.
 */
public class DatabaseList implements Backend {

    private ArrayList<Book> bookList = new ArrayList<>();
    private ArrayList<Customer> customerList = new ArrayList<>();
    private ArrayList<Supplier> supplierList = new ArrayList<>();
    private ArrayList<BooksInStore> booksInStoreList = new ArrayList<>();
    private ArrayList<Order> orderList = new ArrayList<>();
    private ArrayList<BookSupplier> bookSupplierList = new ArrayList<>();

    private static int bookIDGenerator = 1;
    private static int customerIDGenerator = 1;
    private static int orderIDGenerator = 1;
    private static int supplierIDGenerator = 1;
    private static int booksInStoreIDGenerator = 1;

    // default constructor
    public DatabaseList() {
//        ArrayList<Book> bookList = new ArrayList<Book>();
//        ArrayList<Customer> customerList = new ArrayList<>();
//        ArrayList<Supplier> supplierList = new ArrayList<>();
//        ArrayList<BooksInStore> booksInStoreList = new ArrayList<>();
//        ArrayList<Order> orderList = new ArrayList<>();
//        ArrayList<BookSupplier> bookSupplierList = new ArrayList<>();
    }

    // constructor
    public DatabaseList(ArrayList<BooksInStore> booksInStoreList, ArrayList<Supplier> supplierList, ArrayList<Book> bookList, ArrayList<Customer> customerList,
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
        for (Book bookTemp : bookList) {
            if(bookTemp.getBookID() == book.getBookID()) {
                throw new Exception("This book(" + book.getBookID() + " " + book.getTitle() + ") already exists");
            }
        }
//        if (bookList.contains(book)) {
//            throw new Exception("This book(" + book.getBookID() + " " + book.getTitle() + ") already exists");
//        }
        book.setBookID(bookIDGenerator++);
        bookList.add(book);
    }

    @Override
    // add customer to customerList.
    public void addCustomer (Customer customer) throws Exception {
        if (customerList.contains(customer)) {
            throw new Exception("This customer(" + customer.getCustomerID() + " " + customer.getName() + ") already exists");
        }
        customer.setCustomerID(customerIDGenerator++);
        customerList.add(customer);
    }

    @Override
    // add supplier to supplierList.
    public void addSupplier (Supplier supplier) throws Exception {
        if (supplierList.contains(supplier)) {
            throw new Exception("This supplier(" + supplier.getSupplierID() + " " + supplier.getName() + ") already exists");
        }
        supplier.setSupplierID(supplierIDGenerator++);
        supplierList.add(supplier);
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
        booksInStore.setBooksInStoreID(booksInStoreIDGenerator++);
        booksInStoreList.add(booksInStore);
    }
    @Override
    // add order to orderList.
    public void addOrder (Order order) throws Exception{
//        if(!supplierList.contains(order.getSupplier())){
//            throw new Exception("This supplier(" + order.getSupplier().getSupplierID() + " " + order.getSupplier().getName() + ") does not exist in supplierList\n first add the supplier to supplierList!");
//        }

        if(!customerList.contains(order.getCustomer())){
            throw new Exception("This customer(" + order.getCustomer().getCustomerID() + " " + order.getCustomer().getName() + ") does not exist in customerList\n first add the customer to customerList!");
        }
        order.setTotalPrice(computeTotalPrice(order));
        order.setOrderID(orderIDGenerator++);
        if(orderList.contains(order)) {
            throw new Exception("This order(" + order.getOrderID() + ")  already exists");
        }
        orderList.add(order);
    }

    // compute the total price for order.
    public float computeTotalPrice (Order order) throws Exception{
        float totalPrice = 0;
        for (BooksForOrder booksForOrder :order.getBooksForOrders()) {
            if(!bookList.contains(booksForOrder.getBook())){
                throw new Exception("This book(" + booksForOrder.getBook().getBookID() + " " + booksForOrder.getBook().getTitle() + ") does not exist in bookList\n first add the book to bookList!");
            }
            try {
                totalPrice += booksForOrder.getSumOfBooks() * getBookSupplierBySupplierIDAndByBookID(booksForOrder.getSupplier().getSupplierID(),booksForOrder.getBook().getBookID()).getPrice();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("this book should be removed from order\n");
                /////////////////////////////////////////////////////////////////////////////////////////////////////////
                /////////////////////////////////////////////////////////////////////////////////////////////////////////
            }
        }
        return totalPrice;
    }

    @Override
    // add booksForOrder to the list of specific order by orderID.
    public void addBooksForOrder(int orderID, BooksForOrder booksForOrder) throws Exception {
        getOrderByOrderID(orderID).addBooksForOrder(booksForOrder);
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
//        if (bookSupplierList.contains(bookSupplier)) {
//            throw new Exception("This bookSupplier already exists");
//        }
        for (BookSupplier bSTmp: bookSupplierList) {
            if (bSTmp.getBook().getBookID() == bookSupplier.getBook().getBookID() && bSTmp.getSupplier().getSupplierID() == bookSupplier.getSupplier().getSupplierID()) {
                throw new Exception("This bookSupplier already exists");
            }
        }

        bookSupplierList.add(bookSupplier);
    }

    @Override
    // delete book to bookList by bookID.
    public void deleteBook(int bookID) throws Exception {
        Book bookTemp = getBookByBookID(bookID);
        try { // check if this book does not in the store.
            getBooksInStoreByBookID(bookID);
        }
        catch (Exception e) { // if Exception accord it's mean that this book exists only in bookList.
            bookList.remove(bookTemp);
            System.out.println("Book removed");
            return;
        }

        if(getBooksInStoreByBookID(bookID).getAmount() > 0) {
            throw new Exception("There are still copies of this book in the store,\n" +
                    " First deleted from the store.");
        }

        bookList.remove(bookTemp);
        System.out.println("Book removed");
    }

    @Override
    // delete bookInStore from booksInStoreList by bookID.
    public void deleteBookFromStore(int bookID) throws Exception {
//        try {
        // Maybe we should make sure that there isn't an order to this book when there are still more copies in the store. ///////////////////
        booksInStoreList.remove(getBooksInStoreByBookID(bookID));
        System.out.println("book removed from store");
//        }
//        catch (Exception e){
//            throw new Exception("This book already does not exist in booksInStoreList");
//        }
    }

    @Override
    // delete customer from customerList by customerID.
    public void deleteCustomer(int customerID) throws Exception {
//        try {
        Customer customerTemp = getCustomerByCustomerID(customerID);
        try { // check if this customer does not have any order.
            getOrderByCustomerID(customerID);
        }
        catch (Exception e){ // if Exception accord it's mean that this customer does not have any order.
            customerList.remove(customerTemp);
            System.out.println("Customer removed");
            return;
        }
        if(getOrderByCustomerID(customerID).isOrderCompleted() == false) {
            throw new Exception("This customer(" + customerID + ") have an open order(" + getOrderByCustomerID(customerID).getOrderID() + ") to get first");
        }
        customerList.remove(customerTemp);
        System.out.println("Customer removed");
//        }
//        catch (Exception e){
//            throw new Exception("This customer already does not exist in customerList");
//        }
    }

    @Override
    // delete supplier from supplierList by supplierID.
    public void deleteSupplier(int supplierID) throws Exception {
//        try {
        Supplier supplierTemp = getSupplierBySupplierID(supplierID);
        if(getActiveBooksForOrderFromSupplierBySupplierID(supplierID).size() > 0) {
            throw new Exception("This supplier have an open order(" + getActiveBooksForOrderFromSupplierBySupplierID(supplierID).size() + " books for order) to supply first");
        }
        supplierList.remove(supplierTemp);
        System.out.println("Supplier removed");
//        }
//        catch (Exception e){
//            throw new Exception("This supplier already does not exist in supplierList");
//        }
    }


    @Override
    // delete booksInStore from booksInStoreList by booksInStoreID.
    public void deleteBooksInStore(int booksInStoreID) throws Exception {
//        try {
        booksInStoreList.remove(getBooksInStoreByBooksInStoreID(booksInStoreID));
        System.out.println("booksInStoreID removed");
//        }
//        catch (Exception e){
//            throw new Exception("This booksInStoreID already does not exist in booksInStoreList");
//        }
    }

    @Override
    // delete order from orderList by orderID only if order is completed (see deleteOrderPermanently).
    public void deleteOrder(int orderID) throws Exception {
//        try {
        Order orderTemp = getOrderByOrderID(orderID);
        if(orderTemp.isOrderCompleted() == false) {
            throw new Exception("This order isn't completed");
        }
        orderList.remove(orderTemp);
        System.out.println("order removed");
//        }
//        catch (Exception e){
//            throw new Exception("This order already does not exist in orderList");
//        }
    }

    @Override
    // delete booksForOrder from the list in specific order by orderID.
    public void deleteBooksForOrder(int orderID, BooksForOrder booksForOrder) throws Exception {
        getOrderByOrderID(orderID).deleteBooksForOrder(booksForOrder);
    }

    @Override
    // force order delete even if it's not completed.
    public void deleteOrderPermanently(int orderID,boolean bool) throws Exception {
//        try {
        Order orderTemp = getOrderByOrderID(orderID);
        orderList.remove(orderTemp);
        System.out.println("order removed");
//        }
//        catch (Exception e){
//            throw new Exception("This order already does not exist in orderList");
//        }
    }

    @Override
    // delete bookSupplier from bookSupplierList by bookID and by supplierID.
    public void deleteBookSupplier(int bookID ,int supplierID) throws Exception {
//        try {
        BookSupplier bookSupplierTemp = getBookSupplierBySupplierIDAndByBookID(supplierID, bookID);
        bookSupplierList.remove(bookSupplierTemp);
        System.out.println("bookSupplier removed");
//        }
//        catch (Exception e){
//            throw new Exception("This bookSupplier already does not exist in bookSupplierList");
//        }
    }

    @Override
    // update book in bookList by bookID.
    public void updateBook(Book book, int bookID) throws Exception {
        try {
            Book bookTmp = getBookByBookID(bookID);
            bookTmp.setTitle(book.getTitle());
            bookTmp.setYear(book.getYear());
            bookTmp.setAuthor(book.getAuthor());
            bookTmp.setPages(book.getPages());

            System.out.println("Book updated");
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    // update customer in customerList by customerID.
    public void updateCustomer(Customer customer, int customerID) throws Exception {
        try {
            Customer customerTmp = getCustomerByCustomerID(customerID);
            customerTmp.setCustomerType(customer.getCustomerType());
            customerTmp.setName(customer.getName());
            customerTmp.setBirthday(customer.getBirthday());
            customerTmp.setGender(customer.getGender());
            customerTmp.setAddress(customer.getAddress());
            customerTmp.setAccount(customer.getAccount());

            System.out.println("customer updated");
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    // update supplier in supplierList by supplierID.
    public void updateSupplier(Supplier supplier, int supplierID) throws Exception {
        try {
            Supplier supplierTmp = getSupplierBySupplierID(supplierID);
            supplierTmp.setRateing(supplier.getRateing());
            supplierTmp.setName(supplier.getName());
            supplierTmp.setBirthday(supplier.getBirthday());
            supplierTmp.setGender(supplier.getGender());
            supplierTmp.setAddress(supplier.getAddress());
            supplierTmp.setAccount(supplier.getAccount());

            System.out.println("supplier updated");
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    // update booksInStore in booksInStoreList by booksInStoreID.
    public void updateBooksInStore(BooksInStore booksInStore, int booksInStoreID) throws Exception {
        try {
            BooksInStore booksInStoreTmp = getBooksInStoreByBooksInStoreID(booksInStoreID);
            booksInStoreTmp.setBook(booksInStore.getBook());
            booksInStoreTmp.setAmount(booksInStore.getAmount());

            System.out.println("booksInStore updated");
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    // update order in orderList by orderID.
    public void updateOrder(Order order, int orderID) throws Exception {
        try {
            Order orderTmp = getOrderByOrderID(orderID);
            orderTmp.setCustomer(order.getCustomer());
            orderTmp.setBooksForOrders(order.getBooksForOrders());
            orderTmp.setOrderDate(order.getOrderDate());
            orderTmp.setTotalPrice(computeTotalPrice(order));
            orderTmp.setOrderCompleted(order.isOrderCompleted());

            System.out.println("order updated");
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    // update bookSupplier in bookSupplierList.
    public void updateBookSupplier(BookSupplier bookSupplier) throws Exception {
        try {
            getBookSupplierBySupplierIDAndByBookID(bookSupplier.getSupplier().getSupplierID(), bookSupplier.getBook().getBookID())
                    .setPrice(bookSupplier.getPrice());
            System.out.println("bookSupplier updated");
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    // update booksInStore.amount in booksInStoreList by booksInStoreID.
    public void updateInventory(int booksInStoreID, int newAmount) throws Exception {
        try {
            getBooksInStoreByBooksInStoreID(booksInStoreID).setAmount(newAmount);
            System.out.println("inventory updated");
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    // return Book by bookID.
    public Book getBookByBookID (int bookID) throws Exception {
        for (Book book:bookList) {
            if(book.getBookID() == bookID) {
                return book;
            }
        }
        throw new Exception("There is no such bookID(" + bookID + ")");
    }

    @Override
    // return Book by customerID.
    public Customer getCustomerByCustomerID (int customerID) throws Exception {
        for (Customer customer:customerList) {
            if(customer.getCustomerID() == customerID) {
                return customer;
            }
        }
        throw new Exception("There is no such customerID(" + customerID + ")");
    }

    @Override
    // return Supplier by supplierID.
    public Supplier getSupplierBySupplierID (int supplierID) throws Exception {
        for (Supplier supplier:supplierList) {
            if(supplier.getSupplierID() == supplierID) {
                return supplier;
            }
        }
        throw new Exception("There is no such supplierID(" + supplierID + ")");
    }

    @Override
    // return BooksInStore by booksInStoreID.
    public BooksInStore getBooksInStoreByBooksInStoreID (int booksInStoreID) throws Exception {
        for (BooksInStore booksInStore:booksInStoreList) {
            if(booksInStore.getBooksInStoreID() == booksInStoreID) {
                return booksInStore;
            }
        }
        throw new Exception("There is no such booksInStoreID(" + booksInStoreID + ") in the store");
    }

    @Override
    // return BooksInStore by bookID.
    public BooksInStore getBooksInStoreByBookID(int bookID) throws Exception {
        for (BooksInStore booksInStore:booksInStoreList) {
            if(booksInStore.getBook().getBookID() == bookID) {
                return booksInStore;
            }
        }
        throw new Exception("There is no such bookID(" + bookID + ") in the store");
    }

    @Override
    // return Order by orderID.
    public Order getOrderByOrderID(int orderID) throws Exception {
        for (Order order:orderList) {
            if (order.getOrderID() == orderID) {
                return order;
            }
        }
        throw new Exception("There is no such orderID(" + orderID + ")");
    }

    @Override
    // return Order by customerID.
    public Order getOrderByCustomerID(int customerID) throws Exception {
        for (Order order:orderList) {
            if (order.getCustomer().getCustomerID() == customerID) {
                return order;
            }
        }
        throw new Exception("There is no order for this customerID(" + customerID + ")");
    }

    @Override
    // return ArrayList<BooksForOrder> of books that does not supplied yet, by supplierID.
    public ArrayList<BooksForOrder> getActiveBooksForOrderFromSupplierBySupplierID(int supplierID) throws Exception {
        ArrayList<BooksForOrder> booksForOrderArrayListTmp = new ArrayList<BooksForOrder>();
        for (Order order:orderList) {
            if(order.isOrderCompleted() == false){
                for (BooksForOrder booksForOrderTmp:order.getBooksForOrders()) {
                    if (booksForOrderTmp.getSupplier().getSupplierID() == supplierID) {
                        if(booksForOrderTmp.isOrderHasBeenCompleted() == false){
                            booksForOrderArrayListTmp.add(booksForOrderTmp);
                        }
                    }
                }
            }
        }
        return booksForOrderArrayListTmp;
//        throw new Exception("There is no such supplierID(" + supplierID + ")");
    }

    @Override
    // return BookSupplier by supplierID and by bookID.
    public BookSupplier getBookSupplierBySupplierIDAndByBookID (int supplierID, int bookID) throws Exception {
        for (BookSupplier bookSupplier:bookSupplierList) {
            if((bookSupplier.getSupplier().getSupplierID() == supplierID) && (bookSupplier.getBook().getBookID() == bookID) ) {
                return bookSupplier;
            }
        }
        throw new Exception("There is no such supplierID(" + supplierID + ") Linked to that bookID(" + bookID + ")");
    }

    @Override
    // return BookSupplier by bookID.
    public BookSupplier getBookSupplierByBookID (int bookID) throws Exception {
        for (BookSupplier bookSupplier:bookSupplierList) {
            if(bookSupplier.getBook().getBookID() == bookID) {
                return bookSupplier;
            }
        }
        throw new Exception("There is no such bookID(" + bookID + ") Linked to supplier");
    }

    @Override
    // return BookSupplier by supplierID.
    public BookSupplier getBookSupplierBySupplierID (int supplierID) throws Exception {
        for (BookSupplier bookSupplier:bookSupplierList) {
            if(bookSupplier.getSupplier().getSupplierID() == supplierID) {
                return bookSupplier;
            }
        }
        throw new Exception("There is no such supplierID(" + supplierID + ") Linked to a book");
    }

    @Override
    // return ArrayList<Book> of books with specific title by title.
    public ArrayList<Book> getBookListByTitle(String title) throws Exception {
        ArrayList<Book> bookArrayList = new ArrayList<Book>();
        for (Book book:bookList) {
            if(book.getTitle() == title) {
                bookArrayList.add(book);
            }
        }
        return bookArrayList;
    }

    @Override
    // return ArrayList<BooksInStore> of BooksInStore with specific title by title.
    public ArrayList<BooksInStore> getBooksInStoreByTitle(String title) throws Exception {
        ArrayList<BooksInStore> booksInStoreArrayList = new ArrayList<BooksInStore>();
        for (BooksInStore booksInStore:booksInStoreList) {
            if(booksInStore.getBook().getTitle() == title) {
                booksInStoreArrayList.add(booksInStore);
            }
        }
        return booksInStoreArrayList;
    }

    @Override
    // return ArrayList<Order> of orders of specific customer by customerID.
    public ArrayList<Order> getOrderListOfSpecificCustomerByCustomerID(int customerID) throws Exception {
        ArrayList<Order> orderArrayList = new ArrayList<Order>();
        for (Order order:orderList) {
            if(order.getCustomer().getCustomerID() == customerID) {
                orderArrayList.add(order);
            }
        }
        return orderArrayList;
    }

    @Override
    // return ArrayList<Customer> of customers with specific name by name.
    public ArrayList<Customer> getCustomerListByName(String name) throws Exception {
        ArrayList<Customer> customerArrayList = new ArrayList<Customer>();
        for (Customer customer : customerList) {
            if(customer.getName() == name) {
                customerArrayList.add(customer);
            }
        }
        return customerArrayList;
    }

    // getters and setters:

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

    public void setBookSupplierList(ArrayList<BookSupplier> bookSupplierList) {
        this.bookSupplierList = bookSupplierList;
    }



    /*5) test */

    @Override
    public void printList (ArrayList arrayList) {
        if(arrayList.size() == 0){
            System.out.println("Empty list");
            System.out.println("\n");
            return;
        }
        if(arrayList.get(0) instanceof Book){
            for (Book book:(ArrayList<Book>)arrayList) {
                System.out.println(book);
                System.out.println("\n");
            }
            System.out.println("\n\n");
        }
        else if(arrayList.get(0) instanceof Customer){
            for (Customer customer:(ArrayList<Customer>)arrayList) {
                System.out.println(customer);
                System.out.println("\n");
            }
            System.out.println("\n\n");
        }
        else if(arrayList.get(0) instanceof Supplier){
            for (Supplier supplier:(ArrayList<Supplier>)arrayList) {
                System.out.println(supplier);
                System.out.println("\n");
            }
            System.out.println("\n\n");
        }
        else if(arrayList.get(0) instanceof Order){
            for (Order order:(ArrayList<Order>)arrayList) {
                System.out.println(order);
                System.out.println("\n");
            }
            System.out.println("\n\n");
        }
        else if(arrayList.get(0) instanceof BooksInStore){
            for (BooksInStore booksInStore:(ArrayList<BooksInStore>)arrayList) {
                System.out.println(booksInStore);
                System.out.println("\n");
            }
            System.out.println("\n\n");
        }
        else if(arrayList.get(0) instanceof BooksForOrder){
            for (BooksForOrder booksForOrder:(ArrayList<BooksForOrder>)arrayList) {
                System.out.println(booksForOrder);
                System.out.println("\n");
            }
            System.out.println("\n\n");
        }

        else {
            System.out.println("unknown type!");
            System.out.println("\n\n");
            for (Object obj: (ArrayList<Object>)arrayList) {
                System.out.println(obj);
                System.out.println("\n");
            }
        }
    }

    @Override
    public void createLists () throws Exception {
        this.addBook(new Book("Harry Potter and the Sorcerer's Stone", 1997, "J. K. Rowling", 223));
        this.addBook(new Book("Harry Potter and the Chamber of Secrets", 1998, "J. K. Rowling", 251));
        this.addBook(new Book("Harry Potter and the Prisoner of Azkaban", 1999, "J. K. Rowling", 317));
        this.addBook(new Book("Harry Potter and the Goblet of Fire", 2000, "J. K. Rowling", 636));
        this.addBook(new Book("Harry Potter and the Order of the Phoenix", 2003, "J. K. Rowling", 766));
        this.addBook(new Book("Harry Potter and the Half-Blood Prince", 2005, "J. K. Rowling", 607));
        this.addBook(new Book("Harry Potter and the Deathly Hallows", 2007, "J. K. Rowling", 607));

        this.addSupplier(new Supplier(Rateing.FIVE, "Shmulik the great", new Date(), Gender.MALE, "Miron 16 Bnei Brak", new Account()));
        this.addSupplier(new Supplier(Rateing.FOUR, "Reuven the great", new Date(), Gender.MALE, "Hashnaim 19 Bnei Brak", new Account()));
        this.addSupplier(new Supplier(Rateing.FIVE, "Shmulik & Reuven", new Date(), Gender.MALE, "Abcd 1 Bnei Brak", new Account()));
        this.addSupplier(new Supplier(Rateing.ONE, "Reuven in vacation", new Date(), Gender.MALE, "Dcba 2 Bnei Brak", new Account()));

        this.addBooksInStore(new BooksInStore(getBookByBookID(1), 10));
        this.addBooksInStore(new BooksInStore(getBookByBookID(2), 15));
        this.addBooksInStore(new BooksInStore(getBookByBookID(3), 18));
        this.addBooksInStore(new BooksInStore(getBookByBookID(4), 0));

        this.addBookSupplier(new BookSupplier(getSupplierBySupplierID(1), getBookByBookID(1), (float) 55.5));
        this.addBookSupplier(new BookSupplier(getSupplierBySupplierID(1), getBookByBookID(2), (float) 58.5));
        this.addBookSupplier(new BookSupplier(getSupplierBySupplierID(1), getBookByBookID(3), (float) 62.38));
        this.addBookSupplier(new BookSupplier(getSupplierBySupplierID(1), getBookByBookID(4), (float) 78.6));
        this.addBookSupplier(new BookSupplier(getSupplierBySupplierID(1), getBookByBookID(5), (float) 82.4));
        this.addBookSupplier(new BookSupplier(getSupplierBySupplierID(1), getBookByBookID(6), (float) 83.1));
        this.addBookSupplier(new BookSupplier(getSupplierBySupplierID(2), getBookByBookID(1), (float) 83.1));
        this.addBookSupplier(new BookSupplier(getSupplierBySupplierID(2),getBookByBookID(7), (float)83.1));
        this.addBookSupplier(new BookSupplier(getSupplierBySupplierID(3),getBookByBookID(1), (float)50.4));
        this.addBookSupplier(new BookSupplier(getSupplierBySupplierID(3),getBookByBookID(3), (float)65.3));
        this.addBookSupplier(new BookSupplier(getSupplierBySupplierID(4), getBookByBookID(5), (float)82.9));
        this.addBookSupplier(new BookSupplier(getSupplierBySupplierID(4), getBookByBookID(6), (float)80.2));
        this.addBookSupplier(new BookSupplier(getSupplierBySupplierID(4), getBookByBookID(7), (float) 91.32));

        this.addCustomer(new Customer(CustomerType.VIP, "Shmulik", new Date(), Gender.MALE, "Miron 16 Bnei Brak", new Account()));
        this.addCustomer(new Customer(CustomerType.REGULAR, "Shmulik thr regular", new Date(), Gender.MALE, "Miron 16 Bnei Brak", new Account()));
        this.addCustomer(new Customer(CustomerType.VIP, "Reuven", new Date(), Gender.MALE, "Hashnaim 19 Bnei Brak", new Account()));


        booksForOrderArrayList.add(new BooksForOrder(getSupplierBySupplierID(1), getBookByBookID(1), 4, false));
        booksForOrderArrayList.add(new BooksForOrder(getSupplierBySupplierID(1), getBookByBookID(2), 6, false));
        booksForOrderArrayList.add(new BooksForOrder(getSupplierBySupplierID(1), getBookByBookID(3), 8, false));
        booksForOrderArrayList.add(new BooksForOrder(getSupplierBySupplierID(1), getBookByBookID(4), 10, false));
        booksForOrderArrayList.add(new BooksForOrder(getSupplierBySupplierID(1), getBookByBookID(5), 12, false));
        booksForOrderArrayList.add(new BooksForOrder(getSupplierBySupplierID(1), getBookByBookID(6), 14, false));
        booksForOrderArrayList.add(new BooksForOrder(getSupplierBySupplierID(1), getBookByBookID(7), 16, false));

        this.addOrder(new Order(getCustomerByCustomerID(1), booksForOrderArrayList, new Date(), 0, false));


        booksForOrderArrayList.clear();
        booksForOrderArrayList.add(new BooksForOrder(getSupplierBySupplierID(2), getBookByBookID(1), 4, false));
        booksForOrderArrayList.add(new BooksForOrder(getSupplierBySupplierID(2), getBookByBookID(2), 6, false));
        booksForOrderArrayList.add(new BooksForOrder(getSupplierBySupplierID(2), getBookByBookID(3), 8, false));
        booksForOrderArrayList.add(new BooksForOrder(getSupplierBySupplierID(2), getBookByBookID(4), 10, false));
        booksForOrderArrayList.add(new BooksForOrder(getSupplierBySupplierID(2), getBookByBookID(5), 12, false));
        booksForOrderArrayList.add(new BooksForOrder(getSupplierBySupplierID(2), getBookByBookID(6), 14, false));
        booksForOrderArrayList.add(new BooksForOrder(getSupplierBySupplierID(2), getBookByBookID(7), 16, false));

        this.addOrder(new Order(getCustomerByCustomerID(2), booksForOrderArrayList, new Date(), 0, false));


        booksForOrderArrayList.clear();
        booksForOrderArrayList.add(new BooksForOrder(getSupplierBySupplierID(3), getBookByBookID(1), 4, false));
        booksForOrderArrayList.add(new BooksForOrder(getSupplierBySupplierID(3), getBookByBookID(2), 6, false));
        booksForOrderArrayList.add(new BooksForOrder(getSupplierBySupplierID(3), getBookByBookID(3), 8, false));
        booksForOrderArrayList.add(new BooksForOrder(getSupplierBySupplierID(3), getBookByBookID(4), 10, false));
        booksForOrderArrayList.add(new BooksForOrder(getSupplierBySupplierID(3), getBookByBookID(5), 12, false));
        booksForOrderArrayList.add(new BooksForOrder(getSupplierBySupplierID(3), getBookByBookID(6), 14, false));
        booksForOrderArrayList.add(new BooksForOrder(getSupplierBySupplierID(3), getBookByBookID(7), 16, false));

        this.addOrder(new Order(getCustomerByCustomerID(3), booksForOrderArrayList, new Date(), 0, false));


    }

    @Override
    public void updateLists () throws Exception {
        this.updateBook(new Book("updateBook 1", 1997, "J. K. Rowling", 223), 1);
        this.updateBook(new Book("updateBook 2", 1998, "J. K. Rowling", 251), 2);
        this.updateBook(new Book("updateBook 3", 1999, "J. K. Rowling", 317), 3);
        this.updateBook(new Book("updateBook 5", 2003, "J. K. Rowling", 766), 5);
        this.updateBook(new Book("updateBook 6", 2005, "J. K. Rowling", 607), 6);


        getSupplierBySupplierID(1).setName("update manually Supplier 1");
        getSupplierBySupplierID(1).setAddress("update manually address 1");
        getSupplierBySupplierID(1).setAccount(new Account((float) 1234.364, new Date(), (float) 234.364));

        this.updateSupplier(new Supplier(Rateing.FOUR, "updateSupplier 2", new Date(), Gender.MALE, "Hashnaim 19 Bnei Brak", new Account()), 2);
        this.updateSupplier(new Supplier(Rateing.FIVE, "updateSupplier 3", new Date(), Gender.MALE, "Abcd 1 Bnei Brak", new Account()), 3);
        this.updateSupplier(new Supplier(Rateing.ONE, "updateSupplier 4", new Date(), Gender.MALE, "Dcba 2 Bnei Brak", new Account()), 4);


        this.updateBooksInStore(new BooksInStore(getBookByBookID(1), 15), 1);
        this.updateBooksInStore(new BooksInStore(getBookByBookID(2), 20), 2);


        this.updateBookSupplier(new BookSupplier(getSupplierBySupplierID(1), getBookByBookID(1), (float) 11.11));
        this.updateBookSupplier(new BookSupplier(getSupplierBySupplierID(1), getBookByBookID(2), (float) 22.22));
        this.updateBookSupplier(new BookSupplier(getSupplierBySupplierID(1), getBookByBookID(3), (float) 33.33));
        this.updateBookSupplier(new BookSupplier(getSupplierBySupplierID(1), getBookByBookID(4), (float) 44.44));
        this.updateBookSupplier(new BookSupplier(getSupplierBySupplierID(1), getBookByBookID(5), (float) 55.55));
        this.updateBookSupplier(new BookSupplier(getSupplierBySupplierID(1), getBookByBookID(6), (float) 66.66));
        this.updateBookSupplier(new BookSupplier(getSupplierBySupplierID(2), getBookByBookID(1), (float) 77.77));
        this.updateBookSupplier(new BookSupplier(getSupplierBySupplierID(2), getBookByBookID(7), (float) 88.88));
        this.updateBookSupplier(new BookSupplier(getSupplierBySupplierID(3), getBookByBookID(1), (float) 99.99));
        this.updateBookSupplier(new BookSupplier(getSupplierBySupplierID(3), getBookByBookID(3), (float) 111.111));
        this.updateBookSupplier(new BookSupplier(getSupplierBySupplierID(4), getBookByBookID(5), (float) 222.222));
        this.updateBookSupplier(new BookSupplier(getSupplierBySupplierID(4), getBookByBookID(6), (float) 333.333));
        this.updateBookSupplier(new BookSupplier(getSupplierBySupplierID(4), getBookByBookID(7), (float) 444.444));


        this.updateCustomer(new Customer(CustomerType.VIP, "updateCustomer 3", new Date(), Gender.MALE, "Miron 16 Bnei Brak", new Account()), 3);
        this.updateCustomer(new Customer(CustomerType.REGULAR, "updateCustomer 2", new Date(), Gender.MALE, "Miron 16 Bnei Brak", new Account()), 2);
        this.updateCustomer(new Customer(CustomerType.VIP, "updateCustomer 1", new Date(), Gender.MALE, "Hashnaim 19 Bnei Brak", new Account()), 1);


        this.updateOrder(new Order(getCustomerByCustomerID(1), booksForOrderArrayList, new Date(), 0, false),3);
        this.updateOrder(new Order(getCustomerByCustomerID(2), booksForOrderArrayList, new Date(), 0, true),2);
        this.updateOrder(new Order(getCustomerByCustomerID(3), booksForOrderArrayList, new Date(), 0, true), 1);


        this.updateInventory(1, 35);
        this.updateInventory(2, 45);
    }

    @Override
    public void deleteLists() throws Exception {
//        this.deleteBook(1);
//        this.deleteBook(2);
//        this.deleteBook(3);

        this.deleteSupplier(1);
        this.deleteSupplier(4);


        this.deleteBooksInStore(getBooksInStoreByBookID(1).getBooksInStoreID());
        this.deleteBooksInStore(getBooksInStoreByBookID(2).getBooksInStoreID());
        this.deleteBooksInStore(getBooksInStoreByBookID(3).getBooksInStoreID());
        this.deleteBooksInStore(4);

        this.deleteBook(1);
        this.deleteBook(2);
        this.deleteBook(3);

        this.deleteBookSupplier(1, 1);
        this.deleteBookSupplier(6, 4);

        this.deleteOrder(1);
//        this.deleteOrder(3);
        this.deleteOrderPermanently(3, true);

        this.deleteCustomer(1);
        this.deleteCustomer(2);
    }

    ArrayList<BooksForOrder> booksForOrderArrayList = new ArrayList<BooksForOrder>();
    @Override
    public ArrayList<BooksForOrder> getBooksForOrderArrayList() throws Exception {
        return booksForOrderArrayList;
    }
}
