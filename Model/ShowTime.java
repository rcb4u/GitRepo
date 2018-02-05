package com.example.rspl_rahul.gitrepo.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rspl-rahul on 8/1/18.
 */

public class ShowTime {
    String theaters;
    ArrayList<String> timeslots;
    String date;

    public ShowTime() {
    }

    public ShowTime(String theaters,String date ,ArrayList<String> timeslots) {
        this.theaters = theaters;
        this.timeslots = timeslots;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTheaters() {
        return theaters;
    }

    public void setTheaters(String theaters) {
        this.theaters = theaters;
    }

    public ArrayList<String> getTimeslots() {
        return timeslots;
    }

    public void setTimeslots(ArrayList<String> timeslots) {
        this.timeslots = timeslots;
    }

    @Override
    public String toString() {
        return "Class pojo ";
    }
}
