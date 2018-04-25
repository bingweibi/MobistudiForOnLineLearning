package xyz.kfdykme.mobistudi.common;

import android.content.Context;

/**
 * Project Name: Mobistudi2
 * Class Description:
 * Created by kf on 2017/8/22 01:09.
 * Last Edit on 2017/8/22 01:09
 * 修改备注：
 */

public class AppContext {
    public static Context sContext;

    public static Context getContext() {
        return sContext;
    }

    public static void init(Context sContext) {
        AppContext.sContext = sContext;
    }
}
