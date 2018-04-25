package chinaykc.mobistudi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orm.SugarContext;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import chinaykc.mobistudi.Fragments.HomeFragment;
import chinaykc.mobistudi.Fragments.MeFragment;
import chinaykc.mobistudi.Fragments.SeriesFragment;
import chinaykc.mobistudi.MenuItem.AllCourses;
import chinaykc.mobistudi.MenuItem.Friends;
import chinaykc.mobistudi.MenuItem.MyCollection;
import chinaykc.mobistudi.MenuItem.MyZone;
import chinaykc.mobistudi.Start.LoginActivity;
import czd.mobistudi.md.model.storage.LoginShared;
import czd.mobistudi.md.ui.activity.AboutActivity;
import czd.mobistudi.md.ui.activity.NotificationActivity;
import czd.mobistudi.md.ui.activity.SettingActivity;
import czd.mobistudi.md.ui.fragment.MobarFragment;
import czd.mobistudi.md.ui.util.Navigator;
import czd.mobistudi.md.util.HandlerUtils;
import xyz.kfdykme.mobistudi.bean.StudyUser;
import xyz.kfdykme.mobistudi.eventbus.LearnDays;
import xyz.kfdykme.mobistudi.eventbus.TheatFromMain;
import xyz.kfdykme.mobistudi.eventbus.UserID;
import xyz.kfdykme.mobistudi.eventbus.UserTheatInfo;
import xyz.kfdykme.mobistudi.model.BasicModel;
import xyz.kfdykme.mobistudi.model.LocalData;
import xyz.kfdykme.mobistudi.model.LocalDataStruct;
import xyz.kfdykme.mobistudi.tool.FileHelper;

/**
 * @author ykc
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * store local cache
     */
    public static LocalData localData = new LocalData();
    private Button btLogin;
    private String userName;
    private StudyUser me;

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    replaceFragment(new HomeFragment());
                    return true;
                case R.id.navigation_hot:
                    replaceFragment(new MobarFragment());
                    return true;
                case R.id.navigation_course:
                    replaceFragment(new SeriesFragment());
                    return true;
                case R.id.navigation_me:
                    replaceFragment(new MeFragment());
                    return true;
                    default:
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SugarContext.init(this);
        EventBus.getDefault().register(this);

        me = LocalDataStruct.readLocalData().getLoginedUser();
        String temp  = me.getId();

        //传递到myZone
        EventBus.getDefault().postSticky(new LearnDays(me.getLearnedDay(),me.getId()));
        //传递到cat
        EventBus.getDefault().postSticky(new UserTheatInfo(me.getCatTheat(),me.getId()));
        //传递到myLevel/studyProgressActivity
        EventBus.getDefault().postSticky(new UserID(me.getId()));
        EventBus.getDefault().postSticky(new TheatFromMain(me.getCatTheat()));

        replaceFragment(new HomeFragment());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        loadedInit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SugarContext.terminate();
        EventBus.getDefault().unregister(this);
    }

    private long exitTime = 0;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        btLogin = (Button) findViewById(R.id.login_button);
        btLogin.setText(userName);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //Navigation
    private NavigationItemStartCompat notificationAction = new NavigationItemStartCompat(NotificationActivity.class);
    private NavigationItemStartCompat settingAction = new NavigationItemStartCompat(SettingActivity.class);
    private NavigationItemStartCompat aboutAction = new NavigationItemStartCompat(AboutActivity.class);
    private class NavigationItemStartCompat implements Runnable {

        private Class gotoClass;

        NavigationItemStartCompat(Class gotoClass) {
            this.gotoClass = gotoClass;
        }

        @Override
        public void run() {
            if (gotoClass == NotificationActivity.class) {
                Navigator.NotificationWithAutoCompat.start(MainActivity.this);
            } else {
                startActivity(new Intent(MainActivity.this, gotoClass));
            }
        }

        void startDelayed() {
            HandlerUtils.handler.postDelayed(this, 400);
        }

    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.my_zone) {
            Intent intent = new Intent(MainActivity.this, MyZone.class);
            startActivity(intent);
        } else if (id == R.id.all_courses) {
            Intent intent = new Intent(MainActivity.this, AllCourses.class);
            startActivity(intent);
        } else if (id == R.id.friends) {
            Intent intent = new Intent(MainActivity.this, Friends.class);
            startActivity(intent);
        } else if (id == R.id.my_collection) {
            Intent intent = new Intent(MainActivity.this, MyCollection.class);
            startActivity(intent);
        } else if (id == R.id.notifycation) {
            if (czd.mobistudi.md.ui.activity.LoginActivity.checkLogin(this)) {
                notificationAction.startDelayed();
            }
        } else if (id == R.id.feedback) {
//            Intent intent = new Intent(MainActivity.this, AboutUs.class);
//            startActivity(intent);
            aboutAction.startDelayed();
        } else if (id == R.id.setting) {
//            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
//            startActivity(intent);
            settingAction.startDelayed();
        } else if (id == R.id.logout){
            //logout mobar community
            LoginShared.logout(MainActivity.this);

            //
            LocalDataStruct localDataStruct = LocalDataStruct.readLocalData();
            StudyUser currentUser = localDataStruct.getLoginedUser();
            try {
                FileHelper.createFile("users",new Gson().toJson(currentUser),currentUser.getId(),"json");
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"User can't save",Toast.LENGTH_LONG).show();
            }
            localDataStruct.setLoginedUser(null);
            LocalDataStruct.writeLocalData(localDataStruct);

            Intent i = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(i);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFinishLoad(LocalData localData)
    {
        loadedInit();
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onGetLoginUser(StudyUser user){
        LocalDataStruct.writeLocalData(new LocalDataStruct(user));
        userName = user.getUserName();
    }

    private void loadedInit() {
        if(localData.getLocalModules() !=null
                && localData.getLocalSubjects() != null
                && localData.getLocalCourses() != null)
        {
            LinearLayout lL = (LinearLayout) findViewById(R.id.activity_course_list_loading_layout);
            lL.setVisibility(View.GONE);
            if(localData.getLocalModules().get(0).getStudySubjects() == null){
                BasicModel.fixData();
            }
        }
    }

    /**
     * 传递已登录的UserName
     */
    public StudyUser getUserInfo(){
        return me;
    }
}


