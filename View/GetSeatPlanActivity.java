package com.example.rspl_rahul.gitrepo.View;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rspl_rahul.gitrepo.Model.GetSeatPlan;
import com.example.rspl_rahul.gitrepo.Model.GetShowtimes;
import com.example.rspl_rahul.gitrepo.Model.Movie_Data;
import com.example.rspl_rahul.gitrepo.R;
import com.example.rspl_rahul.gitrepo.Rest.ApiClient;
import com.example.rspl_rahul.gitrepo.Rest.ApiInterface;
import com.example.rspl_rahul.gitrepo.Utils.Constants;
import com.example.rspl_rahul.gitrepo.Utils.FormatDate;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetSeatPlanActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout seatFlow;
    HorizontalScrollView horizontal_view;
    ProgressBar progressBar;
    RoundedImageView Movie_ImageView;
    TextView Total_tickets,tv_adult_count,tv_child_count;
    TextView Movie_Title, Movie_genre, Getshow_message, Movie_Date, actionbar_title;
    int ticket_count_child=0;
    int ticket_count_Adult=0;
    Button continue_booking;
    ImageButton Adult_add_btn,Adult_remove_btn,Child_add_btn,Child_remove_btn;
    private LayoutInflater mInflater;
    private List<Button> mSeatButtonList = new ArrayList();
    private List<Button> mAdultSeatButtonList = new ArrayList<>();
    private List<Button> mChildSeatButtonList = new ArrayList<>();
    private Movie_Data movie_data;
    private GetShowtimes.MovieDate Movie_Date_time;
    private List<GetShowtimes.Experience> experience;
    private List<GetShowtimes.ShowTime> showtimes;
    private GetShowtimes.ShowTime showTime;
    private int total_ticket_count;
    private Double ticket_child_price;
    private Double ticket_adult_price;
    private Boolean state;
    private boolean isSeatSelected=false;
    private int clickCount;
    private String selected_seat_id;
    private String selected;
    private ArrayList<String> SelectedSeatID = new ArrayList();
    private ArrayList<String> SelectedSeatlabel =new ArrayList<>();
    GetSeatPlan.SeatPlan seat = null;
    private LinearLayout linearLayout;

    public static int dpToPx(int i) {
        return (int) (((float) i) * Resources.getSystem().getDisplayMetrics().density);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_seat_plan);
        linearLayout= (LinearLayout)findViewById(R.id.layout_container);
        mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        horizontal_view = (HorizontalScrollView) findViewById(R.id.horizontal_view);
        seatFlow = (RelativeLayout) findViewById(R.id.layout_seat_flow);
        progressBar = (ProgressBar) findViewById(R.id.progressView);
        actionbar_title = (TextView) findViewById(R.id.toolbar_title);
        Movie_ImageView = (RoundedImageView) findViewById(R.id.Movie_ImageView);
        Movie_Title = (TextView) findViewById(R.id.Movie_title);
        Movie_genre = (TextView) findViewById(R.id.Movie_genre);
        Movie_Date = (TextView) findViewById(R.id.Movie_Date);
        Getshow_message = (TextView) findViewById(R.id.Getshow_message);
        Total_tickets=(TextView)findViewById(R.id.tv_total_tickets);
        continue_booking=(Button)findViewById(R.id.continue_booking);
        continue_booking.setOnClickListener(this);
        Adult_add_btn =(ImageButton) findViewById(R.id.adult_add);
        Adult_add_btn.setOnClickListener(this);
        Adult_remove_btn =(ImageButton)findViewById(R.id.adult_remove);
        Adult_remove_btn.setOnClickListener(this);
        Child_add_btn =(ImageButton)findViewById(R.id.child_add);
        Child_add_btn.setOnClickListener(this);
        Child_remove_btn =(ImageButton)findViewById(R.id.child_remove);
        Child_remove_btn.setOnClickListener(this);

        tv_adult_count=(TextView)findViewById(R.id.tv_adult_count);
        tv_child_count=(TextView)findViewById(R.id.tv_child_count);
        Movie_Date.setVisibility(View.VISIBLE);
        actionbar_title.setText("SELECT SEATS");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        intitalview();
        try {
            if (bundle != null) {
                movie_data = (Movie_Data) bundle.getSerializable("Movie_data");
                Movie_Date_time = (GetShowtimes.MovieDate) bundle.getSerializable("Movie_ShowTime");
                experience = (List<GetShowtimes.Experience>) bundle.getSerializable("Experiences");
                showTime = (GetShowtimes.ShowTime) bundle.getSerializable("ShowTime");
               /* showtimes = Movie_Date_time.getShowTimes();
                */Log.e("size get showtimes", String.valueOf(showTime.getCategories().size()));
                ticket_adult_price = Double.valueOf(showTime.getCategories().get(0).getSubCategories().get(0).getPrice());
                ticket_child_price = Double.valueOf(showTime.getCategories().get(0).getSubCategories().get(1).getPrice());

                Movie_Title.setText(movie_data.getMovieName());
                Movie_genre.setText(movie_data.getGenre() + "~" + movie_data.getDuration());

                FormatDate formatDate=new FormatDate(Movie_Date_time.getDate());
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(formatDate.getDatewithSuffix());
                stringBuilder.append("  ");
                stringBuilder.append(formatDate.getMonth());
                stringBuilder.append("  ");
                stringBuilder.append(formatDate.getYYYY());
                stringBuilder.append(", ");
                stringBuilder.append(FormatDate.getTimewithPM2(showTime.getShowTime()));
                Movie_Date.setText(stringBuilder.toString());
                Picasso.get()
                        .load(movie_data.getThumbnailImagePath())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(Movie_ImageView);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        getSeatPlan();

    }

    private void getSeatPlan() {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        JSONObject jsonObject = new JSONObject();
        //how to create an json body and pass to api is as follows
        try {
            jsonObject.put("showTimeId", showTime.getShowTimeId());
            jsonObject.put("locationId", "1");
            jsonObject.put("movieId", movie_data.getMovieId());
            jsonObject.put("source", "scopecinemas");//showTime.getCategories().get(0).getCategoryId()
            jsonObject.put("categoryId", showTime.getCategories().get(0).getCategoryId());
            jsonObject.put("token", Constants.TOKEN);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body =
                RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());

        Call<GetSeatPlan> call = apiService.get_Seat_Plan(Constants.AUTHORIZATION, body);
        call.enqueue(new Callback<GetSeatPlan>() {
            @Override
            public void onResponse(Call<GetSeatPlan> call, final Response<GetSeatPlan> response) {
                if (response.isSuccessful()) {

                    if(state=response.body().getState()) {
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                loadingView();
                                setSeatingPlan(response.body().getData().getSeatPlan());
                            };
                        }, 800);
                    }else{
                       showErrorView();
                    }
            }
            }
            @Override
            public void onFailure(Call<GetSeatPlan> call, Throwable t) {
                // loading.dismiss();
                showErrorView();
                Log.e("Response error", String.valueOf(t.fillInStackTrace()));
            }
        });
    }

    private void showErrorView() {
       RelativeLayout technical_error_view=(RelativeLayout)findViewById(R.id.technical_error_view);
        RelativeLayout Internet_error_view=(RelativeLayout)findViewById(R.id.internet_error_view);
        LinearLayout _view=(LinearLayout) findViewById(R.id.layout_container);
       technical_error_view.setVisibility(View.VISIBLE);
       _view.setVisibility(View.INVISIBLE);
        Internet_error_view.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }
    private void loadingView(){
        RelativeLayout technical_error_view=(RelativeLayout)findViewById(R.id.technical_error_view);
        RelativeLayout Internet_error_view=(RelativeLayout)findViewById(R.id.internet_error_view);
        LinearLayout _view=(LinearLayout) findViewById(R.id.layout_container);
        technical_error_view.setVisibility(View.INVISIBLE);
        _view.setVisibility(View.VISIBLE);
        Internet_error_view.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }
