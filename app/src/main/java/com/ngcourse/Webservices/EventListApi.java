package com.ngcourse.Webservices;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import com.ngcourse.NetworkCall.NetworkCallResponse;
import com.ngcourse.NetworkCall.NetworkService;
import com.ngcourse.R;
import com.ngcourse.ResponseInterfaces.ResponseEventList;
import com.ngcourse.Settings.Config;
import com.ngcourse.beans.Event;
import com.ngcourse.retrofitAdapter.ConvertInputStream;
import com.ngcourse.retrofitAdapter.RetrofitAdapter;
import com.ngcourse.utilities.AppToast;
import com.ngcourse.utilities.InternetConnection;
import com.ngcourse.utilities.Logger;
import com.ngcourse.utilities.ReferenceWrapper;
import com.ngcourse.utilities.ToneAndVibrate;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by piyush on 18/6/17.
 */

public class EventListApi {
    public static final String API_TAG = EventListApi.class.getSimpleName();
    private FragmentActivity mContext;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public NetworkCallResponse delegateNetworkCall = null;//Call back interface
    public ResponseEventList responseEventList;

    public EventListApi(FragmentActivity mContext) {
        this.mContext = mContext;
        sharedPreferences = ReferenceWrapper.getReferenceProvider(mContext).getSharedPreferences();
    }

    public void getEventListApi(NetworkCallResponse networkCallResponse, ResponseEventList responseEventList){
        this.delegateNetworkCall = networkCallResponse;//Assigning call back interface
        this.responseEventList = responseEventList;
        if (!InternetConnection.isInternetConnected(mContext)) {
            AppToast.showShortToast(mContext, mContext.getResources().getString(R.string.no_internet));
            ToneAndVibrate.errorVibrate(mContext);
            delegateNetworkCall.callResponse(false,API_TAG);
            return;
        }
        NetworkService service = RetrofitAdapter.createService(NetworkService.class, Config.BASE_URL);
        service.getEventList(new Callback<Response>() {

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
        JSONArray jsonObjectResponse = null;
        try {
            JSONArray jsonArray = new JSONArray(result);
            ArrayList<Event> eventList = new ArrayList<>();
            for (int i=0; i<jsonArray.length(); i++){
                Event event = new Event();
                event.setEvent_name(jsonArray.getJSONObject(i).getString("Event_name"));
                event.set_id(jsonArray.getJSONObject(i).getString("_id"));
                event.setEvent_happen(jsonArray.getJSONObject(i).getString("event_happen"));
                eventList.add(event);
            }
            delegateNetworkCall.callResponse(true, API_TAG);
            responseEventList.responseEvents(eventList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
