package chinaykc.mobistudi.MenuItem;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import chinaykc.mobistudi.R;

/**
 * @author ykc
 * 侧滑->关于我们
 */
public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        VideoView videoView = (VideoView)this.findViewById(R.id.video1 );
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.about));
        videoView.setMediaController(new MediaController(this));
        videoView.start();
    }
}
