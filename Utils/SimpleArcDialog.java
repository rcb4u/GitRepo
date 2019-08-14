package com.example.rspl_rahul.gitrepo.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rspl_rahul.gitrepo.R;

public class SimpleArcDialog extends Dialog {
    private ArcConfiguration mConfiguration;
    private LinearLayout mLayout;
    private SimpleArcLoader mLoaderView;
    private TextView mLoadingText;
    private boolean showWindow = true;

    public SimpleArcDialog(Context context) {
        super(context);
    }

    public SimpleArcDialog(Context context, ArcConfiguration configuration) {
        super(context);
        this.mConfiguration = configuration;
    }

    public SimpleArcDialog(Context context, int themeResId, ArcConfiguration configuration) {
        super(context, themeResId);
        this.mConfiguration = configuration;
    }

    public SimpleArcDialog(Context context, boolean cancelable, OnCancelListener cancelListener,
                           ArcConfiguration configuration) {
        super(context, cancelable, cancelListener);
        this.mConfiguration = configuration;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        setContentView(R.layout.loader_layout);
        this.mLoaderView = (SimpleArcLoader) findViewById(R.id.loader);
        this.mLoadingText = (TextView) findViewById(R.id.loadertext);
        this.mLayout = (LinearLayout) findViewById(R.id.window);
        this.mLoadingText.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        if (this.mConfiguration != null) {
            this.mLoaderView.refreshArcLoaderDrawable(this.mConfiguration);
            updateLoadingText(this.mConfiguration);
        }
        if (this.showWindow) {
            GradientDrawable gd = new GradientDrawable();
            gd.setColor(-1);
            gd.setCornerRadius(10.0f);
            gd.setStroke(2, -1);
            this.mLayout.setBackgroundDrawable(gd);
            if (this.mConfiguration != null && this.mConfiguration.getTextColor() == -1) {
                this.mLoadingText.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            }
        }
    }

    private void updateLoadingText(ArcConfiguration configuration) {
        if (configuration.getText().trim().length() == 0) {
            this.mLoadingText.setVisibility(8);
        } else {
            this.mLoadingText.setText(configuration.getText());
        }
        Typeface typeface = configuration.getTypeFace();
        if (typeface != null) {
            this.mLoadingText.setTypeface(typeface);
        }
        int textSize = configuration.getTextSize();
        if (textSize > 0) {
            this.mLoadingText.setTextSize((float) textSize);
        }
        this.mLoadingText.setTextColor(this.mConfiguration.getTextColor());
    }

    public LinearLayout getLayout() {
        return this.mLayout;
    }

    public TextView getLoadingTextView() {
        return this.mLoadingText;
    }

    public void setConfiguration(ArcConfiguration configuration) {
        this.mConfiguration = configuration;
    }

    public void showWindow(boolean state) {
        this.showWindow = true;
    }
}