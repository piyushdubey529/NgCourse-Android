package com.ngcourse.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by piyush on 28/5/17.
 */

public class Video implements Parcelable{
    private String id;
    private String videoUrl;
    private String videoName;
    private String topic;
    private String youtubeVideoId;
    private String technology;



    public Video(){

    }

    public Video(Parcel in) {
        id = in.readString();
        videoUrl = in.readString();
        videoName = in.readString();
        topic = in.readString();
        youtubeVideoId = in.readString();
        technology = in.readString();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getYoutubeVideoId() {
        return youtubeVideoId;
    }

    public void setYoutubeVideoId(String youtubeVideoId) {
        this.youtubeVideoId = youtubeVideoId;
    }

    public String getId() {
        return id;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getVideoName() {
        return videoName;
    }

    public String getTopic() {
        return topic;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(videoUrl);
        dest.writeString(videoName);
        dest.writeString(topic);
        dest.writeString(youtubeVideoId);
        dest.writeString(technology);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj !=null && obj instanceof Video) return this.id.equals(((Video)obj).id);
        return false;
    }

}
