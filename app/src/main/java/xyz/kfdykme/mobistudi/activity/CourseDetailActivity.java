package xyz.kfdykme.mobistudi.activity;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import chinaykc.mobistudi.R;
import xyz.kfdykme.mobistudi.bean.StudyCourse;
import xyz.kfdykme.mobistudi.common.MobiActivity;
import xyz.kfdykme.mobistudi.eventbus.CourseDetailEvent;
import xyz.kfdykme.mobistudi.fragment.IntraductionFragment;
import xyz.kfdykme.mobistudi.fragment.QuestionFragment;

/**
 * @author cxf
 * 首页->学习进度->点击某一章节->点击某一章节的某一个知识点
 * 首页->学习天数->courseDetail
 * 首页->推荐**问题->courseDetail
 *
 * 某一章节学习进度点击桃子按钮或者某一章节按钮->courseDetail
 * 系列->点击系列精品中某一个具体的问题->视频列表->点击某一个列表
 * 首页->开始学习->数据结构->队列->某一小节
 */
public class CourseDetailActivity extends MobiActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private StudyCourse mCourse;
    private Toolbar toolbar;
    private List<Fragment> mFragments;
    private TabLayout tabLayout;
    private FloatingActionButton fab;
    private xyz.kfdykme.mobistudi.CustomVideoView mVideoView;
    private MediaController mMediaController;

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onCourseDetailEvent(CourseDetailEvent e){
        mCourse = e.getCourse();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curese_detail);

        mFragments = new ArrayList<>();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        mVideoView = (xyz.kfdykme.mobistudi.CustomVideoView) findViewById(R.id.courseVideo);
        initToolbar(toolbar);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCourse == null){

        }else{
            mFragments.add(IntraductionFragment.newInstance(mCourse.getDescription()));
            mFragments.add(QuestionFragment.newInstance(mCourse));
            mFragments.add(PlaceholderFragment.newInstance(mFragments.size()+1));
            mFragments.add(PlaceholderFragment.newInstance(mFragments.size()+1));
            mSectionsPagerAdapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mCourse == null){

        }else if ("队列".equals(mCourse.getTitle())) {
            mMediaController = new MediaController(this);
            mVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.queue));
            mVideoView.setMediaController(new MediaController(this));
            mVideoView.start();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    private void initView(){

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout.setupWithViewPager(mViewPager);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_curese_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_curese_detail, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "介绍";
                case 1:
                    return "练习";
                case 2:
                    return "更多";
                case 3:
                    return "评论";
                    default:
            }
            return null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoView.stopPlayback();
    }
}
