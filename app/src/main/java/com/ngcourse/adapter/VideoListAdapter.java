package com.ngcourse.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ngcourse.Fragments.FragmentPlayYoutubeVideo;
import com.ngcourse.R;
import com.ngcourse.beans.Video;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by piyush on 28/5/17.
 */

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoListAdapterHolder> {

    private FragmentActivity context;
    public ArrayList<Video> videoList;

    public VideoListAdapter(FragmentActivity mContext, ArrayList<Video> videoList){
        this.context = mContext;
        this.videoList = videoList;
    }

    @Override
    public VideoListAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_video_list_layout, parent, false);
       return new VideoListAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoListAdapterHolder holder, final int position) {
     Video video = videoList.get(position);
        holder.videoName.setText(video.getVideoName());
        Picasso.with(context)
                .load("http://img.youtube.com/vi/"+ video.getYoutubeVideoId() + "/default.jpg")
                .into(holder.thumbNail);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlayVideoFragment(position);
            }
        });
    }

    private void openPlayVideoFragment(int position) {
        Fragment playVideoFragment= new FragmentPlayYoutubeVideo();
        FragmentManager fragmentManager = context.getSupportFragmentManager();
        Bundle bundle =new Bundle();
        bundle.putInt("position", position);
        bundle.putParcelableArrayList("videoList", videoList);
        playVideoFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.content_frame, playVideoFragment).commit();
    }

    @Override
    public int getItemCount() {
        if(videoList != null)
        return videoList.size();
        else return 0;
    }

    public class VideoListAdapterHolder extends RecyclerView.ViewHolder{
        private ImageView thumbNail;
        private TextView videoName;
        private LinearLayout linearLayout;

        public VideoListAdapterHolder(View itemView) {
            super(itemView);
            thumbNail = (ImageView) itemView.findViewById(R.id.thumbnail);
            videoName = (TextView) itemView.findViewById(R.id.videoName);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.frame_row);
        }
    }
}
