package com.example.mathchampionsleague;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;


import java.lang.ref.WeakReference;

public class AsyncClass implements Runnable{
    private WeakReference<TextView>  textView;
    private Language language;


    public AsyncClass(TextView textView , Language l){
        this.textView = new WeakReference<>(textView);
        this.language = l;
    }

    @Override
    public void run() {
        //getStringFromServer();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String x = language.getFirstMessage();
        textView.get().post(new Runnable() {
            @Override
            public void run() {
                textView.get().setText(x);
            }
        });
    }


}
