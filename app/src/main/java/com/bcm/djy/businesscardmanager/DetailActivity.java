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

        //If there isn't a new business card, change the last time you visit the business card
        if(id!=null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
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

        //Set the listener of the com, name, tel and email. When the EditText change, the text on the card will also change
        comText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cardCom.setText(comText.getText());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                cardCom.setText(comText.getText());
            }
        });
        nameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cardName.setText(nameText.getText());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                cardName.setText(nameText.getText());
            }
        });
        telText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cardTel.setText(telText.getText());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                cardTel.setText(telText.getText());
            }
        });
        emailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cardEmail.setText(emailText.getText());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                cardEmail.setText(emailText.getText());
            }
        });
    }

    //When you click the Back button, close the DetailActivity
    public void btn_back(View view) {
        finish();
    }

    //When you click the Delete button, delete the business card
    public void btn_delete(View view) {
        //If there is a new card, directly close the DetailActivity
        if(id!=null);
        else finish();

        //Delete the card and close the DetailActivity
        if(myHelper == null)
        {
            myHelper = new MyHelper(this);
        }
        database = myHelper.getWritableDatabase();
        database.delete(DatabaseStatic.TABLE_NAME, DatabaseStatic.ID + "= ?",new String[]{id});
        finish();
    }

    public void btn_confirm(View view) {
        //If there are something empty, you can not confirm the change or insert
        if((comText.getText().length()>0)&&(nameText.getText().length()>0)&&(telText.getText().length()>0)&&(emailText.getText().length()>0));
        else
        {
            Toast.makeText(this, "Please input all the information !", Toast.LENGTH_SHORT).show();
            return;
        }

        String format = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        if(emailText.getText().toString().matches(format));
        else
        {
            Toast.makeText(this, "Please input correct E-mail !", Toast.LENGTH_SHORT).show();
            return;
        }

        //Clear the ArrayList and connect the database
        if (myHelper == null) {
            myHelper = new MyHelper(this);
        }
        database = myHelper.getWritableDatabase();

        //Put all the information into the ContentValues
        ContentValues cV = new ContentValues();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        cV.put(DatabaseStatic.COM, comText.getText().toString());
        cV.put(DatabaseStatic.NAME, nameText.getText().toString());
        cV.put(DatabaseStatic.TEL, telText.getText().toString());
        cV.put(DatabaseStatic.EMAIL, emailText.getText().toString());
        cV.put(DatabaseStatic.LAST_TIME, sdf.format(new Date()));

        //If there is a new card, insert it into database, if there isn't a new card, update the change to the database
        if(id!=null)
            database.update(DatabaseStatic.TABLE_NAME,cV,DatabaseStatic.ID + "= ?",new String[]{id});
        else
            database.insert(DatabaseStatic.TABLE_NAME, null, cV);
        finish();
    }
}
