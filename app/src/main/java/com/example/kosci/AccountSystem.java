package com.example.kosci;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class AccountSystem extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "accounts.db";
    private static final int DATABASE_VERSION = 3;
    private static final String TABLE_ACCOUNTS = "accounts";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    private static final String TABLE_RESULTS = "results";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NICKNAME = "nickname";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_POINTS = "points";

    public AccountSystem(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ACCOUNTS_TABLE = "CREATE TABLE " + TABLE_ACCOUNTS + "("
                + COLUMN_EMAIL + " TEXT PRIMARY KEY,"
                + COLUMN_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_ACCOUNTS_TABLE);

        String CREATE_RESULTS_TABLE = "CREATE TABLE " + TABLE_RESULTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NICKNAME + " TEXT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_POINTS + " INTEGER" + ")";
        db.execSQL(CREATE_RESULTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULTS);
        onCreate(db);
    }

    public void addAccount(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        db.insert(TABLE_ACCOUNTS, null, values);
        db.close();
    }

    public boolean checkAccount(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ACCOUNTS, new String[]{COLUMN_EMAIL, COLUMN_PASSWORD},
                COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{email, password}, null, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    public boolean checkAccount(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ACCOUNTS, new String[]{COLUMN_EMAIL},
                COLUMN_EMAIL + "=?",
                new String[]{email}, null, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    public void addResult(String nickname, String date, int points) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NICKNAME, nickname);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_POINTS, points);
        db.insert(TABLE_RESULTS, null, values);
        db.close();
    }

    public List<ResultItem> getAllResults() {
        List<ResultItem> results = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_RESULTS, null);
        if (cursor.moveToFirst()) {
            do {
                int nicknameIndex = cursor.getColumnIndex(COLUMN_NICKNAME);
                int dateIndex = cursor.getColumnIndex(COLUMN_DATE);
                int pointsIndex = cursor.getColumnIndex(COLUMN_POINTS);

                if (nicknameIndex != -1 && dateIndex != -1 && pointsIndex != -1) {
                    String nickname = cursor.getString(nicknameIndex);
                    String date = cursor.getString(dateIndex);
                    int points = cursor.getInt(pointsIndex);
                    results.add(new ResultItem(nickname, date, String.valueOf(points)));
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return results;
    }
}