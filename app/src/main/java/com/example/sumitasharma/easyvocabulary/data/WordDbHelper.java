package com.example.sumitasharma.easyvocabulary.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


class WordDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "wordsDb.db";
    private static final int VERSION = 1;

    WordDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + WordContract.WordsEntry.TABLE_NAME + " (" +
                WordContract.WordsEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                WordContract.WordsEntry.COLUMN_WORD + " TEXT NOT NULL, " +
                WordContract.WordsEntry.COLUMN_WORD_MEANING + " TEXT NOT NULL, " +
                WordContract.WordsEntry.COLUMN_WORD_LEVEL + " TEXT NOT NULL, " +
                WordContract.WordsEntry.COLUMN_LAST_UPDATED + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                WordContract.WordsEntry.COLUMN_WORD_PRACTICED + " BOOLEAN);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WordContract.WordsEntry.TABLE_NAME);
        onCreate(db);
    }

    public boolean isDbPopulated() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from words limit 5" ,null);
        return res.getCount() > 0;
    }
}
