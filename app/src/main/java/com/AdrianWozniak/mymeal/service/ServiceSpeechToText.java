package com.AdrianWozniak.mymeal.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.widget.Toast;

import java.util.Locale;

/**
 * Class that's is responsible for Speach to Text
 */
public final class ServiceSpeechToText {

    public static void startSpeechToTextActivityForResult(Context context, Activity activity) throws NullPointerException{
        if(context != null && activity != null){
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            if(intent.resolveActivity(context.getPackageManager()) != null){
                activity.startActivityForResult(intent, 10);
            }else{
                Toast.makeText(activity, "Your device don's support Speech Input", Toast.LENGTH_SHORT).show();
            }
        }else{
            throw new NullPointerException("");
        }

    }
}
