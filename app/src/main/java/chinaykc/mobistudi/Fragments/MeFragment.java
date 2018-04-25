package chinaykc.mobistudi.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cat.mobistudi.MyLevel;
import chinaykc.mobistudi.MainActivity;
import chinaykc.mobistudi.MenuItem.Friends;
import chinaykc.mobistudi.R;
import xyz.kfdykme.mobistudi.activity.CourseListActivity;
import xyz.kfdykme.mobistudi.bean.StudyUser;
import xyz.kfdykme.mobistudi.history.HistoryActivity;
import xyz.kfdykme.mobistudi.parents.MyChildActivity;
import xyz.kfdykme.mobistudi.tea.data.DataActivity;

/**
 * @author chinaykc
 * @date 17-5-22
 * 我
 */

public class MeFragment extends Fragment {

    private StudyUser userInfo;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button myLevel;
    private Button mBtMyCourse;
    private Button mBtMyChild;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        userInfo = ((MainActivity)(activity)).getUserInfo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_me,container,false);

        button1 = (Button) view.findViewById(R.id.history);
        button2 = (Button) view.findViewById(R.id.mycourse);
        button3 = (Button) view.findViewById(R.id.questions);
        button4 = (Button) view.findViewById(R.id.club);
        myLevel = (Button) view.findViewById(R.id.myLevel);
        mBtMyCourse = (Button) view.findViewById(R.id.bt_my_course);
        mBtMyChild = (Button) view.findViewById(R.id.bt_my_child);
        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HistoryActivity.class);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CourseListActivity.class);
                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Friends.class);
                startActivity(intent);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Friends.class);
                startActivity(intent);
            }
        });
        myLevel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyLevel.class);
                startActivity(intent);
            }
        });
        mBtMyCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DataActivity.class));
            }
        });
        mBtMyChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyChildActivity.class));
            }
        });
    }
}
