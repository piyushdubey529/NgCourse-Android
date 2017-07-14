package com.ngcourse.adapter;

import android.graphics.PorterDuff;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ngcourse.Fragments.FragmentCourseList;
import com.ngcourse.Fragments.FragmentEventList;
import com.ngcourse.Fragments.FragmentUpload;
import com.ngcourse.Fragments.FragmentUploadBlog;
import com.ngcourse.Fragments.FragmentVideoList;
import com.ngcourse.R;
import com.ngcourse.beans.HorizantalView;
import com.ngcourse.beans.Video;

import java.util.ArrayList;

/**
 * Created by piyush on 25/6/17.
 */

public class HorizontalListAdapter extends RecyclerView.Adapter<HorizontalListAdapter.HorizontalListAdapterHolder> {

    private FragmentActivity context;
    private ArrayList<HorizantalView> horizontalList;

    public HorizontalListAdapter(FragmentActivity mContext, ArrayList<HorizantalView> horizontalList) {
        this.context = mContext;
        this.horizontalList = horizontalList;
    }

    @Override
    public HorizontalListAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_horizontal_videolist, parent, false);
        return new HorizontalListAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(HorizontalListAdapterHolder holder, int position) {
        holder.videoName.setText(horizontalList.get(position).getTopic());
        holder.thumbNail.setImageResource(horizontalList.get(position).getDrawableId());
        final int id = horizontalList.get(position).getDrawableId();
        holder.thumbNail.getDrawable().setColorFilter(ContextCompat.getColor(context, R.color.cardview_light_background), PorterDuff.Mode.DARKEN);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(id);
            }
        });
    }

    private void openFragment(int id) {
        Fragment fragment = null;
        switch (id) {
            case R.drawable.videos:
                fragment = new FragmentVideoList();
                break;
            case R.drawable.courses:
                fragment = new FragmentCourseList();
                break;
            case R.drawable.event:
                fragment = new FragmentEventList();
                break;
            case R.drawable.blogs:
                fragment = new FragmentUploadBlog();
                break;
            case R.drawable.earming:
                break;
            case R.drawable.upload:
                fragment = new FragmentUpload();
                break;
        }
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = context.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public int getItemCount() {
        if (horizontalList != null) return horizontalList.size();
        else return 0;
    }

    public class HorizontalListAdapterHolder extends RecyclerView.ViewHolder {
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
