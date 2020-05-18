package com.example.finalexam801129943;

import java.io.Serializable;

public class MeetingDomain implements Serializable {
    String title;
    String place;
    String date;
    String time;
    String uuid;


    public MeetingDomain() {
    }

    public MeetingDomain(String title, String place, String date, String time, String uuid) {
        this.title = title;
        this.place = place;
        this.date = date;
        this.time = time;
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "MeetingDomain{" +
                "title='" + title + '\'' +
                ", place='" + place + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
