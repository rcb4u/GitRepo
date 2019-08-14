package com.example.rspl_rahul.gitrepo.Adpater;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rspl_rahul.gitrepo.Model.Movie_Data;
import com.example.rspl_rahul.gitrepo.R;
import com.example.rspl_rahul.gitrepo.View.GetShowDateActivity;
import com.example.rspl_rahul.gitrepo.View.MainActivity;
import com.example.rspl_rahul.gitrepo.View.MovieDetailActivity;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rspl-rahul on 3/1/18.
 */
public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private MainActivity mContext;
    private List<Movie_Data> albumList;
    private int mposition;
    public AlbumsAdapter(MainActivity mContext, List<Movie_Data> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Movie_Data album = albumList.get(position);
        holder.title.setText(album.getMovieName());
        holder.Genre.setText(album.getGenre()+" "+album.getDuration());
        // loading album cover using Picasso library
        holder.mratingtv.setText(album.getImdbRate());
        Picasso.get()
                .load(album.getThumbnailImagePath())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.thumbnail);

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mContext, MovieDetailActivity.class);
                List<Movie_Data.Source> source=album.getSources();
                Bundle bundle = new Bundle();
                bundle.putSerializable("Source_Locations", (Serializable) source);
                in.putExtra("movieId", album.getMovieId());
                in.putExtras(bundle);
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(in);
            }
        });
        holder.Book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mContext, GetShowDateActivity.class);
                Bundle bundle = new Bundle();
                Log.e("Movie_ID",album.getMovieId());
                in.putExtra("movieId",album.getMovieId());
                bundle.putSerializable("Movie_data", album);
                in.putExtras(bundle);
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(in);

            }
        });

    }



    @Override
    public int getItemCount() {
        if (albumList.size() < 0)
            return 1;
        return albumList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, Genre,mratingtv;
        public RoundedImageView thumbnail;
        public LinearLayout card_view;
        public Button Book;

        public MyViewHolder(View view) {
            super(view);
            card_view = (LinearLayout) view.findViewById(R.id.Album_card_view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (RoundedImageView) view.findViewById(R.id.thumbnail);
            Book = (Button) view.findViewById(R.id.book);
            Genre = (TextView) view.findViewById(R.id.genre);
            mratingtv=(TextView)view.findViewById(R.id.IMDB);
        }

    }
}