private void intitalview(){
    RelativeLayout technical_error_view=(RelativeLayout)findViewById(R.id.technical_error_view);
    RelativeLayout Internet_error_view=(RelativeLayout)findViewById(R.id.internet_error_view);
    LinearLayout _view=(LinearLayout) findViewById(R.id.layout_container);
    technical_error_view.setVisibility(View.INVISIBLE);
    _view.setVisibility(View.INVISIBLE);
    Internet_error_view.setVisibility(View.INVISIBLE);
    progressBar.setVisibility(View.VISIBLE);
}
    private void setSeatPlanHeight(int i, final int i2) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int i3 = displayMetrics.heightPixels;
        int i4 = displayMetrics.widthPixels;
        ViewGroup.LayoutParams layoutParams = horizontal_view.getLayoutParams();
        layoutParams.height = dpToPx(i + 60);
        layoutParams.width = i4;
        horizontal_view.setLayoutParams(layoutParams);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                horizontal_view.scrollTo((i2 - 44) / 2, 0);
            }
        }, 300);
    }

    private void setSeatingPlan(final List<GetSeatPlan.SeatPlan> list) {
      /*  new Handler().postDelayed(new Runnable() {
            public void run() {*/
                seatFlow.removeAllViews();
                int i = 0;
                int i2 = 0;
                for (int i3 = 0; i3 < list.size(); i3++) {
                    GetSeatPlan.SeatPlan seat = list.get(i3);
                    int parseInt = Integer.parseInt(seat.getX());
                    int parseInt2 = Integer.parseInt(seat.getY());
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(dpToPx(24), dpToPx(24));
                    layoutParams.leftMargin = dpToPx(parseInt);
                    layoutParams.topMargin = dpToPx(parseInt2);
                    Button button = (Button) GetSeatPlanActivity.this.mInflater.inflate(R.layout.seat_button, null);
                    button.setText(seat.getLabel());
                    seat.isSelected = false;
                    seat.Booked = true;
                    if (seat.getStatusCode().intValue() == 1) {
                        button.setBackground(ContextCompat.getDrawable(GetSeatPlanActivity.this, R.drawable.bg_seat_unavailable));
                        button.setTextColor(ContextCompat.getColor(GetSeatPlanActivity.this, R.color.colorPrimary));
                        seat.Booked = false;
                    }
                    if (seat.getStatusCode().intValue() == 2) {
                        button.setBackground(ContextCompat.getDrawable(GetSeatPlanActivity.this, R.drawable.bg_seat_unavailable));
                        button.setTextColor(ContextCompat.getColor(GetSeatPlanActivity.this, R.color.colorPrimary));
                        seat.Booked = false;
                    }
                    seat.seatBtnType = 1;
                    button.setTag(seat);
                    button.setOnClickListener(new seatButtonClicked());
                    mSeatButtonList.add(button);
                    seatFlow.addView(button, layoutParams);

                    if (i2 < parseInt2) {
                        i2 = parseInt2;
                    }
                    if (i < parseInt) {
                        i = parseInt;
                    }
                }
                RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(dpToPx(24), dpToPx(24));
                layoutParams2.leftMargin = dpToPx(dpToPx(4) + i);
                layoutParams2.topMargin = dpToPx(dpToPx(4) + i2);
                seatFlow.addView(new View(GetSeatPlanActivity.this), layoutParams2);
                setSeatPlanHeight(i2, i);
            }
       /* }, 300);
    }*/

    class seatButtonClicked implements View.OnClickListener {
                seatButtonClicked() {
                }

                public void onClick(View view) {
                    Button button = (Button) view;
                    GetSeatPlan.SeatPlan seat = (GetSeatPlan.SeatPlan) button.getTag();
                    // Toast.makeText(GetSeatPlanActivity.this, " Im clicked : "+seat.getLabel(), Toast.LENGTH_SHORT).show();
                    if (!seat.Booked) {
                        return;
                    }
                    if (seat.isSelected) {
                        removeSeat(seat.seatBtnType, button, true);
                    } else {
                        addingSeat(1, button, true);
                    }
                }
            }
       // }, 800);
        // progressBar.setVisibility(View.INVISIBLE);

    //}

    private void addingSeat(int i, Button button, boolean z) {
        GetSeatPlan.SeatPlan seat = (GetSeatPlan.SeatPlan) button.getTag();
        switch (i) {
            case 0:
                button.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_seat_child));
                button.setTextColor(ContextCompat.getColor(this, android.R.color.white));
                ticket_count_child++;
                tv_child_count.setText(Integer.toString(ticket_count_child));
                seat.seatBtnType = 0;
                seat.isSelected = true;
                button.setTag(seat);
                mChildSeatButtonList.add(button);
                SelectedSeatID.add(seat.getId());
                SelectedSeatlabel.add(seat.getLabel());
                break;
            case 1:
                    button.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_seat_selected));
                    button.setTextColor(ContextCompat.getColor(this, android.R.color.white));
                    ticket_count_Adult++;
                    tv_adult_count.setText(Integer.toString(ticket_count_Adult));
                    seat.seatBtnType = 1;
                    seat.isSelected = true;
                    button.setTag(seat);
                    mAdultSeatButtonList.add(button);
                SelectedSeatID.add(seat.getId());
                SelectedSeatlabel.add(seat.getLabel());
                break;
            default:
                break;
        }
        SeatSummary(i,button,seat,z,"2");
    }
    private void removeSeat(int i, Button button, boolean z) {
        GetSeatPlan.SeatPlan seat = (GetSeatPlan.SeatPlan) button.getTag();
        if (seat.isSelected) {
            switch (i) {
                case 0:
                    ticket_count_child--;
                    tv_child_count.setText(Integer.toString(ticket_count_child));
                    seat.seatBtnType = 1;
                    seat.isSelected = false;
                    button.setTag(seat);
                    mChildSeatButtonList.remove(button);
                    SelectedSeatID.remove(seat.getId());
                    SelectedSeatlabel.remove(seat.getLabel());
                    break;
                case 1:
                    ticket_count_Adult--;
                    tv_adult_count.setText(Integer.toString(ticket_count_Adult));
                    seat.seatBtnType = 1;
                    seat.isSelected = false;
                    button.setTag(seat);
                    mAdultSeatButtonList.remove(button);
                    SelectedSeatID.remove(seat.getId());
                    SelectedSeatlabel.remove(seat.getLabel());
                    break;
                default:
                    break;
            }
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_seat_available));
            button.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            SeatSummary(i,button,seat,z,"0");
        }
    }
    private void SeatSummary(int i,Button btn,GetSeatPlan.SeatPlan seat,boolean val,String btn_type){
        total_ticket_count=ticket_count_Adult+ticket_count_child;
        Total_tickets.setText(String.valueOf(total_ticket_count));
        if (ticket_count_Adult > 0) {
            Child_add_btn.setBackground(ContextCompat.getDrawable(this,R.drawable.bg_dark_blue));// R.drawable.bg_corner_radius_dark_blue_enable
            Child_add_btn.setClickable(true);
            Adult_remove_btn.setBackground(ContextCompat.getDrawable(this,R.drawable.bg_dark_blue));
            Adult_remove_btn.setClickable(true);
        } else {
            Child_add_btn.setBackground(ContextCompat.getDrawable(this,R.drawable.bg_light_grey));
            Child_add_btn.setClickable(false);
            Adult_remove_btn.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_light_grey));
            Adult_remove_btn.setClickable(false);
        }
        if (ticket_count_child > 0) {
            Child_remove_btn.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_dark_blue));
            Child_remove_btn.setClickable(true);
            Adult_add_btn.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_dark_blue));
            Adult_add_btn.setClickable(true);
        } else {
            Child_remove_btn.setBackground(ContextCompat.getDrawable(this,R.drawable.bg_light_grey));
            Child_remove_btn.setClickable(false);
            Adult_add_btn.setBackground(ContextCompat.getDrawable(this,R.drawable.bg_light_grey));
            Adult_add_btn.setClickable(false);
        }
        if (this.ticket_count_child == 0 && this.ticket_count_Adult == 0) {
            continue_booking.setBackground(ContextCompat.getDrawable(this,  R.drawable.bg_darker_grey));
        } else {
           continue_booking.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_dark_blue));
        }
        Log.e("Adult_priceChild_price",ticket_adult_price+" "+ticket_child_price);

        double total=(ticket_adult_price*ticket_count_Adult)+(ticket_child_price*ticket_count_child);
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("CONTINUE Rs ");
        stringBuilder3.append(total == 0.0f ? 0 : total);
        stringBuilder3.append("/-");
        continue_booking.setText(stringBuilder3.toString());
        /*if (val) {
            this.clickCount += clickCount;
            selectionASeat(i, btn, seat, btn_type);
        }*/
    }
    @Override
    public void onClick(View v) {
        Button btn;

        switch (v.getId()){
            case R.id.adult_add:
                if(mChildSeatButtonList.size() > 0) {
                    btn = (Button) this.mChildSeatButtonList.get(this.mChildSeatButtonList.size() - 1);
                    seat = ( GetSeatPlan.SeatPlan) btn.getTag();
                    removeSeat(0, btn, false);
                    addingSeat(1, btn, false);
                    return;
                }
                return;
            case R.id.adult_remove:
                if( mAdultSeatButtonList.size() > 0 ) {
                    btn = (Button) this.mAdultSeatButtonList.get(this.mAdultSeatButtonList.size() - 1);
                    seat = ( GetSeatPlan.SeatPlan) btn.getTag();
                    removeSeat(1, btn, false);
                    addingSeat(0, btn, false);
                    return;
                }
                return;
            case R.id.child_add:
                if( mAdultSeatButtonList.size() >0) {
                    btn = (Button) this.mAdultSeatButtonList.get(this.mAdultSeatButtonList.size() - 1);
                    seat = ( GetSeatPlan.SeatPlan) btn.getTag();
                    removeSeat(1, btn, false);
                    addingSeat(0, btn, false);
                    return;
                }
                return;
            case R.id.child_remove:
                if(mChildSeatButtonList.size() >0 ) {
                    btn = (Button) this.mChildSeatButtonList.get(this.mChildSeatButtonList.size() - 1);
                    seat = ( GetSeatPlan.SeatPlan) btn.getTag();
                    removeSeat(0, btn, false);
                    addingSeat(1, btn, false);
                    return;
                }
                return;
            case R.id.continue_booking:
                if(ticket_count_Adult <=0 && ticket_count_child <=0||total_ticket_count<=0){
                    Toast.makeText(this, "Please Select Seat", Toast.LENGTH_SHORT).show();
                }else{
                    Intent in =new Intent(this,TicketViewActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("Movie_data",movie_data);
                    bundle.putSerializable("Movie_ShowTime",Movie_Date_time);
                    bundle.putSerializable("Experiences", (Serializable) experience);
                    bundle.putSerializable("ShowTime",showTime);
                    bundle.putSerializable("SelectedSeatId",SelectedSeatID);
                    bundle.putSerializable("Seat",SelectedSeatlabel);
                    bundle.putInt("ticket_count_Adult",ticket_count_Adult);
                    bundle.putInt("ticket_count_child",ticket_count_child);
                    //bundle.putSerializable("TotalButtonList", (Serializable) mSeatButtonList);

                    in.putExtras(bundle);
                    startActivity(in);

                }
            default:
                return;
        }
    }
 private void selectionASeat(final int i, final Button button, GetSeatPlan.SeatPlan seat, String str) {
     this.selected_seat_id = seat.getId();
     this.selected = seat.getLabel();
     isSeatSelected = true;
        Log.e("Getting seats",selected_seat_id+" "+selected+" "+clickCount);

      /*  this.theater_id = this.mMovieShowTime.theaterId;
        this.show_time_id = this.mMovieShowTime.getShowTimeId();
        this.movie_id = this.mMovie.getMovieId();
        showProgress();
        this.mMovieSync.selectedSeat(this.mAccessToken, this.show_time_id, selected_seat_id, this.mBookingToken, this.user.getUser_key(), this.selected, "3", str, new SelectedSeatCallback() {
            public void onSuccessSelectedSeat(final SelectedSeatResponse selectedSeatResponse) {
                if (!GetSeatPlanActivity.this.isSeatSelected) {
                    GetSeatPlanActivity.this.txtTimer.setVisibility(0);
                    GetSeatPlanActivity.countDownTimerCustom = new CountDownTimerCustom(600000, 1000, GetSeatPlanActivity.this.mContext);
                    GetSeatPlanActivity.countDownTimerCustom.start();
                }
                GetSeatPlanActivity.this.isSeatSelected = true;
                GetSeatPlanActivity.this.clickCount = 0;
                GetSeatPlanActivity.this.mBookingToken = selectedSeatResponse.getData().getBookingId();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        List unavailableSeats = selectedSeatResponse.getData().getUnavailableSeats();
                        for (int i = 0; i < GetSeatPlanActivity.this.mSeatPlanButtonList.size(); i++) {
                            Seat seat = (Seat) ((Button) GetSeatPlanActivity.this.mSeatPlanButtonList.get(i)).getTag();
                            ((Button) GetSeatPlanActivity.this.mSeatPlanButtonList.get(i)).setBackground(ContextCompat.getDrawable(GetSeatPlanActivity.this, R.drawable.bg_seat_available));
                            ((Button) GetSeatPlanActivity.this.mSeatPlanButtonList.get(i)).setTextColor(ContextCompat.getColor(GetSeatPlanActivity.this, R.color.colorPrimary));
                            if (seat.isSelected && seat.bookable) {
                                ((Button) GetSeatPlanActivity.this.mSeatPlanButtonList.get(i)).setBackground(ContextCompat.getDrawable(GetSeatPlanActivity.this, R.drawable.bg_seat_selected));
                                ((Button) GetSeatPlanActivity.this.mSeatPlanButtonList.get(i)).setTextColor(ContextCompat.getColor(GetSeatPlanActivity.this, R.color.app_white));
                            }
                            for (int i2 = 0; i2 < unavailableSeats.size(); i2++) {
                                if (((Button) GetSeatPlanActivity.this.mSeatPlanButtonList.get(i)).getText().toString().toUpperCase().equals(((UnavailableSeat) unavailableSeats.get(i2)).getLabel().toUpperCase())) {
                                    Seat seat2 = (Seat) ((Button) GetSeatPlanActivity.this.mSeatPlanButtonList.get(i)).getTag();
                                    seat2.bookable = false;
                                    ((Button) GetSeatPlanActivity.this.mSeatPlanButtonList.get(i)).setTag(seat2);
                                    ((Button) GetSeatPlanActivity.this.mSeatPlanButtonList.get(i)).setBackground(ContextCompat.getDrawable(GetSeatPlanActivity.this, R.drawable.bg_seat_unavailable));
                                    ((Button) GetSeatPlanActivity.this.mSeatPlanButtonList.get(i)).setTextColor(ContextCompat.getColor(GetSeatPlanActivity.this, R.color.colorPrimary));
                                }
                            }
                        }
                        if (GetSeatPlanActivity.this.total_ticket_count == 0) {
                            GetSeatPlanActivity.countDownTimerCustom.stopTimer();
                            GetSeatPlanActivity.this.txtTimer.setVisibility(8);
                            GetSeatPlanActivity.this.isSeatSelected = false;
                        }
                    }
                }, 0);
                GetSeatPlanActivity.this.dismissProgress();
            }

            public void onFailedSelectedSeat(String str, int i) {
                GetSeatPlanActivity.this.dismissProgress();
                GetSeatPlanActivity.this.removeSeat(i, button, false);
                if (str == null) {
                    GetSeatPlanActivity.this.errorAlert(Constant.NO_INTERNET_MESSAGE);
                    return;
                }
                if (str != null) {
                    GetSeatPlanActivity.this.errorAlert(str);
                }
            }
        });*/
    }
   /* private void finalSeatResult(int i, Button button, GetSeatPlan.SeatPlan seat, boolean z, String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(adultTicketCount);
        stringBuilder.append("");
        float parseFloat = Float.parseFloat(stringBuilder.toString()) * Float.parseFloat(this.adultPrice.getPrice());
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(this.childTicketCount);
        stringBuilder2.append("");
        float parseFloat2 = Float.parseFloat(stringBuilder2.toString()) * Float.parseFloat(this.childPrice.getPrice());
        if (this.adultTicketCount > 0) {
            this.btn_child_add.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_corner_radius_dark_blue_enable));
            this.btn_child_add.setClickable(true);
            this.btn_adult_remove.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_corner_radius_dark_blue_enable));
            this.btn_adult_remove.setClickable(true);
        } else {
            this.btn_child_add.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_holo_blue_bright_disable));
            this.btn_child_add.setClickable(false);
            this.btn_adult_remove.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_holo_blue_bright_disable));
            this.btn_adult_remove.setClickable(false);
        }
        if (this.childTicketCount > 0) {
            this.btn_child_remove.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_corner_radius_dark_blue_enable));
            this.btn_child_remove.setClickable(true);
            this.btn_adult_add.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_corner_radius_dark_blue_enable));
            this.btn_adult_add.setClickable(true);
        } else {
            this.btn_child_remove.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_holo_blue_bright_disable));
            this.btn_child_remove.setClickable(false);
            this.btn_adult_add.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_holo_blue_bright_disable));
            this.btn_adult_add.setClickable(false);
        }
        if (this.childTicketCount == 0 && this.adultTicketCount == 0) {
            this.btn_continue.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_holo_blue_bright_disable));
        } else {
            this.btn_continue.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_corner_radius_dark_blue_enable));
        }
        this.totalTicketCount = this.childTicketCount + this.adultTicketCount;
        parseFloat += parseFloat2;
        Button button2 = this.btn_continue;
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("CONTINUE Rs ");
        stringBuilder3.append(parseFloat == 0.0f ? AppEventsConstants.EVENT_PARAM_VALUE_NO : Helper.priceFormat(parseFloat));
        stringBuilder3.append("/-");
        button2.setText(stringBuilder3.toString());
        TextView textView = this.txt_total_tickets;
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Total Ticket - ");
        stringBuilder2.append(this.totalTicketCount);
        textView.setText(stringBuilder2.toString());
        if (z) {
            this.clickCount += true;
            selectASeat(i, button, seat, str);
        }
    }*/



}

