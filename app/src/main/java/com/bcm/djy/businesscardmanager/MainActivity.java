package com.bcm.djy.businesscardmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.bcm.djy.databaseHelper.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private MyHelper myHelper = null;
    private SQLiteDatabase database = null;
    private SimpleAdapter simAdapt;
    private ListView listView;
    private List<Map<String, String>> data = new ArrayList<Map<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listChange();
    }

    @Override
    public void onResume(){
        super.onResume();
        listChange();
    }

    //When you click the Index button, open the IndexActivity and close the MainActivity
    public void btn_index(View view) {
        startActivity(new Intent(MainActivity.this,IndexActivity.class));
        finish();
    }

    //When you click the Manage button, open the ManageActivity and close the MainActivity
    public void btn_manage(View view) {
        startActivity(new Intent(MainActivity.this,ManageActivity.class));
        finish();
    }

    //When you click the Search button, open the SearchActivity and pause the MainActivity
    public void btn_search(View view) {
        startActivity(new Intent(MainActivity.this,SearchActivity.class));
    }

    //Refresh the ListView of the business card
    public void listChange(){
        //Clear the ArrayList and connect the database
        data = new ArrayList<Map<String, String>>();
        myHelper = new MyHelper(this);
        database = myHelper.getWritableDatabase();

        //Get all the business card information from the database
        Cursor cursor = database.query(DatabaseStatic.TABLE_NAME, null, null, null, null, null, DatabaseStatic.LAST_TIME+" DESC");
        if(cursor.moveToFirst())
        {
            for(; !cursor.isAfterLast(); cursor.moveToNext())
            {
                Map<String, String> item = new HashMap<String, String>();
                item.put("com", cursor.getString(cursor.getColumnIndex(DatabaseStatic.COM)));
                item.put("name", cursor.getString(cursor.getColumnIndex(DatabaseStatic.NAME)));
                item.put("tel", cursor.getString(cursor.getColumnIndex(DatabaseStatic.TEL)));
                item.put("email", cursor.getString(cursor.getColumnIndex(DatabaseStatic.EMAIL)));
                item.put("id",cursor.getString(cursor.getColumnIndex(DatabaseStatic.ID)));
                data.add(item);
            }
        }
        cursor.close();

        //If there are not thing in the database, show it to user
        if(data.isEmpty())
            Toast.makeText(this, "The Database is empty!", Toast.LENGTH_SHORT).show();

        //Get the ListView, create new Adapter and set the Adapter
        listView = (ListView) findViewById(R.id.listView);
        simAdapt = new SimpleAdapter(
                this,
                data,
                R.layout.item_layout,
                new String[] { "com", "name", "tel", "email" },
                new int[] { R.id.item_company, R.id.item_name, R.id.item_tel, R.id.item_email });
        listView.setAdapter(simAdapt);

        //Set the OnItemClickListener, when you click one of the item, open the DetailActivity of the item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HashMap<String, String> map = (HashMap<String, String>) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                intent.putExtra("id",map.get("id"));
                intent.putExtra("com",map.get("com"));
                intent.putExtra("name",map.get("name"));
                intent.putExtra("tel",map.get("tel"));
                intent.putExtra("email",map.get("email"));
                startActivity(intent);
            }
        });
    }

}
