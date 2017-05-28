package com.ngcourse.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ngcourse.beans.Video;

import java.util.ArrayList;

/**
 * Created by piyush on 28/5/17.
 */

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoListAdapterHolder> {

    private Context context;
    private ArrayList<Video> videoList;

    public VideoListAdapter(Context mContext, ArrayList<Video> videoList){
        this.context = mContext;
        this.videoList = videoList;
    }

    @Override
    public VideoListAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(VideoListAdapterHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class VideoListAdapterHolder extends RecyclerView.ViewHolder{

        public VideoListAdapterHolder(View itemView) {
            super(itemView);
        }
    }
}
