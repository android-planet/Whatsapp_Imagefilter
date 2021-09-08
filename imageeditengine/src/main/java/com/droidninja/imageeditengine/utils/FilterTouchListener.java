package com.droidninja.imageeditengine.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.droidninja.imageeditengine.views.PhotoEditorView;
import com.droidninja.imageeditengine.views.imagezoom.ImageViewTouch;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * @author Simon Lightfoot <simon@demondevelopers.com>
 */
public class FilterTouchListener implements View.OnTouchListener {
    private final ImageView mainImageView;
    private final int screenHeight;
    private final View filterLabel;
    private final PhotoEditorView photoEditorView;
    private final FloatingActionButton doneBtn;
    private final RecyclerView filterRecylerview;
    private float viewHeight;
    private View mView;
    private float mMotionDownY;
    boolean isHideFilter = false;


    public FilterTouchListener(View filterLayout, float filterLayoutHeight,
                               final ImageView mainImageView, final PhotoEditorView photoEditorView, View filterLabel,
                               FloatingActionButton doneBtn, RecyclerView filterRecylerview) {
        mView = filterLayout;
        this.filterLabel = filterLabel;
        this.doneBtn = doneBtn;
        this.filterRecylerview = filterRecylerview;

        this.mainImageView = mainImageView;
        this.photoEditorView = photoEditorView;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) mainImageView.getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        viewHeight = filterLayoutHeight;
    }


    @SuppressLint("RestrictedApi")
    @Override
    public boolean onTouch(View v, MotionEvent e) {
        int action = e.getAction();
        float yPost = 0f;
        switch (action & e.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mMotionDownY = e.getRawY() - mView.getTranslationY();
                break;
            case MotionEvent.ACTION_MOVE:
                yPost = e.getRawY() - mMotionDownY;
                Log.i(FilterTouchListener.class.getSimpleName(), String.valueOf(1f - Math.abs(yPost) / 1000)
                        + "--"
                        + yPost
                        + " - "
                        + viewHeight
                        + " - "
                        + mView.getY()
                        + "s - "
                        + screenHeight
                        + " d="
                        + (screenHeight - mView.getY()));
                if ((yPost >= 0 && yPost < viewHeight)) {
                    mView.setTranslationY(yPost);
                    filterLabel.setAlpha(Math.abs(yPost) / 1000);
                    doneBtn.setAlpha(Math.abs(yPost) / 1000);
                    //mainImageView.setScaleX(1f-Math.abs(yPost)/1000);
                    //mainImageView.setScaleY(1f-Math.abs(yPost)/1000);
                    Log.i(FilterTouchListener.class.getSimpleName(), "moved");
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.i(FilterTouchListener.class.getSimpleName(), "ACTION_CANCEL");
                break;
            case MotionEvent.ACTION_UP:

                yPost = e.getRawY() - mMotionDownY;
                Log.i(FilterTouchListener.class.getSimpleName(), "ACTION_UP" + yPost);


                float middle = viewHeight / 2;
                float diff = (screenHeight - mView.getY());

                Log.d("_______hide:", "b_filter:" + middle);
                Log.d("_______hide:", "c_filter:" + diff);
/*
* b_filter:187.5
c_filter:373.61853
*
* :187.5
:109.27063
* */
//if(373<187)
//if(109<187)


                if (diff < middle) {
                    Log.d("_______hide:", "b_filter:if");
                    mView.animate().translationY(viewHeight);
                    mainImageView.animate().scaleX(1f);
                    mainImageView.animate().scaleY(1f);
                    photoEditorView.animate().scaleX(1f);
                    photoEditorView.animate().scaleY(1f);
                    filterLabel.animate().alpha(1f);
                    doneBtn.animate().alpha(1f);
                    doneBtn.setVisibility(View.VISIBLE);
                } else if (isHideFilter == true) {
                    Log.d("_______hide:", "b_filter:true");
                    mView.animate().translationY(viewHeight);
                    mainImageView.animate().scaleX(1f);
                    mainImageView.animate().scaleY(1f);
                    photoEditorView.animate().scaleX(1f);
                    photoEditorView.animate().scaleY(1f);
                    filterLabel.animate().alpha(1f);
                    doneBtn.animate().alpha(1f);
                    doneBtn.setVisibility(View.VISIBLE);
                    isHideFilter= false;
                } else {
                    isHideFilter = true;
                    Log.d("_______hide:", "b_filter:else");
                    mView.animate().translationY(0);
                    mainImageView.animate().scaleX(0.7f);
                    mainImageView.animate().scaleY(0.7f);
                    photoEditorView.animate().scaleX(0.7f);
                    photoEditorView.animate().scaleY(0.7f);
                    filterLabel.animate().alpha(0f);
                    doneBtn.animate().alpha(0f);
                    doneBtn.setVisibility(View.GONE);
                }


                break;
        }
        return true;
    }
}