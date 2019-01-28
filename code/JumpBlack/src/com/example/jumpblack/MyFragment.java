package com.example.jumpblack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class MyFragment extends Fragment {
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        
        view.findViewById(R.id.btn_jump_fragment).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                doJump();
            }
        });
        
        view.findViewById(R.id.btn_close_fragment).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                doFinish();
            }
        });
        
        return view;
    }
    
    private void doJump() {
        Activity activity = getActivity();
        activity.startActivity(new Intent(getContext(), LandscapeActivity.class));
    }
    
    private void doFinish() {
        getActivity().finish();
    }

}
