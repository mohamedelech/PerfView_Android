package com.example.mohamedelech.perfview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mohamed.elech on 19.04.2017.
 */

public class SQLiteDatabaseHandlerAgenda extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "AgendaDB";
    private static final String TABLE_NAME = "Agenda";
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String[] COLUMNS = { KEY_ID, KEY_DATE};


    public SQLiteDatabaseHandlerAgenda(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATION_TABLE = "CREATE TABLE Agenda ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "date TEXT )";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void deleteOne(Agenda agenda) {
        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?",
                new String[] { String.valueOf(agenda.getId()) });
        db.close();

    }

    public Agenda getAgenda(int id) {
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

        Agenda agenda = new Agenda();
        agenda.setId(Integer.parseInt(cursor.getString(0)));
        agenda.setDate(Long.valueOf(cursor.getString(1)));

         return agenda;
    }

    public Agenda getAgenda(long date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, // a. table
                COLUMNS, // b. column names
                " date = ?", // c. selections
                new String[] { String.valueOf(date) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor != null)
            cursor.moveToFirst();

        Agenda agenda = new Agenda();
        agenda.setId(Integer.parseInt(cursor.getString(0)));
        agenda.setDate(Long.valueOf(cursor.getString(1)));

        return agenda;
    }

    public List<Agenda> allagendas() {

        List<Agenda> agendas = new LinkedList<Agenda>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Agenda agenda = null;

        if (cursor.moveToFirst()) {
            do {
                agenda = new Agenda();
                agenda.setId(Integer.parseInt(cursor.getString(0)));
                agenda.setDate(Long.valueOf(cursor.getString(1)));
                agendas.add(agenda);
            } while (cursor.moveToNext());
        }

        return agendas;
    }

    public List<Agenda> allagendasAfter() {

        List<Agenda> agendas = new LinkedList<Agenda>();
        String query = "SELECT  * FROM " + TABLE_NAME +" WHERE date > " + System.currentTimeMillis()+" ORDER BY date ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Agenda agenda = null;

        if (cursor.moveToFirst()) {
            do {
                agenda = new Agenda();
                agenda.setId(Integer.parseInt(cursor.getString(0)));
                agenda.setDate(Long.valueOf(cursor.getString(1)));
                agendas.add(agenda);
            } while (cursor.moveToNext());
        }

        return agendas;
    }

    public void addAgenda(Agenda agenda) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(String.valueOf(KEY_DATE), agenda.getDate());
        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }
}
