package com.ngcourse.utilities;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by piyush on 24/8/16.
 */
public class AppToast {

    private static Toast toast;

    public static void showShortToast (Context mContext, String st){ //"Toast toast" is declared in the class
        try{ toast.getView().isShown();     // true if visible
            toast.setText(st);
        } catch (Exception e) {         // invisible if exception
            toast = Toast.makeText(mContext, st, Toast.LENGTH_SHORT);
        }
        toast.show();  //finally display it
    }

    public static void showLongToast (Context mContext,String st){ //"Toast toast" is declared in the class
        try{ toast.getView().isShown();     // true if visible
            toast.setText(st);
        } catch (Exception e) {         // invisible if exception
            toast = Toast.makeText(mContext, st, Toast.LENGTH_LONG);
        }
        toast.show();  //finally display it
    }


}
