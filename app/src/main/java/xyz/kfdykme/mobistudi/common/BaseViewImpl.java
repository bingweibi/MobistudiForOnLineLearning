package xyz.kfdykme.mobistudi.common;

import android.view.View;

/**
 * Project Name: Mobistudi2
 * Class Description:
 * Created by kf on 2017/8/22 00:51.
 * Last Edit on 2017/8/22 00:51
 * 修改备注：
 */

public abstract class BaseViewImpl<E extends BaseEventListener> implements IBaseView<E>{

    private MobiActivity mActivity;

    private E mEventListener;

    public BaseViewImpl(MobiActivity mActivity) {
        this.mActivity = mActivity;
    }

    public MobiActivity getActivity() {
        return mActivity;
    }

    @Override
    public void setEventListener(E eventListener) {
        this.mEventListener = eventListener;
    }

    public E getEventListener() {
        return mEventListener;
    }


    abstract public View getView();

}
