package com.serdardal.notdefteritest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

//açılış ekranı
public class MainActivity extends AppCompatActivity {

    int password;
    EditText editText;
    SharedPreferences sp;
    int useTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = this.getSharedPreferences("com.serdardal.notdefteritest", Context.MODE_PRIVATE);
        password=sp.getInt("password",1234);

        useTime=sp.getInt("usingTime",0);
        if(useTime==0){
            Intent intent = new Intent(getApplicationContext(),newRegisterActivity.class);
            startActivity(intent);

            this.finish();
        }


        editText = findViewById(R.id.editText);
        editText.requestFocus();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        InputFilter[] lengthFilter = new InputFilter[1];
        lengthFilter[0] = new InputFilter.LengthFilter(4);
        editText.setFilters(lengthFilter);


        editText.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            check();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });


    }

    public void changePassword(View view){
        Intent intent = new Intent(getApplicationContext(),Main3Activity.class);
        startActivity(intent);
    }

    public void check(){
        if(editText.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Şifre Kısmı Boş Olamaz!",Toast.LENGTH_LONG).show();
        }
        else if(editText.getText().length()<4){
            Toast.makeText(getApplicationContext(),"Şifre 4 Basamaklı Olmalı!",Toast.LENGTH_LONG).show();
            editText.setText("");
        }
        else if(Integer.parseInt(editText.getText().toString())!=password){
            Toast.makeText(getApplicationContext(),"Şifre Yanlış!",Toast.LENGTH_LONG).show();
            editText.setText("");
        }
        else {
            Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
            startActivity(intent);
            this.finish();
        }
    }

    public void checkButton(View view){
        check();
    }

}
