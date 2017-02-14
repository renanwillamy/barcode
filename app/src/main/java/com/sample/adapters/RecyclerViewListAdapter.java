package com.sample.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sample.barcode.R;
import com.sample.restapi.Post;

import java.util.List;

public class RecyclerViewListAdapter extends RecyclerView.Adapter<RecyclerViewListAdapter.ViewHolder> {

    private List<Post> mDataset;

    public RecyclerViewListAdapter(List<Post> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public RecyclerViewListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_recyclerview, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTvId.setText(String.valueOf(mDataset.get(position).getId()));
        holder.mTvTitle.setText(mDataset.get(position).getTitle());
        holder.mTvUserId.setText(String.valueOf(mDataset.get(position).getUserId()));
        holder.mTvBody.setText(mDataset.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTvId;
        TextView mTvUserId;
        TextView mTvTitle;
        TextView mTvBody;

        ViewHolder(View v) {
            super(v);
            mTvId = (TextView) v.findViewById(R.id.tv_post_id);
            mTvUserId = (TextView) v.findViewById(R.id.tv_user_id);
            mTvTitle = (TextView) v.findViewById(R.id.tv_post_title);
            mTvBody = (TextView) v.findViewById(R.id.tv_body);
        }
    }
}
