package com.crossover.conferencemanagement;

import com.crossover.conferencemanagement.data.DataManager;
import com.crossover.conferencemanagement.data.model.Doctor;
import com.crossover.conferencemanagement.data.model.Invite;
import com.crossover.conferencemanagement.ui.main.editconference.EditConferenceMvpView;
import com.crossover.conferencemanagement.ui.main.editconference.EditConferencePresenter;
import com.crossover.conferencemanagement.ui.main.editsuggestion.EditSuggestionMvpView;
import com.crossover.conferencemanagement.ui.main.editsuggestion.EditSuggestionPresenter;

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
public class EditSuggestionPresenterTest {
    /*@Rule
    public final ImmediateSchedulersRule rule = new ImmediateSchedulersRule();
    */
    @Mock
    EditSuggestionMvpView mvpView;
    @Mock
    DataManager dataManager;
    private EditSuggestionPresenter presenter;

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

        presenter = new EditSuggestionPresenter(dataManager);
        presenter.attachView(mvpView);
    }


    @Test
    public void submitSuggestion() {
        String title = "Demo";
        String detail = "demo";
        when(dataManager.submitSuggestion(title, detail)).thenReturn(Completable.complete());
        presenter.submitSuggestion(title, detail);
        verify(mvpView).suggestionSubmitted();
    }

    @Test
    public void submitSuggestionError() {
        String title = "Demo";
        String detail = "demo";
        RuntimeException exception=new RuntimeException("Demo Exception");
        when(dataManager.submitSuggestion(title, detail)).thenReturn(Completable.error(exception));
        presenter.submitSuggestion(title, detail);
        verify(mvpView).displayError(exception);
    }




}
