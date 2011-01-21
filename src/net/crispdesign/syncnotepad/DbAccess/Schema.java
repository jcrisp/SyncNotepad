package net.crispdesign.syncnotepad.DbAccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Schema extends SQLiteOpenHelper {
    private final static String DB_NAME = "notes.db";
    private final static int DB_VERSION = 3;

    public static final String NOTES_TABLE = "notes";

    public Schema(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + NOTES_TABLE + " (" +
                "id             integer primary key autoincrement," +
                "note           text not null," +
                "lastModified   integer" +
                ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + NOTES_TABLE);
        onCreate(db);
    }
}
