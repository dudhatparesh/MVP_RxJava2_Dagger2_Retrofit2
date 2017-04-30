package com.crossover.conferencemanagement;

import com.crossover.conferencemanagement.data.DataManager;
import com.crossover.conferencemanagement.data.model.Conference;
import com.crossover.conferencemanagement.data.model.Suggestion;
import com.crossover.conferencemanagement.ui.main.conferencelist.ConferenceListMvpView;
import com.crossover.conferencemanagement.ui.main.conferencelist.ConferenceListPresenter;
import com.crossover.conferencemanagement.ui.main.suggestionlist.SuggestionListMvpView;
import com.crossover.conferencemanagement.ui.main.suggestionlist.SuggestionListPresenter;
import com.crossover.conferencemanagement.util.Constant;

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
public class SuggestionListPresenterTest {
    /*@Rule
    public final ImmediateSchedulersRule rule = new ImmediateSchedulersRule();
    */
    @Mock
    SuggestionListMvpView mvpView;
    @Mock
    DataManager dataManager;
    private SuggestionListPresenter presenter;

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

        presenter = new SuggestionListPresenter(dataManager);
        presenter.attachView(mvpView);
    }

    @Test
    public void checkAdminEmptyList() {
        when(dataManager.loadAdminSuggestions()).thenReturn(Single.<List<Suggestion>>just(new ArrayList<Suggestion>()));
        when(dataManager.getUserType()).thenReturn(Single.just(Constant.UserType.Admin));
        presenter.loadSuggestions();
        verify(mvpView).displayEmptyList();
    }

    @Test
    public void checkAdminList() {
        List<Suggestion> list=new ArrayList<>();
        list.add(new Suggestion());
        when(dataManager.loadAdminSuggestions()).thenReturn(Single.<List<Suggestion>>just(list));
        when(dataManager.getUserType()).thenReturn(Single.just(Constant.UserType.Admin));
        presenter.loadSuggestions();
        verify(mvpView).displaySuggestions(list);
    }
    @Test
    public void checkAdminError() {
        RuntimeException exception = new RuntimeException("This is error");
        when(dataManager.loadAdminSuggestions()).thenReturn(Single.<List<Suggestion>>error(exception));
        when(dataManager.getUserType()).thenReturn(Single.just(Constant.UserType.Admin));
        presenter.loadSuggestions();
        verify(mvpView).displayError(exception.getLocalizedMessage());
    }


    @Test
    public void checkDoctorEmptyList() {
        when(dataManager.loadDoctorSuggestions()).thenReturn(Single.<List<Suggestion>>just(new ArrayList<Suggestion>()));
        when(dataManager.getUserType()).thenReturn(Single.just(Constant.UserType.Doctor));
        presenter.loadSuggestions();
        verify(mvpView).displayEmptyList();
    }

    @Test
    public void checkDoctorList() {
        List<Suggestion> list=new ArrayList<>();
        list.add(new Suggestion());
        when(dataManager.loadDoctorSuggestions()).thenReturn(Single.<List<Suggestion>>just(list));
        when(dataManager.getUserType()).thenReturn(Single.just(Constant.UserType.Doctor));
        presenter.loadSuggestions();
        verify(mvpView).displaySuggestions(list);
    }
    @Test
    public void checkDoctorError() {
        RuntimeException exception = new RuntimeException("This is error");
        when(dataManager.loadDoctorSuggestions()).thenReturn(Single.<List<Suggestion>>error(exception));
        when(dataManager.getUserType()).thenReturn(Single.just(Constant.UserType.Doctor));
        presenter.loadSuggestions();
        verify(mvpView).displayError(exception.getLocalizedMessage());
    }


}
