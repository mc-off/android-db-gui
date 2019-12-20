package com.codingwithmitch.notes;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.codingwithmitch.notes.models.Person;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    Retrofit retrofit;

    JsonPlaceHolderApi jsonPlaceHolderApi;

    private String databaseName;

    Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        findViewById(R.id.button_update);

        Toolbar toolbar = findViewById(R.id.notes_toolbar5);

        findViewById(R.id.button_update).setOnClickListener(this);

        setSupportActionBar(toolbar);

        databaseName = getIncomingIntent();

        Intent i = getIntent();
        person = (Person) i.getSerializableExtra("Person");

        toolbar.setTitle("Edit");

        toolbar.setSubtitle(person.getName());

        retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        setFields();
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

    private void updatePerson(Person person){
        Call<ResponseBody> call = jsonPlaceHolderApi.updatePerson(databaseName, person);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Code unsuccess: " + response.code(), Toast.LENGTH_LONG).show();
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


    @Override
    public void onClick(View view){
        person.setName(getName());
        person.setSurname(getSurname());
        person.setPhone(getPhone());
        updatePerson(person);
//        Toast.makeText(getApplicationContext(),"Select * from "+ getTitle().toString()+
//                " where "+ getSpinnerItem() + " = "+getFieldItem(),Toast.LENGTH_SHORT).show();show
    }

    public String getName(){
        TextView textView = findViewById(R.id.name);
        return textView.getText().toString();
    }
    public String getSurname(){
        TextView textView = findViewById(R.id.searchField);
        return textView.getText().toString();
    }
    public String getPhone(){
        TextView textView = findViewById(R.id.searchField3);
        return textView.getText().toString();
    }

    public void setFields(){
        setName();
        setSurname();
        setPhone();
    }

    public void setName(){
        TextView textView = findViewById(R.id.name);
        textView.setText(person.getName());
    }
    public void setSurname(){
        TextView textView = findViewById(R.id.searchField);
        textView.setText(person.getSurname());
    }
    public void setPhone(){
        TextView textView = findViewById(R.id.searchField3);
        textView.setText(person.getPhone());
    }

}
