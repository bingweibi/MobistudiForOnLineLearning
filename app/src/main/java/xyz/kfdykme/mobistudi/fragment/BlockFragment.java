package xyz.kfdykme.mobistudi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import chinaykc.mobistudi.R;
import xyz.kfdykme.mobistudi.activity.CourseDetailActivity;
import xyz.kfdykme.mobistudi.bean.StudyCourse;
import xyz.kfdykme.mobistudi.eventbus.CourseDetailEvent;

/**
 * @author kf
 * @date 2017/6/10
 */

public class BlockFragment extends BaseFragment {

    private StudyCourse studyCourse;
    private View mView;
    private TextView mTvGoCourse;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_block,container,false);
        mTvGoCourse = (TextView) mView.findViewById(R.id.fragment_block_toDetail);
        mTvGoCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(),CourseDetailActivity.class);
                getContext().startActivity(intent);
                if(studyCourse!=null){
                    EventBus.getDefault().postSticky(new CourseDetailEvent(studyCourse));
                }
            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setStudyCourse(StudyCourse studyCourse) {
        this.studyCourse = studyCourse;
        reflaseData();
    }

    private void reflaseData() {

    }

    public static BlockFragment newInstance(StudyCourse studyCourse)
    {

        BlockFragment fragment = new BlockFragment();
        fragment.setStudyCourse(studyCourse);
        return fragment;
    }

}
