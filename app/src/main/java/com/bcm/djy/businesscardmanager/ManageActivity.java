package com.bcm.djy.businesscardmanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.bcm.djy.databaseHelper.DatabaseStatic;
import com.bcm.djy.databaseHelper.MyHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageActivity extends AppCompatActivity {

    private MyHelper myHelper = null;
    private SQLiteDatabase database = null;
    private SimpleAdapter simAdapt;
    private ListView listView;
    private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        listChange();
    }

    @Override
    public void onResume(){
        super.onResume();
        listChange();
    }

    //When you click the Index button, open the IndexActivity and close the ManageActivity
    public void btn_index(View view) {
        startActivity(new Intent(ManageActivity.this,IndexActivity.class));
        finish();
    }

    //When you click the Recently button, open the MainActivity and close the ManageActivity
    public void btn_recently(View view) {
        startActivity(new Intent(ManageActivity.this,MainActivity.class));
        finish();
    }

    //When you click the Search button, open the SearchActivity and pause the ManageActivity
    public void btn_search(View view) {
        startActivity(new Intent(ManageActivity.this,SearchActivity.class));
    }

    //When you click the Add button, open a DetailActivity for a new business card
    public void btn_add(View view) {
        startActivity(new Intent(ManageActivity.this,DetailActivity.class));
    }

    //When you click the Delete button, the item you selected will be deleted
    public void btn_delete(View view) {
        myHelper = new MyHelper(this);
        database = myHelper.getWritableDatabase();
        //Check all the item in the ArrayList. If the "state" of the item is "checked", it mean it is been selected and will be deleted
        for(int i=0;i<data.size();i++)
            if(data.get(i).get("state")=="checked")
                database.delete(DatabaseStatic.TABLE_NAME, DatabaseStatic.ID + "= ?",new String[]{data.get(i).get("id").toString()});
        listChange();
    }

    //Refresh the ListView of the business card
    public void listChange(){
        //Clear the ArrayList and connect the database
        data = new ArrayList<Map<String, Object>>();
        myHelper = new MyHelper(this);
        database = myHelper.getWritableDatabase();

        //Get all the business card information from the database
        Cursor cursor = database.query(DatabaseStatic.TABLE_NAME, null, null, null, null, null, DatabaseStatic.NAME);
        if(cursor.moveToFirst())
        {
            for(; !cursor.isAfterLast(); cursor.moveToNext())
            {
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("com", cursor.getString(cursor.getColumnIndex(DatabaseStatic.COM)));
                item.put("name", cursor.getString(cursor.getColumnIndex(DatabaseStatic.NAME)));
                item.put("tel", cursor.getString(cursor.getColumnIndex(DatabaseStatic.TEL)));
                item.put("email", cursor.getString(cursor.getColumnIndex(DatabaseStatic.EMAIL)));
                item.put("id",cursor.getString(cursor.getColumnIndex(DatabaseStatic.ID)));
                item.put("state","unchecked");
                item.put("img",R.drawable.unchecked);
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
                R.layout.item_select_layout,
                new String[] { "com", "name", "tel", "email", "img" },
                new int[] { R.id.item_company, R.id.item_name, R.id.item_tel, R.id.item_email, R.id.item_check });
        listView.setAdapter(simAdapt);

        //Set the OnItemClickListener, when you click one of the item, the "state" of the item will be changed
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> item = data.get(position);
                //Change "state"
                if(item.get("state")=="unchecked")
                {
                    item.put("img",R.drawable.checked);
                    item.put("state","checked");
                }
                else if(item.get("state")=="checked")
                {
                    item.put("img",R.drawable.unchecked);
                    item.put("state","unchecked");
                }
                //Update "state"
                data.remove(position);
                data.add(position, item);
                simAdapt.notifyDataSetChanged();
            }
        });
    }
}
