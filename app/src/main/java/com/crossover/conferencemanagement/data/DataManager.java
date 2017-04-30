package com.crossover.conferencemanagement.data;

import android.content.Context;

import com.crossover.conferencemanagement.data.model.Admin;
import com.crossover.conferencemanagement.data.model.Conference;
import com.crossover.conferencemanagement.data.model.Doctor;
import com.crossover.conferencemanagement.data.model.Invite;
import com.crossover.conferencemanagement.data.model.Suggestion;
import com.crossover.conferencemanagement.di.ApplicationContext;
import com.crossover.conferencemanagement.util.Constant;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Single;
import io.reactivex.SingleObserver;

/**
 * Created by PareshDudhat on 10-03-2017.
 */
@Singleton
public class DataManager {
    private Context mContext;
    private DbHelper mDbHelper;
    private PrefHelper mPrefHelper;

    @Inject
    public DataManager(@ApplicationContext Context mContext,
                       DbHelper mDbHelper, PrefHelper mPrefHelper) {
        this.mContext = mContext;
        this.mDbHelper = mDbHelper;
        this.mPrefHelper = mPrefHelper;
    }

    public Single<Integer> getUserType() {
        return new Single<Integer>() {
            @Override
            protected void subscribeActual(SingleObserver<? super Integer> observer) {
                int userType = Integer.parseInt(
                        mPrefHelper.get(PrefHelper.KEY_USER_TYPE, "0"));
                observer.onSuccess(userType);
            }
        };
    }

    public Single<Boolean> login(final String username, final String password, final int userType) {
        return new Single<Boolean>() {
            @Override
            protected void subscribeActual(SingleObserver<? super Boolean> observer) {
                if (userType == Constant.UserType.Doctor) {
                    try {
                        Doctor doctor = mDbHelper.getDoctor(username, password);
                        if (doctor != null) {
                            mPrefHelper.set(PrefHelper.KEY_USER_ID, String.valueOf(doctor.getId()));
                            mPrefHelper.set(PrefHelper.KEY_USER_TYPE,
                                    String.valueOf(Constant.UserType.Doctor));
                            observer.onSuccess(true);
                        } else {
                            observer.onSuccess(false);
                        }
                    } catch (Exception e) {
                        observer.onError(e);
                    }
                } else if (userType == Constant.UserType.Admin) {
                    try {
                        Admin admin = mDbHelper.getAdmin(username, password);
                        if (admin != null) {
                            mPrefHelper.set(PrefHelper.KEY_USER_ID, String.valueOf(admin.getId()));
                            mPrefHelper.set(PrefHelper.KEY_USER_TYPE,
                                    String.valueOf(Constant.UserType.Admin));
                            observer.onSuccess(true);
                        } else {
                            observer.onSuccess(false);
                        }
                    } catch (Exception e) {
                        observer.onError(e);
                    }
                }
            }
        };
    }

    public Completable register(final String username,
                                final String password,
                                final String experience,
                                final String expertise,
                                final String name,
                                final int userType) {
        return new Completable() {
            @Override
            protected void subscribeActual(CompletableObserver observer) {
                if (userType == Constant.UserType.Admin) {
                    try {
                        Admin admin = new Admin();
                        admin.setName(name);
                        admin.setUsername(username);
                        admin.setPassword(password);
                        mDbHelper.getAdminDao().create(admin);
                        mPrefHelper.set(PrefHelper.KEY_USER_ID, String.valueOf(admin.getId()));
                        mPrefHelper.set(PrefHelper.KEY_USER_TYPE,
                                String.valueOf(Constant.UserType.Admin));
                        observer.onComplete();
                    } catch (Exception e) {
                        observer.onError(e);
                    }
                } else if (userType == Constant.UserType.Doctor) {
                    try {
                        Doctor doctor = new Doctor();
                        doctor.setName(name);
                        doctor.setUsername(username);
                        doctor.setPassword(password);
                        doctor.setExperience(Integer.parseInt(experience));
                        doctor.setExpertise(expertise);
                        mDbHelper.getDoctorDao().create(doctor);
                        mPrefHelper.set(PrefHelper.KEY_USER_ID, String.valueOf(doctor.getId()));
                        mPrefHelper.set(PrefHelper.KEY_USER_TYPE,
                                String.valueOf(Constant.UserType.Doctor));
                        observer.onComplete();

                    } catch (Exception e) {
                        observer.onError(e);
                    }
                }
            }
        }

                ;
    }

