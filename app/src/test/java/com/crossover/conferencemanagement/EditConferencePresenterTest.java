package com.crossover.conferencemanagement;

import com.crossover.conferencemanagement.data.DataManager;
import com.crossover.conferencemanagement.data.model.Doctor;
import com.crossover.conferencemanagement.data.model.Invite;
import com.crossover.conferencemanagement.ui.main.editconference.EditConferenceMvpView;
import com.crossover.conferencemanagement.ui.main.editconference.EditConferencePresenter;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by PareshDudhat on 12-03-2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class EditConferencePresenterTest {
    /*@Rule
    public final ImmediateSchedulersRule rule = new ImmediateSchedulersRule();
    */
    @Mock
    EditConferenceMvpView mvpView;
    @Mock
    DataManager dataManager;
    private EditConferencePresenter presenter;

    @BeforeClass
    public static void init() {
        RxJavaPlugins.setIoSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(@NonNull Scheduler scheduler) throws Exception {
                return Schedulers.trampoline();
            }
        });
        RxJavaPlugins.setComputationSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(@NonNull Scheduler scheduler) throws Exception {
                return Schedulers.trampoline();
            }
        });
        RxJavaPlugins.setNewThreadSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(@NonNull Scheduler scheduler) throws Exception {
                return Schedulers.trampoline();
            }
        });
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
            @Override
            public Scheduler apply(@NonNull Callable<Scheduler> schedulerCallable) throws Exception {
                return Schedulers.trampoline();
            }
        });
        RxAndroidPlugins.setMainThreadSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(@NonNull Scheduler scheduler) throws Exception {
                return Schedulers.trampoline();
            }
        });
    }

    @AfterClass
    public static void tearDown() {
        RxAndroidPlugins.reset();
    }

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        presenter = new EditConferencePresenter(dataManager);
        presenter.attachView(mvpView);
    }

    @Test
    public void checkDoctorsList() {
        List<Doctor> doctors = new ArrayList<>();
        when(dataManager.loadDoctors()).thenReturn(Single.just(doctors));
        presenter.loadDoctors();
        verify(mvpView).displayDoctors(doctors);
    }

    @Test
    public void addConference() {
        String title = "Demo";
        String detail = "demo";
        String date = "2016/11/22";
        String location = "Ahmedabad";
        List<Invite> doctors = new ArrayList<>();
        when(dataManager.addConference(title, detail, location, date, doctors)).thenReturn(Completable.complete());
        presenter.addConference(title, detail, location, date, doctors);
        verify(mvpView).conferenceSaved();
    }

    @Test
    public void updateConference() {
        String title = "Demo";
        String detail = "demo";
        String date = "2016/11/22";
        String location = "Ahmedabad";
        String conferenceId = "1";
        List<Invite> doctors = new ArrayList<>();
        when(dataManager.updateConference(conferenceId, title, detail, location, date, doctors)).thenReturn(Completable.complete());
        presenter.updateConference(conferenceId, title, detail, location, date, doctors);
        verify(mvpView).conferenceUpdated();
    }

    @Test
    public void cancelConference() {
        when(dataManager.cancelConference("1")).thenReturn(Completable.complete());
        presenter.cancelConference("1");
        verify(mvpView).conferenceUpdated();
    }


}
