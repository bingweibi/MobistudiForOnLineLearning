package xyz.kfdykme.mobistudi.common;

/**
 * Project Name: Mobistudi2
 * Class Description:
 * Created by kf on 2017/8/22 00:55.
 * Last Edit on 2017/8/22 00:55
 * 修改备注：
 */

public interface IBasePresenter <M extends IBaseModel,V extends  IBaseView>{
    void setModel(M model);

    void addView(V view);

    void attach();

    void detach();

}
