package model.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
import model.backend.Backend;

/**
 * Created by reuvenp on 5/9/2016.
 */
public class DatabaseSQLite implements Backend {

    SQLiteDatabase database;
    DBHelper helper;
    String[] bookTableColumns = {DBHelper.BOOK_ID_COLUMN, DBHelper.BOOK_TITLE_COLUMN, DBHelper.BOOK_AUTHOR_COLUMN,
            DBHelper.BOOK_YEAR_COLUMN, DBHelper.BOOK_PAGES_COLUMN, DBHelper.BOOK_CATEGORY_COLUMN};

    public DatabaseSQLite(Context context)
    {
        helper = new DBHelper(context);
    }

    public void open() throws SQLException {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    @Override
    public void addBook(Book book) throws Exception {
        this.open();
        ContentValues values = new ContentValues();
        values.put(DBHelper.BOOK_TITLE_COLUMN, book.getTitle());
        values.put(DBHelper.BOOK_AUTHOR_COLUMN, book.getAuthor());
        values.put(DBHelper.BOOK_PAGES_COLUMN, book.getPages());
        values.put(DBHelper.BOOK_YEAR_COLUMN, book.getYear());
        values.put(DBHelper.BOOK_CATEGORY_COLUMN, book.getCategory().toString());
        database.insert(DBHelper.BOOK_TABLE_NAME, null, values);
        this.close();
    }

    @Override
    public int addCustomer(Customer customer) throws Exception {
        return 0;
    }

    @Override
    public int addSupplier(Supplier supplier) throws Exception {
        return 0;
    }

    @Override
    public void addBooksInStore(BooksInStore booksInStore) throws Exception {

    }

    @Override
    public void addOrder(Order order) throws Exception {

    }

    @Override
    public void addBooksForOrder(int orderID, BooksForOrder booksForOrder) throws Exception {

    }

    @Override
    public void addBookSupplier(BookSupplier bookSupplier) throws Exception {

    }

    @Override
    public void addUser(User user) throws Exception {

    }

    @Override
    public void deleteBook(int bookID) throws Exception {

    }

    @Override
    public void deleteBookFromStore(int bookID) throws Exception {

    }

    @Override
    public void deleteCustomer(int customerID) throws Exception {

    }

    @Override
    public void deleteSupplier(int supplierID) throws Exception {

    }

    @Override
    public void deleteBooksInStore(int bookID) throws Exception {

    }

    @Override
    public void deleteOrder(int orderID) throws Exception {

    }

    @Override
    public void deleteOrderPermanently(int orderID, boolean bool) throws Exception {

    }

    @Override
    public void deleteBooksForOrder(int orderID, BooksForOrder booksForOrder) throws Exception {

    }

    @Override
    public void deleteBookSupplier(int bookID, int supplierID) throws Exception {

    }

    @Override
    public void deleteUser(int UserID) throws Exception {

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

    @Override
    public void updateBookSupplier(BookSupplier bookSupplier) throws Exception {

    }

    @Override
    public void updateInventory(int bookID, int newAmount) throws Exception {

    }

    @Override
    public void resetUserPassword(int userID, String newPassword) throws Exception {

    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public Book getBookByBookID(int bookID) throws Exception {
        return null;
    }

    @Override
    public Customer getCustomerByCustomerID(int customerID) throws Exception {
        return null;
    }

    @Override
    public Supplier getSupplierBySupplierID(int SupplierID) throws Exception {
        return null;
    }

    @Override
    public BooksInStore getBooksInStoreByBooksInStoreID(int booksInStoreID) throws Exception {
        return null;
    }

    @Override
    public BooksInStore getBooksInStoreByBookID(int bookID) throws Exception {
        return null;
    }

    @Override
    public Order getOrderByOrderID(int orderID) throws Exception {
        return null;
    }

    @Override
    public Order getOrderByCustomerID(int customerID) throws Exception {
        return null;
    }

    @Override
    public ArrayList<BooksForOrder> getActiveBooksForOrderFromSupplierBySupplierID(int supplierID) throws Exception {
        return null;
    }

    @Override
    public BookSupplier getBookSupplierBySupplierIDAndByBookID(int supplierID, int bookID) throws Exception {
        return null;
    }

    @Override
    public ArrayList<BookSupplier> getBookSupplierByBookID(int bookID) throws Exception {
        return null;
    }

    @Override
    public ArrayList<BookSupplier> getBookSupplierBySupplierID(int supplierID) throws Exception {
        return null;
    }

    @Override
    public User getUserByID(int userID) throws Exception {
        return null;
    }

    @Override
    public ArrayList<Book> getBookListBySupplier(int supplierID) {
        return null;
    }

    @Override
    public ArrayList<Book> getBookList() throws Exception {
        this.open();
        ArrayList<Book> bookArrayList = new ArrayList<>();
        Cursor cursor = database.query(DBHelper.BOOK_TABLE_NAME,bookTableColumns,null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Book book = parseBook(cursor);
            bookArrayList.add(book);
            cursor.moveToNext();
        }
        cursor.close();
        this.close();
        return bookArrayList;
    }

    @Override
    public ArrayList<Customer> getCustomerList() throws Exception {
        return null;
    }

    @Override
    public ArrayList<Supplier> getSupplierList() throws Exception {
        return null;
    }

    @Override
    public ArrayList<BooksInStore> getBooksInStoreList() throws Exception {
        return null;
    }

    @Override
    public ArrayList<Order> getOrderList() throws Exception {
        return null;
    }

    @Override
    public ArrayList<BookSupplier> getBookSupplierList() throws Exception {
        return null;
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
    public ArrayList<User> getUserList() throws Exception {
        return null;
    }

    @Override
    public ArrayList<Book> getBookListByCategory(Category category) throws Exception {
        return null;
    }

    @Override
    public int getBookAmountByBookID(int bookID) {
        return 0;
    }

    @Override
    public User login(String email, String password) {
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

    Book parseBook(Cursor cursor)
    {
        int id = cursor.getInt(0);
        String title = cursor.getString(cursor.getColumnIndex(DBHelper.BOOK_TITLE_COLUMN));
        String author = cursor.getString(cursor.getColumnIndex(DBHelper.BOOK_AUTHOR_COLUMN));
        int year = cursor.getInt(cursor.getColumnIndex(DBHelper.BOOK_YEAR_COLUMN));
        int pages = cursor.getInt(cursor.getColumnIndex(DBHelper.BOOK_PAGES_COLUMN));
        String category = cursor.getString(cursor.getColumnIndex(DBHelper.BOOK_CATEGORY_COLUMN));
        Category category1 = Category.valueOf(category);
        Book book = new Book(title,year,author,pages,category1);
        return book;
    }

}
