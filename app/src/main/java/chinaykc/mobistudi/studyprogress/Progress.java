package chinaykc.mobistudi.studyprogress;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import chinaykc.mobistudi.R;

/**
 * @author ykc
 *
 */
public class Progress extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }

        RecyclerView mRV = (RecyclerView) findViewById(R.id.rv);

        List<String> list = new ArrayList<String>();

        for (int i = 0; i < 66; i++) {
            list.add("" + i);
        }

        DemoAdapter adapter = new DemoAdapter(getApplicationContext(), list);

        mRV.setAdapter(adapter);
        //   格子布局
        mRV.setLayoutManager(new GridLayoutManager(getApplicationContext(), 10));
    }
}
