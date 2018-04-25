package xyz.kfdykme.mobistudi.question;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.widget.Toast;

import java.util.ArrayList;

import cat.mobistudi.CustomSGLayoutManager;
import cat.mobistudi.ErrTextAdapter;
import chinaykc.mobistudi.R;
import xyz.kfdykme.mobistudi.activity.CourseDetailActivity;

/**
 * @author bbw
 */
public class Error extends AppCompatActivity {

    private int errTextPosition;
    private ArrayList<String> errlist = new ArrayList<>();
    private RecyclerView errorQuestionRecyclerView;
    private ErrTextAdapter errTextAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        errorQuestionRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView_catError);
        errlist = getIntent().getStringArrayListExtra("error");
        if (errlist.size() ==0){
            errlist.add("恭喜你答对了所有题目!");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        CustomSGLayoutManager mCustomSGLayoutManager = new CustomSGLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        mCustomSGLayoutManager.scrollToPositionWithOffset(errTextPosition,0);
        mCustomSGLayoutManager.setSpeedRatio(1.00);
        errorQuestionRecyclerView.setLayoutManager(mCustomSGLayoutManager);
        new LinearSnapHelper().attachToRecyclerView(errorQuestionRecyclerView);
        errTextAdapter = new ErrTextAdapter(errlist,getApplicationContext());
        errorQuestionRecyclerView.setAdapter(errTextAdapter);
        errTextAdapter.notifyDataSetChanged();
    }
}
