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

public class TableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        Toolbar toolbar = findViewById(R.id.notes_toolbar5);

        setSupportActionBar(toolbar);

        toolbar.setTitle("Table");

        toolbar.setSubtitle(getIncomingIntent());

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
            case (R.id.search_badg):{
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
            }
            break;
            case (R.id.edit_screen):{
                Intent intent = new Intent(this, EditActivity.class);
                startActivity(intent);
            }
            break;
            case (R.id.main_screen):{
                Intent intent = new Intent(this, DbListActivity.class);
                startActivity(intent);
            }
            break;
            default:
                Toast.makeText(getApplicationContext(),"Default", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private String getIncomingIntent() {
        try {
            if (getIntent().hasExtra("dbName")) {
                return getIntent().getStringExtra("dbName");
            }
            else
                return "hse";
        }
        catch (NullPointerException e){
            e.getMessage();
        }
        return "hse";
    }

}
