package com.example.rspl_rahul.gitrepo.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Movie_Data implements Serializable {

    @SerializedName("movieId")
    @Expose
    private String movieId;
    @SerializedName("eventType")
    @Expose
    private String eventType;
    @SerializedName("movieName")
    @Expose
    private String movieName;
    @SerializedName("genre")
    @Expose
    private String genre;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("thumbnailImagePath")
    @Expose
    private String thumbnailImagePath;
    @SerializedName("banner")
    @Expose
    private String banner;
    @SerializedName("imdb_rate")
    @Expose
    private String imdbRate;
    @SerializedName("trailer")
    @Expose
    private String trailer;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("openBookingsOn")
    @Expose
    private String openBookingsOn;
    @SerializedName("movieStartDate")
    @Expose
    private String movieStartDate;
    @SerializedName("movieEndDate")
    @Expose
    private String movieEndDate;
    @SerializedName("sources")
    @Expose
    private List<Source> sources = null;
    private final static long serialVersionUID = -7019538719265488043L;

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getThumbnailImagePath() {
        return thumbnailImagePath;
    }

    public void setThumbnailImagePath(String thumbnailImagePath) {
        this.thumbnailImagePath = thumbnailImagePath;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getImdbRate() {
        return imdbRate;
    }

    public void setImdbRate(String imdbRate) {
        this.imdbRate = imdbRate;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getOpenBookingsOn() {
        return openBookingsOn;
    }

    public void setOpenBookingsOn(String openBookingsOn) {
        this.openBookingsOn = openBookingsOn;
    }

    public String getMovieStartDate() {
        return movieStartDate;
    }

    public void setMovieStartDate(String movieStartDate) {
        this.movieStartDate = movieStartDate;
    }

    public String getMovieEndDate() {
        return movieEndDate;
    }

    public void setMovieEndDate(String movieEndDate) {
        this.movieEndDate = movieEndDate;
    }

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }

    public class Source implements Serializable
    {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("locations")
        @Expose
        private List<Location> locations = null;
        private final static long serialVersionUID = 7575543896388331464L;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Location> getLocations() {
            return locations;
        }

        public void setLocations(List<Location> locations) {
            this.locations = locations;
        }

    }
    public class Location implements Serializable
    {

        @SerializedName("locationId")
        @Expose
        private String locationId;
        @SerializedName("locationName")
        @Expose
        private String locationName;
        @SerializedName("locationMail")
        @Expose
        private String locationMail;
        @SerializedName("locationHotline")
        @Expose
        private String locationHotline;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("locationLogo")
        @Expose
        private String locationLogo;
        private final static long serialVersionUID = -1142584098920832466L;

        public String getLocationId() {
            return locationId;
        }

        public void setLocationId(String locationId) {
            this.locationId = locationId;
        }

        public String getLocationName() {
            return locationName;
        }

        public void setLocationName(String locationName) {
            this.locationName = locationName;
        }

        public String getLocationMail() {
            return locationMail;
        }

        public void setLocationMail(String locationMail) {
            this.locationMail = locationMail;
        }

        public String getLocationHotline() {
            return locationHotline;
        }

        public void setLocationHotline(String locationHotline) {
            this.locationHotline = locationHotline;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getLocationLogo() {
            return locationLogo;
        }

        public void setLocationLogo(String locationLogo) {
            this.locationLogo = locationLogo;
        }

    }
}