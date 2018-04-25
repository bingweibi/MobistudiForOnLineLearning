package czd.mobistudi.md.app;

import android.app.Application;
import android.content.Context;

import net.danlew.android.joda.JodaTimeAndroid;

import chinaykc.mobistudi.BuildConfig;
import czd.mobistudi.md.ui.activity.CrashLogActivity;

public class AppController extends Application implements Thread.UncaughtExceptionHandler {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

//        JodaTimeAndroid.init(this);

        if (!BuildConfig.DEBUG) {
            Thread.setDefaultUncaughtExceptionHandler(this);
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable e) {
        CrashLogActivity.start(this, e);
        System.exit(1);
    }

    public static Context getContext() {
        return context;
    }

}
