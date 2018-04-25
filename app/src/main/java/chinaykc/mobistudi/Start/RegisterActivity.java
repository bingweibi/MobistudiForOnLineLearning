package chinaykc.mobistudi.Start;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cat.mobistudi.HttpUtil;
import chinaykc.mobistudi.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import xyz.kfdykme.mobistudi.bean.AllUserName;
import xyz.kfdykme.mobistudi.bean.StudyUser;
import xyz.kfdykme.mobistudi.tool.FileHelper;

import static xyz.kfdykme.mobistudi.bean.StudyUser.TYPE_NORMAL;

/**
 * @author ykc
 * 注册界面
 */

public class RegisterActivity extends AppCompatActivity {

    private EditText mEtName;
    private EditText mEtPassword;
    private String name;
    private String password;
    private String s;
    private int retCode;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private Button mButton;
    private List<AllUserName> allUserNameList;
    private int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        allUserNameList = requestAllUserName();
        mEtName = (EditText) findViewById(R.id.et_name);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mButton = (Button) findViewById(R.id.tv_yes);
        initView();
    }

    private void initView(){
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 0;
                checkUserName();
            }
        });
    }

    /**
     * 与服务器进行对比username是否已经被注册
     */
    private void checkUserName() {
        name  = mEtName.getText().toString();
        password = mEtPassword.getText().toString();
        for (int i =0;i<allUserNameList.size();i++){
            if (name.equals(allUserNameList.get(i).getUserName())){
                Toast.makeText(this,"用户名已经被注册.", Toast.LENGTH_LONG).show();
                flag = 1;
                name = null;
                password = null;
                break;
            }
        }
        if (flag == 0){
            doRegisterInLocal();
            doRegisterInRemote("http://202.115.30.153/testRegister.php");
        }
    }

    private void doRegisterInLocal(){

        //用户名昵称和密码都不能为空
        if(!name.isEmpty() && !password.isEmpty()){
            String data = null;
            try {
                data = FileHelper.readFile("users",name,"json");
            } catch (IOException e) {
                e.printStackTrace();
            }
            //如果找到已存在的id的话就显示该id已被注册，没找到就进行注册
            if(data != null && data.equals(FileHelper.NOT_FIND)){

                String content= "";
                //创建新的实例并转化为json格式保存
                StudyUser registerUser = new StudyUser( name,TYPE_NORMAL,name,password,0,0,0,"0","0", "0",0);
                content = new Gson().toJson(registerUser);
                try {
                    FileHelper.createFile("users",content,name,"json");
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
                    return ;
                }

                //when register done
                List<String> ls = new ArrayList<>();
                ls.add(name);
                ls.add(password);

                EventBus.getDefault().post(ls);
                finish();
            } else {
                Toast.makeText(this,"This id has been used.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please enter your information.", Toast.LENGTH_SHORT).show();
        }
    }

    private void doRegisterInRemote(String address) {
        //初始化okhttp客户端
        OkHttpClient client = new OkHttpClient.Builder().build();
        //创建post表单，获取username和password（没有做非空判断）
        RequestBody post = new FormBody.Builder()
                .add("username", name)
                .add("password", password)
                .add("type",Integer.toString(TYPE_NORMAL))
                .build();
        //开始请求，填入url，和表单
        final Request request = new Request.Builder()
                .url(address)
                .post(post)
                .build();

        //客户端回调
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //失败的情况（一般是网络链接问题，服务器错误等）
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                //UI线程运行
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            //临时变量（这是okhttp的一个锅，一次请求的response.body().string()只能用一次，否则就会报错）
                            RegisterActivity.this.s = response.body().string();
                            //解析出后端返回的数据来
                            JSONObject jsonObject = new JSONObject(String.valueOf(RegisterActivity.this.s));
                            retCode = jsonObject.getInt("success");
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }

                        //客户端自己判断是否成功。
                        if (retCode == 1) {
                            Toast.makeText(RegisterActivity.this,"注册成功!",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this,"注册错误!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private List<AllUserName> requestAllUserName(){
        String url = "http://202.115.30.153/testGetAllUserName.php";
        HttpUtil.sendOKHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //失败的情况
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                allUserNameList = new Gson().fromJson(responseText,new TypeToken<List<AllUserName>>(){}.getType());
            }
        });
        return allUserNameList;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

