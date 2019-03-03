package com.serdardal.notdefteritest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

//içerik ekranı
public class Main2Activity extends AppCompatActivity {

    static Block chosenBlock;
    SharedPreferences notKayit;
    static ArrayList<Block> blocks;
    ListView listView;
    SQLiteDatabase database ;
    Switch aSwitch;
    ArrayList<Block> sorted;
    ArrayList<Block> arrayList;
    ArrayList<Block> searched;
    SearchView searchView;
    TextView a_z;

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        searchView = findViewById(R.id.searchView);
        aSwitch = findViewById(R.id.switch1);
        database = this.openOrCreateDatabase("Datas",MODE_PRIVATE,null);
        blocks = new ArrayList<Block>();
        sorted = new ArrayList<Block>();
        searched = new ArrayList<Block>();
        a_z= findViewById(R.id.textView4);

        createBlocks();

        for(Block b: blocks){
            sorted.add(new Block(b.getStatement(),b.getId(),b.getPassword(),b.getNotes()));
        }
        Collections.sort(sorted, new ismeGoreSirala());

        sort(blocks);

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setIconified(false);
            }
        });

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(aSwitch.getVisibility()==View.INVISIBLE){
                    aSwitch.setVisibility(View.VISIBLE);
                    a_z.setVisibility(View.VISIBLE);
                    sort(blocks);
                    searchView.setQuery("",false);
                }
                else{
                    aSwitch.setVisibility(View.INVISIBLE);
                    a_z.setVisibility(View.INVISIBLE);
                }



                if(aSwitch.isChecked()){
                    aSwitch.setChecked(false);

                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                if(!s.equals("")) sort(searchBlock(s));
                else{sort(blocks);}

                return false;
            }
        });

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(aSwitch.isChecked()){
                    sort(sorted);
                }
                else{
                    sort(blocks);
                }
            }
        });





    }


    public ArrayList<Block> searchBlock(String s){
        ArrayList<Block> returnBlock = new ArrayList<Block>();
        for(Block b : sorted){
            if(b.getStatement().toLowerCase().startsWith(s.toLowerCase())){
                returnBlock.add(b);
            }
        }
        return returnBlock;
    }

    public void createBlocks(){
        try{
            Cursor cursor = database.rawQuery("SELECT * FROM datas",null);
            int statementIx = cursor.getColumnIndex("statement");
            int idIx = cursor.getColumnIndex("id");
            int passwordIx = cursor.getColumnIndex("password");
            int notesIx = cursor.getColumnIndex("notes");

            cursor.moveToFirst();

            while(cursor != null){
                Block newBlock = new Block(cursor.getString(statementIx),
                        cursor.getString(idIx),
                        cursor.getString(passwordIx),
                        cursor.getString(notesIx));

                blocks.add(newBlock);

                cursor.moveToNext();
            }


        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

    public void sort(ArrayList<Block> aList){

        arrayList=aList;
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);


        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                chosenBlock = arrayList.get(i);
                Intent intent = new Intent(getApplicationContext(),displayBlock.class);
                startActivity(intent);


            }
        }
        );

    }


    public void addBlock(View view){
        Intent intent = new Intent(getApplicationContext(),addBlock.class);
        startActivity(intent);

    }


    public void changePassword(View view){
        Intent intent = new Intent(getApplicationContext(),Main3Activity.class);
        startActivity(intent);
    }
}
