package com.bcm.djy.businesscardmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bcm.djy.databaseHelper.DatabaseStatic;
import com.bcm.djy.databaseHelper.MyHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    private MyHelper myHelper = null;
    private SQLiteDatabase database = null;

    EditText comText;
    EditText nameText;
    EditText telText;
    EditText emailText;

    TextView cardCom;
    TextView cardName;
    TextView cardTel;
    TextView cardEmail;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        id=intent.getStringExtra("id");
        String com=intent.getStringExtra("com");
        String name=intent.getStringExtra("name");
        String tel=intent.getStringExtra("tel");
        String email=intent.getStringExtra("email");



        if(id!=null) {
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            Toast.makeText(this, sdf.format(new Date()), Toast.LENGTH_SHORT).show();
            if(myHelper == null)
            {
                myHelper = new MyHelper(this);
            }
            database = myHelper.getWritableDatabase();
            ContentValues cV = new ContentValues();
            cV.put(DatabaseStatic.LAST_TIME,sdf.format(new Date()));
            database.update(DatabaseStatic.TABLE_NAME, cV, DatabaseStatic.ID + "= ?", new String[]{id});
        }

        comText = (EditText) findViewById(R.id.comText);
        nameText = (EditText) findViewById(R.id.nameText);
        telText = (EditText) findViewById(R.id.telText);
        emailText = (EditText) findViewById(R.id.emailText);

        cardCom = (TextView) findViewById(R.id.company);
        cardName = (TextView) findViewById(R.id.name);
        cardTel = (TextView) findViewById(R.id.tel);
        cardEmail = (TextView) findViewById(R.id.email);

        comText.setText(com);
        nameText.setText(name);
        telText.setText(tel);
        emailText.setText(email);

        cardCom.setText(com);
        cardName.setText(name);
        cardTel.setText(tel);
        cardEmail.setText(email);

        comText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 输入的内容变化的监听
                cardCom.setText(comText.getText());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 输入前的监听

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 输入后的监听
                cardCom.setText(comText.getText());
            }
        });
        nameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 输入的内容变化的监听
                cardName.setText(nameText.getText());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 输入前的监听

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 输入后的监听
                cardName.setText(nameText.getText());
            }
        });
        telText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 输入的内容变化的监听
                cardTel.setText(telText.getText());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 输入前的监听

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 输入后的监听
                cardTel.setText(telText.getText());
            }
        });
        emailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 输入的内容变化的监听
                cardEmail.setText(emailText.getText());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 输入前的监听

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 输入后的监听
                cardEmail.setText(emailText.getText());
            }
        });
    }

    public void btn_back(View view) {
        finish();
    }

    public void btn_delete(View view) {
        if(myHelper == null)
        {
            myHelper = new MyHelper(this);
        }
        database = myHelper.getWritableDatabase();
        database.delete(DatabaseStatic.TABLE_NAME, DatabaseStatic.ID + "= ?",new String[]{id});
        finish();
    }

    public void btn_confirm(View view) {
        if(myHelper == null)
        {
            myHelper = new MyHelper(this);
        }
        database = myHelper.getWritableDatabase();
        ContentValues cV = new ContentValues();
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        cV.put(DatabaseStatic.COM, comText.getText().toString());
        cV.put(DatabaseStatic.NAME, nameText.getText().toString());
        cV.put(DatabaseStatic.TEL, telText.getText().toString());
        cV.put(DatabaseStatic.EMAIL, emailText.getText().toString());
        cV.put(DatabaseStatic.LAST_TIME,sdf.format(new Date()));
        if(id!=null)
            database.update(DatabaseStatic.TABLE_NAME,cV,DatabaseStatic.ID + "= ?",new String[]{id});
        else
            database.insert(DatabaseStatic.TABLE_NAME, null, cV);
        finish();
    }
}
