package xyz.kfdykme.mobistudi.structmap.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xyz.kfdykme.mobistudi.activity.CourseDetailActivity;
import xyz.kfdykme.mobistudi.bean.StudyCourse;
import xyz.kfdykme.mobistudi.common.BasePresenter;
import xyz.kfdykme.mobistudi.common.IBaseView;
import xyz.kfdykme.mobistudi.common.MobiActivity;
import xyz.kfdykme.mobistudi.eventbus.CourseDetailEvent;
import xyz.kfdykme.mobistudi.structmap.StructMapActivity;
import xyz.kfdykme.mobistudi.structmap.StructMapConstant;
import xyz.kfdykme.mobistudi.structmap.model.IStructMapModel;
import xyz.kfdykme.mobistudi.structmap.model.KfMapData;
import xyz.kfdykme.mobistudi.structmap.model.StructMapModel;
import xyz.kfdykme.mobistudi.structmap.view.IStructMapView;
import xyz.kfdykme.mobistudi.structmap.view.StructMapView;
import xyz.kfdykme.mobistudi.structmap.view.StructView;
import xyz.kfdykme.mobistudi.tool.FileHelper;

/**
 * Project Name: Mobistudi
 * Class Description:
 * @author kf
 * @date 2017/9/8 21:28
 * Last Edit on 2017/9/8 21:28
 * 首页->开始学习->数据结构->
 */

public class StructMapPresenter extends BasePresenter<StructMapModel,StructMapView> implements IStructMapPresenter {

    public StructMapPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onAttach() {

        getView().initView(getModel().getSubject());
        getView().setEventListener(mListener);
        ((MobiActivity)getContext()).setContentView(getView().getMapView());
    }

    @Override
    protected void onDetach() {

    }

    private StructMapEventListener mListener = new StructMapEventListener() {
        @Override
        public void onTouchView(View view, MotionEvent e) {

        }

        @Override
        public void onLongClickView(View view) {
            //TODO:save data
        }

        @Override
        public void onClickView(View view) {

            StudyCourse mCourse = null;

            for(StudyCourse course:getModel().getSubject().getStudyCourses()){
                if(course.getTitle().equals(((StructView.KfMapNodeView)view).getText().toString())){
                    mCourse = course;
                }
            }
            if (mCourse == null){
                return;
            }
            EventBus.getDefault().postSticky(new CourseDetailEvent(mCourse));
            Intent intent = new Intent(getContext(),CourseDetailActivity.class);
            getContext().startActivity(intent);
        }

        @Override
        public void onSwitchView(String view) {

        }

        @Override
        public void onSaveSettings() {

        }
    };
}
