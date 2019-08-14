package com.example.rspl_rahul.gitrepo.Model;
import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
//-----------------------------------com.example.rspl_rahul.gitrepo.Model.SelectionResponse.java-----------------------------------
public class Selection_Response implements Serializable {

    @SerializedName("state")
    @Expose
    private Boolean state;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("message")
    @Expose
    private String message;
    private final static long serialVersionUID = 4337649177388625693L;

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("state", state).append("data", data).append("message", message).toString();
    }


    //-----------------------------------com.example.rspl_rahul.gitrepo.Model.Data.java-----------------------------------
    public class Data implements Serializable {

        @SerializedName("bookingIndex")
        @Expose
        private String bookingIndex;
        @SerializedName("referenceNo")
        @Expose
        private String referenceNo;
        @SerializedName("movieId")
        @Expose
        private String movieId;
        @SerializedName("movieName")
        @Expose
        private String movieName;
        @SerializedName("locationId")
        @Expose
        private String locationId;
        @SerializedName("locationName")
        @Expose
        private String locationName;
        @SerializedName("showTime")
        @Expose
        private String showTime;
        @SerializedName("movieDate")
        @Expose
        private String movieDate;
        @SerializedName("holdSeats")
        @Expose
        private List<HoldSeat> holdSeats = null;
        @SerializedName("subCategories")
        @Expose
        private List<SubCategory> subCategories = null;
        private final static long serialVersionUID = -3078273396721839887L;

        public String getBookingIndex() {
            return bookingIndex;
        }

        public void setBookingIndex(String bookingIndex) {
            this.bookingIndex = bookingIndex;
        }

        public String getReferenceNo() {
            return referenceNo;
        }

        public void setReferenceNo(String referenceNo) {
            this.referenceNo = referenceNo;
        }

        public String getMovieId() {
            return movieId;
        }

        public void setMovieId(String movieId) {
            this.movieId = movieId;
        }

        public String getMovieName() {
            return movieName;
        }

        public void setMovieName(String movieName) {
            this.movieName = movieName;
        }

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

        public String getShowTime() {
            return showTime;
        }

        public void setShowTime(String showTime) {
            this.showTime = showTime;
        }

        public String getMovieDate() {
            return movieDate;
        }

        public void setMovieDate(String movieDate) {
            this.movieDate = movieDate;
        }

        public List<HoldSeat> getHoldSeats() {
            return holdSeats;
        }

        public void setHoldSeats(List<HoldSeat> holdSeats) {
            this.holdSeats = holdSeats;
        }

        public List<SubCategory> getSubCategories() {
            return subCategories;
        }

        public void setSubCategories(List<SubCategory> subCategories) {
            this.subCategories = subCategories;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("bookingIndex", bookingIndex).append("referenceNo", referenceNo).append("movieId", movieId).append("movieName", movieName).append("locationId", locationId).append("locationName", locationName).append("showTime", showTime).append("movieDate", movieDate).append("holdSeats", holdSeats).append("subCategories", subCategories).toString();
        }

    }

    //-----------------------------------com.example.rspl_rahul.gitrepo.Model.HoldSeat.java-----------------------------------
    public class HoldSeat implements Serializable {

        @SerializedName("label")
        @Expose
        private String label;
        @SerializedName("subCategoryId")
        @Expose
        private String subCategoryId;
        @SerializedName("subCategoryName")
        @Expose
        private String subCategoryName;
        private final static long serialVersionUID = -5610964927808697061L;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getSubCategoryId() {
            return subCategoryId;
        }

        public void setSubCategoryId(String subCategoryId) {
            this.subCategoryId = subCategoryId;
        }

        public String getSubCategoryName() {
            return subCategoryName;
        }

        public void setSubCategoryName(String subCategoryName) {
            this.subCategoryName = subCategoryName;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("label", label).append("subCategoryId", subCategoryId).append("subCategoryName", subCategoryName).toString();
        }

    }

    //-----------------------------------com.example.rspl_rahul.gitrepo.Model.SubCategory.java-----------------------------------
    public class SubCategory implements Serializable {

        @SerializedName("subCategoryId")
        @Expose
        private String subCategoryId;
        @SerializedName("seatCount")
        @Expose
        private Integer seatCount;
        private final static long serialVersionUID = 8645261007331466288L;

        public String getSubCategoryId() {
            return subCategoryId;
        }

        public void setSubCategoryId(String subCategoryId) {
            this.subCategoryId = subCategoryId;
        }

        public Integer getSeatCount() {
            return seatCount;
        }

        public void setSeatCount(Integer seatCount) {
            this.seatCount = seatCount;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("subCategoryId", subCategoryId).append("seatCount", seatCount).toString();
        }

    }
}