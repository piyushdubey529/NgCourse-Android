package com.ngcourse.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import com.ngcourse.R;
import com.ngcourse.beans.Video;

import java.util.ArrayList;

/**
 * Created by piyush on 28/5/17.
 */

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoListAdapterHolder> {

    private Context context;
    public ArrayList<Video> videoList;

    public VideoListAdapter(Context mContext, ArrayList<Video> videoList){
        this.context = mContext;
        this.videoList = videoList;
    }

    @Override
    public VideoListAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_video_list_layout, parent, false);
        VideoListAdapterHolder viewHolder = new VideoListAdapterHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(VideoListAdapterHolder holder, int position) {
     Video video = videoList.get(position);
        holder.videoName.setText(video.getVideoName());
        Uri uri = Uri.parse(video.getVideoUrl());
        holder.videoView.setVideoURI(uri);
    }

    @Override
    public int getItemCount() {
        if(videoList != null)
        return videoList.size();
        else return 0;
    }

    public class VideoListAdapterHolder extends RecyclerView.ViewHolder{
        private VideoView videoView;
        private TextView videoName;

        public VideoListAdapterHolder(View itemView) {
            super(itemView);
            videoView = (VideoView) itemView.findViewById(R.id.videoView);
            videoName = (TextView) itemView.findViewById(R.id.videoName);
        }
    }
}
