package com.bcm.djy.businesscardmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String[] arrayCom = {"Rich-Man Company","Poor-Man Company","Normal-Man Company","Good-Man Company"};
    String[] arrayName = {"Rich Man","Poor Man","Normal Man","Good Man"};
    String[] arrayTel = {"0000-111111","0000-222222","0000-333333","0000-444444"};
    String[] arrayEmail = {"XX@XX.com","YY@YY.com","ZZ@ZZ.com","AA@AA.com"};

    private SimpleAdapter simAdapt;
    private ListView listView;
    private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 4; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("com", arrayCom[i]);
            item.put("name", arrayName[i]);
            item.put("tel", arrayTel[i]);
            item.put("email", arrayEmail[i]);
            data.add(item);
        }

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
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                Toast.makeText(MainActivity.this,"aha", Toast.LENGTH_SHORT).show();
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
