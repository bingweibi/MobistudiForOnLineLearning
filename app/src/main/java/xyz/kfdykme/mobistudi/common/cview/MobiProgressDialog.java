package xyz.kfdykme.mobistudi.common.cview;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;

import chinaykc.mobistudi.R;

/**
 * Project Name: Mobistudi2
 * Class Description:
 * Created by kf on 2017/8/22 01:20.
 * Last Edit on 2017/8/22 01:20
 * 修改备注：
 */

public class MobiProgressDialog extends Dialog {

    private Context mContext;

    public MobiProgressDialog(@NonNull Context context) {
        super(context, R.style.Mobi_ProgressDialog);
        this.mContext = context;
        setContentView(R.layout.dialog_progress);
        getWindow().getAttributes().gravity = Gravity.CENTER;
    }

    public void showProgressDialog(){
        if(!isShowing()){
            setCanceledOnTouchOutside(false);
            setCancelable(true);
            show();
        }
    }
}
