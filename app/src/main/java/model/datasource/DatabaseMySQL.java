package model.datasource;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.shmulik.control.Mail;

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
import java.util.Date;
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
        params.put("userBirthday", new SimpleDateFormat("yyyy-MM-dd").format(customer.getBirthday()));
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
        params.put("userBirthday", new SimpleDateFormat("yyyy-MM-dd").format(supplier.getBirthday()));
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
    public int addOrder(Order order) throws Exception {
        Map<String, Object> params = new LinkedHashMap<>();
        String result = "";
        params.put("customerID", order.getCustomer().getCustomerID());
        params.put("date", new SimpleDateFormat("yyyy-MM-dd").format(order.getOrderDate()));
        params.put("price", order.getTotalPrice());
        params.put("books", BooksForOrderListToString(order.getBooksForOrders()));
        try
        {
            result = POST("http://plevinsk.vlab.jct.ac.il/addOrder.php", params);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        int id = Integer.parseInt(result.substring(0, result.length() - 1));
        if (id == 0)
            throw new Exception("Error in add order");
        return id;
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
        final Map<String, Object> params = new LinkedHashMap<>();
        params.put("bookID", bookID);
        params.put("supplierID", supplierID);
        try {
            POST("http://plevinsk.vlab.jct.ac.il/deleteBookSupplier.php", params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
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
        final Map<String, Object> params = new LinkedHashMap<>();
        params.put("userID", customer.getCustomerID());
        params.put("userPermission", Permission.CUSTOMER.toString());
        params.put("userName", customer.getName());
        params.put("userBirthday", new SimpleDateFormat("yyyy-MM-dd").format(customer.getBirthday()));
        params.put("userGender", customer.getGender().toString());
        params.put("userAddress", customer.getAddress());
        try {
            POST("http://plevinsk.vlab.jct.ac.il/updateCustomerSupplier.php", params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void updateSupplier(Supplier supplier, int supplierID) throws Exception {
        final Map<String, Object> params = new LinkedHashMap<>();
        params.put("userID", supplier.getSupplierID());
        params.put("userPermission", Permission.SUPPLIER.toString());
        params.put("userName", supplier.getName());
        params.put("userBirthday", new SimpleDateFormat("yyyy-MM-dd").format(supplier.getBirthday()));
        params.put("userGender", supplier.getGender().toString());
        params.put("userAddress", supplier.getAddress());
        try {
            POST("http://plevinsk.vlab.jct.ac.il/updateCustomerSupplier.php", params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void updateBooksInStore(BooksInStore booksInStore, int booksInStoreID) throws Exception {

    }

    @Override
    public void updateOrder(Order order, int orderID) throws Exception {

    }

    @Override
    public void updateBookSupplier(BookSupplier bookSupplier) throws Exception {
        final Map<String, Object> params = new LinkedHashMap<>();
        String result = "";
        params.put("bookSupplierSupplier", bookSupplier.getSupplier().getSupplierID());
        params.put("bookSupplierBook", bookSupplier.getBook().getBookID());
        params.put("bookSupplierPrice", bookSupplier.getPrice());
        params.put("bookSupplierAmount", bookSupplier.getAmount());
        try {
            result = POST("http://plevinsk.vlab.jct.ac.il/updateBookSupplier.php", params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(result);
        }
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
    public void updateUser(User user) throws Exception {
        final Map<String, Object> params = new LinkedHashMap<>();
        String result = "";
        params.put("userID", user.getUserID());
        params.put("userMail", user.getMail());
        params.put("userPassword", user.getPassword());
        params.put("userOrder", bookSupplierListToString(user.getOrder()));
        try {
            result = POST("http://plevinsk.vlab.jct.ac.il/updateUser.php", params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(result);
        }
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
        try {
            JSONArray orders = new JSONObject(GET("http://plevinsk.vlab.jct.ac.il/getOrderByOrderID.php?id=" + orderID)).getJSONArray("orders");
            Order order = jsonToOrder(orders.getJSONObject(0));
            if (order != null)
                return order;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
        try {
            JSONArray bookSuppliers = new JSONObject(GET("http://plevinsk.vlab.jct.ac.il/getBookSupplierBySupplierIDAndByBookID.php?bookID=" + bookID + "&supplierID=" + supplierID)).getJSONArray("bookSuppliers");
            BookSupplier bookSupplier = jsonToBookSupplier(bookSuppliers.getJSONObject(0));
            if (bookSupplier != null)
                return bookSupplier;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public ArrayList<BookSupplier> getBookSupplierByBookID(int bookID) throws Exception {
        final ArrayList<BookSupplier> bookSupplierArrayList = new ArrayList<>();
        try {
            JSONArray bookSuppliers = new JSONObject(GET("http://plevinsk.vlab.jct.ac.il/getBookSupplierByBookID.php?bookID=" + bookID)).getJSONArray("bookSuppliers");
            for (int i = 0; i < bookSuppliers.length(); i++) {
                BookSupplier bookSupplier = jsonToBookSupplier(bookSuppliers.getJSONObject(i));
                if (bookSupplier != null)
                    bookSupplierArrayList.add(bookSupplier);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return bookSupplierArrayList;
        }
        return bookSupplierArrayList;
    }

    @Override
    public ArrayList<BookSupplier> getBookSupplierBySupplierID(int supplierID) throws Exception {
        final ArrayList<BookSupplier> bookSupplierArrayList = new ArrayList<>();
        try {
            JSONArray bookSuppliers = new JSONObject(GET("http://plevinsk.vlab.jct.ac.il/getBookSupplierBySupplierID.php?supplierID=" + supplierID)).getJSONArray("bookSuppliers");
            for (int i = 0; i < bookSuppliers.length(); i++) {
                BookSupplier bookSupplier = jsonToBookSupplier(bookSuppliers.getJSONObject(i));
                if (bookSupplier != null)
                    bookSupplierArrayList.add(bookSupplier);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return bookSupplierArrayList;
        }
        return bookSupplierArrayList;
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
        ArrayList<Book> bookArrayList = new ArrayList<>();
        try {
            ArrayList<BookSupplier> bookSupplierArrayList = this.getBookSupplierBySupplierID(supplierID);
            for (BookSupplier bookSupplier : bookSupplierArrayList)
                bookArrayList.add(bookSupplier.getBook());
            return bookArrayList;
        } catch (Exception e) {
            e.printStackTrace();
            return bookArrayList;
        }
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
            return bookArrayList;
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
            return customerArrayList;
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
            return supplierArrayList;
        }
        return supplierArrayList;
    }

    @Override
    public ArrayList<BooksInStore> getBooksInStoreList() throws Exception {
        return null;
    }

    @Override
    public ArrayList<Order> getOrderList() throws Exception {
        ArrayList<Order> orderArrayList = new ArrayList<>();
        try
        {
            JSONArray orders = new JSONObject(GET("http://plevinsk.vlab.jct.ac.il/getOrderList.php")).getJSONArray("orders");
            for (int i = 0; i < orders.length(); i++)
            {
                Order order = jsonToOrder(orders.getJSONObject(i));
                if (order != null)
                    orderArrayList.add(order);
            }
        }
        catch (Exception e)
        {
            return orderArrayList;
        }
        return orderArrayList;
    }

    @Override
    public ArrayList<BookSupplier> getBookSupplierList() throws Exception {
        final ArrayList<BookSupplier> bookSupplierArrayList = new ArrayList<>();
        try {
            JSONArray bookSuppliers = new JSONObject(GET("http://plevinsk.vlab.jct.ac.il/getBookSupplierList.php")).getJSONArray("bookSuppliers");
            for (int i = 0; i < bookSuppliers.length(); i++) {
                BookSupplier bookSupplier = jsonToBookSupplier(bookSuppliers.getJSONObject(i));
                if (bookSupplier != null)
                    bookSupplierArrayList.add(bookSupplier);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return bookSupplierArrayList;
        }
        return bookSupplierArrayList;
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
        ArrayList<Order> orderArrayList = new ArrayList<>();
        try
        {
            JSONArray orders = new JSONObject(GET("http://plevinsk.vlab.jct.ac.il/getOrderListByCustomerID.php?id=" + customerID)).getJSONArray("orders");
            for (int i = 0; i < orders.length(); i++)
            {
                Order order = jsonToOrder(orders.getJSONObject(i));
                if (order != null)
                    orderArrayList.add(order);
            }
        }
        catch (Exception e)
        {
            return orderArrayList;
        }
        return orderArrayList;
    }

    @Override
    public ArrayList<Customer> getCustomerListByName(String name) throws Exception {
        return null;
    }

    @Override
    public ArrayList<User> getUserList() throws Exception {
        final ArrayList<User> userArrayList = new ArrayList<>();
        try {
            JSONArray users = new JSONObject(GET("http://plevinsk.vlab.jct.ac.il/getUserList.php")).getJSONArray("users");
            for (int i = 0; i < users.length(); i++) {
                User user = jsonToUser(users.getJSONObject(i));
                if (user != null)
                    userArrayList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return userArrayList;
        }
        return userArrayList;
    }

    @Override
    public ArrayList<Book> getBookListByCategory(Category category) throws Exception {
        final ArrayList<Book> bookArrayList = new ArrayList<>();
        try {
            JSONArray books = new JSONObject(GET("http://plevinsk.vlab.jct.ac.il/getBookListByCategory.php?category=" + category.toString())).getJSONArray("books");
            for (int i = 0; i < books.length(); i++) {
                Book book = jsonToBook(books.getJSONObject(i));
                if (book != null)
                    bookArrayList.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return bookArrayList;
        }
        return bookArrayList;
    }

    @Override
    public int getBookAmountByBookID(int bookID) {
        int amount = 0;
        try {
            ArrayList<BookSupplier> bookSupplierArrayList = this.getBookSupplierByBookID(bookID);
            for (BookSupplier bookSupplier : bookSupplierArrayList)
                amount += bookSupplier.getAmount();
        } catch (Exception e) {
            e.printStackTrace();
            return amount;
        }
        return amount;
    }

    @Override
    public User login(String email, String password) {
        try {
            ArrayList<User> userArrayList = this.getUserList();
            for (User user : userArrayList) {
                if (user.getMail().equals(email) && user.getPassword().equals(password))
                    return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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

    @Override
    public void submit(final Order order) throws Exception {
        for (BooksForOrder booksForOrder : order.getBooksForOrders()) {
            try {
                User user = getUserByID(booksForOrder.getBookSupplier().getSupplier().getSupplierID());
                String to = user.getMail();
                String subject = "New Order [#" + order.getOrderID() + "]";
                String body = "Hi " + booksForOrder.getBookSupplier().getSupplier().getName() +
                        "! You have a new order\n\nBook Name: " + booksForOrder.getBookSupplier().getBook().getTitle() +
                        "\nBook Author: " + booksForOrder.getBookSupplier().getBook().getAuthor() +
                        "\nBook Year: " + booksForOrder.getBookSupplier().getBook().getYear() +
                        "\nBook Price: " + booksForOrder.getBookSupplier().getPrice() +
                        "\nAmount: " + booksForOrder.getSumOfBooks() +
                        "\nTotal for Order: " + booksForOrder.getBookSupplier().getPrice() *  booksForOrder.getSumOfBooks() +
                        "\n\nClient Info:\nName: " + order.getCustomer().getName() +
                        "\nClient Address: " + order.getCustomer().getAddress() + "\nemail: " +
                        getUserByID(order.getCustomer().getCustomerID()).getMail();
                sendMail(to, subject,body);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void sendMail(final String to, final String subject, final String body) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Mail m = new Mail("javaproject2016sr@gmail.com", "shmulikreuven");
                try {
                    String[] toArr = {to}; // This is an array, you can add more emails, just separate them with a coma
                    m.setTo(toArr); // load array to setTo function
                    m.setFrom("javaproject2016sr@gmail.com"); // who is sending the email
                    m.setSubject(subject);
                    m.setBody(body);
                    m.send();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
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

    User jsonToUser(JSONObject object) {
        try {
            User user = new User(Permission.valueOf(object.getString("userPermission")), object.getString("userMail"), object.getString("userPassword"), object.getInt("userID"));
            ArrayList<BookSupplier> order = bookSupplierStringToList(object.getString("userOrder"));
            user.setOrder(order);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    BookSupplier jsonToBookSupplier(JSONObject object) {
        try {
            int bookID = object.getInt("bookSupplierBook");
            int supplierID = object.getInt("bookSupplierSupplier");
            Book book = this.getBookByBookID(bookID);
            Supplier supplier = this.getSupplierBySupplierID(supplierID);
            if (book == null || supplier == null)
                return null;
            BookSupplier bookSupplier = new BookSupplier(supplier, book, (float) object.getDouble("bookSupplierPrice"), object.getInt("bookSupplierAmount"));
            return bookSupplier;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    Order jsonToOrder(JSONObject object)
    {
        try
        {
            int orderID = object.getInt("order_id");
            Customer customer = this.getCustomerByCustomerID(object.getInt("customer_id"));
            ArrayList<BooksForOrder> booksForOrders = this.booksForOrderStringToList(object.getString("books_for_order"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(object.getString("date"));
            float price = (float) object.getDouble("total_price");
            Order order = new Order(customer,booksForOrders,date,price,true);
            return order;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    String bookSupplierListToString(ArrayList<BookSupplier> bookSuppliers) {
        try {
            JSONArray jsonArray = new JSONArray();
            for (BookSupplier bookSupplier : bookSuppliers) {
                JSONObject object = new JSONObject();
                object.put("bookID", bookSupplier.getBook().getBookID());
                object.put("supplierID", bookSupplier.getSupplier().getSupplierID());
                jsonArray.put(object);
            }
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("bsa", jsonArray);
            return jsonObject.toString();
        } catch (JSONException e) {
            return "";
        }
    }

    String BooksForOrderListToString(ArrayList<BooksForOrder> booksForOrders){
        try
        {
            JSONArray jsonArray = new JSONArray();
            for (BooksForOrder booksForOrder : booksForOrders)
            {
                JSONObject object = new JSONObject();
                object.put("bookID", booksForOrder.getBookSupplier().getBook().getBookID());
                object.put("supplierID", booksForOrder.getBookSupplier().getSupplier().getSupplierID());
                object.put("amount", booksForOrder.getSumOfBooks());
                jsonArray.put(object);
            }
            JSONObject object = new JSONObject();
            object.put("osa", jsonArray);
            return object.toString();
        }
        catch (JSONException e){
            return "";
        }
    }

    ArrayList<BookSupplier> bookSupplierStringToList(String bookSuppliers) {
        if (bookSuppliers == null || bookSuppliers.equals(""))
            return null;
        ArrayList<BookSupplier> bookSupplierArrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(bookSuppliers);
            JSONArray jsonArray = jsonObject.optJSONArray("bsa");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                int bookID = object.getInt("bookID");
                int supplierID = object.getInt("supplierID");
                try {
                    BookSupplier bookSupplier = this.getBookSupplierBySupplierIDAndByBookID(supplierID, bookID);
                    if (bookSupplier != null)
                        bookSupplierArrayList.add(bookSupplier);
                } catch (Exception e) {

                }
            }
            return bookSupplierArrayList;
        } catch (JSONException e) {
            return null;
        }
    }

    ArrayList<BooksForOrder> booksForOrderStringToList(String booksForOrder)
    {
        if (booksForOrder == null || booksForOrder.equals(""))
            return null;
        ArrayList<BooksForOrder> booksForOrderArrayList = new ArrayList<>();
        try
        {
            JSONObject jsonObject = new JSONObject(booksForOrder);
            JSONArray jsonArray = jsonObject.optJSONArray("osa");
            for (int i = 0; i<jsonArray.length(); i++)
            {
                JSONObject object = jsonArray.getJSONObject(i);
                int amount = object.getInt("amount");
                int supplierID = object.getInt("supplierID");
                int bookID = object.getInt("bookID");
                try
                {
                    BookSupplier bookSupplier = this.getBookSupplierBySupplierIDAndByBookID(supplierID, bookID);
                    if (bookSupplier != null)
                    {
                        BooksForOrder booksForOrder1 = new BooksForOrder(bookSupplier, amount, true);
                        booksForOrderArrayList.add(booksForOrder1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return  booksForOrderArrayList;
        }
        catch (JSONException e) {
            return null;
        }
    }
}
