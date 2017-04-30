package com.crossover.conferencemanagement;

import com.crossover.conferencemanagement.data.DataManager;
import com.crossover.conferencemanagement.ui.main.MainActivity;

import com.crossover.conferencemanagement.ui.option.OptionMvpView;
import com.crossover.conferencemanagement.ui.option.OptionPresenter;
import com.crossover.conferencemanagement.util.Constant;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.Callable;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by PareshDudhat on 12-03-2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class OptionPresenterTest {
    /*@Rule
    public final ImmediateSchedulersRule rule = new ImmediateSchedulersRule();
    */
    @Mock
    OptionMvpView optionMvpView;
    @Mock
    DataManager dataManager;
    private OptionPresenter presenter;

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
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
            @Override
            public Scheduler apply(@NonNull Callable<Scheduler> schedulerCallable) throws Exception {
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

        presenter = new OptionPresenter(dataManager);
        presenter.attachView(optionMvpView);
    }

    @Test
    public void checkNoUserLogin() {
        when(dataManager.getUserIdCallable()).thenReturn(Single.just(0L));
        presenter.checkUserLogin();
        verify(optionMvpView, never()).displayError(new RuntimeException());
        verify(optionMvpView, never()).openActivity(MainActivity.class, null);
    }

    @Test
    public void checkAdminUserLogin() {
        when(dataManager.getUserIdCallable()).thenReturn(Single.just(2L));
        presenter.checkUserLogin();
        verify(optionMvpView, never()).displayError(new RuntimeException());
        verify(optionMvpView).openActivity(MainActivity.class, null);
        }

    @Test
    public void checkDoctorUserLogin() {
        when(dataManager.getUserIdCallable()).thenReturn(Single.just(1L));
        presenter.checkUserLogin();
        verify(optionMvpView, never()).displayError(new RuntimeException());
        verify(optionMvpView).openActivity(MainActivity.class, null);
    }

    @Test
    public void checkException() {
        final Exception e = new RuntimeException("Demo");
        when(dataManager.getUserIdCallable()).thenReturn(Single.<Long>error(new Callable<Throwable>() {
            @Override
            public Throwable call() throws Exception {
                return e;
            }
        }));
        presenter.checkUserLogin();
        verify(optionMvpView).displayError(e);
        verify(optionMvpView, never()).openActivity(MainActivity.class, null);
    }
}
