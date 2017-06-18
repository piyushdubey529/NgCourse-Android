package com.ngcourse.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ngcourse.NetworkCall.NetworkCallResponse;
import com.ngcourse.R;
import com.ngcourse.Webservices.UploadVideoApi;

/**
 * Created by piyush on 17/6/17.
 */

public class FragmentUploadVideo extends Fragment implements View.OnClickListener, NetworkCallResponse{
    private ImageView upload;
    private FragmentActivity mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        return inflater.inflate(R.layout.upload_video_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        setListener();
    }

    private void setListener() {
        upload.setOnClickListener(this);
    }

    private void initView() {
        upload = (ImageView) mContext.findViewById(R.id.upload);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.upload:
                UploadVideoApi uploadVideoApi = new UploadVideoApi(mContext);
                uploadVideoApi.uploadVideoApi(this);
                break;
        }
    }

    @Override
    public void callResponse(Boolean response, String API_TAG) {

    }
}
