package com.ngcourse.Webservices;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import com.ngcourse.NetworkCall.NetworkCallResponse;
import com.ngcourse.NetworkCall.NetworkService;
import com.ngcourse.R;
import com.ngcourse.ResponseInterfaces.ResponseCourseList;
import com.ngcourse.Settings.Config;
import com.ngcourse.beans.Video;
import com.ngcourse.retrofitAdapter.ConvertInputStream;
import com.ngcourse.retrofitAdapter.RetrofitAdapter;
import com.ngcourse.utilities.AppToast;
import com.ngcourse.utilities.InternetConnection;
import com.ngcourse.utilities.Logger;
import com.ngcourse.utilities.ReferenceWrapper;
import com.ngcourse.utilities.ToneAndVibrate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by piyush on 9/6/17.
 */

public class CourseListApi {
    public static final String API_TAG = CourseListApi.class.getSimpleName();
    private FragmentActivity mContext;
    //private final CustomProgressDialog customProgressDialog;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public NetworkCallResponse delegateNetworkCall = null;//Call back interface
    public ResponseCourseList responseCourseList = null;

    public CourseListApi(FragmentActivity mContext) {
        this.mContext = mContext;
        sharedPreferences = ReferenceWrapper.getReferenceProvider(mContext).getSharedPreferences();
    }

    public void getVideoListApi(NetworkCallResponse networkCallResponse, ResponseCourseList responseCourseList){
        this.responseCourseList = responseCourseList;
        this.delegateNetworkCall = networkCallResponse;//Assigning call back interface
        if (!InternetConnection.isInternetConnected(mContext)) {
            AppToast.showShortToast(mContext, mContext.getResources().getString(R.string.no_internet));
            ToneAndVibrate.errorVibrate(mContext);
            delegateNetworkCall.callResponse(false,API_TAG);
            return;
        }
        NetworkService service = RetrofitAdapter.createService(NetworkService.class, Config.BASE_URL);
        service.getCourseList(new Callback<Response>() {

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

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void parseJsonResult(String result) {
      JSONObject jsonObjectResponse = null;
        try {
            jsonObjectResponse = new JSONObject(result);
            JSONArray jsonArray = jsonObjectResponse.getJSONArray("data");
            ArrayList<String> courseList = new ArrayList<>();
            for (int i=0; i<jsonArray.length(); i++){
             courseList.add(jsonArray.getJSONObject(i).getString("courseName"));
            }
            responseCourseList.responseCourses(courseList);
            delegateNetworkCall.callResponse(true, API_TAG);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
