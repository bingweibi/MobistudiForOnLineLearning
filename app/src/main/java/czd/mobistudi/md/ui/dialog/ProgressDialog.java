package czd.mobistudi.md.ui.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.app.AppCompatDialog;

import com.pnikosis.materialishprogress.ProgressWheel;

import chinaykc.mobistudi.R;
import czd.mobistudi.md.model.storage.SettingShared;

public class ProgressDialog extends AppCompatDialog {

    public static ProgressDialog createWithAutoTheme(@NonNull Activity activity) {
        return new ProgressDialog(activity, SettingShared.isEnableThemeDark(activity) ? R.style.AppDialogDark_Progress : R.style.AppDialogLight_Progress);
    }

    ProgressWheel progressWheel;

    private ProgressDialog(@NonNull Activity activity, @StyleRes int theme) {
        super(activity, theme);
        setContentView(R.layout.dialog_progress);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressWheel = findViewById(R.id.progress_wheel);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(progressWheel!=null)
            progressWheel.spin();

    }

    @Override
    protected void onStop() {
        if(progressWheel!=null)
            progressWheel.stopSpinning();
        super.onStop();
    }

}
