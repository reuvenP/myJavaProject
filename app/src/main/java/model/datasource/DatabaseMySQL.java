package model.datasource;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

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
 * Created by reuvenp on 5/22/2016.
 */
public class DatabaseMySQL implements Backend {

    @Override
    public int addBook(Book book) throws Exception {
        final Map<String, Object> params = new LinkedHashMap<>();
        String ID = "";
        params.put("year", book.getYear());
        params.put("author", book.getAuthor());
        params.put("title", book.getTitle());
        params.put("pages", book.getPages());
        params.put("category", book.getCategory().toString());
        try {
            ID = POST("http://plevinsk.vlab.jct.ac.il/addBook.php", params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        int id = Integer.parseInt(ID.substring(0, ID.length() - 1));
        if (id == 0)
            throw new Exception("Error in add book");
        return id;
    }

    @Override
    public int addCustomer(Customer customer) throws Exception {
        final Map<String, Object> params = new LinkedHashMap<>();
        String ID = "";
        params.put("userPermission", Permission.CUSTOMER.toString());
        params.put("userName", customer.getName());
        params.put("userBirthday", customer.getBirthday());
        params.put("userGender", customer.getGender().toString());
        params.put("userAddress", customer.getAddress());
        try {
            ID = POST("http://plevinsk.vlab.jct.ac.il/addCustomerSupplier.php", params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        int id = Integer.parseInt(ID.substring(0, ID.length() - 1));
        if (id == 0)
            throw new Exception("Error in add customer");
        return id;
    }

    @Override
    public int addSupplier(Supplier supplier) throws Exception {
        final Map<String, Object> params = new LinkedHashMap<>();
        String ID = "";
        params.put("userPermission", Permission.SUPPLIER.toString());
        params.put("userName", supplier.getName());
        params.put("userBirthday", supplier.getBirthday());
        params.put("userGender", supplier.getGender().toString());
        params.put("userAddress", supplier.getAddress());
        try {
            ID = POST("http://plevinsk.vlab.jct.ac.il/addCustomerSupplier.php", params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        int id = Integer.parseInt(ID.substring(0, ID.length() - 1));
        if (id == 0)
            throw new Exception("Error in add supplier");
        return id;
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
        final Map<String, Object> params = new LinkedHashMap<>();
        String result = "";
        params.put("bookSupplierSupplier", bookSupplier.getSupplier().getSupplierID());
        params.put("bookSupplierBook", bookSupplier.getBook().getBookID());
        params.put("bookSupplierPrice", bookSupplier.getPrice());
        params.put("bookSupplierAmount", bookSupplier.getAmount());
        try {
            result = POST("http://plevinsk.vlab.jct.ac.il/addBookSupplier.php", params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(result);
        }
    }

    @Override
    public void addUser(User user) throws Exception {
        final Map<String, Object> params = new LinkedHashMap<>();
        String result = "";
        params.put("userID", user.getUserID());
        params.put("userMail", user.getMail());
        params.put("userPassword", user.getPassword());
        try {
            result = POST("http://plevinsk.vlab.jct.ac.il/addUser.php", params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(result);
        }
    }

    @Override
    public void deleteBook(int bookID) throws Exception {
        final Map<String, Object> params = new LinkedHashMap<>();
        params.put("bookID", bookID);
        try {
            POST("http://plevinsk.vlab.jct.ac.il/deleteBook.php", params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
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
        final Map<String, Object> params = new LinkedHashMap<>();
        params.put("bookID", bookID);
        params.put("year", book.getYear());
        params.put("author", book.getAuthor());
        params.put("title", book.getTitle());
        params.put("pages", book.getPages());
        params.put("category", book.getCategory().toString());
        try {
            POST("http://plevinsk.vlab.jct.ac.il/updateBook.php", params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
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
    public void updateUser(User user) throws Exception {

    }

    @Override
    public Book getBookByBookID(int bookID) throws Exception {
        try {
            JSONArray books = new JSONObject(GET("http://plevinsk.vlab.jct.ac.il/getBookByBookID.php?bookID=" + bookID)).getJSONArray("books");
            Book book = jsonToBook(books.getJSONObject(0));
            if (book != null)
                return book;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public Customer getCustomerByCustomerID(int customerID) throws Exception {
        try {
            JSONArray customers = new JSONObject(GET("http://plevinsk.vlab.jct.ac.il/getUserByUserID.php?userID=" + customerID)).getJSONArray("users");
            Customer customer = jsonToCustomer(customers.getJSONObject(0));
            if (customer != null)
                return customer;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public Supplier getSupplierBySupplierID(int SupplierID) throws Exception {
        try {
            JSONArray suppliers = new JSONObject(GET("http://plevinsk.vlab.jct.ac.il/getUserByUserID.php?userID=" + SupplierID)).getJSONArray("users");
            Supplier supplier = jsonToSupplier(suppliers.getJSONObject(0));
            if (supplier != null)
                return supplier;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
        try {
            JSONArray users = new JSONObject(GET("http://plevinsk.vlab.jct.ac.il/getUserByUserID.php?userID=" + userID)).getJSONArray("users");
            User user = jsonToUser(users.getJSONObject(0));
            if (user != null)
                return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public ArrayList<Book> getBookListBySupplier(int supplierID) {
        return null;
    }

    @Override
    public ArrayList<Book> getBookList() throws Exception {
        final ArrayList<Book> bookArrayList = new ArrayList<>();
        try {
            JSONArray books = new JSONObject(GET("http://plevinsk.vlab.jct.ac.il/getBookList.php")).getJSONArray("books");
            for (int i = 0; i < books.length(); i++) {
                Book book = jsonToBook(books.getJSONObject(i));
                if (book != null)
                    bookArrayList.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bookArrayList;
    }

    @Override
    public ArrayList<Customer> getCustomerList() throws Exception {
        final ArrayList<Customer> customerArrayList = new ArrayList<>();
        try {
            JSONArray customers = new JSONObject(GET("http://plevinsk.vlab.jct.ac.il/getCustomerList.php")).getJSONArray("customers");
            for (int i = 0; i < customers.length(); i++) {
                Customer customer = jsonToCustomer(customers.getJSONObject(i));
                if (customer != null)
                    customerArrayList.add(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return customerArrayList;
    }

    @Override
    public ArrayList<Supplier> getSupplierList() throws Exception {
        final ArrayList<Supplier> supplierArrayList = new ArrayList<>();
        try {
            JSONArray suppliers = new JSONObject(GET("http://plevinsk.vlab.jct.ac.il/getSupplierList.php")).getJSONArray("suppliers");
            for (int i = 0; i < suppliers.length(); i++) {
                Supplier supplier = jsonToSupplier(suppliers.getJSONObject(i));
                if (supplier != null)
                    supplierArrayList.add(supplier);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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

    private static String GET(String url) throws Exception {
        final String[] result = {""};
        final String url2 = url;
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        URL obj = new URL(url2);
                        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                        con.setRequestMethod("GET");
                        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) { // success
                            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            String inputLine;
                            StringBuffer response = new StringBuffer();
                            while ((inputLine = in.readLine()) != null) {
                                response.append(inputLine);
                            }
                            in.close();
                            result[0] = response.toString();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result[0];
    }

    private static String POST(final String url, Map<String, Object> params) throws IOException {
        //Convert Map<String,Object> into key=value&key=value pairs.
        final StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (postData.length() != 0)
                postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }

        final String[] result = {""};
        final String url2 = url;
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        URL obj = new URL(url2);
                        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                        con.setRequestMethod("POST");
                        // For POST only - START
                        con.setDoOutput(true);
                        OutputStream os = con.getOutputStream();
                        os.write(postData.toString().getBytes("UTF-8"));
                        os.flush();
                        os.close();
                        // For POST only - END
                        int responseCode = con.getResponseCode();
                        System.out.println("POST Response Code :: " + responseCode);
                        if (responseCode == HttpURLConnection.HTTP_OK) { //success
                            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            String inputLine;
                            StringBuffer response = new StringBuffer();
                            while ((inputLine = in.readLine()) != null) {
                                response.append(inputLine);
                            }
                            in.close();
                            result[0] = response.toString();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result[0];
    }

    Book jsonToBook(JSONObject object) {
        try {
            Book book = new Book(object.getString("title"), object.getInt("year"), object.getString("author"), object.getInt("pages"), Category.valueOf(object.getString("category")));
            book.setBookID(object.getInt("bookID"));
            return book;
        } catch (JSONException e) {
            return null;
        }
    }

    Customer jsonToCustomer(JSONObject object) {
        try {
            if (object.getString("userPermission").equals(Permission.CUSTOMER.toString())) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Customer customer = new Customer(CustomerType.REGULAR, object.getString("userName"), sdf.parse(object.getString("userBirthday")), Gender.valueOf(object.getString("userGender")), object.getString("userAddress"), null);
                customer.setCustomerID(object.getInt("userID"));
                return customer;
            } else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    Supplier jsonToSupplier(JSONObject object) {
        try {
            if (object.getString("userPermission").equals(Permission.SUPPLIER.toString())) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Supplier supplier = new Supplier(Rating.FIVE, object.getString("userName"), sdf.parse(object.getString("userBirthday")), Gender.valueOf(object.getString("userGender")), object.getString("userAddress"), null);
                supplier.setSupplierID(object.getInt("userID"));
                return supplier;
            } else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    User jsonToUser(JSONObject object)
    {
        try
        {
            User user = new User(Permission.valueOf(object.getString("userPermission")),object.getString("userMail"),object.getString("userPassword"), object.getInt("userID"));
            return user;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
