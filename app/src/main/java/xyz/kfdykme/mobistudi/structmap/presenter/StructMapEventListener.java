package xyz.kfdykme.mobistudi.structmap.presenter;

import android.view.MotionEvent;
import android.view.View;

import xyz.kfdykme.mobistudi.common.BaseEventListener;

/**
 * Project Name: Mobistudi
 * Class Description:
 * Created by kf on 2017/9/8 21:11.
 * Last Edit on 2017/9/8 21:11
 * 修改备注：
 */

public interface StructMapEventListener extends BaseEventListener{

    void onTouchView(View view, MotionEvent e);

    void onLongClickView(View view);

    void onClickView(View view);

    void onSwitchView(String view);

    void onSaveSettings();
}
