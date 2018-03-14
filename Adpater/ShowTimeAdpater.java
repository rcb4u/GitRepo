package com.example.rspl_rahul.gitrepo.Adpater;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.rspl_rahul.gitrepo.Model.ShowTime;
import com.example.rspl_rahul.gitrepo.R;
import com.example.rspl_rahul.gitrepo.SeatPlanActivity;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by rspl-rahul on 8/1/18.
 */
public class ShowTimeAdpater extends RecyclerView.Adapter<ShowTimeAdpater.ShowTimeViewHolder> {
    private Context mContext;
    private List<ShowTime> showtimelist;

    public ShowTimeAdpater(Context context, List<ShowTime>showtimelist) {
        this.mContext=context;
        this.showtimelist = showtimelist;
    }

    @Override
    public ShowTimeAdpater.ShowTimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.showtime, parent, false);
        ShowTimeViewHolder showTimeViewHolder =new ShowTimeViewHolder(itemView);
        return showTimeViewHolder;
    }
    @Override
    public void onBindViewHolder(ShowTimeAdpater.ShowTimeViewHolder holder, int position) {
    final ShowTime showTime = showtimelist.get(position);
        holder.theaters.setText(showTime.getTheaters());
        holder.movieDate.setText(showTime.getDate());
        holder.linearLayout.removeAllViews();
        for(int i=1;i<=showTime.getTimeslots().size();i++) {
            holder.myButton[i] = new Button(mContext);
            holder.myButton[i].setTextColor(0xffffffff);
            holder.myButton[i].setBackground(mContext.getResources().getDrawable(R.drawable.shapebuttonsales));
            holder.linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, (float) 1.0);
            lp.setMargins(5, 5, 5, 5);
            holder.linearLayout.addView(holder.myButton[i], lp);
            StringTokenizer tk = new StringTokenizer(showTime.getTimeslots().get(i-1));
            String time = tk.nextToken();

            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm a");
            Date dt = null;
            try {
                dt = sdf.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            holder.myButton[i].setText(sdfs.format(dt));
            holder.myButton[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button)v;
                    String buttonText = b.getText().toString();
                    Toast.makeText(mContext, ""+buttonText, Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(mContext, SeatPlanActivity.class);
                   // in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(in);
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return showtimelist.size();
    }

    public class ShowTimeViewHolder extends RecyclerView.ViewHolder {
        public TextView theaters,movieDate;
        Button[] myButton = new Button[10];
        LinearLayout linearLayout ;

        public ShowTimeViewHolder(View itemView) {
            super(itemView);
            theaters =(TextView)itemView.findViewById(R.id.theaters);
            movieDate=(TextView)itemView.findViewById(R.id.MovieDate);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.buttonlayout);
        }
    }
}
