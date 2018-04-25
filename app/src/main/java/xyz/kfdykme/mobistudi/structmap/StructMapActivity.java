package xyz.kfdykme.mobistudi.structmap;

import android.os.Bundle;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import xyz.kfdykme.mobistudi.bean.StudySubject;
import xyz.kfdykme.mobistudi.bean.StudyUser;
import xyz.kfdykme.mobistudi.common.MobiActivity;
import xyz.kfdykme.mobistudi.eventbus.CourseUnitMapEvent;
import xyz.kfdykme.mobistudi.model.LocalDataStruct;
import xyz.kfdykme.mobistudi.structmap.model.StructMapModel;
import xyz.kfdykme.mobistudi.structmap.presenter.StructMapPresenter;
import xyz.kfdykme.mobistudi.structmap.view.StructMapView;

/**
 * @author cxf
 * 首页->开始学习->数据结构->队列
 */

public class StructMapActivity extends MobiActivity {

    private StudyUser user;
    private StudySubject subject;
    private StructMapView mView;
    private StructMapPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = LocalDataStruct.readLocalData().getLoginedUser();
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onCourseUnitMapEvent(CourseUnitMapEvent e) {

        subject = e.getSubject();
        if(mView == null) {
            mView = new StructMapView(this);
            mPresenter = new StructMapPresenter(this);
            mPresenter.addView(mView);
            StructMapModel model = new StructMapModel();
            model.setSubject(subject);
            mPresenter.setModel(model);
            mPresenter.attach();
            //finish();
        }
    }
}