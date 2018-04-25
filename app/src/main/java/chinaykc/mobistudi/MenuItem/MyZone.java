package chinaykc.mobistudi.MenuItem;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cat.mobistudi.HttpUtil;
import chinaykc.mobistudi.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import xyz.kfdykme.mobistudi.bean.SingInDate;
import xyz.kfdykme.mobistudi.bean.UserLastTheat;
import xyz.kfdykme.mobistudi.eventbus.LearnDays;

/**
 * @author ykc
 * 侧滑->我的空间
 */
public class MyZone extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private int learnDay;
    private String userID;
    private String today;
    private String singInDateFromRemote;
    private String responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_zone);
        EventBus.getDefault().register(this);

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findSingInDateFromRemote(userID);
            }
        });
    }

    private void updateSingInDate(String today) {
        String urlUpdateLearnDay = "http://202.115.30.153/testUpdateSingInDate.php?singInDate="+today+"&id="+userID;
        HttpUtil.sendOKHttpRequest(urlUpdateLearnDay, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("测试", "onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(String.valueOf(responseText));
                    int retCode = jsonObject.getInt("success");
                    Log.d("测试",""+retCode);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void updateLearnedDay(int learnDay) {

        String urlUpdateLearnDay = "http://202.115.30.153/testUpdateLearnDay.php?learnedDay="+learnDay+"&id="+userID;
        HttpUtil.sendOKHttpRequest(urlUpdateLearnDay, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("测试", "onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(String.valueOf(responseText));
                    int retCode = jsonObject.getInt("success");
                    Log.d("测试",""+retCode);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取上次登录日期
     */
    private void findSingInDateFromRemote(String id) {

        String url = "http://202.115.30.153/testGetSingInDate.php?id="+id;
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
                        List<SingInDate> singInDatelist = (new Gson().fromJson(responseText,new TypeToken<List<SingInDate>>(){}.getType()));
                        singInDateFromRemote = singInDatelist.get(0).getSingInDate();
                        today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                        if(singInDateFromRemote.equals(today)){
                            Toast.makeText(MyZone.this,"您已经签到了",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MyZone.this,"签到成功!",Toast.LENGTH_SHORT).show();
                            //更新学习的天数
                            updateLearnedDay(learnDay+1);
                            updateSingInDate(today);
                            fab.setClickable(false);
                        }
                    }
                });
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void getUserLearnDay(LearnDays userLearnDay){
        learnDay = userLearnDay.getLearnDay();
        userID = userLearnDay.getId();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
