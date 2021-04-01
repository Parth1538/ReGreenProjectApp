package com.kf.regreen.regreenproject.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kf.regreen.regreenproject.Model.NurseryList;
import com.kf.regreen.regreenproject.R;
import com.kf.regreen.regreenproject.Utils.RestApi;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AboutOurNurseryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AboutOurNurseryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutOurNurseryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View v;
    TextView txtNurPlanAvailC, txtNurseryNameC, txtAddressC, txtContNameC, txtContNoC, txtNurseryNameNC, txtAddressNC, txtContNameNC, txtContNoNC;
    String name = "", nur_id = "", owner_name, address, contact_no, image_url="";
    Typeface tf_nub, tf_nul, tf_nl;
    ImageView imgNurseryBannerC;

    private OnFragmentInteractionListener mListener;

    public AboutOurNurseryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutOurNurseryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutOurNurseryFragment newInstance(String param1, String param2) {
        AboutOurNurseryFragment fragment = new AboutOurNurseryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_about_our_nursery, container, false);

        v.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        tf_nul = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/nunito_extra_light.ttf");
        tf_nub = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/nunito_medium.ttf");

        tf_nl = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/nunito_light.ttf");

        Intent i = getActivity().getIntent();
        name = i.getStringExtra("name");
        nur_id = i.getStringExtra("nur_id");
        owner_name = i.getStringExtra("owner_name");
        address = i.getStringExtra("address");
        contact_no = i.getStringExtra("contact_no");
        image_url = i.getStringExtra("image_url");

        txtNurseryNameNC = (TextView)v.findViewById(R.id.txtNurseryNameN);
        txtNurseryNameC = (TextView)v.findViewById(R.id.txtNurseryName);
        txtAddressC = (TextView)v.findViewById(R.id.txtAddress);
        txtAddressNC = (TextView)v.findViewById(R.id.txtAddressN);
        txtContNameNC = (TextView)v.findViewById(R.id.txtContNameN);
        txtContNameC = (TextView)v.findViewById(R.id.txtContName);
        txtContNoNC = (TextView)v.findViewById(R.id.txtContNoN);
        txtContNoC = (TextView)v.findViewById(R.id.txtContNo);
        imgNurseryBannerC = (ImageView)v.findViewById(R.id.imgNurseryBanner);

        Picasso.with(getActivity())
                .load(RestApi.IMAGE_URL+image_url)
                .into(imgNurseryBannerC);

        txtNurseryNameNC.setTypeface(tf_nl);
        txtNurseryNameNC.setTextColor(getResources().getColor(R.color.already_acc));
        txtNurseryNameNC.setText("Nursery Name:");

        txtNurseryNameC.setTypeface(tf_nl);
        txtNurseryNameC.setTextColor(getResources().getColor(R.color.already_acc));
        txtNurseryNameC.setText(name);

        txtAddressC.setTypeface(tf_nl);
        txtAddressC.setTextColor(getResources().getColor(R.color.already_acc));
        txtAddressC.setText(address);

        txtAddressNC.setTypeface(tf_nl);
        txtAddressNC.setTextColor(getResources().getColor(R.color.already_acc));

        txtContNameNC.setTypeface(tf_nl);
        txtContNameNC.setTextColor(getResources().getColor(R.color.already_acc));

        txtContNameC.setTypeface(tf_nl);
        txtContNameC.setTextColor(getResources().getColor(R.color.already_acc));
        if(owner_name.equals("")){
            txtContNameC.setText("Not Available");
        }else{
            txtContNameC.setText(owner_name);
        }

        txtContNoNC.setTypeface(tf_nl);
        txtContNoNC.setTextColor(getResources().getColor(R.color.already_acc));

        txtContNoC.setTypeface(tf_nl);
        txtContNoC.setTextColor(getResources().getColor(R.color.already_acc));
        txtContNoC.setText(contact_no);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
