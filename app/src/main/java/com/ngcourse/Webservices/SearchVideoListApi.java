package com.ngcourse.Webservices;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;

import com.ngcourse.NetworkCall.NetworkCallResponse;
import com.ngcourse.NetworkCall.NetworkService;
import com.ngcourse.R;
import com.ngcourse.ResponseInterfaces.ResponseVideoList;
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
 * Created by piyush on 4/6/17.
 */

public class SearchVideoListApi  {
    public static final String API_TAG = SearchVideoListApi.class.getSimpleName();
    private FragmentActivity mContext;
    //private final CustomProgressDialog customProgressDialog;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public NetworkCallResponse delegateNetworkCall = null;//Call back interface
    public ResponseVideoList responseVideoList = null;
    private String keyword;
    private String limit;
    private String skip;

    public SearchVideoListApi(FragmentActivity mContext, String keyword, String limit, String skip){
        this.mContext = mContext;
        sharedPreferences = ReferenceWrapper.getReferenceProvider(mContext).getSharedPreferences();
        this.keyword = keyword;
        this.limit = limit;
        this.skip = skip;
    }

    public void getSearchVideoListApi(NetworkCallResponse networkCallResponse, ResponseVideoList responseVideoList){
        this.delegateNetworkCall = networkCallResponse;//Assigning call back interface
        this.responseVideoList =responseVideoList;
        if (!InternetConnection.isInternetConnected(mContext)) {
            AppToast.showShortToast(mContext, mContext.getResources().getString(R.string.no_internet));
            ToneAndVibrate.errorVibrate(mContext);
            delegateNetworkCall.callResponse(false,API_TAG);
            return;
        }
        NetworkService service = RetrofitAdapter.createService(NetworkService.class, Config.BASE_URL);
        service.getSearchVideoList(keyword, limit, skip, new Callback<Response>() {
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
        JSONArray jsonResponseArray = null;
        try {
            JSONObject jsonObjectResponse = new JSONObject(result);
            jsonResponseArray = jsonObjectResponse.getJSONArray("object");
            ArrayList<Video> videoList = new ArrayList<>();
            for(int i=0; i<jsonResponseArray.length(); i++){
                JSONObject jsonObject = jsonResponseArray.getJSONObject(i);
                Video video = new Video();
                video.setId(jsonObject.getString("_id"));
                video.setTopic(jsonObject.getString("topic"));
                video.setVideoName(jsonObject.getString("videoName"));
                video.setVideoUrl(jsonObject.getString("videoUrl"));
                video.setYoutubeVideoId(jsonObject.getString("youtubeVideoId"));
                video.setTechnology(jsonObject.getString("technology"));
                videoList.add(video);
            }
            delegateNetworkCall.callResponse(true, API_TAG);
            responseVideoList.responseVideos(videoList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
