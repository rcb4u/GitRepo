package com.example.rspl_rahul.gitrepo.Adpater;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rspl_rahul.gitrepo.Model.GetShowtimes;
import com.example.rspl_rahul.gitrepo.Model.Movie_Data;
import com.example.rspl_rahul.gitrepo.R;
import com.example.rspl_rahul.gitrepo.View.GetShowDateActivity;
import com.example.rspl_rahul.gitrepo.View.GetShowTimeActivity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DateListAdapter extends RecyclerView.Adapter<DateListAdapter.MyViewHolder> {
    private GetShowDateActivity mContext;
    private List<GetShowtimes.MovieDate> albumList;
    private List<GetShowtimes.Experience>experiences;
    private Movie_Data movie_data;
    private int mposition;

    public DateListAdapter(GetShowDateActivity getShowDateActivity, Movie_Data album, List<GetShowtimes.Experience> experiences, List<GetShowtimes.MovieDate> movieDates) {
        this.mContext = getShowDateActivity;
        this.albumList = movieDates;
        this.movie_data=album;
        this.experiences=experiences;
    }

    @Override
    public DateListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.date_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( final MyViewHolder holder, int position) {
        //final Movie_Data
        final GetShowtimes.MovieDate album = albumList.get(position);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(album.getDate());
        Log.e("Date :- key ",album.getDate());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE");
        holder.day.setText(simpleDateFormat.format(date).toUpperCase());
        System.out.println("DAY " + simpleDateFormat.format(date).toUpperCase());
        simpleDateFormat = new SimpleDateFormat("MMM");
        holder.month.setText(simpleDateFormat.format(date).toUpperCase());
        System.out.println("MONTH " + simpleDateFormat.format(date).toUpperCase());
        simpleDateFormat = new SimpleDateFormat("dd");
        holder.date.setText(simpleDateFormat.format(date).toUpperCase());
        System.out.println("date " + simpleDateFormat.format(date).toUpperCase());

        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.date_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "IM clicked"+album.getDate(), Toast.LENGTH_SHORT).show();

                Intent in = new Intent(mContext, GetShowTimeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Movie_data",movie_data);
                bundle.putSerializable("Movie_ShowTime", album);
                bundle.putSerializable("Experiences", (Serializable) experiences);
                in.putExtras(bundle);
                mContext.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return albumList == null ? 0 : albumList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView month,date,day;
        LinearLayout date_view;
        public MyViewHolder(View itemView) {
            super(itemView);
            month=(TextView)itemView.findViewById(R.id.txt_month);
            date=(TextView)itemView.findViewById(R.id.txt_date);
            day=(TextView)itemView.findViewById(R.id.txt_day);
            date_view=(LinearLayout)itemView.findViewById(R.id.date_view);
        }
    }
}
