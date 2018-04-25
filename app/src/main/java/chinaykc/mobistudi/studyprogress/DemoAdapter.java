package chinaykc.mobistudi.studyprogress;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import chinaykc.mobistudi.R;

/**
 * Project Name: GridLayoutView
 * @author ykc
 * Class Description:
 * Created by kf on 2017/7/25 20:40.
 * Last Edit on 2017/7/25 20:40
 * 修改备注：
 */

public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.DemoHolder> {

    List<String> data;

    Context context;

    LayoutInflater inflate;

    public interface OnItemClickListener {

        void onClick(View view, int position);

    }

    OnItemClickListener mOnItemClickListener;

    public DemoAdapter(Context context, List<String> data) {
        this.data = data;
        this.context = context;
        inflate = LayoutInflater.from(context);

    }

    public void setmOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public DemoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflate.inflate(R.layout.rv_blank, parent, false);
        DemoHolder holder = new DemoHolder(view);

        return holder;
    }

    int a[] = {1, 2, 4, 3, 5,
            2, 1, 2, 3, 1,
            3, 2, 3, 2, 4,
            5, 2, 3, 1, 4,
            5, 2, 2, 2, 3,
            1, 2, 2, 4, 5,
            2, 2, 1, 2, 2,
            1, 1, 1, 1, 1,
            1, 1, 1, 1, 3,
            4, 2, 2, 3, 2,
            2, 3, 2, 3, 1,
            5, 2, 2, 2, 3,
            1, 2, 2, 4, 5,
            2, 2, 1, 2, 2,
            2, 3, 2, 3, 1,
            5, 2, 2, 2, 3,
            5, 2, 2, 2, 3,
            1, 2, 2, 4, 5,
    };

    @Override
    public void onBindViewHolder(final DemoHolder holder, final int position) {

        //holder.tv.setText(data.get(position));
        String color = "#ffffff";
        /*switch (a[Integer.parseInt(data.get(position))]) {
            case 1:
                color = "#F5F5DC";
                break;
            case 2:
                color = "#78696a";
                break;
            case 3:
                color = "#7186ab";
                break;
            case 4:
                color = "#123456";
                break;
            case 5:
                color = "#acbdef";
                break;
        }*/
        /*switch (a[Integer.parseInt(data.get(position))]) {
            case 1:
                color = "#9fe0f6";
                break;
            case 2:
                color = "#0092c7";
                break;
            case 3:
                color = "#f3e59a";
                break;
            case 4:
                color = "#f3b59b";
                break;
            case 5:
                color = "#f29c9c";
                break;
        }*/
        switch (a[Integer.parseInt(data.get(position))]) {
            case 1:
                color = "#cfe6ff";
                break;
            case 2:
                color = "#9fe0f6";
                break;
            case 3:
                color = "#27b9f2";
                break;
            case 4:
                color = "#27a4f2";
                break;
            case 5:
                color = "#8adc3c";
                break;
        }
        /*switch (a[Integer.parseInt(data.get(position))]) {
            case 1:
                color = "#d8f7e6";
                break;
            case 2:
                color = "#9acdac";
                break;
            case 3:
                color = "#737e62";
                break;
            case 4:
                color = "#feedd7";
                break;
            case 5:
                color = "#f3958a";
                break;
        }*/

        holder.tv.setBackgroundColor(Color.parseColor(color));
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClick(holder.tv, position);
                }
                Toast.makeText(context, "Click No." + position + "Item in OnBindView", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class DemoHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public DemoHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
