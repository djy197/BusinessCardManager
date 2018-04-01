package com.bcm.djy.businesscardmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    EditText comText;
    EditText nameText;
    EditText telText;
    EditText emailText;

    TextView cardCom;
    TextView cardName;
    TextView cardTel;
    TextView cardEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String com=intent.getStringExtra("com");
        String name=intent.getStringExtra("name");
        String tel=intent.getStringExtra("tel");
        String email=intent.getStringExtra("email");

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

    }

    public void btn_confirm(View view) {

    }
}