/*          seatFlow.removeAllViews();
            int i = 0;
            int i2 = 0;
            for (int i3 = 0; i3 < response.body().getData().getSeatPlan().size(); i3++) {
                progressBar.setVisibility(View.INVISIBLE);
                GetSeatPlan.SeatPlan seat = response.body().getData().getSeatPlan().get(i3);
                int parseInt = Integer.parseInt(seat.getX());
                int parseInt2 = Integer.parseInt(seat.getY());
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(dpToPx(24), dpToPx(24));
                layoutParams.leftMargin = dpToPx(parseInt);
                layoutParams.topMargin = dpToPx(parseInt2);
                Button button = (Button)GetSeatPlanActivity.this.mInflater.inflate(R.layout.seat_button, null);
                button.setText(seat.getLabel());
                seatFlow.addView(button, layoutParams);
                if (i2 < parseInt2) {
                    i2 = parseInt2;
                }
                if (i < parseInt) {
                    i = parseInt;
                }
            }
            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(dpToPx(24),dpToPx(24));
            layoutParams2.leftMargin = dpToPx(dpToPx(4) + i);
            layoutParams2.topMargin = dpToPx(dpToPx(4) + i2);
            seatFlow.addView(new View(GetSeatPlanActivity.this), layoutParams2);
            setSeatPlanHeight(i2, i);*/
