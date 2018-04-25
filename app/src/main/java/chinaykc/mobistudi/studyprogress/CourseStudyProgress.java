package chinaykc.mobistudi.studyprogress;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import chinaykc.mobistudi.R;
import xyz.kfdykme.mobistudi.activity.CourseDetailActivity;

/**
 * @author ykc
 * 课程某一章节的学习进程
 * 首页->学习进度->点击某一章节按钮
 */
public class CourseStudyProgress extends AppCompatActivity {

    private String learnProgress;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button buttonPeach1;
    private Button buttonPeach2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_study_progress);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }

        learnProgress = getIntent().getStringExtra("learnProgress");
        button1 = (Button) findViewById(R.id.button_1);
        button2 = (Button) findViewById(R.id.button_2);
        button3 = (Button) findViewById(R.id.button_3);
        buttonPeach1 = (Button) findViewById(R.id.peach_1);
        buttonPeach2 = (Button) findViewById(R.id.peach_2);
    }

    @Override
    protected void onStart() {
        super.onStart();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseStudyProgress.this, CourseDetailActivity.class);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseStudyProgress.this, CourseDetailActivity.class);
                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseStudyProgress.this, CourseDetailActivity.class);
                startActivity(intent);
            }
        });

        buttonPeach1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseStudyProgress.this, CourseDetailActivity.class);
                startActivity(intent);
            }
        });

        buttonPeach2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseStudyProgress.this, CourseDetailActivity.class);
                startActivity(intent);
            }
        });

        if (Integer.parseInt(learnProgress)>=11){
            button3.setBackground(getResources().getDrawable(R.drawable.done));
            button2.setBackground(getResources().getDrawable(R.drawable.done));
            button1.setBackground(getResources().getDrawable(R.drawable.done));
        }else if (Integer.parseInt(learnProgress)>=6){
            button2.setBackground(getResources().getDrawable(R.drawable.done));
            button1.setBackground(getResources().getDrawable(R.drawable.done));
        }else if(Integer.parseInt(learnProgress)>=3){
            button1.setBackground(getResources().getDrawable(R.drawable.done));
        }
    }
}
