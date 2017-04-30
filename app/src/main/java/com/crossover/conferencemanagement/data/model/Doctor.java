package com.crossover.conferencemanagement.data.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by PareshDudhat on 10-03-2017.
 */

@DatabaseTable(tableName = "tbl_doctor")
public class Doctor {
    @DatabaseField(columnName = "id", generatedId = true)
    private long id;
    @DatabaseField(columnName = "username")
    private String username;
    @DatabaseField(columnName = "password")
    private String password;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "experience")
    private int experience;
    @DatabaseField(columnName = "expertise")
    private String expertise;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }
}
