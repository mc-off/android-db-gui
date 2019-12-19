package com.codingwithmitch.notes;

import com.codingwithmitch.notes.models.Database;
import com.codingwithmitch.notes.models.Person;
import com.codingwithmitch.notes.models.Student;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    @GET("students/all")
    Call<List<Student>> getAllStudents();

    @FormUrlEncoded
    @POST("database")
    Call<Database> addDb(@Body Database database);

    @GET("database")
    Call<List<String>> getAllDatabase();

    @FormUrlEncoded
    @DELETE("database")
    Call<Database> deleteDbTwo(@Field("database") String dbName);

    @POST("database")
    Call<ResponseBody> send (
            @Query("database") String database
    );

    @DELETE("database")
    Call<ResponseBody> deleteDb (
            @Query("database") String database
    );

    @GET("persons")
    Call<List<Person>> getAllPersons (
            @Query("database") String database
    );


    @POST("persons")
    Call<ResponseBody> postPerson (
            @Query("database") String database,
            @Body Person person
    );

    @DELETE("persons/{id}")
    Call<ResponseBody> deleteCurrentPerson (
            @Path("id") Long id, @Query("database") String database
    );

    @DELETE("persons")
    Call<ResponseBody> deleteAllPersons (
            @Query("database") String database
    );

}
