package com.ngcourse.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ngcourse.NetworkCall.NetworkCallResponse;
import com.ngcourse.R;
import com.ngcourse.ResponseInterfaces.ResponseCourseList;
import com.ngcourse.Webservices.CourseListApi;
import com.ngcourse.adapter.CourseListAdapter;
import com.ngcourse.utilities.FontAwesome;

import java.util.ArrayList;

/**
 * Created by piyush on 9/6/17.
 */

public class FragmentCourseList extends Fragment implements NetworkCallResponse, ResponseCourseList{
    public static String API_TAG;
    private FragmentActivity mContext;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CourseListAdapter courseListAdapter;
    private FontAwesome filterIcon;
    private FontAwesome searchIcon;
    private Toolbar toolbar;
    private ArrayList<String> courseList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.video_list_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        initView();
        setView();
    }

    private void setView() {
        CourseListApi courseListApi = new CourseListApi(mContext);
        courseListApi.getVideoListApi(this,this);
        mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
      courseListAdapter = new CourseListAdapter(mContext, courseList);
        recyclerView.setAdapter(courseListAdapter);
    }

    private void initView() {
        recyclerView = (RecyclerView) mContext.findViewById(R.id.videolist_recyclerview);
    }

    @Override
    public void callResponse(Boolean response, String API_TAG) {

    }

    @Override
    public void responseCourses(ArrayList<String> courseList) {
        this.courseList = courseList;
        courseListAdapter.courseList = this.courseList;
        courseListAdapter.notifyDataSetChanged();
    }
}
