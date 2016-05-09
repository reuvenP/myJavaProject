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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
