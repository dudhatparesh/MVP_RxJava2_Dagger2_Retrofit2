package com.crossover.conferencemanagement.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.crossover.conferencemanagement.data.model.Admin;
import com.crossover.conferencemanagement.data.model.Conference;
import com.crossover.conferencemanagement.data.model.Doctor;
import com.crossover.conferencemanagement.data.model.Invite;
import com.crossover.conferencemanagement.data.model.Schedule;
import com.crossover.conferencemanagement.data.model.Suggestion;
import com.crossover.conferencemanagement.di.ApplicationContext;
import com.crossover.conferencemanagement.di.DatabaseInfo;
import com.crossover.conferencemanagement.util.Constant;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by PareshDudhat on 10-03-2017.
 */
@Singleton
public class DbHelper extends OrmLiteSqliteOpenHelper {

    private RuntimeExceptionDao<Admin, Long> adminDao;
    private RuntimeExceptionDao<Conference, Long> conferenceDao;
    private RuntimeExceptionDao<Doctor, Long> doctorDao;
    private RuntimeExceptionDao<Suggestion, Long> suggestionDao;
    private RuntimeExceptionDao<Invite, Long> inviteDao;
    private RuntimeExceptionDao<Schedule, Long> scheduleDao;

    @Inject
    public DbHelper(@ApplicationContext Context context,
                    @DatabaseInfo String databaseName
            , @DatabaseInfo int databaseVersion) {
        super(context, databaseName, null, databaseVersion);
    }

    /**
     * onCreate method creates tables required for Application to run which are
     * Admin,Doctor,Conference,Suggestion,Invite
     *
     * @param database
     * @param connectionSource
     */
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Admin.class);
            TableUtils.createTable(connectionSource, Doctor.class);
            TableUtils.createTable(connectionSource, Conference.class);
            TableUtils.createTable(connectionSource, Suggestion.class);
            TableUtils.createTable(connectionSource, Invite.class);
            TableUtils.createTable(connectionSource, Schedule.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * onUpgrade method allows to update table structures with database version
     * changes so in our case we drop all tables and recreate them with #onCreate method
     *
     * @param database
     * @param connectionSource
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Admin.class, false);
            TableUtils.dropTable(connectionSource, Doctor.class, false);
            TableUtils.dropTable(connectionSource, Conference.class, false);
            TableUtils.dropTable(connectionSource, Suggestion.class, false);
            TableUtils.dropTable(connectionSource, Invite.class, false);
            TableUtils.dropTable(connectionSource, Schedule.class, false);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public RuntimeExceptionDao<Admin, Long> getAdminDao() {
        if (adminDao == null) {
            adminDao = getRuntimeExceptionDao(Admin.class);
        }
        return adminDao;
    }

    public RuntimeExceptionDao<Conference, Long> getConferenceDao() {
        if (conferenceDao == null) {
            conferenceDao = getRuntimeExceptionDao(Conference.class);
        }
        return conferenceDao;
    }

    public RuntimeExceptionDao<Doctor, Long> getDoctorDao() {
        if (doctorDao == null) {
            doctorDao = getRuntimeExceptionDao(Doctor.class);
        }
        return doctorDao;
    }

    public RuntimeExceptionDao<Suggestion, Long> getSuggestionDao() {
        if (suggestionDao == null) {
            suggestionDao = getRuntimeExceptionDao(Suggestion.class);
        }
        return suggestionDao;
    }

    public RuntimeExceptionDao<Invite, Long> getInviteDao() {
        if (inviteDao == null) {
            inviteDao = getRuntimeExceptionDao(Invite.class);
        }
        return inviteDao;
    }

    public RuntimeExceptionDao<Schedule, Long> getScheduleDao() {
        if (scheduleDao == null) {
            scheduleDao = getRuntimeExceptionDao(Schedule.class);
        }
        return scheduleDao;
    }

    public Doctor getDoctor(String username, String password) throws SQLException {
        QueryBuilder<Doctor, Long> queryBuilder = getDoctorDao().queryBuilder();
        queryBuilder.where().eq("username", username).and().eq("password", password);
        List<Doctor> doctors = queryBuilder.query();
        if (doctors.size() > 0) {
            return doctors.get(0);
        } else {
            return null;
        }
    }

    public Admin getAdmin(String username, String password) throws SQLException {
        QueryBuilder<Admin, Long> queryBuilder = getAdminDao().queryBuilder();
        queryBuilder.where().eq("username", username).and().eq("password", password);
        List<Admin> admins = queryBuilder.query();
        if (admins.size() > 0) {
            return admins.get(0);
        } else {
            return null;
        }
    }

    public List<Conference> getSortedConferences() throws SQLException {
        QueryBuilder<Conference, Long> queryBuilder = getConferenceDao().queryBuilder();
        queryBuilder.orderBy("id", false);
        return queryBuilder.query();
    }

    public List<Conference> getSchedule(long doctorId) throws SQLException {
        QueryBuilder<Schedule, Long> scheduleQueryBuilder =
                getScheduleDao().queryBuilder();
        scheduleQueryBuilder.orderBy("conference_id", false).where().eq("doctor_id", doctorId);
        List<Schedule> schedules = scheduleQueryBuilder.query();
        List<Conference> conferences = new ArrayList<>();
        for (Schedule schedule : schedules) {
            Conference conference = getConferenceDao().queryForId(schedule.getConferenceId());
            if (conference != null) {
                conferences.add(conference);
            }
        }
        return conferences;
    }

    public List<Suggestion> getSortedSuggestions() throws SQLException {
        QueryBuilder<Suggestion, Long> queryBuilder = getSuggestionDao().queryBuilder();
        queryBuilder.orderBy("id", false);
        return queryBuilder.query();
    }

    public List<Suggestion> getSortedSuggestions(long doctorId) throws SQLException {
        QueryBuilder<Suggestion, Long> queryBuilder = getSuggestionDao().queryBuilder();
        queryBuilder.orderBy("id", false);
        return queryBuilder.query();
    }

    public void removeInvites(Long conferenceId) throws SQLException {
        QueryBuilder<Invite, Long> queryBuilder = getInviteDao().queryBuilder();
        queryBuilder.where().eq("conference_id", conferenceId);
        List<Invite> invites = queryBuilder.query();
        getInviteDao().delete(invites);
    }

    public List<Invite> getInvites(long userId) throws SQLException {
        QueryBuilder<Invite, Long> queryBuilder = getInviteDao().queryBuilder();
        queryBuilder.where().eq("doctor_id", userId).and().eq("status",
                Constant.InviteStatus.INVITED);
        return queryBuilder.query();

    }

    public boolean changeInviteStatus(Conference conference, long userId, String status) throws SQLException {
        QueryBuilder<Invite, Long> builder = getInviteDao().queryBuilder();
        builder.where().eq("doctor_id", userId).and().eq("conference_id",
                conference.getId());
        List<Invite> invites = builder.query();
        if (invites.size() > 0) {
            Invite invite = invites.get(0);
            invite.setStatus(status);
            getInviteDao().update(invite);
            if (status.equals(Constant.InviteStatus.ACCEPTED)) {
                Schedule schedule = new Schedule();
                schedule.setConferenceId(conference.getId());
                schedule.setDoctorId(userId);
                getScheduleDao().create(schedule);
            }
            return true;
        } else {
            return false;
        }
    }
}
