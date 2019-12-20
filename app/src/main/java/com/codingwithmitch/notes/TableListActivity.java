package com.codingwithmitch.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codingwithmitch.notes.persistence.JsonPlaceHolderApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TableListActivity extends AppCompatActivity implements View.OnClickListener {

    Retrofit retrofit;

    JsonPlaceHolderApi jsonPlaceHolderApi;

    private String databaseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_list);

        Toolbar toolbar = findViewById(R.id.notes_toolbar5);

        setSupportActionBar(toolbar);

        findViewById(R.id.students).setOnClickListener(this);

        findViewById(R.id.persons).setOnClickListener(this);

        databaseName= getIncomingIntent();

        toolbar.setTitle(databaseName);

        retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.persons: {
                Intent intent = new Intent(this, TableActivity.class);
                intent.putExtra("dbName", databaseName);
                startActivity(intent);
                break;
            }
            case R.id.students: {
                Intent intent = new Intent(this, StudentTableActivity.class);
                intent.putExtra("dbName", databaseName);
                startActivity(intent);
                break;
            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.clear): {
                clearDb();
            }
            break;
            default:
                Toast.makeText(getApplicationContext(), "Default", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private void clearDb(){
        Call<ResponseBody> call = jsonPlaceHolderApi.clearDb(databaseName);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Code  not success: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), "Code success: " + response.code(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
