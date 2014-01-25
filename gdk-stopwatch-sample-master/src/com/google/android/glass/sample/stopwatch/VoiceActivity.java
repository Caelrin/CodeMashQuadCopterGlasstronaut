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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.codeminders.ardrone.ARDrone;

import java.util.List;

/**
 * Activity showing the options menu.
 */
public class VoiceActivity extends Activity {

    private static final int SPEECH_REQUEST = 0;
    private static final long CONNECT_TIMEOUT = 3000;
    public TextView droneView;

    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Exit, Take Off, Land, Do A Barrel Roll");
        startActivityForResult(intent, SPEECH_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SPEECH_REQUEST && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            // Do something with spokenText.
            if("exit".equalsIgnoreCase(spokenText)){
                stopService(new Intent(this, StopwatchService.class));
            } else {
                if("Take Off".equalsIgnoreCase(spokenText) || "Ignition".equalsIgnoreCase(spokenText) || "Blast Off".equalsIgnoreCase(spokenText)) {
                    setDisplayText("Taking Off");
                    new ControlARDevice().execute("takeoff");
                } else if(spokenText.startsWith("land") || spokenText.startsWith("Land") || spokenText.equalsIgnoreCase("Sit Down") || spokenText.equalsIgnoreCase("Sit Boy")) {
                    setDisplayText("Landing");
                    new ControlARDevice().execute("land");
                } else if(spokenText.equalsIgnoreCase("do a barrel roll") || spokenText.startsWith("do a barrel")) {
                    setDisplayText("Landing");
                    new ControlARDevice().execute("flipleft");
                } else if(spokenText.equalsIgnoreCase("wave")) {
                    setDisplayText("Wave");
                    new ControlARDevice().execute("wave");
                } else if(spokenText.equalsIgnoreCase("dance")) {
                    setDisplayText("Dance");
                    new ControlARDevice().execute("dance");
                } else if(spokenText.equalsIgnoreCase("Go Up")) {
                    setDisplayText("Go Up");
                    new ControlARDevice().execute("up");
                } else if(spokenText.equalsIgnoreCase("Go Forward")) {
                    setDisplayText("Go Forward");
                    new ControlARDevice().execute("front");
                } else if(spokenText.equalsIgnoreCase("Go Back")) {
                    setDisplayText("Go Back");
                    new ControlARDevice().execute("back");
                } else if(spokenText.equalsIgnoreCase("Go Down")) {
                    setDisplayText("Go Down");
                    new ControlARDevice().execute("down");
                }else {
                    setDisplayText("Did not understand command");
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

        finish();
    }
    public void setDisplayText(String s) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        displaySpeechRecognizer();
        Log.e("Menu", "111111111111111 Started VOICE!");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
