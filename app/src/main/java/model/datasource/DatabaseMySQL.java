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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

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
 * Created by reuvenp on 5/22/2016.
 */
public class DatabaseMySQL implements Backend {

    @Override
    public int addBook(Book book) throws Exception {
        final Map<String, Object> params2 = new LinkedHashMap<>();
        final String[] ID = new String[1];
        params2.put("year",book.getYear());
        params2.put("author", book.getAuthor());
        params2.put("title", book.getTitle());
        params2.put("pages", book.getPages());
        params2.put("category", book.getCategory().toString());
        try
        {
            new AsyncTask<Void, Void, Void>(){
                @Override
                protected Void doInBackground(Void... params) {
                    try
                    {
                        ID[0] = POST("http://plevinsk.vlab.jct.ac.il/addBook.php",params2);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    return  null;
                }
            }.execute().get();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        int id = Integer.parseInt(ID[0].substring(0,ID[0].length()-1));
        if (id == 0)
            throw new Exception("Error in add book");
        return id;
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
    public void updateUser(User user) throws Exception {

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
        final ArrayList<Book> bookArrayList = new ArrayList<>();
        try{
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try{
                        JSONArray books = new JSONObject(GET("http://plevinsk.vlab.jct.ac.il/getBookList.php")).getJSONArray("books");
                        for (int i = 0; i<books.length();i++){
                            Book book = jsonToBook(books.getJSONObject(i));
                            if (book != null)
                                bookArrayList.add(book);
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPreExecute() {
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                }
            }.execute().get();
        }
        catch (Exception e){
            e.printStackTrace();
        }
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

    private static String GET(String url) throws Exception {
        URL obj = new URL(url);
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
            // print result
            return response.toString();
        }
        else {
            return "";
        }
    }

    private static String POST(String url, Map<String,Object> params) throws IOException {
        //Convert Map<String,Object> into key=value&key=value pairs.
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0)
                postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }

        URL obj = new URL(url);
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
            return response.toString();
        }
        else
            return "";
    }

    Book jsonToBook(JSONObject object)
    {
        try {
            Book book = new Book(object.getString("title"), object.getInt("year"),object.getString("author"), object.getInt("pages"), Category.valueOf(object.getString("category")));
            book.setBookID(object.getInt("bookID"));
            return book;
        } catch (JSONException e) {
            return null;
        }
    }
}
