package xyz.kfdykme.mobistudi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import chinaykc.mobistudi.R;
import czd.mobistudi.mo.activity.BlogDetailActivity;
import xyz.kfdykme.mobistudi.bean.StudyCourse;


/**
 * @author bibingwei
 */
public class CommentFragment extends BaseFragment {

    private View mView;
    private TextView mTvGoMobar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_comment,container,false);
        mTvGoMobar = mView.findViewById(R.id.fragmentToMobar);
        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mTvGoMobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BlogDetailActivity.class);
                intent.putExtra("url","http://blog.jobbole.com/113552/");
                getActivity().startActivity(intent);
            }
        });
    }

    public static CommentFragment newInstance(StudyCourse studyCourse) {

        CommentFragment fragment = new CommentFragment();
        return fragment;
    }
}
