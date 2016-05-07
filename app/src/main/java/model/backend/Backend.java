package model.backend;

import java.util.ArrayList;

import entities.Book;
import entities.BookSupplier;
import entities.BooksForOrder;
import entities.BooksInStore;
import entities.Category;
import entities.Customer;
import entities.Order;
import entities.Supplier;
import entities.User;

/**
 * Created by shmuel on 29/03/2016.
 */
public interface Backend {

    /*1)*/
    public void addBook(Book book) throws Exception;// add book to bookList.
    public int addCustomer(Customer customer) throws Exception;// add customer to customerList.
    public int addSupplier(Supplier supplier) throws Exception;// add supplier to supplierList.
    public void addBooksInStore(BooksInStore booksInStore) throws Exception;// add booksInStore to booksInStoreList.
    public void addOrder(Order order) throws Exception;// add order to orderList.
    public void addBooksForOrder(int orderID, BooksForOrder booksForOrder) throws Exception;// add booksForOrder to the list of specific order by orderID.
    public void addBookSupplier(BookSupplier bookSupplier) throws Exception;// add bookSupplier to bookSupplierList.
    public void addUser(User user) throws Exception;

    /*2)*/
    public void deleteBook(int bookID) throws Exception;// delete book to bookList by bookID.
    public void deleteBookFromStore(int bookID) throws Exception;// delete bookInStore from booksInStoreList by bookID.
    public void deleteCustomer(int customerID) throws Exception;// delete customer from customerList by customerID.
    public void deleteSupplier(int supplierID) throws Exception;// delete supplier from supplierList by supplierID.
    public void deleteBooksInStore(int bookID) throws Exception;// delete bookInStore from booksInStoreList by bookID.
    public void deleteOrder(int orderID) throws Exception;// delete order from orderList by orderID only if order is completed (see deleteOrderPermanently).
    public void deleteOrderPermanently(int orderID, boolean bool) throws Exception;// force order delete even if it's not completed.
    public void deleteBooksForOrder(int orderID, BooksForOrder booksForOrder) throws Exception;// delete booksForOrder from the list in specific order by orderID.
    public void deleteBookSupplier(int bookID, int supplierID) throws Exception;// delete bookSupplier from bookSupplierList by bookID and by supplierID.
    public void deleteUser(int UserID) throws Exception;

    /*3)*/
    public void updateBook(Book book, int bookID) throws Exception;// update book in bookList by bookID.
    public void updateCustomer(Customer customer, int customerID) throws Exception;// update customer in customerList by customerID.
    public void updateSupplier(Supplier supplier, int supplierID) throws Exception;// update supplier in supplierList by supplierID.
    public void updateBooksInStore(BooksInStore booksInStore, int booksInStoreID) throws Exception;// update booksInStore in booksInStoreList by booksInStoreID.
    public void updateOrder(Order order, int orderID) throws Exception;// update order in orderList by orderID.
    public void updateBookSupplier(BookSupplier bookSupplier) throws Exception;// update bookSupplier in bookSupplierList.
    public void updateInventory(int bookID, int newAmount) throws Exception;// update booksInStore.amount in booksInStoreList by booksInStoreID.
    public void resetUserPassword(int userID, String newPassword) throws Exception;

    /*4)*/
    public Book getBookByBookID(int bookID) throws Exception;// return Book by bookID.
    public Customer getCustomerByCustomerID(int customerID) throws Exception;// return Book by customerID.
    public Supplier getSupplierBySupplierID(int SupplierID) throws Exception;// return Supplier by supplierID.
    public BooksInStore getBooksInStoreByBooksInStoreID(int booksInStoreID) throws Exception;// return BooksInStore by booksInStoreID.
    public BooksInStore getBooksInStoreByBookID(int bookID) throws Exception;// return BooksInStore by bookID.
    public Order getOrderByOrderID(int orderID) throws Exception;// return Order by orderID.
    public Order getOrderByCustomerID(int customerID) throws Exception;// return Order by customerID.
    public ArrayList<BooksForOrder> getActiveBooksForOrderFromSupplierBySupplierID(int supplierID) throws Exception;// return ArrayList<BooksForOrder> of books that does not supplied yet, by supplierID.
    public BookSupplier getBookSupplierBySupplierIDAndByBookID(int supplierID, int bookID) throws Exception;// return BookSupplier by supplierID and by bookID.
    public ArrayList<BookSupplier> getBookSupplierByBookID(int bookID) throws Exception;// return BookSupplier by bookID.
    public ArrayList<BookSupplier> getBookSupplierBySupplierID(int supplierID) throws Exception;// return BookSupplier by supplierID.
    public User getUserByID(int userID) throws Exception;

    public ArrayList<Book> getBookList() throws Exception;
    public ArrayList<Customer> getCustomerList() throws Exception;
    public ArrayList<Supplier> getSupplierList() throws Exception;
    public ArrayList<BooksInStore> getBooksInStoreList() throws Exception;
    public ArrayList<Order> getOrderList() throws Exception;
    public ArrayList<BookSupplier> getBookSupplierList() throws Exception;
    public ArrayList<Book> getBookListByTitle(String title) throws Exception;// return ArrayList<Book> of books with specific title by title.
    public ArrayList<BooksInStore> getBooksInStoreByTitle(String title) throws Exception;// return ArrayList<BooksInStore> of BooksInStore with specific title by title.
    public ArrayList<Order> getOrderListOfSpecificCustomerByCustomerID(int customerID) throws Exception;// return ArrayList<Order> of orders of specific customer by customerID.
    public ArrayList<Customer> getCustomerListByName(String name) throws Exception;// return ArrayList<Customer> of customers with specific name by name.
    public ArrayList<User> getUserList() throws Exception;
    public ArrayList<Book> getBookListByCategory(Category category) throws Exception;
    public int getBookAmountByBookID(int bookID);

    public User login(String email, String password);

    /*5) test */
    public void printList(ArrayList arrayList);
    public void createLists() throws Exception;
    public ArrayList<BooksForOrder> getBooksForOrderArrayList() throws Exception;
    public void updateLists() throws Exception;
    public void deleteLists() throws Exception;
}
