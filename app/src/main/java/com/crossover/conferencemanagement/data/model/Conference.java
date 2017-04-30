package com.crossover.conferencemanagement.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by PareshDudhat on 10-03-2017.
 */

@DatabaseTable(tableName = "tbl_conference")
public class Conference implements Parcelable {
    public static final Creator<Conference> CREATOR = new Creator<Conference>() {
        @Override
        public Conference createFromParcel(Parcel in) {
            return new Conference(in);
        }

        @Override
        public Conference[] newArray(int size) {
            return new Conference[size];
        }
    };
    @DatabaseField(columnName = "id", generatedId = true)
    private long id;
    @DatabaseField(columnName = "topic")
    private String topic;
    @DatabaseField(columnName = "detail")
    private String detail;
    @DatabaseField(columnName = "date")
    private String date;
    @DatabaseField(columnName = "location_name")
    private String locationName;
    @DatabaseField(columnName = "status")
    private String status;
    @DatabaseField(columnName = "admin_id")
    private long adminId;

    public Conference() {
    }

    protected Conference(Parcel in) {
        id = in.readLong();
        topic = in.readString();
        detail = in.readString();
        date = in.readString();
        locationName = in.readString();
        status = in.readString();
        adminId = in.readLong();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getAdminId() {
        return adminId;
    }

    public void setAdminId(long adminId) {
        this.adminId = adminId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(topic);
        dest.writeString(detail);
        dest.writeString(date);
        dest.writeString(locationName);
        dest.writeString(status);
        dest.writeLong(adminId);
    }
}
