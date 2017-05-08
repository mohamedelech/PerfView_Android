package com.example.mohamedelech.perfview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mohamed.elech on 19.04.2017.
 */

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PerfDB";
    private static final String TABLE_NAME = "Performances";
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_MOVEMENT= "movement";
    private static final String KEY_REPS = "reps";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_PHOTO = "photo";
    private static final String KEY_ADDRESS = "address";
    private static final String[] COLUMNS = { KEY_ID, KEY_DATE, KEY_MOVEMENT,KEY_REPS,KEY_WEIGHT,
            KEY_PHOTO,KEY_ADDRESS };


    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATION_TABLE = "CREATE TABLE Performances ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "date TEXT, "
                + "movement TEXT, " + "reps TEXT, " + "weight TEXT, " + "photo TEXT, " + "address TEXT )";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void deleteOne(Performance perf) {
        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?",
                new String[] { String.valueOf(perf.getId()) });
        db.close();

    }

    public Performance getPerf(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, // a. table
                COLUMNS, // b. column names
                " id = ?", // c. selections
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor != null)
            cursor.moveToFirst();

        Performance perf = new Performance();
        perf.setId(Integer.parseInt(cursor.getString(0)));
        perf.setDate(cursor.getString(1));
        perf.setMovement(cursor.getString(2));
        perf.setReps(cursor.getString(3));
        perf.setWeight(cursor.getString(4));
        perf.setPhoto(cursor.getString(5));
        perf.setAdresse(cursor.getString(6));


        return perf;
    }

    public List<Performance> allPerf() {

        List<Performance> perfs = new LinkedList<Performance>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Performance perf = null;

        if (cursor.moveToFirst()) {
            do {
                perf = new Performance();
                perf.setId(Integer.parseInt(cursor.getString(0)));
                perf.setDate(cursor.getString(1));
                perf.setMovement(cursor.getString(2));
                perf.setReps(cursor.getString(3));
                perf.setWeight(cursor.getString(4));
                perf.setPhoto(cursor.getString(5));
                perf.setAdresse(cursor.getString(6));
                perfs.add(perf);
            } while (cursor.moveToNext());
        }

        return perfs;
    }

    public void addPerf(Performance perf) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, perf.getDate());
        values.put(KEY_MOVEMENT, perf.getMovement());
        values.put(KEY_REPS, perf.getReps());
        values.put(KEY_WEIGHT, perf.getWeight());
        values.put(KEY_PHOTO, perf.getPhoto());
        values.put(KEY_ADDRESS, perf.getAdresse());
        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    public int updatePerf(Performance perf) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, perf.getDate());
        values.put(KEY_MOVEMENT, perf.getMovement());
        values.put(KEY_REPS, perf.getReps());
        values.put(KEY_WEIGHT, perf.getWeight());
        values.put(KEY_PHOTO, perf.getPhoto());
        values.put(KEY_ADDRESS, perf.getAdresse());

        int i = db.update(TABLE_NAME, // table
                values, // column/value
                "id = ?", // selections
                new String[] { String.valueOf(perf.getId()) });

        db.close();

        return i;
    }

    public int updatePerf(Performance perf,String date,String movement,
                          String reps,String weight,String photo,String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, date);
        values.put(KEY_MOVEMENT, movement);
        values.put(KEY_REPS, reps);
        values.put(KEY_WEIGHT, weight);
        values.put(KEY_PHOTO, photo);
        values.put(KEY_ADDRESS, address);

        int i = db.update(TABLE_NAME, // table
                values, // column/value
                "id = ?", // selections
                new String[] { String.valueOf(perf.getId()) });

        db.close();

        return i;
    }
}
