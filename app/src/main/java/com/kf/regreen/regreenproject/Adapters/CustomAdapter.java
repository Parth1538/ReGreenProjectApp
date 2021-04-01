package com.kf.regreen.regreenproject.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.kf.regreen.regreenproject.R;
import com.kf.regreen.regreenproject.Utils.MySpannable;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    Context context;
    private ArrayList<String> nameList;
    private ArrayList<String> timeList;
    private ArrayList<String> textList;
    private ArrayList<Integer> imgContentList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtRecyclerNameC;
        TextView txtRecyclerTimeC;
        TextView txtRecyclerTextC;
        ImageView imgRecylerContentC;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.txtRecyclerNameC = (TextView) itemView.findViewById(R.id.txtRecyclerName);
            this.txtRecyclerTimeC = (TextView) itemView.findViewById(R.id.txtRecyclerTime);
            this.txtRecyclerTextC = (TextView) itemView.findViewById(R.id.txtRecyclerText);
            this.imgRecylerContentC = (ImageView) itemView.findViewById(R.id.imgRecylerContent);
        }
    }

    public CustomAdapter(Context c,ArrayList<String> name, ArrayList<String> time, ArrayList<String> text, ArrayList<Integer> imageContent) {
        context = c;
        this.nameList = name;
        this.timeList = time;
        this.textList = text;
        this.imgContentList = imageContent;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recycler_list_view, parent, false);
//        MyViewHolder myViewHolder = new MyViewHolder(view);
//        return myViewHolder;
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

//        TextView txtRecyclerNameC = holder.txtRecyclerNameC;
//        TextView txtRecyclerTimeC = holder.txtRecyclerTimeC;
        TextView txtRecyclerTextC = holder.txtRecyclerTextC;
        ImageView imgRecylerContentC = holder.imgRecylerContentC;

//        txtRecyclerNameC.setText(nameList.get(listPosition).toString());
//        txtRecyclerTimeC.setText(timeList.get(listPosition).toString());
       /* String text = textList.get(listPosition).toString();
        if (text.length()>20) {
            text=text.substring(0,20)+"...";
            txtRecyclerTextC.setText(Html.fromHtml(text+"<font color='red'> <u>View More</u></font>"));
        }else{
            txtRecyclerTextC.setText(text);
        }*/


//        makeTextViewResizable(txtRecyclerTextC, 3, "Show More", true);
        imgRecylerContentC.setImageResource(imgContentList.get(listPosition));
    }

    @Override
    public int getItemCount() {
        return 5;
    }


}