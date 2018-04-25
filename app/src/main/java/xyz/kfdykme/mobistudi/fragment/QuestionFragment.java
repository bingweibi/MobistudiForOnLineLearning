package xyz.kfdykme.mobistudi.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cat.mobistudi.HttpUtil;
import chinaykc.mobistudi.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import xyz.kfdykme.mobistudi.bean.Question;
import xyz.kfdykme.mobistudi.eventbus.QuestionList;
import xyz.kfdykme.mobistudi.question.QuestionActivity;
import xyz.kfdykme.mobistudi.bean.StudyCourse;

/**
 * @author cxf
 */
public class QuestionFragment extends BaseFragment {

    private View mView;
    private StudyCourse mStudyCourse;
    private List<Question> questionList = new ArrayList<>();
    private TextView mTvStart;
    public void setStudyCourse(StudyCourse studyCourse) {
        this.mStudyCourse = studyCourse;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_questions,container,false);
        mTvStart = (TextView) mView.findViewById(R.id.fragment_question_start_do_text_view);
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();

        String courseUrl = "http://202.115.30.153/testGetExceriseQuestion.php?course=" + mStudyCourse.getTitle();
        HttpUtil.sendOKHttpRequest(courseUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(),"获取题目信息失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    String responseText = response.body().string();
                    JSONArray allQuestion = new JSONArray(responseText);
                    for (int i=0;i<allQuestion.length();i++){
                        questionList.add(new Gson().fromJson(String.valueOf(allQuestion.getJSONObject(i)), xyz.kfdykme.mobistudi.bean.Question.class));
                    }
                    if (questionList.size() == 0){

                    }else {
                        mTvStart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                EventBus.getDefault().postSticky(new QuestionList(questionList));
                                Intent i = new Intent(getContext(), QuestionActivity.class);
                                EventBus.getDefault().postSticky(mStudyCourse);
                                getActivity().startActivity(i);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public static QuestionFragment newInstance(StudyCourse studyCourse) {
        QuestionFragment fragment = new QuestionFragment();
        fragment.setStudyCourse(studyCourse);
        return fragment;
    }
}
