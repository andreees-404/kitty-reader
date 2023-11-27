package com.cutedomain.kittyreader.domain.utils;

import android.content.Context;
import android.graphics.Matrix;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.webkit.WebView;

import androidx.annotation.NonNull;

import com.cutedomain.kittyreader.views.EpubActivity;

import java.util.function.LongToDoubleFunction;

public class MyOnTouchListener implements View.OnTouchListener{

    private static final float MIN_SWIPE_DISTANCE = 500;
    private float startX;
    private ScaleGestureDetector scaleGestureDetector;
    private EpubActivity activity;
    private WebView mWebView;
    private String TAG = "MyOnTouchListener";

    public MyOnTouchListener(Context context, EpubActivity activity, WebView pageContent) {
        this.scaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureListener());
        this.activity = activity;
        this.mWebView = pageContent;

    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                float deltaX = event.getX() - startX;

                if (Math.abs(deltaX) > MIN_SWIPE_DISTANCE && !isZooming()){
                    if(deltaX < 0){
                        Log.d(TAG, "onTouch: Mostrar página derecha");
                        // Derecha
                        activity.showNextPage();
                    } else {
                        Log.d(TAG, "onTouch: Mostrar página izquierda");
                        activity.showBackPage();
                    }

                    // El evento fue iniciado
                    return true;
                }
                break;
        }

        return false; // Evento no iniciado
    }

    public boolean isZooming(){
        int scrollX = mWebView.getScrollX();
        int scrollY = mWebView.getScrollY();
        Log.d(TAG, "isZooming: scrollX = " + scrollX + ", scrollY = " + scrollY);
        return (scrollX > 0 || scrollY > 0); 


    }
    private static class ScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(@NonNull ScaleGestureDetector detector) {
            return super.onScale(detector);
        }

    }
}