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

//şifre değiştirme ekranı
public class Main3Activity extends AppCompatActivity {

    EditText newPassword;
    EditText oldPassword;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        sp= this.getSharedPreferences("com.serdardal.notdefteritest", Context.MODE_PRIVATE);

        InputFilter[] lengthFilter = new InputFilter[1];
        lengthFilter[0] = new InputFilter.LengthFilter(4);

        newPassword = findViewById(R.id.editText3);
        oldPassword = findViewById(R.id.editText7);

        newPassword.setFilters(lengthFilter);
        oldPassword.setFilters(lengthFilter);


    }

    public void sifreKaydet(View view){
        if(Integer.parseInt(oldPassword.getText().toString())!= sp.getInt("password",0)){
            Toast.makeText(getApplicationContext(),"Eski Şifre Yanlış!",Toast.LENGTH_LONG).show();
            oldPassword.setText("");
        }

        else if(newPassword.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(),"Şifre Kısmı Boş Olamaz!",Toast.LENGTH_LONG).show();
        }

        else if(newPassword.getText().length()<4) {
            Toast.makeText(getApplicationContext(), "Şifre 4 Basamaklı Olmalı!", Toast.LENGTH_LONG).show();
            newPassword.setText("");
        }

        else if(Integer.parseInt(newPassword.getText().toString())== sp.getInt("password",0)){
            Toast.makeText(getApplicationContext(),"Yeni Şifre Eskisiyle Aynı Olamaz!",Toast.LENGTH_LONG).show();
            newPassword.setText("");
        }
        else{
            sp.edit().putInt("password", Integer.parseInt(newPassword.getText().toString())).apply();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

            this.finish();
        }

    }
}
