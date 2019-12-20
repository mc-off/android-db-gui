package com.codingwithmitch.notes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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

import com.codingwithmitch.notes.adapters.PersonRecyclerAdapter;
import com.codingwithmitch.notes.models.Person;
import com.codingwithmitch.notes.util.VerticalSpacingItemDecorator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity implements
        View.OnClickListener,
        PersonRecyclerAdapter.OnPersonListener{

    static final String[] PARAMS = {"name"};

    private RecyclerView mRecyclerView;

    // vars
    private ArrayList<Person> personArrayList = new ArrayList<>();
    private PersonRecyclerAdapter mPersonRecycleAdapter;


    private String databaseName;

    Retrofit retrofit;

    JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mRecyclerView = findViewById(R.id.recycle_view);


        findViewById(R.id.button).setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.notes_toolbar5);

        setSupportActionBar(toolbar);

        databaseName = getIncomingIntent();

        toolbar.setTitle("Person");

        toolbar.setSubtitle(databaseName);

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


        initRecyclerView();

        retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        System.out.println(item.getTitle() + "click");
        switch (item.getItemId()){
            case (R.id.main_screen):{
                System.out.println("Main screen");
                Intent intent = new Intent(this, DbListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
            break;
            case (R.id.table_screen):{
                System.out.println("Table screen");
                Intent intent = new Intent(this, TableActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

                startActivity(intent);
            }
            break;
            case (R.id.edit_screen):{
                System.out.println("Edit screen");
                Intent intent = new Intent(this, EditActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

                startActivity(intent);
            }
            break;
            default:
                //Toast.makeText(getApplicationContext(),"Default", Toast.LENGTH_SHORT).show();
                break;
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
        getPosts(getFieldItem());
//        Toast.makeText(getApplicationContext(),"Select * from "+ getTitle().toString()+
//                " where "+ getSpinnerItem() + " = "+getFieldItem(),Toast.LENGTH_SHORT).show();show
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

    private void update(ArrayList<Person> arrayList){
        personArrayList.clear();
        personArrayList.addAll(arrayList);
        //personArrayList = arrayList;
        mPersonRecycleAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        mRecyclerView.addItemDecoration(itemDecorator);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
        mPersonRecycleAdapter = new PersonRecyclerAdapter(personArrayList, this);
        mRecyclerView.setAdapter(mPersonRecycleAdapter);
    }

    @Override
    public void onNoteClick(int position) {
        /*Intent intent = new Intent(this, TableActivity.class);
        intent.putExtra("dbName", personArrayList.get(position));
        System.out.println("Pederau personArrayList.get(position)" + personArrayList.get(position));
        startActivity(intent);*/
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            final int adapterPostition = viewHolder.getAdapterPosition();
        }
    };

    public void getPosts(String searchRequest) {

        Call<List<Person>> listCall = jsonPlaceHolderApi.getSeachPersons(databaseName,searchRequest);

        listCall.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"Connection error",Toast.LENGTH_SHORT).show();
                    return;
                }
                System.out.println(response.body().toArray().toString());
                update(new ArrayList<>(response.body()));
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failure",Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

}
