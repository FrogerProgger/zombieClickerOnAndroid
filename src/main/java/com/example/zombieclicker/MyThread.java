package com.example.zombieclicker;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Handler;
import android.widget.TextView;

public class MyThread implements Runnable {
    private Activity mActivity;
    private Handler mHandler;
    private SharedPreferences preferences;
    private boolean isRunning;

    public MyThread(Activity activity, SharedPreferences preferences, boolean isRunning) {
        mActivity = activity;
        mHandler = new Handler();
        this.preferences = preferences;
        this.isRunning = isRunning;
    }

    @Override
    public void run() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {

                TextView textViewZombie = mActivity.findViewById(R.id.countZombieText);
                TextView textViewResearch = mActivity.findViewById(R.id.countResearchText);

                int incomeResearch = preferences.getInt("researchIncome", 0);
                int incomeZombie = preferences.getInt("zombieIncome", 0);

                int countOfZombie = preferences.getInt("countOfZombie", 0);
                int countOfResearch = preferences.getInt("countOfResearch", 0);
                int newCountOfResearch = countOfResearch + incomeResearch;


                Save("countOfZombie", countOfZombie + incomeZombie);
                Save("countOfResearch", newCountOfResearch);


                if (isRunning) {
                    textViewZombie.setText(Integer.toString(countOfZombie));
                    textViewResearch.setText(Integer.toString(newCountOfResearch - incomeResearch));
                }
                mHandler.postDelayed(this, 1000);

            }
        });
    }

    public void stopTask() {
        isRunning = false;
    }

    public void startTask() {
        isRunning = true;
    }

    private void Save(String category, int integer) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(category, integer);

        editor.apply();
    }
}