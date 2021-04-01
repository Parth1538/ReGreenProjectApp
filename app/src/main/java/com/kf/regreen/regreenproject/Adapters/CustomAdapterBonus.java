package com.kf.regreen.regreenproject.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kf.regreen.regreenproject.R;
import com.kf.regreen.regreenproject.Utils.Constant;
import com.kf.regreen.regreenproject.Utils.RestApi;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by admin on 05/01/2018.
 */

public class CustomAdapterBonus extends RecyclerView.Adapter<CustomAdapterBonus.MyViewHolder> {

    public Context context;
    private ArrayList<String> nameList;
    private ArrayList<String> amountList;
    private ArrayList<String> imgContentList;
    public ArrayList<String> dates;
    public ArrayList<String> message;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtNursaryNameRewardsC;
        TextView txtAmountRewardsC, txtdate, txtmessage;
        ImageView imgNurseryRewardsC;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.txtNursaryNameRewardsC = (TextView) itemView.findViewById(R.id.txtNursaryNameRewards);
            this.txtdate = (TextView) itemView.findViewById(R.id.txtdate);
            this.txtAmountRewardsC = (TextView) itemView.findViewById(R.id.txtAmountRewards);
            this.imgNurseryRewardsC = (ImageView) itemView.findViewById(R.id.imgNurseryRewards);
            this.txtmessage = (TextView) itemView.findViewById(R.id.txtmessage);
        }
    }

    public CustomAdapterBonus(Context c, ArrayList<String> name, ArrayList<String> amount, ArrayList<String> imageContent, ArrayList<String> dates, ArrayList<String> messages) {
        this.context = c;
        this.nameList = name;
        this.amountList = amount;
        this.imgContentList = imageContent;
        this.dates = dates;
        this.message = messages;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rewards_detail, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
//        return myViewHolder;
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {


        holder.txtNursaryNameRewardsC.setText(nameList.get(listPosition).toString());
        holder.txtAmountRewardsC.setText(amountList.get(listPosition).toString());
        holder.txtdate.setText(dates.get(listPosition).toString());
        holder.txtmessage.setText(message.get(listPosition).toString());

        Picasso.with(context)
                .load(imgContentList.get(listPosition))
                .into(holder.imgNurseryRewardsC);

    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }


}
