package com.bcm.djy.businesscardmanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bcm.djy.databaseHelper.DatabaseStatic;
import com.bcm.djy.databaseHelper.MyHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    private MyHelper myHelper = null;
    private SQLiteDatabase database = null;
    private SimpleAdapter simAdapt;
    private EditText searchText;
    private String searchCondition;
    private ListView listView;
    private TextView searchTag;
    private List<Map<String, String>> data = new ArrayList<Map<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchTag = (TextView)findViewById(R.id.searchTag);
        searchText = (EditText) findViewById(R.id.searchText);
        //If there are changes in the EditText, change the searchCondition
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchCondition="%"+searchText.getText().toString()+"%";
                listChange();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchCondition="%"+searchText.getText().toString()+"%";
                listChange();
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        listChange();
    }

    //When you click the Back button, close the SearchActivity
    public void btn_back(View view) {
        finish();
    }

    //Refresh the ListView of the business card
    public void listChange(){
        //Clear the ArrayList and connect the database
        data = new ArrayList<Map<String, String>>();
        myHelper = new MyHelper(this);
        database = myHelper.getWritableDatabase();

        //Set the sql query, find the business card whose "com", "name", "tel", or "email" include the searchCondition
        String sql="SELECT * FROM "+DatabaseStatic.TABLE_NAME+" WHERE "+DatabaseStatic.COM+" LIKE '"+searchCondition+"' OR "+
                DatabaseStatic.NAME+" LIKE '"+searchCondition+"' OR "+DatabaseStatic.TEL+" LIKE '"+searchCondition+"' OR "+
                DatabaseStatic.EMAIL+" LIKE '"+ searchCondition+"' ORDER BY "+DatabaseStatic.NAME;

        //Get the business card information which you want to find from the database
        Cursor cursor = database.rawQuery(sql, null);
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

        //If there are not thing in the search result, show it to user
        if(data.isEmpty()) {
            searchTag.setText("There is nothing found !");
        }
        else searchTag.setText("");

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
                Intent intent = new Intent(SearchActivity.this,DetailActivity.class);
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
