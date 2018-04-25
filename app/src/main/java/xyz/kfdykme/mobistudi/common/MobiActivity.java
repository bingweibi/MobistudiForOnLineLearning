package xyz.kfdykme.mobistudi.common;

import android.content.Context;
import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import chinaykc.mobistudi.R;
import xyz.kfdykme.mobistudi.common.cview.MobiProgressDialog;

/**
 * Project Name: Mobistudi2
 * Class Description:
 *
 * @author kf
 * @date 2017/8/22 01:15
 * Last Edit on 2017/8/22 01:15
 * 修改备注：
 */

public class MobiActivity extends AppCompatActivity{

    private Toolbar toolbar;
    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public Context getContext(){
        return AppContext.getContext();
    }

    public void initToolbar(Toolbar toolbar){
        this.toolbar = toolbar;
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setTitle(String title){
        toolbar.setTitle(title);
    }
}
