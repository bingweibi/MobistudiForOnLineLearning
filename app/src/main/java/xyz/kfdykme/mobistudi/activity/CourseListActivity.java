package xyz.kfdykme.mobistudi.activity;

import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import chinaykc.mobistudi.MainActivity;
import chinaykc.mobistudi.R;
import xyz.kfdykme.mobistudi.adapter.CourseListAdapter;
import xyz.kfdykme.mobistudi.common.MobiActivity;
import xyz.kfdykme.mobistudi.eventbus.CourseListEvent;

/**
 * 我->课程
 * 首页->开始学习
 * @author yxf
 */
public class CourseListActivity extends MobiActivity {

    private RecyclerView courseListView;
    private CourseListAdapter mAdapter;
    private Snackbar mSnackbar;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        initToolbar(mToolbar);

        courseListView = (RecyclerView) findViewById(R.id.activity_course_list_recyclerview);
        courseListView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
        mSnackbar = Snackbar.make(courseListView,"Loading",Snackbar.LENGTH_INDEFINITE);
        mSnackbar.show();
        EventBus.getDefault().postSticky(new CourseListEvent(MainActivity.localData.getLocalModules()));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onCourseListEvent(CourseListEvent e){
        mAdapter = new CourseListAdapter(this,e.getStudyModules());
        courseListView.setAdapter(mAdapter);
        courseListView.setItemAnimator(new DefaultItemAnimator());
        mSnackbar.dismiss();
    }
}
