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

import com.codingwithmitch.notes.adapters.StudentRecyclerAdapter;
import com.codingwithmitch.notes.models.Person;
import com.codingwithmitch.notes.models.Student;
import com.codingwithmitch.notes.persistence.JsonPlaceHolderApi;
import com.codingwithmitch.notes.util.VerticalSpacingItemDecorator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudentTableActivity extends AppCompatActivity implements
        StudentRecyclerAdapter.OnStudentListener,
        FloatingActionButton.OnClickListener{

    private static final String TAG = "NotesListActivity";

    // ui components
    private RecyclerView mRecyclerView;

    // vars
    private ArrayList<Student> studentArrayList = new ArrayList<>();
    private StudentRecyclerAdapter mStudentRecycleAdapter;


    private String databaseName;

    Retrofit retrofit;

    JsonPlaceHolderApi jsonPlaceHolderApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        mRecyclerView = findViewById(R.id.recycle_view);

        Toolbar toolbar = findViewById(R.id.notes_toolbar5);

        setSupportActionBar(toolbar);

        toolbar.setTitle("Students");

        databaseName = getIncomingIntent();

        toolbar.setSubtitle(databaseName);

        initRecyclerView();

        retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        getPosts();
    }

    private void insertOne(Long personId){
        try {
            if (personId==null){
                throw new Exception();
            }
            Student student = new Student(
                     Long.valueOf(studentArrayList.size()),
                    true,
                    "HSE",
                     personId
            );
            studentArrayList.add(student);
            mStudentRecycleAdapter.notifyDataSetChanged();
            Call<ResponseBody> call = jsonPlaceHolderApi.postStudent(databaseName, student);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getContext(), "Code: " + response.code(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    Toast.makeText(getApplicationContext(), "Code: " + response.code(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            getPosts();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "Persons table is null", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void update(ArrayList<Student> arrayList){
        studentArrayList.clear();
        studentArrayList.addAll(arrayList);
        //studentArrayList = arrayList;
        mStudentRecycleAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        mRecyclerView.addItemDecoration(itemDecorator);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
        mStudentRecycleAdapter = new StudentRecyclerAdapter(studentArrayList, this);
        mRecyclerView.setAdapter(mStudentRecycleAdapter);
    }


    @Override
    public void onNoteClick(int position) {
        Toast.makeText(getApplicationContext(),"Click in item number: "+position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {

    }

    private void deleteStudent (Student student) {
        studentArrayList.remove(student);

        mStudentRecycleAdapter.notifyDataSetChanged();

        Call<ResponseBody> call = jsonPlaceHolderApi.deleteCurrentStudent(student.getId(), databaseName);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Code: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), "Code: " + response.code(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // mNoteRepository.deleteNoteTask(note);
    }

    private void deleteAllStudents () {
        studentArrayList.clear();

        mStudentRecycleAdapter.notifyDataSetChanged();

        Call<ResponseBody> call = jsonPlaceHolderApi.deleteAllStudents(databaseName);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Code: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), "Code: " + response.code(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_SHORT).show();
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

            new AlertDialog.Builder(StudentTableActivity.this)
                    .setTitle("Delete entry")
                    .setMessage("Are you sure you want to delete this entry?")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            deleteStudent(studentArrayList.get(adapterPostition));
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    };

    public void getPosts() {

        Call<List<Student>> listCall = jsonPlaceHolderApi.getAllStudents(getDatabaseName());

        listCall.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"Connection error",Toast.LENGTH_SHORT).show();
                    return;
                }
                System.out.println(response.body().toArray().toString());
                update(new ArrayList<>(response.body()));
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failure",Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.table_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.update_db): {
                getPosts();
            }
            break;
            case (R.id.plus): {
                getRandomPersonId();
            }
            break;
            case (R.id.delete_all): {
                deleteAllStudents();
            }
            break;
            default:
                Toast.makeText(getApplicationContext(), "Default", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private Context getContext() {
        return StudentTableActivity.this;
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

    public String getDatabaseName() {
        return databaseName;
    }

    public void getRandomPersonId() {

        Call<List<Person>> listCall = jsonPlaceHolderApi.getAllPersons(getDatabaseName());

        listCall.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"Connection error",Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<Long> idArrayList = new ArrayList<>();
                List<Person> people = response.body();

                for (Person person : people){
                    idArrayList.add(person.getId());
                }


                insertOne(idArrayList.isEmpty()? null : idArrayList
                        .get(new Random().nextInt(idArrayList.size())));
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failure",Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }


}
