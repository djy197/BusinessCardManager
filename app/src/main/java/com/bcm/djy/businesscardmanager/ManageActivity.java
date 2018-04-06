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

    public void btn_index(View view) {
        startActivity(new Intent(ManageActivity.this,IndexActivity.class));
        finish();
    }

    public void btn_recently(View view) {
        startActivity(new Intent(ManageActivity.this,MainActivity.class));
        finish();
    }

    public void btn_search(View view) {
        startActivity(new Intent(ManageActivity.this,SearchActivity.class));
    }

    public void btn_add(View view) {
        startActivity(new Intent(ManageActivity.this,DetailActivity.class));
    }

    public void btn_delete(View view) {
        myHelper = new MyHelper(this);
        database = myHelper.getWritableDatabase();
        for(int i=0;i<data.size();i++)
            if(data.get(i).get("state")=="checked")
                database.delete(DatabaseStatic.TABLE_NAME, DatabaseStatic.ID + "= ?",new String[]{data.get(i).get("id").toString()});
        listChange();
    }

    public void listChange(){
        data = new ArrayList<Map<String, Object>>();
        myHelper = new MyHelper(this);
        database = myHelper.getWritableDatabase();

        Cursor cursor = database.query(DatabaseStatic.TABLE_NAME, null, null, null, null, null, DatabaseStatic.NAME);
        if(cursor.moveToFirst()) // 显示数据库的内容
        {
            for(; !cursor.isAfterLast(); cursor.moveToNext()) // 获取查询游标中的数据
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

        if(data.isEmpty())
            Toast.makeText(this, "数据库为空", Toast.LENGTH_SHORT).show();

        listView = (ListView) findViewById(R.id.listView);

        simAdapt = new SimpleAdapter(
                this,
                data,
                R.layout.item_select_layout,
                new String[] { "com", "name", "tel", "email", "img" },// 与下面数组元素要一一对应
                new int[] { R.id.item_company, R.id.item_name, R.id.item_tel, R.id.item_email, R.id.item_check });

        listView.setAdapter(simAdapt);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> item = data.get(position);
                if(item.get("state")=="unchecked")
                {
                    item.put("img",R.drawable.checked);
                    item.put("state","checked");
                }
                else if(item.get("state")=="checked")
                {
                    item.put("img",R.drawable.unchecked);
                    item.put("state","uncheck");
                }
                data.remove(position);//清除此行对应数据集中的数据
                data.add(position, item);
                simAdapt.notifyDataSetChanged();
            }
        });
    }
}
