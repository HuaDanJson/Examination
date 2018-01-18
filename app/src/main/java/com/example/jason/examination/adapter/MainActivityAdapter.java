package com.example.jason.examination.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jason.examination.Bean.ExameURLBean;
import com.example.jason.examination.R;
import com.example.jason.examination.activity.WebViewActivity;
import com.example.jason.examination.constants.ConstKey;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jason on 2018/1/17.
 */

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.MainActivityViewHolder> {

    private Activity activity;
    private List<ExameURLBean> exameURLBeanList = new ArrayList<>();


    public MainActivityAdapter(Activity activity, List<ExameURLBean> exameURLBeanList) {
        this.activity = activity;
        this.exameURLBeanList = exameURLBeanList;
    }

    public void setData(List<ExameURLBean> exameURLBeanList) {
        this.exameURLBeanList = exameURLBeanList;
        notifyDataSetChanged();
    }

    @Override
    public MainActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_main_activity, parent, false);
        MainActivityViewHolder holder = new MainActivityViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MainActivityViewHolder holder, final int position) {
        holder.btnTextItemMainActivity.setText(exameURLBeanList.get(position).getName());
        holder.btnTextItemMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, WebViewActivity.class);
                intent.putExtra(ConstKey.INTENT_KEY_TO_WEB_VIEW_ACTIVITY_TITLE, exameURLBeanList.get(position).getName());
                intent.putExtra(ConstKey.INTENT_KEY_TO_WEB_VIEW_ACTIVITY_URL, exameURLBeanList.get(position).getUrl());
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return exameURLBeanList.size();
    }

    public class MainActivityViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.btn_text_item_main_activity) Button btnTextItemMainActivity;

        public MainActivityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}