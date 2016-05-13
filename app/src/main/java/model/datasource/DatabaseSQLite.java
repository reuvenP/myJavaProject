package model.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;

import entities.Book;
import entities.BookSupplier;
import entities.BooksForOrder;
import entities.BooksInStore;
import entities.Category;
import entities.Customer;
import entities.CustomerType;
import entities.Gender;
import entities.Order;
import entities.Permission;
import entities.Rating;
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
    String[] userTableColumns = {DBHelper.USER_ID_COLUMN,DBHelper.USER_PERMISSION_COLUMN,DBHelper.USER_NAME_COLUMN,
            DBHelper.USER_BIRTHDAY_COLUMN ,DBHelper.USER_GENDER_COLUMN ,DBHelper.USER_ADDRESS_COLUMN,
            DBHelper.USER_MAIL_COLUMN ,DBHelper.USER_PASSWORD_COLUMN /*,DBHelper.USER_ORDER_COLUMN*/};
    String[] bookSupplierColumns = {DBHelper.BOOK_SUPPLIER_SUPPLIER_COLUMN, DBHelper.BOOK_SUPPLIER_BOOK_COLUMN,
            DBHelper.BOOK_SUPPLIER_PRICE_COLUMN, DBHelper.BOOK_SUPPLIER_AMOUNT_COLUMN};

    public DatabaseSQLite(Context context)
    {
        helper = new DBHelper(context);
        this.open();
    }

    public void open() throws SQLException {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    @Override
    public int addBook(Book book) throws Exception {
        ArrayList<Book> bookArrayList = this.getBookList();
        for (Book book1 : bookArrayList)
        {
            if (book.getTitle().equals(book1.getTitle()) && book.getAuthor().equals(book1.getAuthor()) &&
                    book.getYear() == book1.getYear() && book.getPages() == book1.getPages() &&
                    book.getCategory() == book1.getCategory())
                throw new Exception("This book already exist");
        }
        ContentValues values = new ContentValues();
        values.put(DBHelper.BOOK_TITLE_COLUMN, book.getTitle());
        values.put(DBHelper.BOOK_AUTHOR_COLUMN, book.getAuthor());
        values.put(DBHelper.BOOK_PAGES_COLUMN, book.getPages());
        values.put(DBHelper.BOOK_YEAR_COLUMN, book.getYear());
        values.put(DBHelper.BOOK_CATEGORY_COLUMN, book.getCategory().toString());
        long id = database.insert(DBHelper.BOOK_TABLE_NAME, null, values);
        return (int)id;
    }

    @Override
    public int addCustomer(Customer customer) throws Exception {
        ContentValues values = new ContentValues();
        values.put(DBHelper.USER_NAME_COLUMN, customer.getName());
        values.put(DBHelper.USER_ADDRESS_COLUMN, customer.getAddress());
        values.put(DBHelper.USER_GENDER_COLUMN, customer.getGender().toString());
        values.put(DBHelper.USER_BIRTHDAY_COLUMN, customer.getBirthday().getTime());
        values.put(DBHelper.USER_PERMISSION_COLUMN, Permission.CUSTOMER.toString());
        long id = database.insert(DBHelper.USER_TABLE_NAME,null,values);
        return (int)id;
    }

    @Override
    public int addSupplier(Supplier supplier) throws Exception {
        ContentValues values = new ContentValues();
        values.put(DBHelper.USER_NAME_COLUMN, supplier.getName());
        values.put(DBHelper.USER_ADDRESS_COLUMN, supplier.getAddress());
        values.put(DBHelper.USER_GENDER_COLUMN, supplier.getGender().toString());
        values.put(DBHelper.USER_BIRTHDAY_COLUMN, supplier.getBirthday().getTime());
        values.put(DBHelper.USER_PERMISSION_COLUMN, Permission.SUPPLIER.toString());
        long id = database.insert(DBHelper.USER_TABLE_NAME,null,values);
        return (int)id;
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
        ContentValues values = new ContentValues();
        values.put(DBHelper.BOOK_SUPPLIER_SUPPLIER_COLUMN, bookSupplier.getSupplier().getSupplierID());
        values.put(DBHelper.BOOK_SUPPLIER_BOOK_COLUMN, bookSupplier.getBook().getBookID());
        values.put(DBHelper.BOOK_SUPPLIER_PRICE_COLUMN, bookSupplier.getPrice());
        values.put(DBHelper.BOOK_SUPPLIER_AMOUNT_COLUMN, bookSupplier.getAmount());
        database.insert(DBHelper.BOOK_SUPPLIER_RELATION_NAME,null,values);
    }

    @Override
    public void addUser(User user) throws Exception {
        ArrayList<User> userArrayList = this.getUserList();
        for (User user1 : userArrayList)
        {
            if (user.getMail().equals(user1.getMail()))
                throw new Exception("This mail already exist");
        }
        ContentValues values = new ContentValues();
        values.put(DBHelper.USER_MAIL_COLUMN, user.getMail());
        values.put(DBHelper.USER_PASSWORD_COLUMN, user.getPassword());
        database.update(DBHelper.USER_TABLE_NAME,values,DBHelper.USER_ID_COLUMN+"="+user.getUserID(),null);
    }

    @Override
    public void deleteBook(int bookID) throws Exception {
        database.delete(DBHelper.BOOK_TABLE_NAME, DBHelper.BOOK_ID_COLUMN + "=" + bookID, null);
    }

    @Override
    public void deleteBookFromStore(int bookID) throws Exception {
        database.delete(DBHelper.BOOK_TABLE_NAME, DBHelper.BOOK_ID_COLUMN + "=" + Integer.toString(bookID), null);
    }

    @Override
    public void deleteCustomer(int customerID) throws Exception {
        database.delete(DBHelper.USER_TABLE_NAME,DBHelper.USER_ID_COLUMN + "=" + customerID, null);
    }

    @Override
    public void deleteSupplier(int supplierID) throws Exception {
        database.delete(DBHelper.USER_TABLE_NAME,DBHelper.USER_ID_COLUMN + "=" + supplierID, null);
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
        database.delete(DBHelper.BOOK_SUPPLIER_RELATION_NAME,DBHelper.BOOK_SUPPLIER_SUPPLIER_COLUMN+"="+supplierID + " and " +DBHelper.BOOK_SUPPLIER_BOOK_COLUMN + "=" + bookID,null);
    }

    @Override
    public void deleteUser(int UserID) throws Exception {
        database.delete(DBHelper.USER_TABLE_NAME,DBHelper.USER_ID_COLUMN + "=" + UserID, null);
    }

    @Override
    public void updateBook(Book book, int bookID) throws Exception {
        ArrayList<Book> bookArrayList = this.getBookList();
        for (Book book1 : bookArrayList)
        {
            if (book.getTitle().equals(book1.getTitle()) && book.getAuthor().equals(book1.getAuthor()) &&
                    book.getYear() == book1.getYear() && book.getPages() == book1.getPages() &&
                    book.getCategory() == book1.getCategory())
                throw new Exception("This book already exist");
        }
        ContentValues values = new ContentValues();
        values.put(DBHelper.BOOK_TITLE_COLUMN, book.getTitle());
        values.put(DBHelper.BOOK_AUTHOR_COLUMN, book.getAuthor());
        values.put(DBHelper.BOOK_PAGES_COLUMN, book.getPages());
        values.put(DBHelper.BOOK_YEAR_COLUMN, book.getYear());
        values.put(DBHelper.BOOK_CATEGORY_COLUMN, book.getCategory().toString());
        database.update(DBHelper.BOOK_TABLE_NAME,values,DBHelper.BOOK_ID_COLUMN + "=" + bookID, null);
    }

    @Override
    public void updateCustomer(Customer customer, int customerID) throws Exception {
        ContentValues values = new ContentValues();
        values.put(DBHelper.USER_NAME_COLUMN, customer.getName());
        values.put(DBHelper.USER_ADDRESS_COLUMN, customer.getAddress());
        values.put(DBHelper.USER_GENDER_COLUMN, customer.getGender().toString());
        values.put(DBHelper.USER_BIRTHDAY_COLUMN, customer.getBirthday().getTime());
        values.put(DBHelper.USER_PERMISSION_COLUMN, Permission.CUSTOMER.toString());
        database.update(DBHelper.USER_TABLE_NAME, values, DBHelper.USER_ID_COLUMN+"="+customerID,null);
    }

    @Override
    public void updateSupplier(Supplier supplier, int supplierID) throws Exception {
        ContentValues values = new ContentValues();
        values.put(DBHelper.USER_NAME_COLUMN, supplier.getName());
        values.put(DBHelper.USER_ADDRESS_COLUMN, supplier.getAddress());
        values.put(DBHelper.USER_GENDER_COLUMN, supplier.getGender().toString());
        values.put(DBHelper.USER_BIRTHDAY_COLUMN, supplier.getBirthday().getTime());
        values.put(DBHelper.USER_PERMISSION_COLUMN, Permission.SUPPLIER.toString());
        database.update(DBHelper.USER_TABLE_NAME,values,DBHelper.USER_ID_COLUMN+"="+supplierID,null);
    }

    @Override
    public void updateBooksInStore(BooksInStore booksInStore, int booksInStoreID) throws Exception {

    }

    @Override
    public void updateOrder(Order order, int orderID) throws Exception {

    }

    @Override
    public void updateBookSupplier(BookSupplier bookSupplier) throws Exception {
        ContentValues values = new ContentValues();
        values.put(DBHelper.BOOK_SUPPLIER_SUPPLIER_COLUMN, bookSupplier.getSupplier().getSupplierID());
        values.put(DBHelper.BOOK_SUPPLIER_BOOK_COLUMN, bookSupplier.getBook().getBookID());
        values.put(DBHelper.BOOK_SUPPLIER_PRICE_COLUMN, bookSupplier.getPrice());
        values.put(DBHelper.BOOK_SUPPLIER_AMOUNT_COLUMN, bookSupplier.getAmount());
        database.update(DBHelper.BOOK_SUPPLIER_RELATION_NAME,values,DBHelper.BOOK_SUPPLIER_SUPPLIER_COLUMN+"="+bookSupplier.getSupplier().getSupplierID() + " and " +DBHelper.BOOK_SUPPLIER_BOOK_COLUMN + "=" + bookSupplier.getBook().getBookID(),null);
    }

    @Override
    public void updateInventory(int bookID, int newAmount) throws Exception {

    }

    @Override
    public void resetUserPassword(int userID, String newPassword) throws Exception {
        User user = this.getUserByID(userID);
        user.setPassword(newPassword);
        this.updateUser(user);
    }

    @Override
    public void updateUser(User user) throws Exception{
        ArrayList<User> userArrayList = this.getUserList();
        for (User user1 : userArrayList)
        {
            if (user.getMail().equals(user1.getMail()))
                throw new Exception("This mail already exist");
        }
        ContentValues values = new ContentValues();
        values.put(DBHelper.USER_MAIL_COLUMN, user.getMail());
        values.put(DBHelper.USER_PASSWORD_COLUMN, user.getPassword());
        database.update(DBHelper.USER_TABLE_NAME,values,DBHelper.USER_ID_COLUMN+"="+user.getUserID(),null);
    }

    @Override
    public Book getBookByBookID(int bookID) throws Exception {
        Cursor cursor = database.query(DBHelper.BOOK_TABLE_NAME, bookTableColumns,DBHelper.BOOK_ID_COLUMN+"="+bookID,null,null,null,null);
        if (cursor == null || cursor.getCount() == 0)
            throw new Exception("There is no such ID");
        cursor.moveToFirst();
        return parseBook(cursor);
    }

    @Override
    public Customer getCustomerByCustomerID(int customerID) throws Exception {
        Cursor cursor = database.query(DBHelper.USER_TABLE_NAME, userTableColumns,DBHelper.USER_ID_COLUMN+"="+customerID,null,null,null,null);
        if (cursor == null || cursor.getCount() == 0)
            throw new Exception("There is no such ID");
        cursor.moveToFirst();
        return parseCustomer(cursor);
    }

    @Override
    public Supplier getSupplierBySupplierID(int SupplierID) throws Exception {
        Cursor cursor = database.query(DBHelper.USER_TABLE_NAME, userTableColumns,DBHelper.USER_ID_COLUMN+"="+SupplierID,null,null,null,null);
        if (cursor == null || cursor.getCount() == 0)
            throw new Exception("There is no such ID");
        cursor.moveToFirst();
        return parseSupplier(cursor);
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
        Cursor cursor = database.query(DBHelper.BOOK_SUPPLIER_RELATION_NAME,bookSupplierColumns,DBHelper.BOOK_SUPPLIER_SUPPLIER_COLUMN+"="+supplierID + " and " +DBHelper.BOOK_SUPPLIER_BOOK_COLUMN + "=" + bookID,null,null,null,null);
        if (cursor == null || cursor.getCount() == 0)
            throw new Exception("There is no such ID");
        cursor.moveToFirst();
        return parseBookSupplier(cursor);
    }

    @Override
    public ArrayList<BookSupplier> getBookSupplierByBookID(int bookID) throws Exception {
        ArrayList<BookSupplier> bookSupplierArrayList = new ArrayList<>();
        Cursor cursor = database.query(DBHelper.BOOK_SUPPLIER_RELATION_NAME,bookSupplierColumns,DBHelper.BOOK_SUPPLIER_BOOK_COLUMN+"="+bookID,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            BookSupplier bookSupplier = parseBookSupplier(cursor);
            bookSupplierArrayList.add(bookSupplier);
            cursor.moveToNext();
        }
        cursor.close();
        return bookSupplierArrayList;
    }

    @Override
    public ArrayList<BookSupplier> getBookSupplierBySupplierID(int supplierID) throws Exception {
        ArrayList<BookSupplier> bookSupplierArrayList = new ArrayList<>();
        Cursor cursor = database.query(DBHelper.BOOK_SUPPLIER_RELATION_NAME,bookSupplierColumns,DBHelper.BOOK_SUPPLIER_SUPPLIER_COLUMN+"="+supplierID,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            BookSupplier bookSupplier = parseBookSupplier(cursor);
            bookSupplierArrayList.add(bookSupplier);
            cursor.moveToNext();
        }
        cursor.close();
        return bookSupplierArrayList;
    }

    @Override
    public User getUserByID(int userID) throws Exception {
        Cursor cursor = database.query(DBHelper.USER_TABLE_NAME, userTableColumns,DBHelper.USER_ID_COLUMN+"="+userID,null,null,null,null);
        if (cursor == null || cursor.getCount() == 0)
            throw new Exception("There is no such ID");
        cursor.moveToFirst();
        return parseUser(cursor);
    }

    @Override
    public ArrayList<Book> getBookListBySupplier(int supplierID) {
        ArrayList<Book> bookArrayList = new ArrayList<>();
        try {
            ArrayList<BookSupplier> bookSupplierArrayList = this.getBookSupplierBySupplierID(supplierID);
            for (BookSupplier bookSupplier : bookSupplierArrayList)
            {
                bookArrayList.add(bookSupplier.getBook());
            }
            return bookArrayList;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ArrayList<Book> getBookList() throws Exception {
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
        return bookArrayList;
    }

    @Override
    public ArrayList<Customer> getCustomerList() throws Exception {
        ArrayList<Customer> customerArrayList = new ArrayList<>();
        Cursor cursor = database.query(DBHelper.USER_TABLE_NAME, userTableColumns,null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Customer customer = parseCustomer(cursor);
            customerArrayList.add(customer);
            cursor.moveToNext();
        }
        cursor.close();
        return customerArrayList;
    }

    @Override
    public ArrayList<Supplier> getSupplierList() throws Exception {
        ArrayList<Supplier> supplierArrayList = new ArrayList<>();
        Cursor cursor = database.query(DBHelper.USER_TABLE_NAME, userTableColumns,null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Supplier supplier = parseSupplier(cursor);
            supplierArrayList.add(supplier);
            cursor.moveToNext();
        }
        cursor.close();
        return supplierArrayList;
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
        ArrayList<BookSupplier> bookSupplierArrayList = new ArrayList<>();
        Cursor cursor = database.query(DBHelper.BOOK_SUPPLIER_RELATION_NAME,bookSupplierColumns,null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            BookSupplier bookSupplier = parseBookSupplier(cursor);
            bookSupplierArrayList.add(bookSupplier);
            cursor.moveToNext();
        }
        cursor.close();
        return bookSupplierArrayList;
    }

    @Override
    public ArrayList<Book> getBookListByTitle(String title) throws Exception {
        ArrayList<Book> bookArrayList = new ArrayList<>();
        Cursor cursor = database.query(DBHelper.BOOK_TABLE_NAME,bookTableColumns,DBHelper.BOOK_TITLE_COLUMN+"="+title, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Book book = parseBook(cursor);
            bookArrayList.add(book);
            cursor.moveToNext();
        }
        cursor.close();
        return bookArrayList;
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
        ArrayList<User> userArrayList = new ArrayList<>();
        Cursor cursor = database.query(DBHelper.USER_TABLE_NAME, userTableColumns,null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            User user = parseUser(cursor);
            userArrayList.add(user);
            cursor.moveToNext();
        }
        cursor.close();
        return userArrayList;
    }

    @Override
    public ArrayList<Book> getBookListByCategory(Category category) throws Exception {
        ArrayList<Book> bookArrayList = new ArrayList<>();
       /* Cursor cursor = database.query(DBHelper.BOOK_TABLE_NAME,bookTableColumns,DBHelper.BOOK_CATEGORY_COLUMN+"="+category,null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Book book = parseBook(cursor);
            bookArrayList.add(book);
            cursor.moveToNext();
        }
        cursor.close();*/
        ArrayList<Book> bookArrayList1 = this.getBookList();
        for (Book book : bookArrayList1)
        {
            if (book.getCategory() == category)
                bookArrayList.add(book);
        }
        return bookArrayList;    }

    @Override
    public int getBookAmountByBookID(int bookID) {
        try {
            ArrayList<BookSupplier> bookSupplierArrayList = this.getBookSupplierByBookID(bookID);
            int sum = 0;
            for (BookSupplier bookSupplier : bookSupplierArrayList)
            {
                sum += bookSupplier.getAmount();
            }
            return sum;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public User login(String email, String password) {
        String s = DatabaseUtils.sqlEscapeString(email);
        Cursor cursor = database.query(DBHelper.USER_TABLE_NAME, userTableColumns,DBHelper.USER_MAIL_COLUMN+"="+ s,null,null,null,null);
        if (cursor == null || cursor.getCount() < 1)
            return null;
        cursor.moveToFirst();
        User user = parseUser(cursor);
        if (user == null)
            return null;
        if (user.getPassword().equals(password))
            return user;
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
        book.setBookID(id);
        return book;
    }

    Customer parseCustomer(Cursor cursor)
    {
        int id = cursor.getInt(0);
        String name = cursor.getString(cursor.getColumnIndex(DBHelper.USER_NAME_COLUMN));
        int birthdayInt = cursor.getInt(cursor.getColumnIndex(DBHelper.USER_BIRTHDAY_COLUMN));
        Date birthday = new Date(birthdayInt);
        String genderString = cursor.getString(cursor.getColumnIndex(DBHelper.USER_GENDER_COLUMN));
        Gender gender = Gender.valueOf(genderString);
        String address = cursor.getString(cursor.getColumnIndex(DBHelper.USER_ADDRESS_COLUMN));
        Customer customer = new Customer(CustomerType.REGULAR,name,birthday,gender,address,null);
        customer.setCustomerID(id);
        return customer;
    }

    Supplier parseSupplier(Cursor cursor)
    {
        int id = cursor.getInt(0);
        String name = cursor.getString(cursor.getColumnIndex(DBHelper.USER_NAME_COLUMN));
        int birthdayInt = cursor.getInt(cursor.getColumnIndex(DBHelper.USER_BIRTHDAY_COLUMN));
        Date birthday = new Date(birthdayInt);
        String genderString = cursor.getString(cursor.getColumnIndex(DBHelper.USER_GENDER_COLUMN));
        Gender gender = Gender.valueOf(genderString);
        String address = cursor.getString(cursor.getColumnIndex(DBHelper.USER_ADDRESS_COLUMN));
        Supplier supplier = new Supplier(Rating.THREE,name,birthday,gender,address,null);
        supplier.setSupplierID(id);
        return supplier;
    }

    User parseUser(Cursor cursor)
    {
        int id = cursor.getInt(0);
        String permissionString = cursor.getString(cursor.getColumnIndex(DBHelper.USER_PERMISSION_COLUMN));
        Permission permission = Permission.valueOf(permissionString);
        String mail = cursor.getString(cursor.getColumnIndex(DBHelper.USER_MAIL_COLUMN));
        String password = cursor.getString(cursor.getColumnIndex(DBHelper.USER_PASSWORD_COLUMN));
        User user = new User(permission,mail,password,id);
        return user;
    }

    BookSupplier parseBookSupplier(Cursor cursor)
    {
        int bookID = cursor.getInt(cursor.getColumnIndex(DBHelper.BOOK_SUPPLIER_BOOK_COLUMN));
        int supplierID = cursor.getInt(cursor.getColumnIndex(DBHelper.BOOK_SUPPLIER_SUPPLIER_COLUMN));
        float price = cursor.getFloat(cursor.getColumnIndex(DBHelper.BOOK_SUPPLIER_PRICE_COLUMN));
        int amount = cursor.getInt(cursor.getColumnIndex(DBHelper.BOOK_SUPPLIER_AMOUNT_COLUMN));
        try {
            Book book = this.getBookByBookID(bookID);
            Supplier supplier = this.getSupplierBySupplierID(supplierID);
            BookSupplier bookSupplier = new BookSupplier(supplier,book,price,amount);
            return bookSupplier;
        } catch (Exception e) {
            return null;
        }
    }

}
