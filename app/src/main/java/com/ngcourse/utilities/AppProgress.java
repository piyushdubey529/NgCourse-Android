package com.ngcourse.utilities;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

/**
 * Created by piyushchaudhary on 27/12/16.
 */

public class AppProgress {

    public static void showProgress(FragmentActivity mContext, ProgressBar progressBar){
        progressBar.setVisibility(View.VISIBLE);
        mContext.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public static void hideProgress(FragmentActivity mContext, ProgressBar progressBar){
        mContext.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressBar.setVisibility(View.GONE);
    }

}
