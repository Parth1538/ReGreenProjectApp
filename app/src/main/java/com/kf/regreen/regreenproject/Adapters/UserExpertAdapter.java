package com.kf.regreen.regreenproject.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kf.regreen.ListenerUtils.FAQListener;
import com.kf.regreen.regreenproject.Activities.ExpertsAnswerActivity;
import com.kf.regreen.regreenproject.Activities.ExpertsDetailActivity;
import com.kf.regreen.regreenproject.Model.ExpertList;
import com.kf.regreen.regreenproject.Model.UserList;
import com.kf.regreen.regreenproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserExpertAdapter extends RecyclerView.Adapter<UserExpertAdapter.MyViewHolder> {

    Activity context;
    ArrayList<UserList> arrUserList = new ArrayList<>();
    int status = 0;
    FAQListener faqListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardC;
        TextView txtusername, txtquestiondate, txtquestion, txtquestiondesc, txtanswer, txtexpertname;
        // ivquestionpic1, ivquestionpic2, ivquestionpic3, ivquestionpic4,
        ImageView ivuserpic, ivattachment;
        LinearLayout llquestionpic;
        Button btnaddtofaq, btnaddtospam;

        public MyViewHolder(View itemView) {
            super(itemView);
            cardC = (CardView) itemView.findViewById(R.id.cardExperts);
            llquestionpic = (LinearLayout) itemView.findViewById(R.id.llquestionpic);

            txtusername = (TextView) itemView.findViewById(R.id.txtusername);
            txtquestiondate = (TextView) itemView.findViewById(R.id.txtquestiondate);
            txtquestion = (TextView) itemView.findViewById(R.id.txtquestion);
            txtquestiondesc = (TextView) itemView.findViewById(R.id.txtquestiondesc);
            txtanswer = (TextView) itemView.findViewById(R.id.txtanswer);
            txtexpertname = (TextView) itemView.findViewById(R.id.txtexpertname);

            ivuserpic = (ImageView) itemView.findViewById(R.id.ivuserpic);
            ivattachment = (ImageView) itemView.findViewById(R.id.ivattachment);
//            ivquestionpic1 = (ImageView) itemView.findViewById(R.id.ivquestionpic_1);
//            ivquestionpic2 = (ImageView) itemView.findViewById(R.id.ivquestionpic_2);
//            ivquestionpic3 = (ImageView) itemView.findViewById(R.id.ivquestionpic_3);
//            ivquestionpic4 = (ImageView) itemView.findViewById(R.id.ivquestionpic_4);

            btnaddtofaq = (Button) itemView.findViewById(R.id.btnaddtofaq);
            btnaddtospam = (Button) itemView.findViewById(R.id.btnaddtospam);
        }
    }

    public UserExpertAdapter(Activity c, ArrayList<UserList> arrUserListPara, int statusPara, FAQListener faqListenerPara) {
        context = c;
        arrUserList = arrUserListPara;
        status = statusPara;
        faqListener = faqListenerPara;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.experts_user_list_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        holder.txtusername.setText(arrUserList.get(listPosition).getUser_name());
        holder.txtquestiondate.setText(arrUserList.get(listPosition).getQue_date());
        holder.txtquestion.setText(setHtmlFormatValue("Que: ", arrUserList.get(listPosition).getQue_title()));
        holder.txtquestiondesc.setText(setHtmlFormatValue("Description: ", arrUserList.get(listPosition).getQue_description()));

        Picasso.with(context)
                .load(arrUserList.get(listPosition).getUser_pic()).error(R.drawable.upload_photo)
                .into(holder.ivuserpic);
        if (!arrUserList.get(listPosition).getQue_image_url_1().isEmpty() ||
                !arrUserList.get(listPosition).getQue_image_url_2().isEmpty() ||
                !arrUserList.get(listPosition).getQue_image_url_3().isEmpty() ||
                !arrUserList.get(listPosition).getQue_image_url_4().isEmpty()) {
            holder.ivattachment.setVisibility(View.VISIBLE);
        } else {
            holder.ivattachment.setVisibility(View.GONE);
        }
        holder.llquestionpic.setVisibility(View.GONE);
        if (status == 0) {
            int hours = Integer.parseInt(arrUserList.get(listPosition).getQue_date().toString());
            if (hours==0){
                holder.txtquestiondate.setText("Just now");
            }else{
                holder.txtquestiondate.setText(arrUserList.get(listPosition).getQue_date() + " Hours ago");
            }

            if (hours > 46) {
                holder.txtquestiondate.setTextColor(Color.RED);
            } else {
                holder.txtquestiondate.setTextColor(Color.BLACK);
            }

            holder.txtexpertname.setVisibility(View.GONE);
            holder.btnaddtofaq.setVisibility(View.GONE);
            holder.btnaddtospam.setVisibility(View.VISIBLE);
            holder.txtanswer.setVisibility(View.GONE);

            holder.btnaddtospam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    faqListener.onSuccess(listPosition, 1);
                }
            });
        } else if (status == 1) {
            holder.txtexpertname.setVisibility(View.GONE);
            holder.btnaddtofaq.setVisibility(View.VISIBLE);
            holder.btnaddtospam.setVisibility(View.GONE);
            holder.txtanswer.setVisibility(View.VISIBLE);
            holder.txtanswer.setText(setHtmlFormatValue("Ans:", arrUserList.get(listPosition).getQue_answer()));
            holder.btnaddtofaq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    faqListener.onSuccess(listPosition, 0);
                }
            });
        } else {
            holder.txtexpertname.setVisibility(View.VISIBLE);
            holder.txtanswer.setVisibility(View.VISIBLE);
            holder.btnaddtospam.setVisibility(View.GONE);
            holder.btnaddtofaq.setVisibility(View.GONE);
            if (arrUserList.get(listPosition).getQue_answer().isEmpty()) {
                holder.txtanswer.setText(setHtmlFormatValue("Ans:", "----"));
            } else {
                holder.txtanswer.setText(setHtmlFormatValue("Ans:", arrUserList.get(listPosition).getQue_answer()));
            }

            holder.txtexpertname.setText(setHtmlFormatValue("Expert: ", arrUserList.get(listPosition).getExpert_name()));
        }
        holder.cardC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ExpertsAnswerActivity.class);
                i.putExtra("UserDetails", arrUserList.get(listPosition));
                i.putExtra("status", status);
                context.startActivityForResult(i, 123);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrUserList.size();
    }

    public Spanned setHtmlFormatValue(String key, String value) {
        return Html.fromHtml("<font color='#1B5E20'>" + key + " </font>" + value);
    }
}