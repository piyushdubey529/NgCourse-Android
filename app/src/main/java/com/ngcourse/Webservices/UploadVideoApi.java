package com.ngcourse.Webservices;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import com.ngcourse.NetworkCall.NetworkCallResponse;
import com.ngcourse.NetworkCall.NetworkService;
import com.ngcourse.R;
import com.ngcourse.Settings.Config;
import com.ngcourse.retrofitAdapter.ConvertInputStream;
import com.ngcourse.retrofitAdapter.RetrofitAdapter;
import com.ngcourse.utilities.AppToast;
import com.ngcourse.utilities.InternetConnection;
import com.ngcourse.utilities.Logger;
import com.ngcourse.utilities.ReferenceWrapper;
import com.ngcourse.utilities.ToneAndVibrate;
import java.io.File;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by piyush on 17/6/17.
 */

public class UploadVideoApi {
    public static final String API_TAG = UploadVideoApi.class.getSimpleName();
    private FragmentActivity mContext;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public NetworkCallResponse delegateNetworkCall = null;//Call back interface
    private String folderName = "videoFolder";
    private String fileName = "videoFile.mp4";

    public UploadVideoApi(FragmentActivity mContext){
        this.mContext = mContext;
        sharedPreferences = ReferenceWrapper.getReferenceProvider(mContext).getSharedPreferences();
    }

    public void uploadVideoApi(NetworkCallResponse networkCallResponse){
        this.delegateNetworkCall = networkCallResponse;//Assigning call back interface
        if (!InternetConnection.isInternetConnected(mContext)) {
            AppToast.showShortToast(mContext, mContext.getResources().getString(R.string.no_internet));
            ToneAndVibrate.errorVibrate(mContext);
            delegateNetworkCall.callResponse(false,API_TAG);
            return;
        }
        NetworkService service = RetrofitAdapter.createService(NetworkService.class, Config.BASE_URL);
        File videoFile = getVideoFile();
        TypedFile typedFile = new TypedFile("multipart/form-data", videoFile);
        String description = "hello, this is description speaking";


        service.uploadVideo(typedFile, new Callback<Response>() {
            @Override
            public void success(Response body, Response obj) {
                String result = ConvertInputStream.getFormattedResponse(body);
                Logger.LogError(API_TAG + "response", "server response" + " " + result);
                parseJsonResult(result);
            }

            @Override
            public void failure(RetrofitError error) {
                Logger.LogError(API_TAG + "response", "errorBeepAndVibrate" + "-" + error + "@@" + error.getKind() + "&&" + error.getUrl() + " " + error.getMessage());
                RetrofitAdapter adapter = new RetrofitAdapter(mContext);
                adapter.retrofitErrorHandler(error);
                delegateNetworkCall.callResponse(false,API_TAG);
            }
        });
    }

    private void parseJsonResult(String result) {

    }


    public File getVideoFile() {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        File folder;
        if (currentapiVersion >= Build.VERSION_CODES.M){
            // Do something for lollipop and above versions
            folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),folderName);
        } else{
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            folder = new File(extStorageDirectory,folderName );
        }
        File videoFile = new File(folder, fileName);
        return videoFile;
    }
}
