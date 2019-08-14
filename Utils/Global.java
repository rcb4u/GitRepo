package com.example.rspl_rahul.gitrepo.Utils;

import android.app.Application;

import com.example.rspl_rahul.gitrepo.View.SeatPlanActivity;


public class Global extends Application {
    private String bookingID;
    private String first_name;
    private String last_name;
    public SeatPlanActivity seatPlanFragment;
  //  public SummeryFragment summeryFragment;
    private String uid;

    public String getBookingID() {
        return this.bookingID;
    }

    public void setBookingID(String abookingID) {
        this.bookingID = abookingID;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String auid) {
        this.uid = auid;
    }

    public String getFirstName() {
        return this.first_name;
    }

    public void setFirstName(String aEmail) {
        this.first_name = aEmail;
    }

    public String getLast_name() {
        return this.last_name;
    }

    public void setLast_name(String aLast_name) {
        this.last_name = aLast_name;
    }

    public void onCreate() {
        super.onCreate();
       /* ImageLoader.getInstance()
                .init(new AlertDialog.Builder(getApplicationContext())
                        .threadPoolSize(4).diskCacheExtraOptions(480, 320, null)
                        .memoryCache(new LruMemoryCache(2097152))
                        .defaultDisplayImageOptions(new DisplayImageOptions.Builder()
                        .cacheInMemory(false).cacheOnDisk(true)
                        .bitmapConfig(Config.RGB_565)
                        .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                        .build()).build());*/
    }
}
