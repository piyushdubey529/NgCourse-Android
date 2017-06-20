package com.ngcourse.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ngcourse.NetworkCall.NetworkCallResponse;
import com.ngcourse.R;
import com.ngcourse.Webservices.EventListApi;
import com.ngcourse.adapter.CourseListAdapter;
import com.ngcourse.utilities.FontAwesome;

/**
 * Created by piyush on 18/6/17.
 */

public class FragmentEventList extends Fragment implements NetworkCallResponse{
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.event_list_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        EventListApi eventListApi = new EventListApi(mContext);
        eventListApi.getEventListApi(this);
    }

    @Override
    public void callResponse(Boolean response, String API_TAG) {

    }
}
