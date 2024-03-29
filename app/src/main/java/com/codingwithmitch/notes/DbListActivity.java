package com.codingwithmitch.notes;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codingwithmitch.notes.adapters.DbRecyclerAdapter;
import com.codingwithmitch.notes.persistence.JsonPlaceHolderApi;
import com.codingwithmitch.notes.util.VerticalSpacingItemDecorator;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DbListActivity extends AppCompatActivity implements
        DbRecyclerAdapter.OnDbListener,
        FloatingActionButton.OnClickListener {

    private static final String TAG = "NotesListActivity";

    // ui components
    private RecyclerView mRecyclerView;

    // vars
    private ArrayList<String> mdataBases = new ArrayList<>();
    private DbRecyclerAdapter mDbRecyclerAdapter;

    Retrofit retrofit;

    JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_list);
        mRecyclerView = findViewById(R.id.recycle_view);

        initRecyclerView();

        setSupportActionBar((Toolbar) findViewById(R.id.notes_toolbar));
        setTitle("DbList");

        Toolbar toolbar = findViewById(R.id.notes_toolbar5);

        setSupportActionBar(toolbar);

        retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        getPosts();
    }

        private void insertOne(){
            String databaseName = "title" + mdataBases.size()+2;
            mdataBases.add(databaseName);
            mDbRecyclerAdapter.notifyDataSetChanged();
            Call<ResponseBody> call = jsonPlaceHolderApi.send(databaseName);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(DbListActivity.this, "Code: " + response.code(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    Toast.makeText(DbListActivity.this, "Code: " + response.code(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(DbListActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            getPosts();

        }

    private void update(ArrayList<String> arrayList){
        mdataBases.clear();
        mdataBases.addAll(arrayList);
        mDbRecyclerAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        mRecyclerView.addItemDecoration(itemDecorator);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
        mDbRecyclerAdapter = new DbRecyclerAdapter(mdataBases, this);
        mRecyclerView.setAdapter(mDbRecyclerAdapter);
    }


    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this, TableListActivity.class);
        intent.putExtra("dbName", mdataBases.get(position));
        System.out.println("Pederau mdataBases.get(position)" + mdataBases.get(position));
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

    }

    private void deleteDb(String databaseName) {
        mdataBases.remove(databaseName);

        mDbRecyclerAdapter.notifyDataSetChanged();

        Call<ResponseBody> call = jsonPlaceHolderApi.deleteDb(databaseName);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(DbListActivity.this, "Code: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(DbListActivity.this, "Code: " + response.code(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(DbListActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            final int adapterPostition = viewHolder.getAdapterPosition();

            new AlertDialog.Builder(DbListActivity.this)
                    .setTitle("Delete entry")
                    .setMessage("Are you sure you want to delete this entry?")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            deleteDb(mdataBases.get(adapterPostition));
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    };

    public void getPosts() {

        Call<List<String>> listCall = jsonPlaceHolderApi.getAllDatabase();

        listCall.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"Connection error",Toast.LENGTH_SHORT).show();
                    return;
                }
                System.out.println(response.body().toArray().toString());
                update(new ArrayList<>(response.body()));
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failure",Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.db_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.update_db): {
                getPosts();
            }
            break;
            case (R.id.plus_db): {
                insertOne();
            }
            break;
            case (R.id.edit_screen_main): {
                Intent intent = new Intent(this, EditActivity.class);
                startActivity(intent);
            }
            break;
            case (R.id.table_screen_main): {
                Intent intent = new Intent(this, TableActivity.class);
                startActivity(intent);
            }
            break;
            case (R.id.search_screen_main): {
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
            }
            break;
            default:
                Toast.makeText(getApplicationContext(), "Default", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    public Context getContext() {
        return DbListActivity.this;
    }
}
