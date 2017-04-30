package com.crossover.conferencemanagement.data.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by PareshDudhat on 10-03-2017.
 */

@DatabaseTable(tableName = "tbl_suggestion")
public class Suggestion {
    @DatabaseField(columnName = "id", id = true)
    private long id;
    @DatabaseField(columnName = "doctor_id")
    private long doctorId;
    @DatabaseField(columnName = "topic")
    private String topic;
    @DatabaseField(columnName = "detail")
    private String detail;
    @DatabaseField(columnName = "created_date")
    private String createdDate;
    @DatabaseField(columnName = "status")
    private String status;
    @DatabaseField(columnName = "isRead")
    private boolean isRead;

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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
