package xyz.kfdykme.mobistudi.model;


import okhttp3.Call;

/**
 * Created by kf on 2017/5/28.
 */

public interface OkhttpModel {

    public Call checkLogin();

    public Call loadUserData(String user);

    public void loadAllData();

    public Call loadModule();

    public Call loadSubject();

    public Call loadCourse();

//    public Call loadQuestion();
//
//    public Call loadAnswer();
}
