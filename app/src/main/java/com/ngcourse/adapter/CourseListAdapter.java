package com.ngcourse.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ngcourse.Fragments.FragmentCourseVideoList;
import com.ngcourse.Fragments.FragmentPlayYoutubeVideo;
import com.ngcourse.R;
import com.ngcourse.beans.Course;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by piyush on 9/6/17.
 */

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseViewHolder>{
    private FragmentActivity mContext;
    public ArrayList<Course> courseList;

    public CourseListAdapter(FragmentActivity mContext, ArrayList<Course> courseList){
        this.mContext = mContext;
        this.courseList = courseList;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_course_list_layout, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, final int position) {
      holder.courseName.setText(courseList.get(position).getCourseName());
        Picasso.with(mContext)
                .load("http://magemello.github.io/articles/img/ng2-logo.png")
                .into(holder.courseImage);
        holder.rowFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragmentCourseVideoList(courseList.get(position).getTechnology());
            }
        });
    }

    private void openFragmentCourseVideoList(String keyword) {
        Fragment fragment= new FragmentCourseVideoList();
        FragmentManager fragmentManager = mContext.getSupportFragmentManager();
        Bundle bundle =new Bundle();
        bundle.putString("keyword", keyword);
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

    @Override
    public int getItemCount() {
        if(courseList != null) return courseList.size();
        return 0;
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder{
        private TextView courseName;
        private ImageView courseImage;
         private LinearLayout rowFrame;
        public CourseViewHolder(View itemView) {
            super(itemView);
            courseName = (TextView) itemView.findViewById(R.id.courseName);
            courseImage = (ImageView) itemView.findViewById(R.id.courseImage);
            rowFrame = (LinearLayout) itemView.findViewById(R.id.frame_row);
        }
    }
}
