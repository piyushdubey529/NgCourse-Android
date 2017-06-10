package com.ngcourse.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ngcourse.R;

import java.util.ArrayList;

/**
 * Created by piyush on 9/6/17.
 */

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseViewHolder>{
    private FragmentActivity mContext;
    public ArrayList<String> courseList;

    public CourseListAdapter(FragmentActivity mContext, ArrayList<String> courseList){
        this.mContext = mContext;
        this.courseList = courseList;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_course_list_layout, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
      holder.courseName.setText(courseList.get(position));
    }

    @Override
    public int getItemCount() {
        if(courseList != null) return courseList.size();
        return 0;
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder{
        private TextView courseName;

        public CourseViewHolder(View itemView) {
            super(itemView);
            courseName = (TextView) itemView.findViewById(R.id.courseName);
        }
    }
}
