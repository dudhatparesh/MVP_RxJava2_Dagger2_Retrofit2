package com.crossover.conferencemanagement.data.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by PareshDudhat on 13-03-2017.
 */
@DatabaseTable(tableName = "tbl_calendar")
public class Schedule {
    @DatabaseField(columnName = "id", generatedId = true)
    private long id;
    @DatabaseField(columnName = "doctor_id")
    private long doctorId;
    @DatabaseField(columnName = "conference_id")
    private long conferenceId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    public long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(long conferenceId) {
        this.conferenceId = conferenceId;
    }
}
