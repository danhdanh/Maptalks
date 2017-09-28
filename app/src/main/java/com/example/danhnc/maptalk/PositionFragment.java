package com.example.danhnc.maptalk;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class PositionFragment extends Fragment {
    TextView tv1,tv2, tvtoado;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_position, container, false);
        Toast.makeText(getContext(), "day la position fragment", Toast.LENGTH_SHORT).show();
        tv1 = (TextView) view.findViewById(R.id.textView1);
        tv2 = view.findViewById(R.id.textView2);
        tv1.setEnabled(false);
        tv2.setEnabled(true);
        tvtoado = view.findViewById(R.id.textView3);
        //String toado= getArguments().getString("toa do");
        //tvtoado.setText(toado);

        tv2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Fragment newCommentFragment = new CommentFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.flPosition,newCommentFragment);
                transaction.addToBackStack(null);

                transaction.commit();}
            });
        return view;
    }
}
