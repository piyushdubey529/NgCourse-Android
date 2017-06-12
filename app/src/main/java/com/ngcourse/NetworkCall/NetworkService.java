package com.ngcourse.NetworkCall;

import com.ngcourse.Settings.Config;
import retrofit.http.Headers;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by piyush on 28/5/17.
 */

public interface NetworkService {
    @Headers({
            "Content-type: application/json",
    })
    @GET(Config.GET_VIDEO_LIST_URL + "/{skip}/{limit}")
    void getVideoList(@Path("skip") String skip,
                      @Path("limit") String limit,
                      Callback<Response> cb);

    @GET(Config.GET_SEARCHED_VIDEO_LIST_URL )
    void getSearchVideoList(@Query("keyword") String keyword,
                            @Query("limit") String limit,
                            @Query("skip") String skip,
                            Callback<Response> cb);

    @GET(Config.GET_SEARCH_COURSE_LIST )
    void getCourseSearchList(@Query("keyword") String keyword,
                            @Query("limit") String limit,
                            @Query("skip") String skip,
                            Callback<Response> cb);

    @GET(Config.GET_FILTERED_VIDEO_LIST_URL + "/{filterkey}/{skip}/{limit}" )
    void getFilteredVideoList(@Path("filterkey") String filterKey,
                              @Path("skip") String skip,
                              @Path("limit") String limit,
                              Callback<Response> cb);

    @GET(Config.GET_COURSE_LIST_URL)
    void getCourseList(Callback<Response> cb);

    @GET(Config.GET_COURSE_VIDEO_LIST + "/{keyword}/{skip}/{limit}")
    void getCourseVideoList(@Path("keyword") String keyword,
                            @Path("skip") String skip,
                            @Path("limit") String limit,Callback<Response> cb);
}
