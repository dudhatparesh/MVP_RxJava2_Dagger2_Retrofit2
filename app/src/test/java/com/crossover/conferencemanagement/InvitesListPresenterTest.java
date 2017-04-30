package com.crossover.conferencemanagement;

import android.content.Context;

import com.crossover.conferencemanagement.data.DataManager;
import com.crossover.conferencemanagement.data.model.Conference;
import com.crossover.conferencemanagement.di.ActivityContext;
import com.crossover.conferencemanagement.ui.main.invites.InvitesMvpView;
import com.crossover.conferencemanagement.ui.main.invites.InvitesPresenter;
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
public class InvitesListPresenterTest {
    /*@Rule
    public final ImmediateSchedulersRule rule = new ImmediateSchedulersRule();
    */
    @Mock
    InvitesMvpView mvpView;
    @Mock
    DataManager dataManager;
    @Mock
    @ActivityContext
    Context context;
    private InvitesPresenter presenter;

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
        //LocalBroadcastManager manager=context.getSystemService(Context);
        presenter = new InvitesPresenter(dataManager, null);
        presenter.attachView(mvpView);
    }

    @Test
    public void checkEmptyList() {
        when(dataManager.loadInvites()).thenReturn(Single.<List<Conference>>just(new ArrayList<Conference>()));
        presenter.loadInvites();
        verify(mvpView).displayEmptyList();
    }

    @Test
    public void checkInviteList() {
        List<Conference> list = new ArrayList<>();
        list.add(new Conference());
        when(dataManager.loadInvites()).thenReturn(Single.<List<Conference>>just(list));
        presenter.loadInvites();
        verify(mvpView).displayInvites(list);
    }

    @Test
    public void checkError() {
        RuntimeException exception = new RuntimeException("This is error");
        when(dataManager.loadInvites()).thenReturn(Single.<List<Conference>>error(exception));
        presenter.loadInvites();
        verify(mvpView).displayError(exception.getLocalizedMessage());
    }

    @Test
    public void checkInviteAccepted() {
        Conference conference = new Conference();
        when(dataManager.changeInviteStatus(conference, Constant.InviteStatus.ACCEPTED)).thenReturn(Single.<List<Conference>>just(new ArrayList<Conference>()));
        presenter.acceptInvite(conference);
        verify(mvpView).displayEmptyList();
    }

    @Test
    public void checkInviteRejected() {
        Conference conference = new Conference();
        when(dataManager.changeInviteStatus(conference,
                Constant.InviteStatus.REJECTED)).thenReturn(Single.<List<Conference>>just(new ArrayList<Conference>()));
        presenter.rejectInvite(conference);
        verify(mvpView).displayEmptyList();
    }

}
