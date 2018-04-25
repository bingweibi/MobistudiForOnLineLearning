package xyz.kfdykme.mobistudi.structmap.view;

import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import chinaykc.mobistudi.R;
import xyz.kfdykme.mobistudi.bean.StudyCourse;
import xyz.kfdykme.mobistudi.bean.StudySubject;
import xyz.kfdykme.mobistudi.common.BaseViewImpl;
import xyz.kfdykme.mobistudi.common.MobiActivity;
import xyz.kfdykme.mobistudi.structmap.model.KfMapData;
import xyz.kfdykme.mobistudi.structmap.presenter.StructMapEventListener;

/**
 * Project Name: Mobistudi
 * Class Description:
 *
 * @author kf
 * @date 2017/9/8 22:11
 * Last Edit on 2017/9/8 22:11
 * 修改备注：
 */

public class StructMapView extends BaseViewImpl<StructMapEventListener> implements IStructMapView {

    private StructView mapView;
    private View rootView;

    public StructMapView(MobiActivity mActivity) {
        super(mActivity);
        rootView = LayoutInflater.from(mActivity).inflate(R.layout.activity_course_unit_map,null);
    }

    @Override
    public void initView(StudySubject mSubject) {
        mapView = new StructView(getActivity());
        mapView.setSubjectName(mSubject.getTitle());
        mapView.setOnItemViewClickListener(new StructView.OnItemViewClickListener() {
            @Override
            public void onClick(StructView.KfMapNodeView view) {
                if (getEventListener() != null){
                    getEventListener().onClickView(view);
                }
            }

            @Override
            public void onLongClick(StructView.KfMapNodeView view) {
                if (getEventListener() != null) {
                    getEventListener().onLongClickView(view);
                }
            }
        });
        List<KfMapData<String>> data = new ArrayList<>();
        for (int i = 0; i < mSubject.getStudyCourses().size(); i++) {
            data.add(new KfMapData<>(mSubject.getStudyCourses().get(i).getTitle()));
        }

        for (int i = 0; i < mSubject.getStudyCourses().size(); i++) {
            KfMapData d = data.get(i);

            d.setNex(new ArrayList<KfMapData<String>>());
            for (StudyCourse c : mSubject.getStudyCourses()) {
                if (c.getTitle().equals(d.getData())) {
                    for (StudyCourse c2 : c.getNexCourses()) {
                        for (KfMapData<String> d2 : data) {
                            if (c2.getTitle().equals(d2.getData())) {
                                d.getNex().add(d2);
                            }
                        }
                    }
                }
            }
            mapView.setData(data);
            mapView.show();
            //rootView = mapView;
        }
    }

    @Override
    public View getView() {
        return rootView;
    }

    public StructView getMapView() {
        return mapView;
    }
}
