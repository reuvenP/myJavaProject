package model.backend;

import android.content.Context;

/**
 * Created by shmuel on 29/03/2016.
 */
public class BackendFactory {

    /*1)*/ static Backend instance = null;

    /*2)*/
    public static String mode = "sql";

    /*3)*/
    public final static Backend getInstance(Context context) {
    /*3.1)*/
        if (mode == "lists") {
            if (instance == null) instance = new model.datasource.DatabaseList();
            return instance;
        }

        /*3.2)*/
        if (mode == "sql") {
            if (instance == null) instance = new model.datasource.DatabaseSQLite(context);
            return instance;
        }

        /*3.3)*/
        if (mode == "service") {
            if (instance == null) instance = new model.datasource.DatabaseMySQL();
            return instance;
        }
        else {
            return null;
        }
    }
}
