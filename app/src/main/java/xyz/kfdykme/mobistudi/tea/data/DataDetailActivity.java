package xyz.kfdykme.mobistudi.tea.data;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import chinaykc.mobistudi.R;
import xyz.kfdykme.mobistudi.adapter.CourseBlockAdapter2;
import xyz.kfdykme.mobistudi.bean.StudyCourse;
import xyz.kfdykme.mobistudi.bean.StudySubject;
import xyz.kfdykme.mobistudi.eventbus.CourseUnitMapEvent;
import xyz.kfdykme.mobistudi.model.LocalData;
import xyz.kfdykme.mobistudi.structmap.StructMapActivity;

/**
 * 学生各章的学习情况
 * @author bibingwei
 */
public class DataDetailActivity extends AppCompatActivity {

    final List<StudyCourse> list =  new ArrayList<>();
    final List<StudyCourse> list1 =  new ArrayList<>();
    final List<StudyCourse> list2=  new ArrayList<>();
    final List<StudyCourse> list3 =  new ArrayList<>();
    final List<StudyCourse> list4 =  new ArrayList<>();
    final List<StudyCourse> list5 =  new ArrayList<>();
    final List<StudyCourse> list6 =  new ArrayList<>();
    final List<StudyCourse> list7 =  new ArrayList<>();
    final List<StudyCourse> list8 =  new ArrayList<>();
    private RecyclerView recyclerViewOfIntro;
    private RecyclerView recyclerViewOfQueue;
    private RecyclerView recyclerViewOfLinearTable;
    private RecyclerView recyclerViewOfStack;
    private RecyclerView recyclerViewOfString;
    private RecyclerView recyclerViewOfArray;
    private RecyclerView recyclerViewOfFigure;
    private RecyclerView recyclerViewOfSort;
    private RecyclerView recyclerViewOfTree;
    StudySubject subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_deatil);
        EventBus.getDefault().register(this);

        recyclerViewOfIntro = (RecyclerView) findViewById(R.id.recycler_view_intro);
        recyclerViewOfQueue = (RecyclerView) findViewById(R.id.recycler_view_queue);
        recyclerViewOfLinearTable = (RecyclerView) findViewById(R.id.recycler_view_linearTable);
        recyclerViewOfStack = (RecyclerView) findViewById(R.id.recycler_view_stack);
        recyclerViewOfString = (RecyclerView) findViewById(R.id.recycler_view_string);
        recyclerViewOfArray = (RecyclerView) findViewById(R.id.recycler_view_array);
        recyclerViewOfFigure = (RecyclerView) findViewById(R.id.recycler_view_figure);
        recyclerViewOfSort = (RecyclerView) findViewById(R.id.recycler_view_sort);
        recyclerViewOfTree = (RecyclerView) findViewById(R.id.recycler_view_tree);
    }

    @Override
    protected void onStart() {
        super.onStart();

        recyclerViewOfIntro.setLayoutManager(new GridLayoutManager(this, 6));
        recyclerViewOfIntro.setItemAnimator(new DefaultItemAnimator());
        for(int i =0; i < 1;i++){
            list.add(new StudyCourse(i+"","","",null,null,null));
        }

        final CourseBlockAdapter2 adapter = new CourseBlockAdapter2(this,list);
        adapter.setItemClickListener(new CourseBlockAdapter2.ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(DataDetailActivity.this, StructMapActivity.class);
                startActivity(i);
            }
        });
        recyclerViewOfIntro.setAdapter(adapter);

        recyclerViewOfQueue.setLayoutManager(new GridLayoutManager(this, 6));
        recyclerViewOfQueue.setItemAnimator(new DefaultItemAnimator());
        for(int i =0; i < 8;i++){
            list1.add(new StudyCourse(i+"","","",null,null,null));
        }

        final CourseBlockAdapter2 adapter1 = new CourseBlockAdapter2(this,list1);
        adapter1.setItemClickListener(new CourseBlockAdapter2.ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(DataDetailActivity.this, StructMapActivity.class);
                EventBus.getDefault().postSticky(new CourseUnitMapEvent(subject));
                startActivity(i);
                finish();
            }
        });
        recyclerViewOfQueue.setAdapter(adapter1);

        recyclerViewOfLinearTable.setLayoutManager(new GridLayoutManager(this, 6));
        recyclerViewOfLinearTable.setItemAnimator(new DefaultItemAnimator());
        for(int i =0; i < 5;i++){
            list2.add(new StudyCourse(i+"","","",null,null,null));
        }

        final CourseBlockAdapter2 adapter2 = new CourseBlockAdapter2(this,list2);
        adapter2.setItemClickListener(new CourseBlockAdapter2.ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(DataDetailActivity.this, StructMapActivity.class);
                startActivity(i);
            }
        });
        recyclerViewOfLinearTable.setAdapter(adapter2);

        recyclerViewOfStack.setLayoutManager(new GridLayoutManager(this, 6));
        recyclerViewOfStack.setItemAnimator(new DefaultItemAnimator());
        for(int i =0; i < 4;i++){
            list3.add(new StudyCourse(i+"","","",null,null,null));
        }

        final CourseBlockAdapter2 adapter3 = new CourseBlockAdapter2(this,list3);
        adapter3.setItemClickListener(new CourseBlockAdapter2.ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(DataDetailActivity.this, StructMapActivity.class);
                startActivity(i);
            }
        });
        recyclerViewOfStack.setAdapter(adapter3);

        recyclerViewOfString.setLayoutManager(new GridLayoutManager(this, 6));
        recyclerViewOfString.setItemAnimator(new DefaultItemAnimator());
        for(int i =0; i < 5;i++){
            list4.add(new StudyCourse(i+"","","",null,null,null));
        }

        final CourseBlockAdapter2 adapter4 = new CourseBlockAdapter2(this,list4);
        adapter4.setItemClickListener(new CourseBlockAdapter2.ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(DataDetailActivity.this, StructMapActivity.class);
                startActivity(i);
            }
        });
        recyclerViewOfString.setAdapter(adapter4);

        recyclerViewOfArray.setLayoutManager(new GridLayoutManager(this, 6));
        recyclerViewOfArray.setItemAnimator(new DefaultItemAnimator());
        for(int i =0; i < 11;i++){
            list5.add(new StudyCourse(i+"","","",null,null,null));
        }

        final CourseBlockAdapter2 adapter5 = new CourseBlockAdapter2(this,list5);
        adapter5.setItemClickListener(new CourseBlockAdapter2.ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(DataDetailActivity.this, StructMapActivity.class);
                startActivity(i);
            }
        });
        recyclerViewOfArray.setAdapter(adapter5);

        recyclerViewOfFigure.setLayoutManager(new GridLayoutManager(this, 6));
        recyclerViewOfFigure.setItemAnimator(new DefaultItemAnimator());
        for(int i =0; i < 11;i++){
            list6.add(new StudyCourse(i+"","","",null,null,null));
        }

        final CourseBlockAdapter2 adapter6 = new CourseBlockAdapter2(this,list6);
        adapter.setItemClickListener(new CourseBlockAdapter2.ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(DataDetailActivity.this, StructMapActivity.class);
                startActivity(i);
            }
        });
        recyclerViewOfFigure.setAdapter(adapter6);

        recyclerViewOfSort.setLayoutManager(new GridLayoutManager(this, 6));
        recyclerViewOfSort.setItemAnimator(new DefaultItemAnimator());
        for(int i =0; i < 8;i++){
            list7.add(new StudyCourse(i+"","","",null,null,null));
        }

        final CourseBlockAdapter2 adapter7 = new CourseBlockAdapter2(this,list7);
        adapter7.setItemClickListener(new CourseBlockAdapter2.ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(DataDetailActivity.this, StructMapActivity.class);
                startActivity(i);
            }
        });
        recyclerViewOfSort.setAdapter(adapter7);

        recyclerViewOfTree.setLayoutManager(new GridLayoutManager(this, 6));
        recyclerViewOfTree.setItemAnimator(new DefaultItemAnimator());
        for(int i =0; i < 8;i++){
            list8.add(new StudyCourse(i+"","","",null,null,null));
        }

        final CourseBlockAdapter2 adapter8 = new CourseBlockAdapter2(this,list8);
        adapter8.setItemClickListener(new CourseBlockAdapter2.ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(DataDetailActivity.this, StructMapActivity.class);
                startActivity(i);
            }
        });
        recyclerViewOfTree.setAdapter(adapter8);
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onCourseUnitMapEvent(LocalData e) {

        subject = e.getLocalSubjects().get(2);
        }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
