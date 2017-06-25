package com.ngcourse.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ngcourse.R;
import com.ngcourse.beans.Video;

import java.util.ArrayList;

/**
 * Created by piyush on 25/6/17.
 */

public class HorizontalListAdapter extends RecyclerView.Adapter<HorizontalListAdapter.HorizontalListAdapterHolder> {

    private FragmentActivity context;

    public HorizontalListAdapter(FragmentActivity mContext, ArrayList<Video> videoList){
        this.context = mContext;
    }

    @Override
    public HorizontalListAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(HorizontalListAdapterHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class HorizontalListAdapterHolder extends RecyclerView.ViewHolder{
        private ImageView thumbNail;
        private TextView videoName;
        private LinearLayout linearLayout;

        public HorizontalListAdapterHolder(View itemView) {
            super(itemView);
            thumbNail = (ImageView) itemView.findViewById(R.id.thumbnail);
            videoName = (TextView) itemView.findViewById(R.id.videoName);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.frame_row);
        }
    }
}
