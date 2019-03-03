package com.serdardal.notdefteritest;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class addBlock extends AppCompatActivity {

    EditText textStatement;
    EditText textId;
    EditText textPassword;
    EditText textNotes;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_block);


        textStatement = (EditText) findViewById(R.id.editText2);
        textId = (EditText) findViewById(R.id.editText5);
        textPassword = (EditText) findViewById(R.id.editText6);
        textNotes = (EditText) findViewById(R.id.editText8);

    }

    public void saveBlock(View view){
        if(textStatement.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Açıklama boş olamaz!",Toast.LENGTH_LONG).show();
        }
        else{
            boolean match = false;
            for(Block b : Main2Activity.blocks){
                if(b.getStatement().equals(textStatement.getText().toString())){
                    match=true;
                }
            }

            if(match){
                Toast.makeText(getApplicationContext(),"Böyle bir açıklama zaten var!",Toast.LENGTH_LONG).show();
                textStatement.setText("");
            }

            else{
                try{
                    database = this.openOrCreateDatabase("Datas",MODE_PRIVATE,null);

                    database.execSQL("CREATE TABLE IF NOT EXISTS datas (statement VARCHAR, id VARCHAR, password VARCHAR, notes VARCHAR)");

                    String sqlString="INSERT INTO datas (statement, id, password, notes) VALUES (?, ?, ?, ?)";

                    SQLiteStatement statement = database.compileStatement(sqlString);
                    statement.bindString(1,textStatement.getText().toString());
                    statement.bindString(2,textId.getText().toString());
                    statement.bindString(3,textPassword.getText().toString());
                    statement.bindString(4,textNotes.getText().toString());
                    statement.execute();

                    Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
                    startActivity(intent);

                    this.finish();


                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

        }



    }
}
