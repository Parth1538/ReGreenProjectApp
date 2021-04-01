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

public class ThirdFragment extends Fragment {

    public static View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // create ContextThemeWrapper from the original Activity Context with the custom theme
//        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.toolbarTheme);
//
//        // clone the inflater using the ContextThemeWrapper
//        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);

        v = inflater.inflate(R.layout.third_fragment, container, false);

        RelativeLayout btnSkipFrag3 = (RelativeLayout) v.findViewById(R.id.btnSkipFrag3);
        btnSkipFrag3.setOnClickListener(new View.OnClickListener() {
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

    public static ThirdFragment newInstance(String text) {

        ThirdFragment f = new ThirdFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);

        return f;
    }
}