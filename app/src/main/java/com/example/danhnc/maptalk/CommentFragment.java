package com.example.danhnc.maptalk;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by danhnc on 9/26/2017.
 */

public class CommentFragment extends Fragment {
    TextView tv1;
    TextView tv2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        Toast.makeText(getContext(), "day la comment fragment", Toast.LENGTH_SHORT).show();
        tv1 = (TextView) view.findViewById(R.id.textView1);
        tv2 = view.findViewById(R.id.textView2);
        tv1.setEnabled(true);
        tv2.setEnabled(false);

        tv1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Fragment newCommentFragment = new PositionFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.flPosition,newCommentFragment);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });
        return view;
    }
}
