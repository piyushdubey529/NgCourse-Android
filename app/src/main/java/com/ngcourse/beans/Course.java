package com.ngcourse.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by piyush on 10/6/17.
 */

public class Course implements Parcelable{
     String id;
    String courseName;
    String technology;

    public Course(){

    }

    public Course(Parcel in) {
        id = in.readString();
        courseName = in.readString();
        technology = in.readString();
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(courseName);
        dest.writeString(technology);
    }
}
