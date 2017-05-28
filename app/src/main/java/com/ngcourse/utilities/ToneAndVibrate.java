package com.ngcourse.utilities;

import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Vibrator;

/**
 * Created by piyush on 11/8/16.
 */
public class ToneAndVibrate {

    public static void errorBeepAndVibrate(Context mContext){
        ToneGenerator toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        toneGen.startTone(ToneGenerator.TONE_SUP_ERROR,500);
        Vibrator v = (Vibrator) mContext.getSystemService(mContext.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(500);
    }

    public static void successBeep(){
        ToneGenerator toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        toneGen.startTone(ToneGenerator.TONE_PROP_BEEP2,200);

    }

    public static void alertBeep(){
        ToneGenerator toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        toneGen.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD,500);
    }

    public static void errorVibrate(Context mContext){
        Vibrator v = (Vibrator) mContext.getSystemService(mContext.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(500);
    }
}
