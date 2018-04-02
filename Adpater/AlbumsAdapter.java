package com.example.rspl_rahul.gitrepo.Adpater;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rspl_rahul.gitrepo.Model.Data;
import com.example.rspl_rahul.gitrepo.R;
import com.example.rspl_rahul.gitrepo.ShowTimesActivity;

import java.util.List;

/**
 * Created by rspl-rahul on 3/1/18.
 */

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Data> albumList;
    private int mposition;

    public AlbumsAdapter(Context mContext, List<Data> albumList) {
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
        final Data album = albumList.get(position);
        holder.title.setText(album.getName());
        // loading album cover using Glide library
        Glide.with(mContext).load(album.getThumbnailImagePath()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mposition = position;
                Log.e("mposition", "" + mposition);
                showPopupMenu(holder.overflow);
            }
        });
        holder.Book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mContext, ShowTimesActivity.class);
                in.putExtra("movieId", album.getMovieId());
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(in);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    @Override
    public int getItemCount() {
        if (albumList.size() < 0)
            return 1;
        return albumList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, summery;
        public ImageView thumbnail, overflow;
        public CardView card_view;
        public Button Book;

        public MyViewHolder(View view) {
            super(view);
            card_view = (CardView) view.findViewById(R.id.card_view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            Book = (Button) view.findViewById(R.id.book);
            //  summery = (TextView) view.findViewById(R.id.summery);
        }

    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Log.e("mbuttonclick", "" + mposition);
                    Data album = albumList.get(mposition);
                    Toast.makeText(mContext, album.getSummery(), Toast.LENGTH_SHORT).show();

                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }
}
