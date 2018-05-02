package xyz.kfdykme.mobistudi.fragment;


import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import chinaykc.mobistudi.Fragments.SeriesFragment;
import chinaykc.mobistudi.MainActivity;
import chinaykc.mobistudi.R;
import chinaykc.mobistudi.SeriesContent.ActivitySeriesContent;
import xyz.kfdykme.mobistudi.activity.CourseDetailActivity;
import xyz.kfdykme.mobistudi.bean.StudyCourse;

/**
 * @author kf
 * @date 2017/6/10
 */

public class MoreFragment extends BaseFragment {

    private StudyCourse studyCourse;
    private View mView;
    private TextView mTvGoSeries;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_more,container,false);
        mTvGoSeries = mView.findViewById(R.id.fragmentToSeries);
        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mTvGoSeries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivitySeriesContent.class);
                intent.putExtra("title","队列");
                intent.putExtra("content","队列是一种特殊的线性表，特殊之处在于它只允许在表的前端（front）进行删除操作，而在表的后端（rear）进行插入操作，和栈一样，队列是一种操作受限制的线性表。进行插入操作的端称为队尾，进行删除操作的端称为队头。");
                intent.putExtra("position",5);
                startActivity(intent);
            }
        });
    }

    public void setStudyCourse(StudyCourse studyCourse) {
        this.studyCourse = studyCourse;
        reflaseData();
    }

    private void reflaseData() {

    }

    public static MoreFragment newInstance(StudyCourse studyCourse) {

        MoreFragment fragment = new MoreFragment();
        fragment.setStudyCourse(studyCourse);
        return fragment;
    }
}
