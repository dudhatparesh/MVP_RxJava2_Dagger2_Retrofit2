package com.crossover.conferencemanagement;

import com.crossover.conferencemanagement.data.DataManager;
import com.crossover.conferencemanagement.ui.main.MainActivity;
import com.crossover.conferencemanagement.ui.authentication.registration.RegistrationMvpView;
import com.crossover.conferencemanagement.ui.authentication.registration.RegistrationPresenter;

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

import io.reactivex.Completable;
import io.reactivex.Scheduler;
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
public class RegistrationPresenterTest {
    /*@Rule
    public final ImmediateSchedulersRule rule = new ImmediateSchedulersRule();
    */
    @Mock
    RegistrationMvpView mvpView;
    @Mock
    DataManager dataManager;
    private RegistrationPresenter presenter;

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

        presenter = new RegistrationPresenter(dataManager);
        presenter.attachView(mvpView);
    }

    @Test
    public void checkValidAdminRegistration() {
        when(dataManager.register("admin", "1234", "", "", "Admin", Constant.UserType.Admin))
                .thenReturn(Completable.complete());
        presenter.register("admin", "1234", "", "", "Admin", Constant.UserType.Admin);
        verify(mvpView).displayToast(R.string.registered_successfully);
        verify(mvpView).openActivity(MainActivity.class);
    }


    @Test
    public void checkValidDoctorLogin() {
        when(dataManager.register("doctor", "1234", "Cardiologist", "10", "Paresh Dudhat",
                Constant.UserType.Doctor))
                .thenReturn(Completable.complete());
        presenter.register("doctor", "1234", "Cardiologist", "10", "Paresh Dudhat", Constant.UserType.Doctor);
        verify(mvpView).displayToast(R.string.registered_successfully);
        verify(mvpView).openActivity(MainActivity.class);
    }

    @Test
    public void checkAdminException() {
        Exception e = new RuntimeException("This is error");
        when(dataManager.register("admin", "1234", "", "", "Admin", Constant.UserType.Admin))
                .thenReturn(Completable.error(e));
        presenter.register("admin", "1234", "", "", "Admin", Constant.UserType.Admin);
        verify(mvpView).displayToast(e.getLocalizedMessage());
        verify(mvpView, never()).openActivity(MainActivity.class);
    }

    @Test
    public void checkDoctorException() {
        Exception e = new RuntimeException("This is error");
        when(dataManager.register("admin", "1234", "Cardio", "12", "Admin", Constant.UserType.Doctor))
                .thenReturn(Completable.error(e));
        presenter.register("admin", "1234", "Cardio", "12", "Admin", Constant.UserType.Doctor);
        verify(mvpView).displayToast(e.getLocalizedMessage());
        verify(mvpView, never()).openActivity(MainActivity.class);
    }


}
