package xyz.kfdykme.mobistudi.tea.data;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import chinaykc.mobistudi.R;
import xyz.kfdykme.mobistudi.activity.CourseListActivity;
import xyz.kfdykme.mobistudi.adapter.CourseBlockAdapter;
import xyz.kfdykme.mobistudi.adapter.CourseBlockAdapter3;
import xyz.kfdykme.mobistudi.bean.StudyCourse;

/**
 * @author cxf
 * 我->我的课程
 */
public class DataActivity extends AppCompatActivity {

    List<View> views = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        TabLayout tl = (TabLayout) findViewById(R.id.tl);
        final ViewPager vp = (ViewPager) findViewById(R.id.vp);

        views.add(LayoutInflater.from(this).inflate(R.layout.view_tea_data_list,null));
        views.add(LayoutInflater.from(this).inflate(R.layout.view_tea_data_detail,null));

        View l = views.get(0);

        l.findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp.setCurrentItem(1);
            }
        });
        l.findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp.setCurrentItem(1);
            }
        });

        View detail = views.get(1);
        final List<StudyCourse> list =  new ArrayList<>();
        final TabLayout tl_d = (TabLayout) detail.findViewById(R.id.tl_detail);
        tl_d.addTab(tl_d.newTab().setText("学生"));
        tl_d.addTab(tl_d.newTab().setText("总体"));
        final RecyclerView rv = (RecyclerView) detail.findViewById(R.id.rv);
        rv.setLayoutManager(new GridLayoutManager(this, 6));
        rv.setItemAnimator(new DefaultItemAnimator());
        for(int i =0; i < 5;i++){
            list.add(new StudyCourse(i+"","","",null,null,null));
        }

        final CourseBlockAdapter adapter = new CourseBlockAdapter(this,list);
        adapter.setItemClickListener(new CourseBlockAdapter.ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(DataActivity.this, DataDetailActivity.class);
                startActivity(i);
            }
        });
        rv.setAdapter(adapter);

        tl_d.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                List<StudyCourse> list =  new ArrayList<>();
                CourseBlockAdapter adapter = null;
                CourseBlockAdapter3 adapter1 = null;
                if("学生".equals(tab.getText())){
                    for(int i =0; i < 5;i++){
                        list.add(new StudyCourse(i+"","","",null,null,null));
                    }
                    adapter = new CourseBlockAdapter(getApplicationContext(),list);
                    adapter.setItemClickListener(new CourseBlockAdapter.ItemClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            Intent i = new Intent(DataActivity.this, DataDetailActivity.class);
                            startActivity(i);
                        }
                    });
                    rv.setAdapter(adapter);
                }
                if("总体".equals(tab.getText())){
                    for(int i =0; i < 10;i++){
                        list.add(new StudyCourse(i+"","","",null,null,null));
                    }
                    adapter1 = new CourseBlockAdapter3(getApplicationContext(),list);
                    adapter1.setItemClickListener(new CourseBlockAdapter3.ItemClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            Intent i = new Intent(DataActivity.this, DataDetailActivity.class);
                            startActivity(i);
                        }
                    });
                    rv.setAdapter(adapter1);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        vp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(views.get(position));
                return views.get(position);
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch(position){
                    case 0:
                        return "列表";
                    case 1:
                        return "详情";
                        default:
                }

                return super.getPageTitle(position);
            }
        });

        tl.setupWithViewPager(vp);
    }
}
