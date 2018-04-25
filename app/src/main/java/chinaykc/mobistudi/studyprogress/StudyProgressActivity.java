package chinaykc.mobistudi.studyprogress;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.List;

import cat.mobistudi.HttpUtil;
import chinaykc.mobistudi.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import xyz.kfdykme.mobistudi.bean.LearnProgress;
import xyz.kfdykme.mobistudi.eventbus.UserID;

/**
 * @author ykc
 * 首页->学习进度
 * 课程的学习进度
 */
public class StudyProgressActivity extends AppCompatActivity {

    private Button button1;
    private Button button2;
    private String userID;
    private String responseText;
    private String learnProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_progress);
        EventBus.getDefault().register(this);

        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }

        button1 = (Button) findViewById(R.id.button_1);
        button2 = (Button) findViewById(R.id.button_2);
    }

    @Override
    protected void onStart() {
        super.onStart();

        findLearnProgress(userID);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudyProgressActivity.this, CourseStudyProgress.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudyProgressActivity.this, CourseStudyProgress.class);
                intent.putExtra("learnProgress",learnProgress);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void getUserTheatFromMain(UserID userID){
        this.userID = userID.getUserID();
    }

    private void findLearnProgress(String id) {

        String url = "http://202.115.30.153/testGetLearnProgress.php?id="+id;
        HttpUtil.sendOKHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("测试", "onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseText = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<LearnProgress> learnProgressList = (new Gson().fromJson(responseText,new TypeToken<List<LearnProgress>>(){}.getType()));
                        learnProgress = learnProgressList.get(0).getLearnProgress();
                        if (Integer.parseInt(learnProgress)==11){
                            button2.setBackground(getResources().getDrawable(R.drawable.done));
                        }
                    }
                });
            }
        });
    }
}
