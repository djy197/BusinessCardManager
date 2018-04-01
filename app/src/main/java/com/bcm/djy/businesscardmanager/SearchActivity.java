package com.bcm.djy.businesscardmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    String[] arrayCom = {"Rich-Man Company","Poor-Man Company","Normal-Man Company","Good-Man Company"};
    String[] arrayName = {"Rich Man","Poor Man","Normal Man","Good Man"};
    String[] arrayTel = {"0000-111111","0000-222222","0000-333333","0000-444444"};
    String[] arrayEmail = {"XX@XX.com","YY@YY.com","ZZ@ZZ.com","AA@AA.com"};

    private SimpleAdapter simAdapt;
    private ListView listView;
    private List<Map<String, String>> data = new ArrayList<Map<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

    }

    public void btn_back(View view) {
        finish();
    }

    public void btn_search(View view) {
        for (int i = 0; i < 4; i++) {
            Map<String, String> item = new HashMap<String, String>();
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
    }
}
