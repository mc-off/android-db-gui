package com.codingwithmitch.notes;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    static final String[] PARAMS = {"id", "name"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        findViewById(R.id.button).setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.notes_toolbar5);

        setSupportActionBar(toolbar);

        toolbar.setTitle("Search");


        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, PARAMS);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);


        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                String item = (String)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case (R.id.main_screen):{
                Intent intent = new Intent(this, DbListActivity.class);
                startActivity(intent);
            }
            case (R.id.table_screen):{
                Intent intent = new Intent(this, TableActivity.class);
                startActivity(intent);
            }
            case (R.id.edit_screen):{
                Intent intent = new Intent(this, EditActivity.class);
                startActivity(intent);
            }

            default:
                Toast.makeText(getApplicationContext(),"Default", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public String getSpinnerItem(){
        Spinner spinner = findViewById(R.id.spinner);
        return spinner.getSelectedItem().toString();
    }

    public String getFieldItem(){
        TextView textView = findViewById(R.id.searchField);
        return textView.getText().toString();
    }

    @Override
    public void onClick(View view){
        Toast.makeText(getApplicationContext(),"Select * from "+ getTitle().toString()+
                " where "+ getSpinnerItem() + " = "+getFieldItem(),Toast.LENGTH_SHORT).show();
    }

}
