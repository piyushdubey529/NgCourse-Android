package com.ngcourse.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ngcourse.R;
import com.ngcourse.beans.Course;
import com.ngcourse.beans.Event;

import java.util.ArrayList;

/**
 * Created by piyush on 22/6/17.
 */

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder>{

    private FragmentActivity mContext;
    public ArrayList<Event> eventList;

    public EventListAdapter(FragmentActivity mContext, ArrayList<Event> eventList){
        this.mContext = mContext;
        this.eventList = eventList;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_event_list_layout, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        holder.eventName.setText(eventList.get(position).getEvent_name());
    }

    @Override
    public int getItemCount() {
        if(eventList != null) return eventList.size();
        return 0;
    }

    public class EventViewHolder extends RecyclerView.ViewHolder{
        private TextView eventName;
        private ImageView eventImage;
        private LinearLayout rowFrame;
        public EventViewHolder(View itemView) {
            super(itemView);
            eventName = (TextView) itemView.findViewById(R.id.eventName);
            eventImage = (ImageView) itemView.findViewById(R.id.eventImage);
            rowFrame = (LinearLayout) itemView.findViewById(R.id.frame_row);
        }
    }
}
