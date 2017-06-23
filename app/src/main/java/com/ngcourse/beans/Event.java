package com.ngcourse.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by piyush on 22/6/17.
 */

public class Event implements Parcelable {
    String _id;
    String Event_name;
    String event_happen;

    public String get_id() {
        return _id;
    }

    public String getEvent_happen() {
        return event_happen;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setEvent_happen(String event_happen) {
        this.event_happen = event_happen;
    }

    public Event(){

    }

    protected Event(Parcel in) {
        Event_name = in.readString();
        event_happen = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public String getEvent_name() {
        return Event_name;
    }

    public void setEvent_name(String event_name) {
        Event_name = event_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Event_name);
        dest.writeString(event_happen);
        dest.writeString(_id);
    }
}
