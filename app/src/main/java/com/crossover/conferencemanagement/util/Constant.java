package com.crossover.conferencemanagement.util;

/**
 * Created by PareshDudhat on 11-03-2017.
 */

public class Constant {
    public interface UserType {
        int Doctor = 1;
        int Admin = 2;
    }

    public interface Key {
        String USER_TYPE = "user_type";
        String CONFERENCE = "conference";
    }

    public interface SuggestionStatus {
        String APPROVED = "approved";
        String REJECTED = "rejected";
    }

    public interface ConferenceStatus {
        String CANCELLED = "cancelled";
        String SCHEDULED = "scheduled";
    }

    public interface InviteStatus {
        String INVITED = "invited";
        String ACCEPTED = "accepted";
        String REJECTED = "rejected";
    }

    public interface Action {
        String LOAD_SCHEDULE = "load_schedule";
    }
}
