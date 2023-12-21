package com.example.myfirstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ContactActivity extends AppCompatActivity {

    ListView simpleList;
    String coutryList[] ={ "ali" , "ilyes", "weal", "akrem" ,"lotfi" ,"aziz","ali","akrem","weal" };
    int flags[] = new int[]{ R.drawable.ali, R.drawable.weal, R.drawable.akrem, R.drawable.aziz,R.drawable.lotfi,R.drawable.ilyes,R.drawable.weal,R.drawable.aziz,R.drawable.ilyes};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        simpleList=(ListView) findViewById(R.id.ListView);

        ArrayAdapter<String> arrayAdapter =new ArrayAdapter<String>(this, R.layout.activity_list, R.id.textView,coutryList );
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), coutryList, flags);

        simpleList.setAdapter(customAdapter);


    }
}