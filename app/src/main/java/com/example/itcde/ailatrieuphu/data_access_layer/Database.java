package com.example.itcde.ailatrieuphu.data_access_layer;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by itcde on 25/07/2017.
 */

public class Database extends SQLiteOpenHelper {

    private static String DB_PATH = "";
    private static final String DB_NAME = "databases.sqlite";

    public Context context;

    private SQLiteDatabase sqLiteDatabase;

    public Database(Context context) throws IOException {
        super(context, DB_NAME, null, 1);
        this.context = context;
        //getPathDB();
        //createDataBase();
    }

    public void getPathDB(){
        if(android.os.Build.VERSION.SDK_INT >= 17){
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }
        else
        {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
            Log.d("db", DB_PATH);
        }
    }

    public void createDataBase(Intent intent) throws IOException
    {
        //If the database does not exist, copy it from the assets.

        boolean mDataBaseExist = checkDataBase();
        if(!mDataBaseExist)
        {
            this.getReadableDatabase();
            this.close();
            Log.d("qqq", "here");
            try
            {
                //Copy the database from assests
                copyDataBase(intent);
            }
            catch (IOException mIOException)
            {
                Log.e("error", "database");
                throw new Error("ErrorCopyingDataBase");
            }
        }
        else{
            context.startActivity(intent);
        }
    }

    //Copy the database from assets
    private void copyDataBase(Intent intent) throws IOException
    {
        InputStream mInput = context.getAssets().open("databases.sqlite");
        String outFileName = DB_PATH + DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer))>0)
        {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();

        context.startActivity(intent);
    }

    //Kiểm tra database có tồn tại trong cở sở dữ liệu chưa
    public boolean checkDataBase(){
        File databaseFile = new File(DB_PATH + DB_NAME);
        return databaseFile.exists();
    }


    public void openDatabase(){
        String path = DB_PATH + DB_NAME;
        sqLiteDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase(){
        if(sqLiteDatabase != null){
            sqLiteDatabase.close();
        }
    }

    //Truy vấn trả về giá trị
    public Cursor getData(String sql){
        sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery(sql, null);
    }

    //Truy vấn không trả về giá trị
    public void queryData(String sql){
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(sql);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
