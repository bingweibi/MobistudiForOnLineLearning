package xyz.kfdykme.mobistudi.question;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cat.mobistudi.HttpUtil;
import chinaykc.mobistudi.MainActivity;
import chinaykc.mobistudi.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import xyz.kfdykme.mobistudi.activity.CourseDetailActivity;
import xyz.kfdykme.mobistudi.activity.CourseListActivity;
import xyz.kfdykme.mobistudi.bean.StudyCourse;
import xyz.kfdykme.mobistudi.eventbus.QuestionList;
import xyz.kfdykme.mobistudi.eventbus.UserID;

/**
 * @author cxf
 */
public class QuestionActivity extends AppCompatActivity {

    private String courseTitle;
    private TextView description;
    private RadioGroup radioGroupAnswer;
    private RadioButton radioButtonChecked;
    private RadioButton radioButtonA;
    private RadioButton radioButtonB;
    private RadioButton radioButtonC;
    private RadioButton radioButtonD;
    private Button nextQuestion;
    private List<xyz.kfdykme.mobistudi.bean.Question> questionList = new ArrayList<>();
    private ArrayList<String> errList = new ArrayList<>();
    private int cursor = 0;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        EventBus.getDefault().register(this);

        description = (TextView) findViewById(R.id.activity_question_questionDescription);
        radioGroupAnswer = (RadioGroup) findViewById(R.id.radioGroup_answer);
        radioButtonA = (RadioButton) findViewById(R.id.A);
        radioButtonB = (RadioButton) findViewById(R.id.B);
        radioButtonC = (RadioButton) findViewById(R.id.C);
        radioButtonD = (RadioButton) findViewById(R.id.D);
        nextQuestion = (Button) findViewById(R.id.nextQuestion);
    }

    @Override
    protected void onStart() {
        super.onStart();

        description.setText(questionList.get(0).getDescription());
        radioButtonA.setText(questionList.get(0).getA());
        radioButtonB.setText(questionList.get(0).getB());
        radioButtonC.setText(questionList.get(0).getC());
        radioButtonD.setText(questionList.get(0).getD());

        nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showQuestion();
            }
        });
    }

    private void showQuestion() {

        for (int j = 0; j < radioGroupAnswer.getChildCount(); j++){
            radioButtonChecked = (RadioButton) radioGroupAnswer.getChildAt(j);
            if (radioButtonChecked.isChecked()){
                String choiceNum = String.valueOf(radioButtonChecked.getText().charAt(0)).trim();
                if (!choiceNum.equals(questionList.get(cursor).getCorrect().trim())){
                    errList.add(questionList.get(cursor).getDescription());
                }
            }
        }
        radioGroupAnswer.clearCheck();
        cursor++;
        if(cursor == questionList.size() ){
            Intent intent = new Intent(QuestionActivity.this,Error.class);
            intent.putStringArrayListExtra("error", errList);
            //提交当前的学习进度
            updateLearnProgress(String.valueOf(cursor));
            startActivity(intent);
            return;
        }
        description.setText(questionList.get(cursor).getDescription());
        radioButtonA.setText(questionList.get(cursor).getA());
        radioButtonB.setText(questionList.get(cursor).getB());
        radioButtonC.setText(questionList.get(cursor).getC());
        radioButtonD.setText(questionList.get(cursor).getD());
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onGetCourseId(StudyCourse studyCourse){
        courseTitle = studyCourse.getTitle();
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onGetQuestionList(QuestionList questionList1){
        questionList = questionList1.getQuestionList();
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onGetuserID(UserID userID){
        this.userID = Integer.parseInt(userID.getUserID());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

           startActivity(new Intent(this, CourseDetailActivity.class));
           finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void updateLearnProgress(String progress) {
        String urlUpdateLearnDay = "http://202.115.30.153/testUpdateLearnProgress.php?learnProgress="+progress+"&id="+userID;
        HttpUtil.sendOKHttpRequest(urlUpdateLearnDay, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(String.valueOf(responseText));
                    int retCode = jsonObject.getInt("success");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
