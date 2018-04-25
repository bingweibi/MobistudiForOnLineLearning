package czd.mobistudi.md.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import chinaykc.mobistudi.*;
import czd.mobistudi.md.ui.activity.base.BaseActivity;
import czd.mobistudi.md.ui.util.ActivityUtils;
import czd.mobistudi.md.util.HandlerUtils;

public class LaunchActivity extends BaseActivity implements Runnable {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        HandlerUtils.handler.postDelayed(this, 2000);
    }

    @Override
    public void run() {
        if (ActivityUtils.isAlive(this)) {
            startActivity(new Intent(this, chinaykc.mobistudi.MainActivity.class));
            finish();
        }
    }

}
