package com.kf.regreen.regreenproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kf.regreen.regreenproject.Activities.ExpertsDetailActivity;
import com.kf.regreen.regreenproject.Model.ExpertList;
import com.kf.regreen.regreenproject.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class CustomAdapterexpert extends RecyclerView.Adapter<CustomAdapterexpert.MyViewHolder> {

    Context context;
    ArrayList<ExpertList> arrExpertList = new ArrayList<>();

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardC;
        TextView txtExpertNameC;
        TextView txtExpertDegreeC;
        TextView txtExpertDescriptionC;
        ImageView imgExpertListC;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.cardC = (CardView) itemView.findViewById(R.id.cardExperts);
            this.txtExpertNameC = (TextView) itemView.findViewById(R.id.txtExpertName);
            this.txtExpertDegreeC = (TextView) itemView.findViewById(R.id.txtExpertDegree);
            this.txtExpertDescriptionC = (TextView) itemView.findViewById(R.id.txtExpertDescription);
            this.imgExpertListC = (ImageView) itemView.findViewById(R.id.imgExpert);
        }
    }

    public CustomAdapterexpert(Context c, ArrayList<ExpertList> arrExpertListPara) {
        context = c;
        arrExpertList = arrExpertListPara;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.experts_list_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView txtExpertNameC = holder.txtExpertNameC;
        TextView txtExpertDegreeC = holder.txtExpertDegreeC;
        TextView txtExpertDescriptionC = holder.txtExpertDescriptionC;
        ImageView imgExpertListC = holder.imgExpertListC;


        txtExpertNameC.setText(arrExpertList.get(listPosition).getFirst_name());
        txtExpertDegreeC.setText(arrExpertList.get(listPosition).getE_designation());
        txtExpertDescriptionC.setText(arrExpertList.get(listPosition).getE_area_experts());

        Picasso.with(context)
                .load(arrExpertList.get(listPosition).getProfile_pic()).error(R.drawable.upload_photo)
                .into(imgExpertListC);

        holder.cardC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ExpertsDetailActivity.class);
                i.putExtra("ExpertDetail", arrExpertList.get(listPosition));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrExpertList.size();
    }
}