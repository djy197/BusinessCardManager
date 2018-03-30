package com.bcm.djy.businesscardmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    public void btn_back(View view) {
        finish();
    }

    public void btn_delete(View view) {

    }

    public void btn_confirm(View view) {

    }
}