    public Single<List<Conference>> loadConferences() {
        return new Single<List<Conference>>() {
            @Override
            protected void subscribeActual(SingleObserver<? super List<Conference>> observer) {
                try {
                    List<Conference> conferences = mDbHelper.getSortedConferences();
                    observer.onSuccess(conferences);
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        };
    }

    public Completable addConference(
            String topic, String detail, String location, String date,
            final List<Invite> invites) {
        final Conference conference = new Conference();
        conference.setTopic(topic);
        conference.setDetail(detail);
        conference.setDate(date);
        conference.setLocationName(location);
        conference.setStatus(Constant.ConferenceStatus.SCHEDULED);
        return new Completable() {

            @Override
            protected void subscribeActual(CompletableObserver observer) {
                try {
                    conference.setAdminId(Long.parseLong(mPrefHelper.get(PrefHelper.KEY_USER_ID, "0")));
                    mDbHelper.getConferenceDao().create(conference);
                    for (int i = 0; i < invites.size(); i++) {
                        Invite invite = invites.get(i);
                        invites.remove(i);
                        invite.setConferenceId(conference.getId());
                        invites.add(i, invite);
                    }
                    mDbHelper.getInviteDao().create(invites);
                    observer.onComplete();
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        };
    }


    public Single<List<Suggestion>> loadAdminSuggestions() {
        return new Single<List<Suggestion>>() {
            @Override
            protected void subscribeActual(SingleObserver<? super List<Suggestion>> observer) {
                try {
                    List<Suggestion> conferences = mDbHelper.getSortedSuggestions();
                    observer.onSuccess(conferences);
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        };
    }

    public Single<List<Suggestion>> loadDoctorSuggestions() {
        return new Single<List<Suggestion>>() {
            @Override
            protected void subscribeActual(SingleObserver<? super List<Suggestion>> observer) {
                try {
                    List<Suggestion> conferences = mDbHelper.getSortedSuggestions(getUserId());
                    observer.onSuccess(conferences);
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        };
    }

    public Completable updateConference(final String id, final String topic, final String detail, final String location, final String date, final List<Invite> invites) {
        return new Completable() {
            @Override
            protected void subscribeActual(CompletableObserver observer) {
                try {
                    Conference conference = mDbHelper.getConferenceDao().queryForId(Long.valueOf(id));
                    conference.setTopic(topic);
                    conference.setDetail(detail);
                    conference.setDate(date);
                    conference.setLocationName(location);
                    conference.setStatus(Constant.ConferenceStatus.SCHEDULED);
                    mDbHelper.getConferenceDao().update(conference);
                    mDbHelper.removeInvites(Long.valueOf(id));
                    mDbHelper.getInviteDao().create(invites);
                    observer.onComplete();
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        };
    }

    public Completable cancelConference(final String conferenceId) {
        return new Completable() {
            @Override
            protected void subscribeActual(CompletableObserver observer) {
                try {
                    long confId = Long.valueOf(conferenceId);
                    Conference conference = mDbHelper.getConferenceDao().queryForId(confId);
                    conference.setStatus(Constant.ConferenceStatus.CANCELLED);
                    mDbHelper.getConferenceDao().update(conference);
                    observer.onComplete();
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        };
    }

    public Single<List<Conference>> loadConferencesForDoctor() {
        return new Single<List<Conference>>() {
            @Override
            protected void subscribeActual(SingleObserver<? super List<Conference>> observer) {
                try {
                    List<Conference> conferences = mDbHelper.getSchedule(getUserId());
                    observer.onSuccess(conferences);
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        };
    }

    private long getUserId() {
        return Long.parseLong(mPrefHelper.get(
                PrefHelper.KEY_USER_ID, "0"
        ));
    }

    public Single<Long> getUserIdCallable() {
        return new Single<Long>() {
            @Override
            protected void subscribeActual(SingleObserver<? super Long> observer) {
                try {
                    observer.onSuccess(Long.parseLong(mPrefHelper.get(
                            PrefHelper.KEY_USER_ID, "0"
                    )));
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        };
    }

    public Single<List<Conference>> loadInvites() {
        return new Single<List<Conference>>() {
            @Override
            protected void subscribeActual(SingleObserver<? super List<Conference>> observer) {
                try {
                    List<Invite> invites = mDbHelper.getInvites(getUserId());
                    List<Conference> conferences = new ArrayList<>();
                    for (Invite invite : invites) {
                        Conference conference = mDbHelper.getConferenceDao().queryForId(invite.getConferenceId());
                        if (conference != null) {
                            conferences.add(conference);
                        }
                    }
                    observer.onSuccess(conferences);
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        };
    }

    public Single<List<Doctor>> loadDoctors() {
        return new Single<List<Doctor>>() {
            @Override
            protected void subscribeActual(SingleObserver<? super List<Doctor>> observer) {
                try {
                    observer.onSuccess(mDbHelper.getDoctorDao().queryForAll());
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        };
    }

    public Completable logout() {
        return new Completable() {
            @Override
            protected void subscribeActual(CompletableObserver s) {
                try {
                    mPrefHelper.set(PrefHelper.KEY_USER_ID, "0");
                    s.onComplete();
                } catch (Exception e) {
                    s.onError(e);
                }
            }
        };
    }

    public Single<List<Conference>> changeInviteStatus(Conference conference, String status) {
        try {

            if (mDbHelper.changeInviteStatus(conference, getUserId(), status)) {
                return loadInvites();
            } else {
                return new Single<List<Conference>>() {
                    @Override
                    protected void subscribeActual(SingleObserver<? super List<Conference>> observer) {
                        observer.onError(new RuntimeException("No Invite found"));
                    }
                };
            }
        } catch (final Exception e) {
            return new Single<List<Conference>>() {
                @Override
                protected void subscribeActual(SingleObserver<? super List<Conference>> observer) {
                    observer.onError(e);
                }
            };
        }
    }

    public Completable submitSuggestion(final String topic, final String detail) {
        return new Completable() {
            @Override
            protected void subscribeActual(CompletableObserver s) {
                try {
                    Suggestion suggestion = new Suggestion();
                    suggestion.setTopic(topic);
                    suggestion.setDetail(detail);
                    suggestion.setDoctorId(getUserId());
                    mDbHelper.getSuggestionDao().create(suggestion);
                    s.onComplete();
                } catch (Exception e) {
                    s.onError(e);
                }
            }
        };
    }
}
