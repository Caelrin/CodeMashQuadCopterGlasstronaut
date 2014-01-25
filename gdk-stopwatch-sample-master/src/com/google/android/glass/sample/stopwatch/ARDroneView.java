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
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * Animated countdown going from {@code mTimeSeconds} to 0.
 *
 * The current animation for each second is as follow:
 *   1. From 0 to 500ms, move the TextView from {@code MAX_TRANSLATION_Y} to 0 and its alpha from
 *      {@code 0} to {@code ALPHA_DELIMITER}.
 *   2. From 500ms to 1000ms, update the TextView's alpha from {@code ALPHA_DELIMITER} to {@code 1}.
 * At each second change, update the TextView text.
 */
public class ARDroneView extends FrameLayout implements Serializable{
    private static final String TAG = "ARDroneView";
    public static final String BASE_TEXT = "Tap to speak: \n - Blast Off\n - Sit Down\n - Do a barrel roll\n" +
            " - Wave\n - Dance\n - Exit";
    private String text = BASE_TEXT;
    private ARDroneListener mListener;

    public void setDisplayText(String s) {
        text = s + "\n" + BASE_TEXT;
    }

    public interface ARDroneListener {
        /**
         * Notified of a tick, indicating a layout change.
         */
        public void onTick(long millisUntilFinish);

        /**
         * Notified when the countdown is finished.
         */
        public void onFinish();
    }

    /** Time delimiter specifying when the second component is fully shown. */
    public static final float ANIMATION_DURATION_IN_MILLIS = 850.0f;

    // About 24 FPS.
    private static final long DELAY_MILLIS = 41;

    private final TextView mSecondsView;

    private boolean mStarted;

    public ARDroneView(Context context) {
        this(context, null, 0);
    }

    public ARDroneView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ARDroneView(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
        LayoutInflater.from(context).inflate(R.layout.card_drone, this);

        mSecondsView =  (TextView) findViewById(R.id.seconds_view);

        mHandler.postDelayed(mUpdateViewRunnable, DELAY_MILLIS);
    }

    private final Handler mHandler = new Handler();

    public void setListener(ARDroneListener listener) {
        mListener = listener;
    }


    private final Runnable mUpdateViewRunnable = new Runnable() {
        @Override
        public void run() {
            mSecondsView.setText(text);
            mListener.onTick(500);
            mHandler.postDelayed(mUpdateViewRunnable, DELAY_MILLIS);
        }
    };

    public void start() {
        if (!mStarted) {
            mStarted = true;
            mHandler.postDelayed(mUpdateViewRunnable, DELAY_MILLIS);
        }
    }
}
