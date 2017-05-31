package com.ngcourse.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
    public View.OnClickListener onClickListener;

    public VideoListAdapter(Context mContext, ArrayList<Video> videoList, View.OnClickListener clickListener){
        this.context = mContext;
        this.videoList = videoList;
        this.onClickListener = clickListener;
    }

    @Override
    public VideoListAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_video_list_layout, parent, false);
        view.setOnClickListener(onClickListener);
       return new VideoListAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoListAdapterHolder holder, int position) {
     Video video = videoList.get(position);
        holder.videoName.setText(video.getVideoName());
        Uri uri = Uri.parse(video.getVideoUrl());

    }

    @Override
    public int getItemCount() {
        if(videoList != null)
        return videoList.size();
        else return 0;
    }

    public class VideoListAdapterHolder extends RecyclerView.ViewHolder{
        private FrameLayout videoView;
        private TextView videoName;

        public VideoListAdapterHolder(View itemView) {
            super(itemView);
            videoView = (FrameLayout) itemView.findViewById(R.id.videoView);
            videoName = (TextView) itemView.findViewById(R.id.videoName);
        }
    }
}
