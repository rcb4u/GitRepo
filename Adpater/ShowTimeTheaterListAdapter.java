package com.example.rspl_rahul.gitrepo.Adpater;
import android.media.Image;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.dgreenhalgh.android.simpleitemdecoration.grid.GridBottomOffsetItemDecoration;
import com.dgreenhalgh.android.simpleitemdecoration.grid.GridTopOffsetItemDecoration;
import com.example.rspl_rahul.gitrepo.Model.GetShowtimes;
import com.example.rspl_rahul.gitrepo.R;
import com.example.rspl_rahul.gitrepo.Utils.GridSpaceItemDecoration;
import com.example.rspl_rahul.gitrepo.View.GetShowTimeActivity;

import java.util.List;

import static com.example.rspl_rahul.gitrepo.View.GetShowTimeActivity.dpToPx;

public class ShowTimeTheaterListAdapter extends  RecyclerView.Adapter<ShowTimeTheaterListAdapter.MyViewHolder> {
    private List<GetShowtimes.Experience> experience;
    private GetShowTimeActivity mContext;
    private ShowTimesAdapterCallback mCallback;
    private List<GetShowtimes.ShowTime> albumList;

    public interface ShowTimesAdapterCallback {
        void onClickItem(GetShowtimes.ShowTime showTime);
    }
    class C10851 implements ShowTimeListAdapter.MovieExperienceAdapterCallback {
        C10851() {
        }
        public void onClickItem(GetShowtimes.ShowTime showTime) {
            ShowTimeTheaterListAdapter.this.mCallback.onClickItem(showTime);
        }
    }

    public ShowTimeTheaterListAdapter(GetShowTimeActivity getShowTimeActivity, List<GetShowtimes.Experience> experience, List<GetShowtimes.ShowTime> showtimes,ShowTimesAdapterCallback mCallback) {
        this.mContext = getShowTimeActivity;
        this.albumList = showtimes;
        this.experience=experience;
        this.mCallback=mCallback;
    }

    @Override
    public ShowTimeTheaterListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_time_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        //final Movie_Data
        final GetShowtimes.ShowTime album = albumList.get(position);
        holder.Theater_Name.setText(album.getTheaterName());
        Log.e("value",album.getTheaterName());
        ShowTimeListAdapter itemListDataAdapter = new ShowTimeListAdapter(mContext,experience,album,new C10851());
        holder.mrecyclerView.setHasFixedSize(true);
        holder.mrecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        holder.mrecyclerView.setAdapter(itemListDataAdapter);
    }
    @Override
    public int getItemCount() {
        return albumList == null ? 0 : albumList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Theater_Name;
        public RecyclerView mrecyclerView;
        public ImageView showImage;
        public MyViewHolder(View itemView) {
            super(itemView);
            Theater_Name=(TextView)itemView.findViewById(R.id.heading);
            mrecyclerView=(RecyclerView)itemView.findViewById(R.id.rv_experience);
           // showImage=(ImageView)itemView.findViewById(R.id.);
        }
    }
}