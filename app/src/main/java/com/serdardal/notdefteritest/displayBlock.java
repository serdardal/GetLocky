package com.serdardal.notdefteritest;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class displayBlock extends AppCompatActivity {

    EditText textStatement;
    EditText textId;
    EditText textPassword;
    EditText textNotes;
    SharedPreferences sp;
    Block chosen;
    SQLiteDatabase database;
    String statementOld;
    AlertDialog AlertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_block);

        chosen = Main2Activity.chosenBlock;

        textStatement = (EditText) findViewById(R.id.editText2);
        textId = (EditText) findViewById(R.id.editText5);
        textPassword = (EditText) findViewById(R.id.editText6);
        textNotes = (EditText) findViewById(R.id.editText8);

        textStatement.setText(chosen.getStatement());
        textId.setText(chosen.getId());
        textPassword.setText(chosen.getPassword());
        textNotes.setText(chosen.getNotes());

        statementOld =chosen.getStatement();
    }

    public void remove(View view){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("İçeriği Sil?")
                .setMessage("Silmek istediğinden emin misin?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        delete();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();



    }

    public void delete(){
        try{
            database = this.openOrCreateDatabase("Datas",MODE_PRIVATE,null);

            database.execSQL("CREATE TABLE IF NOT EXISTS datas (statement VARCHAR, id VARCHAR, password VARCHAR, notes VARCHAR)");

            String sqlString = "DELETE FROM datas WHERE statement LIKE (?)";
            SQLiteStatement statement = database.compileStatement(sqlString);
            statement.bindString(1,textStatement.getText().toString());
            statement.execute();

            Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
            startActivity(intent);

            this.finish();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void saveChanges(View view){
        if(textStatement.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Açıklama boş olamaz!",Toast.LENGTH_LONG).show();
        }
        else{
            boolean match = false;
            for(Block b : Main2Activity.blocks){
                if(!b.equals(chosen) && b.getStatement().equals(textStatement.getText().toString())){
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

                    String sqlString = "UPDATE datas SET statement = (?) WHERE statement LIKE (?)";
                    String sqlId = "UPDATE datas SET id = (?) WHERE statement LIKE (?)";
                    String sqlPassword = "UPDATE datas SET password = (?) WHERE statement LIKE (?)";
                    String sqlNotes = "UPDATE datas SET notes = (?) WHERE statement LIKE (?)";

                    SQLiteStatement statement = database.compileStatement(sqlString);
                    statement.bindString(1,textStatement.getText().toString());
                    statement.bindString(2, statementOld);
                    statement.execute();

                    SQLiteStatement statement2 = database.compileStatement(sqlId);
                    statement2.bindString(1,textId.getText().toString());
                    statement2.bindString(2,textStatement.getText().toString());
                    statement2.execute();

                    SQLiteStatement statement3 = database.compileStatement(sqlPassword);
                    statement3.bindString(1,textPassword.getText().toString());
                    statement3.bindString(2,textStatement.getText().toString());
                    statement3.execute();

                    SQLiteStatement statement4 = database.compileStatement(sqlNotes);
                    statement4.bindString(1,textNotes.getText().toString());
                    statement4.bindString(2,textStatement.getText().toString());
                    statement4.execute();

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

    public void copyId(View view){
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("id",textId.getText().toString());
        clipboardManager.setPrimaryClip(clipData);

        Toast.makeText(getApplicationContext(),"Kopyalandı!",Toast.LENGTH_SHORT).show();
    }


    public void copyPassword(View view){
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("password",textPassword.getText().toString());
        clipboardManager.setPrimaryClip(clipData);

        Toast.makeText(getApplicationContext(),"Kopyalandı!",Toast.LENGTH_SHORT).show();
    }
}
