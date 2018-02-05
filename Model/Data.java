package com.example.rspl_rahul.gitrepo.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rspl-rahul on 30/11/17.
 */

public class Data {
    @SerializedName("source")
    String source;
    @SerializedName("movieId")
       int movieId;
    @SerializedName("eventType")
    String eventType;
    @SerializedName("name")
    String name;
    @SerializedName("genre")
    String genre;
    @SerializedName("summery")
    String summery;
    @SerializedName("banner")
    String banner;
    @SerializedName("thumbnailImagePath")
    String thumbnailImagePath;
    @SerializedName("openBookingsOn")
    String openBookingsOn;

    public Data(String source, int movieId, String eventType, String name, String genre, String summery, String banner, String thumbnailImagePath, String openBookingsOn) {
        this.source = source;
        this.movieId = movieId;
        this.eventType = eventType;
        this.name = name;
        this.genre = genre;
        this.summery = summery;
        this.banner = banner;
        this.thumbnailImagePath = thumbnailImagePath;
        this.openBookingsOn = openBookingsOn;
    }

    public Data(){

    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSummery() {
        return summery;
    }

    public void setSummery(String summery) {
        this.summery = summery;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getThumbnailImagePath() {
        return thumbnailImagePath;
    }

    public void setThumbnailImagePath(String thumbnailImagePath) {
        this.thumbnailImagePath = thumbnailImagePath;
    }

    public String getOpenBookingsOn() {
        return openBookingsOn;
    }

    public void setOpenBookingsOn(String openBookingsOn) {
        this.openBookingsOn = openBookingsOn;
    }

}
