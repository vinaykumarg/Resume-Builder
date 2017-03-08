package com.example.vinayg.resumebuilder.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.example.vinayg.resumebuilder.database.MyDataHelper.KEY_EDUCATION;
import static com.example.vinayg.resumebuilder.database.MyDataHelper.KEY_INTERESTID;
import static com.example.vinayg.resumebuilder.database.MyDataHelper.KEY_PROJECT;

/**
 * Created by vinay.g.
 */

public class MyAppDb {
    private SQLiteDatabase database;
    private MyDataHelper dbHelper;
    private String[] userColumns = {MyDataHelper.KEY_ID,MyDataHelper.KEY_UID,MyDataHelper.KEY_EMAIL,MyDataHelper.KEY_EXPERIENCE};
    private String[] summaryColums = {MyDataHelper.KEY_ID,MyDataHelper.KEY_SUMMARY};
    private String[] educationColumns = {MyDataHelper.KEY_EDUCATION,MyDataHelper.KEY_ID,MyDataHelper.KEY_TYPEOFINSTITUTE,MyDataHelper.KEY_INSTITUTE,MyDataHelper.KEY_STREAM,
            MyDataHelper.KEY_START,MyDataHelper.KEY_STOP,MyDataHelper.KEY_SCORE};
    private String[] projectsColumns = {MyDataHelper.KEY_PROJECT,MyDataHelper.KEY_ID,MyDataHelper.KEY_TITLE,MyDataHelper.KEY_DESCRIPTION};
    private String[] interestColumns = {MyDataHelper.KEY_INTERESTID,MyDataHelper.KEY_ID,MyDataHelper.KEY_INTEREST};
    public MyAppDb(Context context){
        dbHelper = new MyDataHelper(context);
    }
    public void createUser(String id, String username, String email ) {
        ContentValues values = new ContentValues();
        values.put(MyDataHelper.KEY_ID,id);
        values.put(MyDataHelper.KEY_UID, username);
        values.put(MyDataHelper.KEY_EMAIL , email);
        database.insert(MyDataHelper.TABLE_USER, null,
                values);
    }
    public User getUser(String id) {
        String where = "SELECT * from "+MyDataHelper.TABLE_USER+" WHERE "+ MyDataHelper.KEY_ID+"=?";
        Cursor cursor = database.rawQuery(where,new String[]{id});
        User newUser = null;
        if(cursor.moveToFirst()) {
            newUser = cursorToUser(cursor);
        }
        cursor.close();
        return newUser;
    }
    private User cursorToUser(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getString(0));
        user.setEmail(cursor.getString(1));
        user.setUid(cursor.getString(2));
        user.setExperience(cursor.getInt(3));
        return user;
    }
    public void createSummary(String id, String Summary) {
        ContentValues values = new ContentValues();
        values.put(MyDataHelper.KEY_ID, id);
        values.put(MyDataHelper.KEY_SUMMARY, Summary);
        String selection = summaryColums[0] + " = ?";
        String[] selectionArgs = { id };
        if (ifSummeryExists(id)){
            int d = database.update(MyDataHelper.TABLE_SUMMARY, values,selection, selectionArgs);
        } else {
            database.insert(MyDataHelper.TABLE_SUMMARY, null,
                    values);
        }
    }

    public Summary getSummary(String id) {
        String whereClause = summaryColums[0]+" = ?";
        String[] whereArgs = new String[] {id};
        Cursor cursor = database.query(MyDataHelper.TABLE_SUMMARY,
                summaryColums, whereClause, whereArgs,
                null, null, null);
        Summary newSummary = null;
        if(cursor.moveToFirst()) {
            newSummary = cursorToSummary(cursor);
        }
        cursor.close();
        return newSummary;
    }
    private Summary cursorToSummary(Cursor cursor) {
        Summary summary = new Summary();
        summary.setId(cursor.getString(0));
        summary.setSummary(cursor.getString(1));
        return summary;
    }
    public void createEducation(String type,String id, String institute, String stream, String start, String stop, String score){
        ContentValues values = new ContentValues();
        values.put(MyDataHelper.KEY_ID, id);
        values.put(MyDataHelper.KEY_TYPEOFINSTITUTE,type);
        values.put(MyDataHelper.KEY_INSTITUTE, institute);
        values.put(MyDataHelper.KEY_STREAM, stream);
        values.put(MyDataHelper.KEY_START,start);
        values.put(MyDataHelper.KEY_STOP,stop);
        values.put(MyDataHelper.KEY_SCORE,score);
        database.insert(MyDataHelper.TABLE_EDUCATION, null,
                values);
    }
    public void createProjects(String id, String title, String description ){
        ContentValues values = new ContentValues();
        values.put(MyDataHelper.KEY_ID, id);
        values.put(MyDataHelper.KEY_TITLE,title);
        values.put(MyDataHelper.KEY_DESCRIPTION,description);
        database.insert(MyDataHelper.TABLE_PROJECTS, null,
                values);
    }


    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }
    public void createInterest(String id, String interest) {
        ContentValues values = new ContentValues();
        values.put(MyDataHelper.KEY_ID, id);
        values.put(MyDataHelper.KEY_INTEREST,interest);
        database.insert(MyDataHelper.TABLE_INTERESTS, null,
                values);
    }

    public boolean checkifexist(String id) {
        List<String> list = selectAllUsers();
        if (list.contains(id)) {
            return true;
        }
        return false;
    }
    public List<String> selectAll() {
        List<String> list = new ArrayList<String>();
        Cursor cursor =database.query(MyDataHelper.TABLE_SUMMARY, summaryColums,
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return list;
    }
    public List<String> selectAllUsers() {
        List<String> list = new ArrayList<String>();
        Cursor cursor =database.query(MyDataHelper.TABLE_USER, userColumns,
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1)+" "+cursor.getString(3));
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return list;
    }
    public List<Projects> gettAllProjects(String pid) {
        String selection = projectsColumns[1] + " = ?";
        String[] selectionArgs = { pid };
        List<Projects> list = new ArrayList<Projects>();
        Cursor cursor =database.query(MyDataHelper.TABLE_PROJECTS, projectsColumns,
                selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Projects projects = new Projects();
                projects.setPid(cursor.getInt(0));
                projects.setId(cursor.getString(1));
                projects.setTitle(cursor.getString(2));
                projects.setDescription(cursor.getString(3));
                list.add(projects);
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return list;
    }
    public List<Education> gettAllEducation(String pid) {
        String selection = educationColumns[1] + " = ?";
        String[] selectionArgs = { pid };
        List<Education> list = new ArrayList<Education>();
        Cursor cursor =database.query(MyDataHelper.TABLE_EDUCATION, educationColumns,
                selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Education education = new Education();
                education.setId(cursor.getLong(0));
                education.setUid(cursor.getString(1));
                education.setInstituteType(cursor.getString(2));
                education.setInstitute(cursor.getString(3));
                education.setStream(cursor.getString(4));
                education.setStartyear(cursor.getInt(5));
                education.setEndyear(cursor.getInt(6));
                education.setScore(cursor.getLong(7));
                list.add(education);
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return list;
    }

    public List<Interest> gettAllInterest(String pid) {
        String selection = interestColumns[1] + " = ?";
        String[] selectionArgs = { pid };
        List<Interest> list = new ArrayList<Interest>();
        Cursor cursor =database.query(MyDataHelper.TABLE_INTERESTS, interestColumns,
                selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Interest interest = new Interest();
                interest.setId(cursor.getString(0));
                interest.setUid(cursor.getString(1));
                interest.setInterest(cursor.getString(2));
                list.add(interest);
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return list;
    }

    public boolean ifSummeryExists(String id) {
        String whereClause = summaryColums[0]+" = ?";
        String[] whereArgs = new String[] {id};
        Cursor cursor =database.query(MyDataHelper.TABLE_SUMMARY, summaryColums,
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            if (id.equals(cursor.getString(0))){
                cursor.close();
                return true;
            }
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return false;
    }

    public void updateProject(int pid, String title, String description) {
        ContentValues values = new ContentValues();
        values.put(projectsColumns[2], title);
        values.put(projectsColumns[3],description);
        String selection = projectsColumns[0] + " = ?";
        String[] selectionArgs = { ""+pid };
        database.update(MyDataHelper.TABLE_PROJECTS, values,selection, selectionArgs);
    }

    public void deleteProject(int pid) {
        // Define 'where' part of query.
        String selection = KEY_PROJECT + " = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { ""+pid };
        // Issue SQL statement.
        database.delete(MyDataHelper.TABLE_PROJECTS, selection, selectionArgs);
    }

    public void deleteEducation(long id) {
        // Define 'where' part of query.
        String selection = KEY_EDUCATION + " = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { ""+id };
        // Issue SQL statement.
        database.delete(MyDataHelper.TABLE_EDUCATION, selection, selectionArgs);
    }

    public void updateInterest(String interestid, String uid, String interest) {
        ContentValues values = new ContentValues();
        values.put(interestColumns[1], uid);
        values.put(projectsColumns[2],interest);
        String selection = interestColumns[0] + " = ?";
        String[] selectionArgs = { interestid };
        database.update(MyDataHelper.TABLE_INTERESTS, values,selection, selectionArgs);
    }


    public void deleteInterest(String id) {
        // Define 'where' part of query.
        String selection = KEY_INTERESTID + " = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { id };
        // Issue SQL statement.
        database.delete(MyDataHelper.TABLE_INTERESTS, selection, selectionArgs);
    }

    public void editEducation(long educationid, String type, String uid, String institute, String stream, String startyear, String endyear, String score) {
        String selection = educationColumns[0] + " = ?";
        String[] selectionArgs = { ""+educationid };
        ContentValues values = new ContentValues();
        values.put(MyDataHelper.KEY_ID, uid);
        values.put(MyDataHelper.KEY_TYPEOFINSTITUTE,type);
        values.put(MyDataHelper.KEY_INSTITUTE, institute);
        values.put(MyDataHelper.KEY_STREAM, stream);
        values.put(MyDataHelper.KEY_START,startyear);
        values.put(MyDataHelper.KEY_STOP,endyear);
        values.put(MyDataHelper.KEY_SCORE,score);
        database.update(MyDataHelper.TABLE_EDUCATION, values,selection, selectionArgs);
    }

    public void updateExperience(String id, String Experinece) {
        ContentValues values = new ContentValues();
        values.put(userColumns[3], Experinece);
        String selection = userColumns[0] + " = ?";
        String[] selectionArgs = { id };
        database.update(MyDataHelper.TABLE_USER, values,selection, selectionArgs);
    }

    public String getExperience(String uid) {
        String selection = userColumns[0] + " = ?";
        String[] selectionArgs = { uid };
        String experience = "";
        Cursor cursor = database.query(MyDataHelper.TABLE_USER, userColumns,
                selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            experience = cursor.getInt(3)+"";
            return experience;
        }
        return null;
    }
}
