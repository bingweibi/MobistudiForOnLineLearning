package xyz.kfdykme.mobistudi.parents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import chinaykc.mobistudi.R;
import xyz.kfdykme.mobistudi.activity.DataZoneActivity;

/**
 * @author cxf
 * 我->我的孩子
 */
public class MyChildActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_child);

        Button bt_bind_child = (Button) findViewById(R.id.bt_bind_child);

        bt_bind_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(), DataZoneActivity.class));
            }
        });
    }
}
