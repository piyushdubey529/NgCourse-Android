package com.ngcourse.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ngcourse.NetworkCall.NetworkCallResponse;
import com.ngcourse.R;
import com.ngcourse.ResponseInterfaces.ResponseCourseList;
import com.ngcourse.Webservices.CourseListApi;
import com.ngcourse.adapter.CourseListAdapter;
import com.ngcourse.beans.Course;
import com.ngcourse.utilities.FontAwesome;

import java.util.ArrayList;

/**
 * Created by piyush on 9/6/17.
 */

public class FragmentCourseList extends Fragment implements NetworkCallResponse, ResponseCourseList, View.OnClickListener,
        TextWatcher{
    public static String API_TAG;
    private FragmentActivity mContext;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CourseListAdapter courseListAdapter;
    private FontAwesome filterIcon;
    private FontAwesome searchIcon;
    private LinearLayout searchLayout;
    private EditText searchInput;
    private FontAwesome backButton;
    private Toolbar toolbar;
    private ArrayList<Course> courseList;

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
        setListener();
        setView();
    }

    private void setListener() {
        searchIcon.setOnClickListener(this);
        backButton.setOnClickListener(this);
        searchInput.addTextChangedListener(this);
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
        searchIcon = (FontAwesome) mContext.findViewById(R.id.search_icon);
        toolbar = (Toolbar) mContext.findViewById(R.id.toolbar);
        searchLayout = (LinearLayout) mContext.findViewById(R.id.searchLayout);
        searchInput = (EditText) mContext.findViewById(R.id.searchInput);
        backButton = (FontAwesome) mContext.findViewById(R.id.backButton);
        searchIcon.setVisibility(View.VISIBLE);
    }


    @Override
    public void callResponse(Boolean response, String API_TAG) {

    }

    @Override
    public void responseCourses(ArrayList<Course> courseList) {
        this.courseList = courseList;
        courseListAdapter.courseList = courseList;
        courseListAdapter.notifyDataSetChanged();
    }

    private void closeSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchLayout.getApplicationWindowToken(), 0);
    }

    private void openSoftKeyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(
                searchLayout.getApplicationWindowToken(),
                InputMethodManager.SHOW_FORCED, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_icon:
                toolbar.setVisibility(View.GONE);
                searchLayout.setVisibility(View.VISIBLE);
                searchInput.requestFocus();
                openSoftKeyboard();
                break;

            case R.id.backButton:
                searchLayout.setVisibility(View.GONE);
                toolbar.setVisibility(View.VISIBLE);
                closeSoftKeyboard();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Bundle bundle = new Bundle();
        bundle.putString("keyword", s.toString());
        openSearchCourseFragment(bundle);
    }

    private void openSearchCourseFragment(Bundle bundle) {
        Fragment fragment = new FragmentCourseSearchList();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = mContext.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
