package com.example.rspl_rahul.gitrepo.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import java.lang.ref.WeakReference;

public class SimpleArcLoader extends View implements Animatable {
    private static long FRAME_DURATION = 16;
    public static int MARGIN_LARGE = 10;
    public static int MARGIN_MEDIUM = 5;
    public static int MARGIN_NONE = 0;
    public static int SPEED_FAST = 10;
    public static int SPEED_MEDIUM = 5;
    public static int SPEED_SLOW = 1;
    public static int STROKE_WIDTH_DEFAULT = 10;
    ArcDrawable mArcDrawable;

    private static class ArcDrawable extends Drawable implements Animatable {
        boolean isRunning;
        int mAnimationSpeed;
        int mArcAnglePosition;
        int[] mArcColors;
        int mArcMargin;
        ArcConfiguration mConfiguration;
        boolean mDrawCirle;
        Paint mPaint;
        int mStrokeWidth;
        WeakReference<View> mViewReference;
        final Runnable updater = new C07831();

        class C07831 implements Runnable {
            C07831() {
            }

            public void run() {
                ArcDrawable arcDrawable = ArcDrawable.this;
                arcDrawable.mArcAnglePosition += ArcDrawable.this.mAnimationSpeed;
                if (ArcDrawable.this.mArcAnglePosition > 360) {
                    ArcDrawable.this.mArcAnglePosition = 0;
                }
                ArcDrawable.this.scheduleSelf(ArcDrawable.this.updater, SimpleArcLoader.FRAME_DURATION + SystemClock.uptimeMillis());
                ArcDrawable.this.invalidateSelf();
            }
        }

        public ArcDrawable(ArcConfiguration configuration, View viewReference) {
            this.mConfiguration = configuration;
            this.mViewReference = new WeakReference(viewReference);
            initComponents();
        }

        private void initComponents() {
            this.mStrokeWidth = this.mConfiguration.getArcWidth();
            this.mArcMargin = this.mConfiguration.getArcMargin();
            this.mArcColors = this.mConfiguration.getColors();
            this.mAnimationSpeed = this.mConfiguration.getAnimationSpeed();
            this.mDrawCirle = this.mConfiguration.drawCircle();
            this.mPaint = new Paint();
            this.mPaint.setAntiAlias(true);
            this.mPaint.setStrokeWidth((float) this.mStrokeWidth);
            this.mPaint.setStyle(Style.STROKE);
            if (this.mConfiguration.getLoaderStyle() == STYLE.SIMPLE_ARC && this.mArcColors.length > 1) {
                this.mArcColors = new int[]{this.mArcColors[0], this.mArcColors[0]};
            }
        }

        public void start() {
            if (!isRunning()) {
                this.isRunning = true;
                scheduleSelf(this.updater, SimpleArcLoader.FRAME_DURATION + SystemClock.uptimeMillis());
                invalidateSelf();
            }
        }

        public void stop() {
            if (isRunning()) {
                this.isRunning = false;
                unscheduleSelf(this.updater);
                invalidateSelf();
            }
        }

        public boolean isRunning() {
            return this.isRunning;
        }

        public void draw(Canvas canvas) {
            View currentView = (View) this.mViewReference.get();
            if (currentView != null) {
                int w = currentView.getWidth();
                int h = currentView.getHeight();
                int arc1_bound_start = this.mArcMargin + (this.mStrokeWidth * 2);
                int arc_padding = 0;
                if (this.mDrawCirle) {
                    this.mPaint.setStyle(Style.FILL);
                    this.mPaint.setColor(-1);
                    canvas.drawCircle((float) (w / 2), (float) (h / 2), (float) (w / 2), this.mPaint);
                    this.mPaint.setStyle(Style.STROKE);
                    arc_padding = 0 + 3;
                }
                RectF arc1_bound = new RectF((float) (arc1_bound_start + arc_padding), (float) (arc1_bound_start + arc_padding), (float) (((w - (this.mStrokeWidth * 2)) - this.mArcMargin) - arc_padding), (float) (((h - (this.mStrokeWidth * 2)) - this.mArcMargin) - arc_padding));
                RectF arc2_bound = new RectF((float) (this.mStrokeWidth + arc_padding), (float) (this.mStrokeWidth + arc_padding), (float) ((w - this.mStrokeWidth) - arc_padding), (float) ((h - this.mStrokeWidth) - arc_padding));
                int colors_length = this.mArcColors.length;
                int i = 0;
                while (true) {
                    if (i < (colors_length > 4 ? 4 : colors_length)) {
                        int startangle = i * 90;
                        this.mPaint.setColor(this.mArcColors[i]);
                        canvas.drawArc(arc1_bound, (float) (this.mArcAnglePosition + startangle), 90.0f, false, this.mPaint);
                        canvas.drawArc(arc2_bound, (float) (startangle - this.mArcAnglePosition), 90.0f, false, this.mPaint);
                        i++;
                    } else {
                        return;
                    }
                }
            }
        }

        public void setAlpha(int i) {
        }

        public void setColorFilter(ColorFilter colorFilter) {
        }

        @SuppressLint("WrongConstant")
        public int getOpacity() {
            return 0;
        }
    }

    public enum STYLE {
        SIMPLE_ARC,
        COMPLETE_ARC
    }

    public SimpleArcLoader(Context context) {
        super(context);
    }

    public SimpleArcLoader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SimpleArcLoader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SimpleArcLoader(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attributeSet) {
        this.mArcDrawable = new ArcDrawable(readFromAttributes(attributeSet), this);
        setBackgroundDrawable(this.mArcDrawable);
        start();
    }

    private ArcConfiguration readFromAttributes(AttributeSet attributeSet) {
        ArcConfiguration configuration = new ArcConfiguration(getContext());
        return configuration;
    }

    public void start() {
        if (this.mArcDrawable != null) {
            this.mArcDrawable.start();
        }
    }

    public void stop() {
        if (this.mArcDrawable != null) {
            this.mArcDrawable.stop();
        }
    }

    public boolean isRunning() {
        if (this.mArcDrawable != null) {
            return this.mArcDrawable.isRunning();
        }
        return false;
    }

    public void refreshArcLoaderDrawable(ArcConfiguration configuration) {
        if (isRunning()) {
            stop();
        }
        this.mArcDrawable = new ArcDrawable(configuration, this);
        setBackgroundDrawable(this.mArcDrawable);
        start();
    }
}
