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
 * Created by piyush on 25/6/17.
 */

public class IonicVideoListAdapter extends RecyclerView.Adapter<IonicVideoListAdapter.IonicVideoListAdapterHolder> {
    private FragmentActivity context;
    public ArrayList<Video> videoList;

    public IonicVideoListAdapter(FragmentActivity mContext, ArrayList<Video> videoList){
        this.context = mContext;
        this.videoList = videoList;
    }

    @Override
    public IonicVideoListAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_home_video_list, parent, false);
        return new IonicVideoListAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(IonicVideoListAdapterHolder holder, final int position) {
        Video video = videoList.get(position);
        holder.videoName.setText(video.getVideoName());
        Picasso.with(context)
                .load("http://magemello.github.io/articles/img/ng2-logo.png")
                .into(holder.thumbNail);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlayVideoFragment(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(videoList!= null) return videoList.size();
        else return 0;
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


    public class IonicVideoListAdapterHolder extends RecyclerView.ViewHolder{
        private ImageView thumbNail;
        private TextView videoName;
        private LinearLayout linearLayout;

        public IonicVideoListAdapterHolder(View itemView) {
            super(itemView);
            thumbNail = (ImageView) itemView.findViewById(R.id.thumbnail);
            videoName = (TextView) itemView.findViewById(R.id.videoName);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.frame_row);
        }
    }
}
