package com.codingwithmitch.notes.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Database {

    final String database;

    public Database(String database) {
        this.database = database;
    }
}
