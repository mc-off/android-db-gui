package com.codingwithmitch.notes;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class DbListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_list);

        Toolbar toolbar = findViewById(R.id.notes_toolbar5);

        setSupportActionBar(toolbar);

        toolbar.setTitle("DbList");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.db_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case (R.id.search_screen):{
                Intent intent = new Intent(this, SearchActivity.class);
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
}
