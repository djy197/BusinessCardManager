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

        myHelper = new MyHelper(this);
        database = myHelper.getWritableDatabase();

        Cursor cursor = database.query(DatabaseStatic.TABLE_NAME, null, null, null, null, null, null);
        if(cursor.moveToFirst()) // 显示数据库的内容
        {
            for(; !cursor.isAfterLast(); cursor.moveToNext()) // 获取查询游标中的数据
            {
                Map<String, String> item = new HashMap<String, String>();
                item.put("com", cursor.getString(cursor.getColumnIndex(DatabaseStatic.COM)));
                item.put("name", cursor.getString(cursor.getColumnIndex(DatabaseStatic.NAME)));
                item.put("tel", cursor.getString(cursor.getColumnIndex(DatabaseStatic.TEL)));
                item.put("email", cursor.getString(cursor.getColumnIndex(DatabaseStatic.EMAIL)));
                data.add(item);
            }
        }
        cursor.close();

        if(data.isEmpty())
            Toast.makeText(this, "数据库为空", Toast.LENGTH_SHORT).show();

        listView = (ListView) findViewById(R.id.listView);

        simAdapt = new SimpleAdapter(
                this,
                data,
                R.layout.item_layout,
                new String[] { "com", "name", "tel", "email" },// 与下面数组元素要一一对应
                new int[] { R.id.item_company, R.id.item_name, R.id.item_tel, R.id.item_email });

        listView.setAdapter(simAdapt);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HashMap<String, String> map = (HashMap<String, String>) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                intent.putExtra("com",map.get("com"));
                intent.putExtra("name",map.get("name"));
                intent.putExtra("tel",map.get("tel"));
                intent.putExtra("email",map.get("email"));
                startActivity(intent);
            }
        });
    }

    public void btn_index(View view) {
        startActivity(new Intent(MainActivity.this,IndexActivity.class));
        finish();
    }

    public void btn_manage(View view) {
        startActivity(new Intent(MainActivity.this,ManageActivity.class));
        finish();
    }

    public void btn_search(View view) {
        startActivity(new Intent(MainActivity.this,SearchActivity.class));
    }
}
