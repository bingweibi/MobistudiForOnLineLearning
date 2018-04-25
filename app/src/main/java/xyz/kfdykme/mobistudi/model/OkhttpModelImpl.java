package xyz.kfdykme.mobistudi.model;

import android.util.Log;
import com.google.gson.Gson;
import org.greenrobot.eventbus.EventBus;
import java.io.IOException;

import chinaykc.mobistudi.MainActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author kf
 * @date 2017/5/28
 */

public class OkhttpModelImpl implements OkhttpModel {

    /**
     * get UserData form temp data website
     */
    final String mbaseUrl = "http://kfdykme.xyz/data/json/";
    private OkHttpClient mOkHttoClient = new OkHttpClient();

    public OkhttpModelImpl(){

    }

    @Override
    public Call checkLogin() {

        String userData = "user.json";
        Request.Builder b = new Request.Builder().url(mbaseUrl+userData);
        final Request r = b.build();
        Call mCall = mOkHttoClient.newCall(r);
        return mCall;
    }

    @Override
    public Call loadUserData(String user) {
        String users = "users/";
        Request.Builder b = new Request.Builder().url(mbaseUrl+users+user+".json");
        Call mCall = mOkHttoClient.newCall(b.build());
        return mCall;
    }

    @Override
    public void loadAllData() {
        //module
        if(MainActivity.localData.getLocalModules() == null){
            new OkhttpModelImpl().loadModule().enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    String json = response.body().string();
                    BasicModel bM = new Gson().fromJson(json,BasicModel.class);
                    MainActivity.localData.setLocalModules(bM.getResult());
                    EventBus.getDefault().post(MainActivity.localData);
                    Log.i("loadData","Module");
                }
            });
        }
        //subject
        if(MainActivity.localData.getLocalSubjects() == null){
            new OkhttpModelImpl().loadSubject().enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    String json = response.body().string();
                    BasicModel bM = new Gson().fromJson(json,BasicModel.class);
                    MainActivity.localData.setLocalSubjects(bM.getSubject());
                    EventBus.getDefault().post(MainActivity.localData);
                    Log.i("loadData","Subject");
                }
            });
        }
        //course
        if(MainActivity.localData.getLocalCourses() == null){
            new OkhttpModelImpl().loadCourse().enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    String json = response.body().string();
                    BasicModel bM = new Gson().fromJson(json,BasicModel.class);
                    MainActivity.localData.setLocalCourses(bM.getCourse());
                    EventBus.getDefault().postSticky(MainActivity.localData);
                    Log.i("loadData","Course");
                }
            });
        }

        //question
//        if(MainActivity.localData.getLocalQuestion() == null){
//            new OkhttpModelImpl().loadQuestion().enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//
//                    String json = response.body().string();
//                    BasicModel bM = new Gson().fromJson(json,BasicModel.class);
//                    MainActivity.localData.setLocalQuestion(bM.getQuestion());
//                    Log.i("loadData","Question");
//                }
//            });
//        }
//
//        //answer
//        if(MainActivity.localData.getLocalAnswer() == null){
//            new OkhttpModelImpl().loadAnswer().enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//
//                    String json = response.body().string();
//                    BasicModel bM = new Gson().fromJson(json,BasicModel.class);
//                    MainActivity.localData.setLocalAnswer(bM.getAnswer());
//                    EventBus.getDefault().post(MainActivity.localData);
//                    Log.i("loadData","Answer");
//                }
//            });
//        }

        EventBus.getDefault().post(MainActivity.localData);
        Log.i("loadData","Finished");
    }

    @Override
    public Call loadModule() {
        Call mCall = mOkHttoClient.newCall(
            new Request.Builder().url("http://202.115.30.153/testModule.php").build());
        return mCall;
    }

    @Override
    public Call loadSubject() {
        Call mCall = mOkHttoClient.newCall(
                new Request.Builder().url("http://202.115.30.153/testSubject.php").build());
        return mCall;
    }

    @Override
    public Call loadCourse() {
        Call mCall = mOkHttoClient.newCall(
                new Request.Builder().url("http://202.115.30.153/testCourse.php").build());
        return mCall;
    }

//    @Override
//    public Call loadQuestion() {
//        Call mCall = mOkHttoClient.newCall(
//                new Request.Builder().url("http://202.115.30.153/testQuestion.php").build());
//        return mCall;
//    }
//
//    @Override
//    public Call loadAnswer() {
//        Call mCall = mOkHttoClient.newCall(
//                new Request.Builder().url("http://202.115.30.153/testAnswer.php").build());
//        return mCall;
//    }
}
