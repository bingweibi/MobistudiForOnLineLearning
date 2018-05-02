package chinaykc.mobistudi.Start;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.gson.Gson;
import com.orm.SugarContext;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cat.mobistudi.HttpUtil;
import chinaykc.mobistudi.MainActivity;
import chinaykc.mobistudi.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import xyz.kfdykme.mobistudi.bean.StudyUser;
import xyz.kfdykme.mobistudi.model.LocalDataStruct;
import xyz.kfdykme.mobistudi.model.OkhttpModelImpl;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 * @author ykc
 * 侧滑->注销
 * 登陆界面
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    private String user;
    private String password;
    private String responseText = "";
    private String temp;

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    private UserLoginTask mAuthTask = null;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Snackbar mSnackbar;
    private int userType = StudyUser.TYPE_STU;
    private TextView mEmailSignInButton;
    private TabLayout mTl;
    private View tv_hide;
    private RelativeLayout fl;
    private VideoView vv;
    private TextView mTvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initPermission();
        setContentView(R.layout.activity_login);
        EventBus.getDefault().register(this);
        SugarContext.init(this);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.passWord);
        mEmailSignInButton = (TextView) findViewById(R.id.email_sign_in_button);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        mTl = (TabLayout) findViewById(R.id.tb_login);
        fl= (RelativeLayout) findViewById(R.id.fl_video);
        tv_hide= findViewById(R.id.tv_hide);
        vv= (VideoView) findViewById(R.id.vv_about);
        mTvRegister= (TextView) findViewById(R.id.tv_register);

        mTl.addTab(mTl.newTab().setText("Stu"));
        mTl.addTab(mTl.newTab().setText("Tea"));
        mTl.addTab(mTl.newTab().setText("Par"));

        //判断是否已经有登陆用户，如果有就直接进入app
        LocalDataStruct lDStruct = LocalDataStruct.readLocalData();
        if(lDStruct != null&& lDStruct.getLoginedUser() != null) {
            toMainActivity(new Gson().fromJson((findFromRemote("http://202.115.30.153/testLogin.php",
                    lDStruct.getLoginedUser().getUserName(),lDStruct.getLoginedUser().getPassWord())),StudyUser.class));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        populateAutoComplete();

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    user = mEmailView.getText().toString();
                    password = mPasswordView.getText().toString();
                    attemptLogin(user,password);
                    return true;
                }
                return false;
            }
        });

        //登录按钮
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                user = mEmailView.getText().toString();
                password = mPasswordView.getText().toString();
                attemptLogin(user,password);
            }
        });

        mSnackbar = Snackbar.make(mEmailSignInButton,"",Snackbar.LENGTH_INDEFINITE);

        //注册按钮
        mTvRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

        vv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vv.isPlaying()){
                    vv.pause();
                } else {
                    vv.start();
                }
            }
        });

        tv_hide.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideVideo(fl,vv);
            }
        });

        mEmailView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    hideVideo(fl,vv);
                } else {
                    showVideo(fl,vv);
                }
            }
        });

        mPasswordView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    hideVideo(fl,vv);
                } else {
                    showVideo(fl,vv);
                }
            }
        });

        vv.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.about));
        vv.start();
        vv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(vv.isPlaying()){
                    vv.pause();
                } else{
                    vv.start();
                }
                return false;
            }
        });

        // init tab to set user type
        mTl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if("Stu".equals(tab.getText())){
                    userType = StudyUser.TYPE_STU;
                }
                if("Tea".equals(tab.getText())){
                    userType = StudyUser.TYPE_TEA;
                }
                if("Par".equals(tab.getText())) {
                    userType = StudyUser.TYPE_PAR;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void hideVideo(RelativeLayout fl, VideoView vv) {
        fl.setVisibility(View.GONE);
        vv.pause();
    }
    private void showVideo(RelativeLayout fl, VideoView vv) {
        fl.setVisibility(View.VISIBLE);
        vv.start();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        SugarContext.terminate();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        final RelativeLayout fl = (RelativeLayout) findViewById(R.id.fl_video);
        final VideoView vv = (VideoView) findViewById(R.id.vv_about);

        if(fl.getVisibility() == View.GONE){
            showVideo(fl,vv);
        }
        else{
            super.onBackPressed();
        }
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }
        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }

    private void initPermission() {
            String[] permissions = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
            };
            ArrayList toApplyList = new ArrayList();

            for (String perm : permissions) {

                if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                    toApplyList.add(perm);
                    //进入到这里代表没有权限.
                }
            }

            String[] tmpList = new String[toApplyList.size()];

            if (!toApplyList.isEmpty()) {
                ActivityCompat.requestPermissions(this, (String[]) toApplyList.toArray(tmpList), 123);
            }
        }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRegisterDone(List<String> ls){
        mEmailView.setText(ls.get(0));
        mPasswordView.setText(ls.get(1));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginFinished(LoginFinishedEvent e){
        StudyUser user = new Gson().fromJson(e.getUserData(),StudyUser.class);
        toMainActivity(user);
    }

    private void toMainActivity(StudyUser user){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        EventBus.getDefault().postSticky(user);
        new OkhttpModelImpl().loadAllData();
        finish();
    }

    /**
     * 登录操作
     */
    private void attemptLogin(String userName,String passWord) {

        mSnackbar.setText("Checking user data...").show();
        //get user datas
        StudyUser sUser;
        sUser = new Gson().fromJson(findFromRemote("http://202.115.30.153/testLogin.php",userName,passWord),StudyUser.class);
        if(sUser != null && passWord.equals(sUser.getPassWord())) {
            EventBus.getDefault().post(new LoginFinishedEvent(new Gson().toJson(sUser)));
        }else{
            Toast.makeText(LoginActivity.this,"not find",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 登录远程检测
     */
    private String findFromRemote(String address,String userName,String passWord) {

        String url = address+"?userName="+userName+"&passWord="+passWord;

        mProgressView.setVisibility(View.VISIBLE);
        HttpUtil.sendOKHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseText = response.body().string();
            }
        });

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int len = responseText.length();
        temp = responseText.substring(1,len-5);
        mProgressView.setVisibility(View.INVISIBLE);
        return temp;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    @SuppressLint("StaticFieldLeak")
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    public class Event{
        String loginData = "";

        public Event(String loginData) {
            this.loginData = loginData;
        }

        public String getLoginData() {
            return loginData;
        }

        public void setLoginData(String loginData) {
            this.loginData = loginData;
        }
    }

    public class LoginFinishedEvent{
        String userData="";

        public LoginFinishedEvent(String userData) {
            this.userData = userData;
        }

        public String getUserData() {
            return userData;
        }

        public void setUserData(String userData) {
            this.userData = userData;
        }
    }
}
