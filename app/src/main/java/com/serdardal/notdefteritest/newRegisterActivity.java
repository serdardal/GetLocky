package com.serdardal.notdefteritest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

//ilk girişte şifre oluşturma ekranı
public class newRegisterActivity extends AppCompatActivity {

    EditText firstPassword;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_register);

        firstPassword = findViewById(R.id.editText4);

        InputFilter[] lengthFilter = new InputFilter[1];
        lengthFilter[0] = new InputFilter.LengthFilter(4);
        firstPassword.setFilters(lengthFilter);

        sp = this.getSharedPreferences("com.serdardal.notdefteritest", Context.MODE_PRIVATE);
    }

    public void saveFirstPassword(View view){

        if(firstPassword.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Şifre Kısmı Boş Olamaz!",Toast.LENGTH_LONG).show();
        }
        else if(firstPassword.getText().length()<4){
            Toast.makeText(getApplicationContext(),"Şifre 4 Basamaklı Olmalı!",Toast.LENGTH_LONG).show();
            firstPassword.setText("");
        }
        else{
            sp.edit().putInt("password",Integer.parseInt(firstPassword.getText().toString())).apply();
            sp.edit().putInt("usingTime",1).apply();
            Toast.makeText(getApplicationContext(),"Şifre Oluşturma Başarılı!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);

            this.finish();

        }

    }
}
