package xyz.kfdykme.mobistudi.common;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Project Name: Mobistudi2
 * Class Description:
 * Created by kf on 2017/8/22 00:59.
 * Last Edit on 2017/8/22 00:59
 * 修改备注：
 */

public class MobiApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext.init(getApplicationContext());
        Realm.init(AppContext.getContext());
        RealmConfiguration mobiConfig = new RealmConfiguration.Builder()
                .name("mobistudi.realm")
                .build();
        Realm.setDefaultConfiguration(mobiConfig);

    }


}
