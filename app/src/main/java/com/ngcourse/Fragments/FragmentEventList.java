package com.ngcourse.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.ngcourse.NetworkCall.NetworkCallResponse;
import com.ngcourse.R;
import com.ngcourse.ResponseInterfaces.ResponseEventList;
import com.ngcourse.Webservices.EventListApi;
import com.ngcourse.adapter.EventListAdapter;
import com.ngcourse.beans.Event;
import com.ngcourse.utilities.FontAwesome;
import java.util.ArrayList;

/**
 * Created by piyush on 18/6/17.
 */

public class FragmentEventList extends Fragment implements NetworkCallResponse,ResponseEventList{
    public static String API_TAG;
    private FragmentActivity mContext;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private EventListAdapter eventListAdapter;
    private FontAwesome filterIcon;
    private FontAwesome searchIcon;
    private LinearLayout searchLayout;
    private EditText searchInput;
    private FontAwesome backButton;
    private ArrayList<Event> eventList;


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
        EventListApi eventListApi = new EventListApi(mContext);
        eventListApi.getEventListApi(this,this);
        mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        eventListAdapter = new EventListAdapter(mContext, eventList);
        recyclerView.setAdapter(eventListAdapter);
    }

    private void initView() {
        recyclerView = (RecyclerView) mContext.findViewById(R.id.videolist_recyclerview);
    }

    @Override
    public void callResponse(Boolean response, String API_TAG) {

    }

    @Override
    public void responseEvents(ArrayList<Event> eventList) {
        this.eventList = eventList;
        eventListAdapter.eventList = eventList;
        eventListAdapter.notifyDataSetChanged();
    }
}
