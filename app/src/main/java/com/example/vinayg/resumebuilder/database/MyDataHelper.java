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

    // User Table - column names
    public static final String KEY_ID = "id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_EXPERIENCE = "experience";
    public static final String COLUMN_KEY_UID = "uid";
    // Summary Table - column names
    public static final String COLUMN_SUMMARY = "summary";
    // Interest Table - column names
    public static final String COLUMN_INTEREST = "interest";
    public static final String COLUMN_KEY_INTERESTID = "interest_id";
    // Education Table - column names
    public static final String COLUMN_KEY_EDUCATION = "education_key";
    public static final String COLUMN_TYPEOFINSTITUTE = "institute_type";
    public static final String COLUMN_INSTITUTE = "institute";
    public static final String COLUMN_STREAM = "stream";
    public static final String COLUMN_START = "start";
    public static final String COLUMN_STOP = "stop";
    public static final String COLUMN_SCORE = "score";
    // Project Table - column names
    public static final String COLUMN_KEY_PROJECT = "projectid";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";



    // Table Create Statements
    // Todo table create statement
    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "(" + KEY_ID + " TEXT PRIMARY KEY," + COLUMN_EMAIL
            + " TEXT UNIQUE," + COLUMN_KEY_UID + " TEXT," + COLUMN_EXPERIENCE
            + " INTEGER" + ")";


    // Tag table create statement
    private static final String CREATE_TABLE_SUMMARY = "CREATE TABLE "
            + TABLE_SUMMARY + "(" + KEY_ID + " TEXT, "
            + COLUMN_SUMMARY + " TEXT, " +
            "FOREIGN KEY("+KEY_ID+") REFERENCES "+TABLE_USER+"("+KEY_ID+") )";



    private static final String CREATE_TABLE_EDUCATION = "CREATE TABLE "
            + TABLE_EDUCATION + "(" + COLUMN_KEY_EDUCATION +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ KEY_ID + " TEXT,"
            + COLUMN_TYPEOFINSTITUTE + " TEXT,"
            + COLUMN_INSTITUTE + " TEXT," + COLUMN_STREAM + " TEXT, "
            + COLUMN_START + " INTEGER, " + COLUMN_STOP + " INTEGER, "
            + COLUMN_SCORE +" REAL, "
            +"FOREIGN KEY("+KEY_ID+") REFERENCES "+TABLE_USER+"("+KEY_ID+") " +")";


    private static final String CREATE_TABLE_PROJECTS = "CREATE TABLE "
            + TABLE_PROJECTS + "(" + COLUMN_KEY_PROJECT +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ KEY_ID + " TEXT,"
            + COLUMN_TITLE + " TEXT," + COLUMN_DESCRIPTION + " TEXT, "
            +"FOREIGN KEY("+KEY_ID+") REFERENCES "+TABLE_USER+"("+KEY_ID+")" +")";


    private static final String CREATE_TABLE_INTERESTS = "CREATE TABLE "
            + TABLE_INTERESTS + "("  +COLUMN_KEY_INTERESTID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_ID + " TEXT,"
            + COLUMN_INTEREST + " TEXT,"
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