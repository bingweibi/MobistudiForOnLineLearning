package xyz.kfdykme.mobistudi.common;

import android.content.Context;

/**
 * Project Name: Mobistudi2
 * Class Description:
 *
 * @author kf
 * @date 2017/8/22 00:57
 * Last Edit on 2017/8/22 00:57
 * 修改备注：
 */

public abstract class BasePresenter<M extends IBaseModel,V extends IBaseView> implements IBasePresenter<M,V> {

    private M mModel;
    private V mView;
    private Context context;

    public BasePresenter(Context context) {
        this.context = context;
    }

    @Override
    public void setModel(M model) {
     mModel = model;
    }

    @Override
    public void addView(V view) {
        mView = view;
    }

    public M getModel() {
        return mModel;
    }

    public V getView(){return mView;}

    @Override
    public void attach() {
        onAttach();
    }

    @Override
    public void detach() {
        if(mModel != null){
            mModel = null;
        }
        if(mView != null){
            mView = null;
        }
        onDetach();
    }

    protected abstract void onAttach();

    protected abstract void onDetach();

    public Context getContext() {
        return context;
    }
}
