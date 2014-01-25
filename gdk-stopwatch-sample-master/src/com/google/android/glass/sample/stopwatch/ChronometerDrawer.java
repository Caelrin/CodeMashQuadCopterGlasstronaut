/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.glass.sample.stopwatch;

import android.content.Context;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;

import java.util.concurrent.TimeUnit;

/**
 * SurfaceHolder.Callback used to draw the chronometer on the timeline LiveCard.
 */
public class ChronometerDrawer implements SurfaceHolder.Callback {
    private static final String TAG = "ChronometerDrawer";

    private ARDroneView droneView;
    private SurfaceHolder mHolder;


    public ChronometerDrawer(Context context) {
        droneView = new ARDroneView(context);
        droneView.setListener(new ARDroneView.ARDroneListener() {

            @Override
            public void onTick(long millisUntilFinish) {
                draw(droneView);
            }

            @Override
            public void onFinish() {
            }
        });
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);

        droneView.measure(measuredWidth, measuredHeight);
        droneView.layout(
                0, 0, droneView.getMeasuredWidth(), droneView.getMeasuredHeight());
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.e(TAG, "Surface created");
        mHolder = holder;
        droneView.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.e(TAG, "Surface destroyed");
    }

    private void draw(View view) {
        Canvas canvas;
        try {
            canvas = mHolder.lockCanvas();
        } catch (Exception e) {
            return;
        }
        if (canvas != null) {
            view.draw(canvas);
            mHolder.unlockCanvasAndPost(canvas);
        }
    }

    public ARDroneView getDroneView() {
        return droneView;
    }
}
