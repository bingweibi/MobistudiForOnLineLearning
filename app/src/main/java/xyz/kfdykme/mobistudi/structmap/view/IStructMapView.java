package xyz.kfdykme.mobistudi.structmap.view;

import xyz.kfdykme.mobistudi.bean.StudySubject;
import xyz.kfdykme.mobistudi.common.IBaseView;
import xyz.kfdykme.mobistudi.structmap.presenter.StructMapEventListener;
import xyz.kfdykme.mobistudi.structmap.presenter.StructMapPresenter;

/**
 * Project Name: Mobistudi
 * Class Description:
 * Created by kf on 2017/9/8 21:16.
 * Last Edit on 2017/9/8 21:16
 * 修改备注：
 */

public interface IStructMapView extends IBaseView<StructMapEventListener> {


    void initView(StudySubject subject);
}
