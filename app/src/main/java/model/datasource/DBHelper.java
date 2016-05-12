package model.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by reuvenp on 5/9/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    //entities names
    public static final String DATABASE_NAME = "ProjectDB";
    public static final String BOOK_TABLE_NAME = "books";
    public static final String USER_TABLE_NAME = "users";
    public static final String BOOK_SUPPLIER_RELATION_NAME = "bookSuppliers";
    public static final String BOOKS_FOR_ORDER_TABLE_NAME = "booksForOrder";
    public static final String ORDER_TABLE_NAME = "orders";

    public static final String BOOK_ID_COLUMN = "bookID";
    public static final String BOOK_TITLE_COLUMN = "bookTitle";
    public static final String BOOK_AUTHOR_COLUMN = "bookAuthor";
    public static final String BOOK_YEAR_COLUMN = "bookYear";
    public static final String BOOK_PAGES_COLUMN = "bookPages";
    public static final String BOOK_CATEGORY_COLUMN = "bookCategory";

    public static final String USER_ID_COLUMN = "userID";
    public static final String USER_PERMISSION_COLUMN = "userPermission";
    public static final String USER_NAME_COLUMN = "userName";
    public static final String USER_BIRTHDAY_COLUMN = "userBirthday";
    public static final String USER_GENDER_COLUMN = "userGender";
    public static final String USER_ADDRESS_COLUMN = "userAddress";
    public static final String USER_MAIL_COLUMN = "userMail";
    public static final String USER_PASSWORD_COLUMN = "userPassword";
    public static final String USER_ORDER_COLUMN = "userOrder";

    public static final String BOOK_SUPPLIER_SUPPLIER_COLUMN = "bookSupplierSupplier";
    public static final String BOOK_SUPPLIER_BOOK_COLUMN = "bookSupplierBook";
    public static final String BOOK_SUPPLIER_PRICE_COLUMN = "bookSupplierPrice";
    public static final String BOOK_SUPPLIER_AMOUNT_COLUMN = "bookSupplierAmount";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + BOOK_TABLE_NAME + "(" + BOOK_ID_COLUMN +
        " integer primary key autoincrement, " + BOOK_TITLE_COLUMN +
        " text not null, " + BOOK_AUTHOR_COLUMN + " text not null, " +
        BOOK_YEAR_COLUMN + " integer not null, " + BOOK_PAGES_COLUMN + " integer not null, " +
        BOOK_CATEGORY_COLUMN + " text not null);");
        db.execSQL("create table " + USER_TABLE_NAME + "(" + USER_ID_COLUMN +
                " integer primary key autoincrement, " + USER_PERMISSION_COLUMN + " text not null, " +
        USER_NAME_COLUMN + " text not null, " + USER_BIRTHDAY_COLUMN  + " integer not null, " +
        USER_GENDER_COLUMN + " text not null, " + USER_ADDRESS_COLUMN + " text not null, " +
        USER_MAIL_COLUMN + " text, " + USER_PASSWORD_COLUMN + " text, " +
        USER_ORDER_COLUMN + "json);");
        db.execSQL("create table " + BOOK_SUPPLIER_RELATION_NAME + "(" + BOOK_SUPPLIER_SUPPLIER_COLUMN +
        " integer not null, " + BOOK_SUPPLIER_BOOK_COLUMN + " integer not null, " + BOOK_SUPPLIER_PRICE_COLUMN +
        " real not null, " + BOOK_SUPPLIER_AMOUNT_COLUMN + " integer not null, FOREIGN KEY(" +
        BOOK_SUPPLIER_SUPPLIER_COLUMN + ") REFERENCES " + USER_TABLE_NAME + " (" + USER_ID_COLUMN + "), FOREIGN KEY(" +
        BOOK_SUPPLIER_BOOK_COLUMN + ") REFERENCES " + BOOK_TABLE_NAME + " (" + BOOK_ID_COLUMN + "));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
