package com.ngcourse.beans;

/**
 * Created by piyush on 8/7/17.
 */

public class HorizantalView {
    int drawableId;
    String topic;

    public HorizantalView() {

    }

    public HorizantalView(int drawableId, String topic) {
        this.drawableId = drawableId;
        this.topic = topic;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
