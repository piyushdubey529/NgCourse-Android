package com.ngcourse.beans;

/**
 * Created by piyush on 28/5/17.
 */

public class Video {
    private String id;
    private String videoUrl;
    private String videoName;
    private String topic;

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
}
