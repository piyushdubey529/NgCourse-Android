package com.ngcourse.NetworkCall;

import com.ngcourse.Settings.Config;

import retrofit.http.Headers;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;

/**
 * Created by piyush on 28/5/17.
 */

public interface NetworkService {
    @Headers({
            "Content-type: application/json",
    })
    @GET(Config.GET_VIDEO_LIST_URL)
    void getVideoList(Callback<Response> cb);

}
