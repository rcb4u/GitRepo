package com.example.rspl_rahul.gitrepo.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
/**
 * Created by rspl-rahul on 30/11/17.
 */
public class GetMovies implements Serializable {
    @SerializedName("state")
    @Expose
    private Boolean state;
    @SerializedName("data")
    @Expose
    private List<Movie_Data> data = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("client")
    @Expose
    private String client;
    private final static long serialVersionUID = -7423284911337055566L;

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public List<Movie_Data> getData() {
        return data;
    }

    public void setData(List<Movie_Data> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}

