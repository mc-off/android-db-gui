package com.codingwithmitch.notes;

import com.codingwithmitch.notes.models.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    @GET("students/all")
    Call<List<Student>> getAllStudents();


    @POST("/database/add")
    void addDb(@Query("database") String dbName);

    @GET("/database/get/all")
    Call<List<String>> getAllDatabase();


}
