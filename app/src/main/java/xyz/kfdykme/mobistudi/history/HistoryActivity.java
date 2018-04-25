package xyz.kfdykme.mobistudi.history;

import android.os.Bundle;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import chinaykc.mobistudi.R;
import xyz.kfdykme.mobistudi.common.MobiActivity;

/**
 * Project Name: Mobistudi2
 * Class Description:
 * @author kf
 * @date 2017/8/23 16:50
 * Last Edit on 2017/8/23 16:50
 * 修改备注：
 * 我->历史
 */

public class HistoryActivity extends MobiActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_history);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onNoting(String a){
        return;
    }

}
