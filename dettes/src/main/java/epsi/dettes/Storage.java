package epsi.dettes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Etienne on 21/01/15.
 */
public class Storage extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "FeedReader.db";

    private static final String SQL_UP_0 =
            "CREATE TABLE dette (dette_id INTEGER PRIMARY KEY, title TEXT, name TEXT)";

    private static final String SQL_DOWN_0 = "DROP TABLE IF EXISTS dette";

    private static final String SQL_UP_1 =
            "ALTER Table dette ADD date TEXT";

    private static final String SQL_DOWN_1 = "BEGIN TRANSACTION;" +
            "CREATE TEMPORARY TABLE dette_backup(dette_id,title,name);\n" +
            "INSERT INTO dette_backup SELECT dette_id,title,name FROM dette;\n" +
            "DROP TABLE dette;\n" +
            SQL_UP_0 +
            "INSERT INTO todo SELECT dette_id,title,checked FROM dette_backup;\n" +
            "DROP TABLE dette_backup;\n" +
            "COMMIT;";

    private static final String SQL_UP_2 =
            "ALTER Table dette ADD date_done TEXT";

    private static final String SQL_DOWN_2 = "BEGIN TRANSACTION;" +
            "CREATE TEMPORARY TABLE todo_backup(dette_id,title,name,date);\n" +
            "INSERT INTO dette_backup SELECT dette_id,title,name,date FROM dette;\n" +
            "DROP TABLE dette;\n" +
            SQL_UP_0 +
            "INSERT INTO dette SELECT dette_id,title,name,date FROM dette_backup;\n" +
            "DROP TABLE dette_backup;\n" +
            "COMMIT;";

    public Storage(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_UP_0);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        int currentVersion = oldVersion;
        if(currentVersion == 0 && newVersion > currentVersion) {
            db.execSQL(SQL_UP_1);
            currentVersion = 1;
        }
        if(currentVersion == 1 && newVersion > currentVersion) {
            db.execSQL(SQL_UP_2);
            currentVersion = 2;
        }
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        int currentVersion = oldVersion;
        if(currentVersion == 2 && newVersion < currentVersion) {
            db.execSQL(SQL_DOWN_2);
            currentVersion = 1;
        }
        if(currentVersion == 1 && newVersion < currentVersion) {
            db.execSQL(SQL_DOWN_1);
            currentVersion = 0;
        }
    }

    public void addDette(String title, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("name", name);
        db.insert("dette", null, values);
        db.close();
    }

    public Dette getDette(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("dette",                  //table
                new String[] { "dette_id", "title", "name" }, // columns
                "dette_id" + "=?",                               // WHERE clause
                new String[] { String.valueOf(id) },            // WHERE arguments
                null,                                           // GROUP BY
                null,                                           // HAVING
                null,                                           // ORDER BY
                null);                                          // LIMIT
        if (cursor != null)
            cursor.moveToFirst();

        Dette dette = new Dette(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2));
        return dette;
    }

    public List<Dette> getAll() {
        List<Dette> detteList = new ArrayList<Dette>();
        // Select All Query
        String selectQuery = "SELECT  * FROM dette";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Dette dette = new Dette(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2));

                detteList.add(dette);
            } while (cursor.moveToNext());
        }

        return detteList;
    }

    public int count() {
        String countQuery = "SELECT  * FROM dette";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public int updateDette(Dette dette) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", dette.title);
        values.put("name", dette.name);

        return db.update("Dette",
                values,
                "dette_id" + " = ?",
                new String[] { String.valueOf(dette.dette_id) });
    }

    public void deleteDette(Dette dette) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("dette", "dette_id" + " = ?",
                new String[] { String.valueOf(dette.dette_id) });
        db.close();
    }
}
