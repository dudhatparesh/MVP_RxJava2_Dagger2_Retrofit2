package com.crossover.conferencemanagement;

import com.crossover.conferencemanagement.data.DataManager;
import com.crossover.conferencemanagement.ui.main.MainActivity;
import com.crossover.conferencemanagement.ui.authentication.login.LoginMvpView;
import com.crossover.conferencemanagement.ui.authentication.login.LoginPresenter;

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
public class LoginPresenterTest {
    /*@Rule
    public final ImmediateSchedulersRule rule = new ImmediateSchedulersRule();
    */
    @Mock
    LoginMvpView mvpView;
    @Mock
    DataManager dataManager;
    private LoginPresenter presenter;

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

        presenter = new LoginPresenter(dataManager);
        presenter.attachView(mvpView);
    }

    @Test
    public void checkValidAdminLogin() {
        when(dataManager.login("admin", "1234", Constant.UserType.Admin)).thenReturn(Single.just(true));
        presenter.login("admin", "1234", Constant.UserType.Admin);
        verify(mvpView).displayToast(R.string.logged_in_successfully);
        verify(mvpView).openActivity(MainActivity.class);
    }

    @Test
    public void checkInvalidAdminLogin() {
        when(dataManager.login("admin", "1234", Constant.UserType.Admin)).thenReturn(Single.just(false));
        presenter.login("admin", "1234", Constant.UserType.Admin);
        verify(mvpView).displayToast(R.string.login_failed);
        verify(mvpView, never()).openActivity(MainActivity.class);
    }

    @Test
    public void checkValidDoctorLogin() {
        when(dataManager.login("doctor", "1234", Constant.UserType.Doctor)).thenReturn(Single.just(true));
        presenter.login("doctor", "1234", Constant.UserType.Doctor);
        verify(mvpView).displayToast(R.string.logged_in_successfully);
        verify(mvpView).openActivity(MainActivity.class);
    }

    @Test
    public void checkInvalidDoctorLogin() {
        when(dataManager.login("admin", "1234", Constant.UserType.Admin)).thenReturn(Single.just(false));
        presenter.login("admin", "1234", Constant.UserType.Admin);
        verify(mvpView).displayToast(R.string.login_failed);
        verify(mvpView, never()).openActivity(MainActivity.class);
    }

    @Test
    public void checkException() {
        Exception e = new RuntimeException("There is an error");
        when(dataManager.login("admin", "1234", Constant.UserType.Admin)).thenReturn(Single.<Boolean>error(e));
        presenter.login("admin", "1234", Constant.UserType.Admin);
        verify(mvpView).displayToast(e.getLocalizedMessage());
        verify(mvpView, never()).openActivity(MainActivity.class);
    }


}
