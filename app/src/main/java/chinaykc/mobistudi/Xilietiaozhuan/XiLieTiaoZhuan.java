package chinaykc.mobistudi.Xilietiaozhuan;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import chinaykc.mobistudi.R;
import xyz.kfdykme.mobistudi.activity.CourseDetailActivity;

/**
 * @author ykc
 * 系列跳转界面
 */
public class XiLieTiaoZhuan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xi_lie_tiao_zhuan);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }
    }
}
