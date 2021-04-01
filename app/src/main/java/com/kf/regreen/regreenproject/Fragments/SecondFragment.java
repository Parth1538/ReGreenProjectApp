package com.kf.regreen.regreenproject.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.kf.regreen.regreenproject.Activities.LoginScreen;
import com.kf.regreen.regreenproject.Activities.SignUpForm;
import com.kf.regreen.regreenproject.R;


public class SecondFragment extends Fragment {

    public static View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // create ContextThemeWrapper from the original Activity Context with the custom theme
        v = inflater.inflate(R.layout.second_fragment, container, false);

        RelativeLayout btnSkipFrag2 = (RelativeLayout)v.findViewById(R.id.btnSkipFrag2);
        btnSkipFrag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginScreen.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        // inflate the layout using the cloned inflater, not default inflater
        return v;//localInflater.inflate(R.layout.third_fragment, container, false);

    }

    public static SecondFragment newInstance(String text) {

        SecondFragment f = new SecondFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);

        return f;
    }
}