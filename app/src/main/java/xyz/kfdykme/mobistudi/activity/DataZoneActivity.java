package xyz.kfdykme.mobistudi.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import chinaykc.mobistudi.R;
import xyz.kfdykme.mobistudi.adapter.CourseBlockAdapter;
import xyz.kfdykme.mobistudi.bean.StudyCourse;
import xyz.kfdykme.mobistudi.bean.StudyUser;
import xyz.kfdykme.mobistudi.model.LocalDataStruct;

/**
 * @author cxf
 * 我->我的孩子->点击按钮->dataZone
 */
public class DataZoneActivity extends AppCompatActivity {

    private RecyclerView mRv;
    private StudyUser Me;
    private CourseBlockAdapter mAdapter;
    private Spinner mSpinner;
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private ViewPager mViewPager;
    private TextView mTvGoCourse;
    private TextView mTvCourseTitle;
    private StudyCourse currentCourse;
    private int currentCoursePos;
    private View currentView;
    private RelativeLayout mRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_zone);
        //load user
        Me = LocalDataStruct.readLocalData().getLoginedUser();
        setTitle(Me.getUserName());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        List<StudyCourse> list =  new ArrayList<>();
        CourseBlockAdapter adapter = null;
            for(int i =0; i < 40;i++){
                list.add(new StudyCourse(i+"","","",null,null,null));
            }
            adapter = new CourseBlockAdapter(getApplicationContext(),list);
            adapter.setItemClickListener(new CourseBlockAdapter.ItemClickListener() {
                @Override
                public void onClick(View view, int position) {
                     Toast.makeText(getApplicationContext(),"参考课程，无数据", Toast.LENGTH_SHORT).show();
                }
            });
        rv.setLayoutManager(new GridLayoutManager(this, 5));
        rv.setItemAnimator(new DefaultItemAnimator());

        rv.setAdapter(adapter);

//        initView();
    }
//
//    private void initView(){
//        mRv = (RecyclerView) findViewById(R.id.content_data_zone_rv);
//
//        mRl = (RelativeLayout) findViewById(R.id.rl_detail);
//        LinearLayoutManager lm = new LinearLayoutManager(this);
//        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
//        mRv.setLayoutManager(new GridLayoutManager(getApplicationContext(),7));
//        mSpinner = (Spinner) findViewById(R.id.content_data_zone_spinner);
//
//
//        final List<String> list = new ArrayList<String>();
//
//        for(int i =0;i<5;i++)
//            for(StudySubject s :MainActivity.localData.findStudySubject(Me.getDoingSubjectIds() )){
//                    list.add(s.getTitle());
//            }
//
//        if(list.size() == 0)
//        {
//            mSpinner.setVisibility(View.GONE);
//            mRl.setVisibility(View.GONE);
//            mRv.setVisibility(View.GONE);
//        } else{
//
//            mSpinner.setVisibility(View.VISIBLE);
//            mRl.setVisibility(View.VISIBLE);
//            mRv.setVisibility(View.VISIBLE);
//        }
//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        mSpinner.setAdapter(adapter);
//
//        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.i("DataZoneActivity",list.get(i));
//                selectedSubjcet(list.get(i));
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//
//        mTvGoCourse = (TextView) findViewById(R.id.content_data_zone_to_detail);
//        mTvGoCourse.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(getApplicationContext(),CourseDetailActivity.class);
//                startActivity(intent);
//                if(currentCourse!=null)
//                    EventBus.getDefault().postSticky(new CourseDetailEvent(currentCourse));
//            }
//        });
//
//        mTvCourseTitle = (TextView) findViewById(R.id.content_data_zone_course_title);
//
//    }
//
//    private void selectedSubjcet(String subjectTitle) {
//        StudySubject subject = null;
//        for(int i =0;i<15;i++)
//            for (StudySubject s : MainActivity.localData.getLocalSubjects()) {
//                if (s.getTitle().equals(subjectTitle))
//                    subject = s;
//            }
//
//        if(subject.getStudyCourses().size() == 0)
//        {
//            mRv.setVisibility(View.GONE);
//            mRl.setVisibility(View.GONE);
//        } else{
//            mRv.setVisibility(View.VISIBLE);
//            mRl.setVisibility(View.VISIBLE);
//        }
//        if (subject != null)
//            mAdapter = new CourseBlockAdapter(this, subject.getStudyCourses());
//        final StudySubject finalSubject = subject;
//        mAdapter.setItemClickListener(new CourseBlockAdapter.ItemClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//
//                selectCourse(view,finalSubject.getStudyCourses().get(position));
//
//            }
//        });
//        mRv.setAdapter(mAdapter);
//
//    }
//
//    private void selectCourse(View view,StudyCourse course){
//
//        LinearLayout lL;
//        if(currentView != null) {
//            lL = (LinearLayout) currentView.getParent();
//            lL.setBackgroundColor(Color.parseColor("#ffffff"));
//            //currentView.setBackgroundColor(Color.parseColor("#00b4ff"));
//        }
//        currentView = view;
//        lL = (LinearLayout) view.getParent();
//        lL.setBackgroundColor(Color.rgb((int)(Math.random()*250),(int)(Math.random()*250),(int)(Math.random()*250)));
//        Log.i("DataZoneActivity",""+mFragments.size());
//        currentCourse = course;
//        mTvCourseTitle.setText(course.getTitle());
//
//        int gw=0,aw= 0,bw = 0;
//        if(course.getCourseDataPerUsers()!=null)
//        for(CourseDataPerUser c:course.getCourseDataPerUsers()){
//            if(c.getUser()!=null && c.getUser().getId().equals(Me.getId())){
//                gw = c.getData().getGoodWegiht();
//                aw = c.getData().getAllWeight();
//                bw = c.getData().getBadWeight();
//            }
//        }
////        if(gw == bw){
////            view.setBackgroundColor(Color.parseColor("#eeeeee"));
////        }
////        if(gw > bw){
////            view.setBackgroundColor(Color.argb((int)(((double)(gw*250/aw))),0,0,250));
////        }
////        if(gw < bw){
////            view.setBackgroundColor(Color.argb((int)(((double)(bw*250/aw))),250,0,0));
////        }
////        //view.setBackgroundColor(Color.rgb((int)(Math.random()*250),(int)(Math.random()*250),(int)(Math.random()*250)));
//
//        view.setBackgroundColor(Color.rgb((int)((Math.random()*4)*50)+50,(int)((Math.random()*4)*50)+50,(int)((Math.random()*4)*50)+50));
//
//        Log.i("DataZone",new Gson().toJson(course.getCourseDataPerUsers()));
////        Intent intent = new Intent(this,CourseDetailActivity.class);
////        startActivity(intent);
////        EventBus.getDefault().postSticky(new CourseDetailEvent(course));
//    }

}
