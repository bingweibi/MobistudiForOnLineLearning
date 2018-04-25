package xyz.kfdykme.mobistudi.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import chinaykc.mobistudi.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IntraductionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IntraductionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IntraductionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;



    public IntraductionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment IntraductionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IntraductionFragment newInstance(String param1) {
        IntraductionFragment fragment = new IntraductionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_intraduction, container, false);
        TextView textView = (TextView) view.findViewById(R.id.fragment_intraduction_textview);
        textView.setText(mParam1);
        return view;
    }







}
