package com.example.vinayg.resumebuilder.database;

/**
 * Created by vinay.g.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyDataHelper extends SQLiteOpenHelper {
    // Logcat tag
    private static final String TAG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "profile";

    // Table Names
    public static final String TABLE_USER = "users";
    public static final String TABLE_SUMMARY = "summaries";
    public static final String TABLE_EDUCATION = "education";
    public static final String TABLE_PROJECTS = "projects";
    public static final String TABLE_INTERESTS = "interests";

    // Common column names
    public static final String KEY_ID = "id";


    // NOTES Table - column names
    public static final String KEY_EMAIL = "email";
    public static final String KEY_EXPERIENCE = "experience";
    public static final String KEY_UID = "uid";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_SUMMARY = "summary";
    public static final String KEY_INTEREST = "interest";
    public static final String KEY_EDUCATION = "education_key";
    public static final String KEY_INSTITUTE = "institute";
    public static final String KEY_STREAM = "stream";
    public static final String KEY_START = "start";
    public static final String KEY_STOP = "stop";
    public static final String KEY_SCORE = "score";
    public static final String KEY_PROJECT = "projectid";
    public static final String KEY_TYPEOFINSTITUTE = "institute_type";
    public static final String KEY_INTERESTID = "interest_id";

    // Table Create Statements
    // Todo table create statement
    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "(" + KEY_ID + " TEXT PRIMARY KEY," + KEY_EMAIL
            + " TEXT UNIQUE," + KEY_UID + " TEXT," + KEY_EXPERIENCE
            + " INTEGER" + ")";


    // Tag table create statement
    private static final String CREATE_TABLE_SUMMARY = "CREATE TABLE "
            + TABLE_SUMMARY + "(" + KEY_ID + " TEXT, "
            +KEY_SUMMARY + " TEXT, " +
            "FOREIGN KEY("+KEY_ID+") REFERENCES "+TABLE_USER+"("+KEY_ID+") )";



    private static final String CREATE_TABLE_EDUCATION = "CREATE TABLE "
            + TABLE_EDUCATION + "(" +KEY_EDUCATION+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ KEY_ID + " TEXT,"
            +KEY_TYPEOFINSTITUTE + " TEXT,"
            + KEY_INSTITUTE + " TEXT," + KEY_STREAM + " TEXT, "
            + KEY_START + " INTEGER, " + KEY_STOP + " INTEGER, "
            +KEY_SCORE+" REAL, "
            +"FOREIGN KEY("+KEY_ID+") REFERENCES "+TABLE_USER+"("+KEY_ID+") " +")";


    private static final String CREATE_TABLE_PROJECTS = "CREATE TABLE "
            + TABLE_PROJECTS + "(" +KEY_PROJECT+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ KEY_ID + " TEXT,"
            + KEY_TITLE + " TEXT," + KEY_DESCRIPTION + " TEXT, "
            +"FOREIGN KEY("+KEY_ID+") REFERENCES "+TABLE_USER+"("+KEY_ID+")" +")";


    private static final String CREATE_TABLE_INTERESTS = "CREATE TABLE "
            + TABLE_INTERESTS + "("  +KEY_INTERESTID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_ID + " TEXT,"
            + KEY_INTEREST + " TEXT,"
            +"FOREIGN KEY("+KEY_ID+") REFERENCES "+TABLE_USER+"("+KEY_ID+")" +")";

    public MyDataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_SUMMARY);
        db.execSQL(CREATE_TABLE_EDUCATION);
        db.execSQL(CREATE_TABLE_PROJECTS);
        db.execSQL(CREATE_TABLE_INTERESTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUMMARY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EDUCATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INTERESTS);

        // create new tables
        onCreate(db);
    }
}