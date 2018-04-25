package xyz.kfdykme.mobistudi.common;

import android.view.View;

/**
 * Project Name: Mobistudi2
 * Class Description:
 * Created by kf on 2017/8/22 00:50.
 * Last Edit on 2017/8/22 00:50
 * 修改备注：
 */

public interface IBaseView<E extends BaseEventListener> {
    void setEventListener(E eventListener);
}
