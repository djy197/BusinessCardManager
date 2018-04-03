package com.bcm.djy.databaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by 10856 on 2018/4/1.
 */

public class MyHelper extends SQLiteOpenHelper {
    public static String CREATE_TABLE = "create table "+DatabaseStatic.TABLE_NAME+"("+
            DatabaseStatic.ID+" Integer primary key autoincrement, "+
            DatabaseStatic.COM+" varchar(20) not null, "+
            DatabaseStatic.NAME+" varchar(20) not null, "+
            DatabaseStatic.TEL+" varchar(20) not null, "+
            DatabaseStatic.EMAIL+" varchar(20) not null, "+
            DatabaseStatic.LAST_TIME+" varchar(20) not null)";
    private Context myContext = null;

    public MyHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context,DatabaseStatic.DATABASE_NAME,null,DatabaseStatic.DATABASE_VERSION);
    }

    public MyHelper(Context context){
        super(context, DatabaseStatic.DATABASE_NAME, null, DatabaseStatic.DATABASE_VERSION);
        myContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        Log.i("UseDatabase","创建数据库");
        Toast.makeText(myContext,"创建数据库",Toast.LENGTH_SHORT).show();
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0,int arg1,int arg2){

    }

}
