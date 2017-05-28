package com.ngcourse.retrofitAdapter;

import android.content.Context;

import com.ngcourse.utilities.AppToast;
import com.ngcourse.R;
import com.squareup.okhttp.OkHttpClient;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by piyush on 6/7/16.
 */
public class RetrofitAdapter {
    private static OkHttpClient client;
    Context context;

    public RetrofitAdapter(Context context) {
        this.context = context;
    }

    public static <object> object createService(Class<object> serviceClass, String baseUrl) {
        client = new OkHttpClient();
        CookieHandler.setDefault(new CookieManager());
        client.setConnectTimeout(30 * 1000, TimeUnit.MILLISECONDS);
        client.setReadTimeout(30 * 1000, TimeUnit.MILLISECONDS);
        OkClient serviceClient = new OkClient(client);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(baseUrl)
                .setClient(serviceClient)
                .build();
        return restAdapter.create(serviceClass);
    }

    public static <object> object createPersistantService(Class<object> serviceClass, String baseUrl, boolean newClient) {
        if(newClient){
            client = new OkHttpClient();
        }
        client.setConnectTimeout(30, TimeUnit.SECONDS);
        client.setReadTimeout(30, TimeUnit.SECONDS);
        OkClient serviceClient = new OkClient(client);
    RestAdapter restAdapter = new RestAdapter.Builder()
            .setEndpoint(baseUrl)
            .setClient(serviceClient)
            .build();
    return restAdapter.create(serviceClass);
}

    public void retrofitErrorHandler(RetrofitError error) {

        if (error == null) {
            AppToast.showLongToast(context, context.getResources().getString(R.string.unknown_error_kind));
            return;
        }
        switch (error.getKind()) {
            case NETWORK:
                AppToast.showLongToast(context, context.getResources().getString(R.string.network_error));
                break;
            case UNEXPECTED:
                AppToast.showLongToast(context, context.getResources().getString(R.string.unexpected_error));
            case HTTP:
                Response response = error.getResponse();
                if (response != null) {
                    switch (response.getStatus()) {
                        case 400:
                            AppToast.showLongToast(context, context.getResources().getString(R.string.an_error_occured));
                            break;
                        case 401:
                            AppToast.showLongToast(context, context.getResources().getString(R.string.unauthorized_user));
                            break;
                        case 403:
                            AppToast.showLongToast(context, context.getResources().getString(R.string.forbidden));
                            break;
                        case 404:
                            AppToast.showShortToast(context, context.getResources().getString(R.string.server_unable_to_process));
                            break;
                        case 408:
                            AppToast.showShortToast(context, context.getResources().getString(R.string.toastResponseTimeout));
                            break;
                        case 500:
                            AppToast.showShortToast(context, context.getResources().getString(R.string.internal_server_error));
                            break;
                        case 600:
                            AppToast.showShortToast(context, context.getResources().getString(R.string.toastHostError));
                            break;
                        case 601:
                            AppToast.showShortToast(context, context.getResources().getString(R.string.toastConnectionFailed));
                            break;
                        case 503:
                            AppToast.showShortToast(context, context.getResources().getString(R.string.server_unable_to_process));
                            break;
                        default:
                            AppToast.showShortToast(context, context.getResources().getString(R.string.toastServerFailedRespond));
                    }
                    break;
                }
            default:
                AppToast.showLongToast(context, context.getResources().getString(R.string.unknown_error_kind) + " " + error.getKind());
        }
    }
}