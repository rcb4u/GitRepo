package com.example.rspl_rahul.gitrepo.Utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

import com.example.rspl_rahul.gitrepo.R;
import com.example.rspl_rahul.gitrepo.Utils.SimpleArcLoader.STYLE;

public class ArcConfiguration {
    private int mAnimationSpeed;
    private boolean mArcCircle;
    private int mArcMargin;
    private int[] mColors;
    private STYLE mLoaderStyle;
    private int mStrokeWidth;
    private String mText;
    private int mTextColor;
    private int mTextSize;
    private Typeface mTypeFace;

    private ArcConfiguration() {
        this.mTypeFace = null;
        this.mText = "Loading..";
        this.mTextSize = 0;
        this.mTextColor = -1;
        this.mColors = new int[]{Color.parseColor("#F90101"),
                Color.parseColor("#0266C8"),
                Color.parseColor("#F2B50F"), Color.parseColor("#00933B")};
    }

    public ArcConfiguration(Context context) {
        this.mTypeFace = null;
        this.mText = "Loading..";
        this.mTextSize = 0;
        this.mTextColor = -1;
        this.mColors = new int[]{Color.parseColor("#F90101"),
                Color.parseColor("#0266C8"),
                Color.parseColor("#F2B50F"),
                Color.parseColor("#00933B")
        };
        this.mLoaderStyle = STYLE.SIMPLE_ARC;
        this.mArcMargin = SimpleArcLoader.MARGIN_MEDIUM;
        this.mAnimationSpeed = SimpleArcLoader.SPEED_MEDIUM;
        this.mStrokeWidth = (int) context.getResources().getDimension(R.dimen.stroke_width);
    }

    public ArcConfiguration(Context context, STYLE mArcStyle) {
        this(context);
        this.mLoaderStyle = mArcStyle;
    }

    public STYLE getLoaderStyle() {
        return this.mLoaderStyle;
    }

    public void setLoaderStyle(STYLE mLoaderStyle) {
        this.mLoaderStyle = mLoaderStyle;
    }

    public int getArcMargin() {
        return this.mArcMargin;
    }

    public void setArcMargin(int mArcMargin) {
        this.mArcMargin = mArcMargin;
    }

    public int getAnimationSpeed() {
        return this.mAnimationSpeed;
    }

    public void setAnimationSpeed(int mAnimationSpeed) {
        this.mAnimationSpeed = mAnimationSpeed;
    }

    public void setAnimationSpeedWithIndex(int mAnimationIndex) {
        switch (mAnimationIndex) {
            case 0:
                this.mAnimationSpeed = SimpleArcLoader.SPEED_SLOW;
                return;
            case 1:
                this.mAnimationSpeed = SimpleArcLoader.SPEED_MEDIUM;
                return;
            case 2:
                this.mAnimationSpeed = SimpleArcLoader.SPEED_FAST;
                return;
            default:
                return;
        }
    }

    public int getArcWidth() {
        return this.mStrokeWidth;
    }

    public void setArcWidthInPixel(int mStrokeWidth) {
        this.mStrokeWidth = mStrokeWidth;
    }

    public void setColors(int[] colors) {
        if (colors.length > 0) {
            this.mColors = colors;
        }
    }

    public int[] getColors() {
        return this.mColors;
    }

    public void setTypeFace(Typeface typeFace) {
        this.mTypeFace = typeFace;
    }

    public Typeface getTypeFace() {
        return this.mTypeFace;
    }

    public void setText(String mText) {
        this.mText = mText;
    }

    public String getText() {
        return this.mText;
    }

    public void setTextSize(int size) {
        this.mTextSize = size;
    }

    public int getTextSize() {
        return this.mTextSize;
    }

    public int getTextColor() {
        return this.mTextColor;
    }

    public void setTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
    }

    public boolean drawCircle() {
        return this.mArcCircle;
    }

    public void drawCircle(boolean drawCircle) {
        this.mArcCircle = drawCircle;
    }
}
